/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.databases;

import uk.ac.shef.com2002.grp4.common.data.Appointment;
import uk.ac.shef.com2002.grp4.common.UserFacingException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Used to control database interaction.
 * Specifically the appointments table
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   1/11/2016
 */
public class AppointmentUtils {

	/**
	 * This gets the appointments that a particular patient has booked.
	 *
	 * @param id - the id of the patient
	 * @return an ArrayList of Appointment(s)
	 */
    public static List<Appointment> getAppointmentByPatientID(int id) {
        List<Appointment> appointments = new ArrayList<>();
        return ConnectionManager.withStatement("SELECT * FROM appointments WHERE id=?",(stmt)->{
            stmt.setInt(1,id);
            ResultSet res = stmt.executeQuery();
            while(res.next()){
                appointments.add(new Appointment(res.getDate(1).toLocalDate(), res.getTime(4).toLocalTime(),res.getInt(5), res.getString(2), res.getLong(3), res.getBoolean(6)));
            }
            return appointments;
        });
    }

	/**
	 * This gets the appointments on a particular day.
	 *
	 * @param date - the date that appointments are needed for
	 * @return an ArrayList of Appointment(s)
	 */
	public static List<Appointment> getAppointmentByDate(LocalDate date) {
		java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        List<Appointment> appointments = new ArrayList<>();
        return ConnectionManager.withStatement("SELECT * FROM appointments WHERE date=?",(stmt)-> {
            stmt.setDate(1, sqlDate);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                appointments.add(new Appointment(res.getDate(1).toLocalDate(), res.getTime(4).toLocalTime(),res.getInt(5), res.getString(2), res.getLong(3), res.getBoolean(6)));
            }
            return appointments;
        });
    }

	/**
	 * This gets all appointments that fall within a certain date range.
	 *
	 * @param start - the startDate
	 * @param end - the endDate
	 * @return an ArrayList of Appointment(s)
	 */
	public static List<Appointment> getAppointmentByDateRange(LocalDate start, LocalDate end) {
		java.sql.Date sqlStart = java.sql.Date.valueOf(start);
		java.sql.Date sqlEnd = java.sql.Date.valueOf(end);
		List<Appointment> appointments = new ArrayList<>();
		return ConnectionManager.withStatement("SELECT * FROM appointments WHERE date BETWEEN ? AND ?",(stmt)-> {
			stmt.setDate(1, sqlStart);
			stmt.setDate(2, sqlEnd);
			ResultSet res = stmt.executeQuery();
			while (res.next()) {
				appointments.add(new Appointment(res.getDate(1).toLocalDate(), res.getTime(4).toLocalTime(),res.getInt(5), res.getString(2), res.getLong(3), res.getBoolean(6)));
			}
			return appointments;
		});
	}

	/**
	 * This finds whether two appointments overlap.
	 *
	 * @param start1 - startTime of Appointment 1
	 * @param end1 - endTime of Appointment 1
	 * @param start2 - startTime of Appointment 2
	 * @param end2 - endTime of Appointment 2
	 * @return a Boolean, True if they overlap
	 */
	public static boolean isOverlapping(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
		/*
		  -----        ------    -----     -----
            ----     ---------    --         ------
		 */
		return
			end1.isAfter(start2) && end1.isBefore(end2) ||
			start1.isAfter(start2) && end1.isBefore(end2) ||
			start1.isBefore(start2) && end1.isAfter(end2) ||
			start1.isAfter(start2) && start1.isBefore(end2) ||
			end1.equals(end2);
	}

	/**
	 * /**
	 * This gets all appointments that fall within a certain time range on a particular day.
	 *
	 * @param day - the date
	 * @param start - the startTime
	 * @param end - the endTime
	 * @return an ArrayList of Appointment(s)
	 */
	public static List<Appointment> getAppointmentByTimeRange(LocalDate day, LocalTime start, LocalTime end) {
		java.sql.Date sqlDate = java.sql.Date.valueOf(day);
		List<Appointment> appointments = new ArrayList<>();
		return ConnectionManager.withStatement("SELECT * FROM appointments WHERE date = ?",(stmt)-> {
			stmt.setDate(1, sqlDate);
			ResultSet res = stmt.executeQuery();
			while (res.next()) {
				appointments.add(new Appointment(res.getDate(1).toLocalDate(), res.getTime(4).toLocalTime(), res.getInt(5), res.getString(2), res.getLong(3), res.getBoolean(6)));
			}
			return appointments.stream()
				.filter(a -> isOverlapping(start, end, a.getStart(), a.getEnd()))
				.collect(Collectors.toList());
			});
	}

	/**
	 * This inserts a new Appointment into the database.
	 *
	 * @param date - date of the appointment
	 * @param partner - the partner
	 * @param patient_id - the id of the patient
	 * @param start - the startTime of the appointment
	 * @param duration - the duration of the appointment
	 */
	public static void insertAppointment(LocalDate date, String partner, long patient_id, LocalTime start, Duration duration, boolean complete) {
		java.sql.Date sqlDate = java.sql.Date.valueOf(date);
		java.sql.Time sqlStartTime = java.sql.Time.valueOf(start);

		LocalTime end = start.plus(duration);
		if (start.isBefore(LocalTime.of(9, 0)) || end.isAfter(LocalTime.of(17,0))) {
			throw new UserFacingException("Appointment outside of appointment hours.");
		}
		
		List<Appointment> appointmentsAtTime = getAppointmentByTimeRange(date, start, end);
		if (appointmentsAtTime.stream()
			.filter(p -> p.getPartner().equals(partner))
			.count() > 0) {
			throw new UserFacingException("The partner already has an appointment at this time.");
		}
		if (appointmentsAtTime.stream()
			.filter(p -> p.getPatientId() == patient_id)
			.count() > 0) {
			throw new UserFacingException("The patient already has an appointment at this time.");
		}

		ConnectionManager.withStatement("INSERT INTO appointments VALUES (?,?,?,?,?,?)",(stmt)-> {
            stmt.setDate(1, sqlDate);
            stmt.setString(2, partner);
            stmt.setLong(3, patient_id);
            stmt.setTime(4, sqlStartTime);
            stmt.setInt(5, (int)duration.toMinutes());
            stmt.setBoolean(6, complete);
            stmt.executeUpdate();
            return null;
        });
    }

	/**
	 * This deletes a particular Appointment from the database.
	 *
	 * @param appointment - an Appointment object
	 */
	public static void deleteAppointment(Appointment appointment) {
		java.sql.Date sqlDate = java.sql.Date.valueOf(appointment.getDate());
		java.sql.Time sqlStartTime = java.sql.Time.valueOf(appointment.getStart());
		ConnectionManager.withStatement("DELETE FROM appointments WHERE date=? AND partner=? AND start=?",(stmt)-> {
				stmt.setDate(1, sqlDate);
				stmt.setString(2, appointment.getPartner());
				stmt.setTime(3, sqlStartTime);
				stmt.executeUpdate();
			return null;
		});
	}
    
	public static void updateCompleteAppointment(Appointment appointment, boolean complete) {
		appointment.setComplete(complete);
		ConnectionManager.withStatement("UPDATE appointments SET complete=? WHERE date=? AND partner=? AND start=?",(stmt)-> {
			stmt.setBoolean(1, complete);
			stmt.setDate(2, Date.valueOf(appointment.getDate()));
			stmt.setString(3, appointment.getPartner());
			stmt.setTime(4, Time.valueOf(appointment.getStart()));
			stmt.executeUpdate();
		return null;
	});
	}
	
}
