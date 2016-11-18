/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Appointment;
import uk.ac.shef.com2002.grp4.UserFacingException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class AppointmentUtils {

    public static List<Appointment> getAppointmentByPatientID(int id) {
        List<Appointment> appointments = new ArrayList<>();
        return ConnectionManager.withStatement("SELECT * FROM appointments WHERE id=?",(stmt)->{
            stmt.setInt(1,id);
            ResultSet res = stmt.executeQuery();
            while(res.next()){
                appointments.add(new Appointment(res.getDate(1).toLocalDate(), res.getTime(4).toLocalTime(),res.getInt(5), res.getString(2), res.getLong(3)));
            }
            return appointments;
        });
    }
    
    public static List<Appointment> getAppointmentByDate(LocalDate date) {
		java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        List<Appointment> appointments = new ArrayList<>();
        return ConnectionManager.withStatement("SELECT * FROM appointments WHERE date=?",(stmt)-> {
            stmt.setDate(1, sqlDate);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                appointments.add(new Appointment(res.getDate(1).toLocalDate(), res.getTime(4).toLocalTime(),res.getInt(5), res.getString(2), res.getLong(3)));
            }
            return appointments;
        });
    }

	public static List<Appointment> getAppointmentByDateRange(LocalDate start, LocalDate end) {
		java.sql.Date sqlStart = java.sql.Date.valueOf(start);
		java.sql.Date sqlEnd = java.sql.Date.valueOf(end);
		List<Appointment> appointments = new ArrayList<>();
		return ConnectionManager.withStatement("SELECT * FROM appointments WHERE date BETWEEN ? AND ?",(stmt)-> {
			stmt.setDate(1, sqlStart);
			stmt.setDate(2, sqlEnd);
			ResultSet res = stmt.executeQuery();
			while (res.next()) {
				appointments.add(new Appointment(res.getDate(1).toLocalDate(), res.getTime(4).toLocalTime(),res.getInt(5), res.getString(2), res.getLong(3)));
			}
			return appointments;
		});
	}

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

	public static List<Appointment> getAppointmentByTimeRange(LocalDate day, LocalTime start, LocalTime end) {
		java.sql.Date sqlDate = java.sql.Date.valueOf(day);
		List<Appointment> appointments = new ArrayList<>();
		return ConnectionManager.withStatement("SELECT * FROM appointments WHERE date = ?",(stmt)-> {
			stmt.setDate(1, sqlDate);
			ResultSet res = stmt.executeQuery();
			while (res.next()) {
				appointments.add(new Appointment(res.getDate(1).toLocalDate(), res.getTime(4).toLocalTime(),res.getInt(5), res.getString(2), res.getLong(3)));
			}
			return appointments.stream()
				.filter(a -> isOverlapping(start, end, a.getStart(), a.getEnd()))
				.collect(Collectors.toList());
			});
	}

	public static void insertAppointment(LocalDate date, String practioner, long patient_id, LocalTime start, Duration duration) {
		java.sql.Date sqlDate = java.sql.Date.valueOf(date);
		java.sql.Time sqlStartTime = java.sql.Time.valueOf(start);

		LocalTime end = start.plus(duration);
		if (start.isBefore(LocalTime.of(9, 0)) || end.isAfter(LocalTime.of(17,0))) {
			throw new UserFacingException("Appointment outside of appointment hours.");
		}
		if (getAppointmentByTimeRange(date, start, end).stream()
			.filter(p -> p.getPractitioner().equals(practioner))
			.count() > 0) {
			throw new UserFacingException("Appointment overlap.");
		}

		ConnectionManager.withStatement("INSERT INTO appointments VALUES (?,?,?,?,?)",(stmt)-> {
            stmt.setDate(1, sqlDate);
            stmt.setString(2, practioner);
            stmt.setLong(3, patient_id);
            stmt.setTime(4, sqlStartTime);
            stmt.setInt(5, (int)duration.toMinutes());
            stmt.executeUpdate();
            return null;
        });
    }

	public static void deleteAppointment(Appointment appointment) {
		java.sql.Date sqlDate = java.sql.Date.valueOf(appointment.getDate());
		java.sql.Time sqlStartTime = java.sql.Time.valueOf(appointment.getStart());
		ConnectionManager.withStatement("DELETE FROM appointments WHERE date=? AND practitioner=? AND start=?",(stmt)-> {
				stmt.setDate(1, sqlDate);
				stmt.setString(2, appointment.getPractitioner());
				stmt.setTime(3, sqlStartTime);
				stmt.executeUpdate();
			return null;
		});
	}
    
}
