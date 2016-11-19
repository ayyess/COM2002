package uk.ac.shef.com2002.grp4.common.databases;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import uk.ac.shef.com2002.grp4.common.Partner;
import uk.ac.shef.com2002.grp4.common.data.Appointment;
import uk.ac.shef.com2002.grp4.common.data.Patient;
import uk.ac.shef.com2002.grp4.common.data.PatientPlan;
import uk.ac.shef.com2002.grp4.common.data.Treatment;

public class TreatmentApplicationUtils {

	/**
     * This gets all treatments that a patient has had.
     *
     * @param p - The patient to get the treatments for
     * @return an Array of Treatment
     */
    public static Treatment[] getPatientTreatments(Patient p) {
        ArrayList<Treatment> treatments = new ArrayList<Treatment>();
        return ConnectionManager.withStatement(
        		"SELECT t.* FROM treatment_applications t " + 
        		"INNER JOIN appointments a ON a.date=t.appointment_date " + 
        		"INNER JOIN patients p ON p.id=a.patient_id " + 
        		"WHERE p.id=?;",
        		(stmt)-> {
            stmt.setLong(1, p.getID());
        	ResultSet res = stmt.executeQuery();
            while (res.next()) {
                treatments.add(new Treatment(res.getString(1), res.getInt(2), res.getString(3)));
            }
            Treatment[] tp = new Treatment[treatments.size()];
            treatments.toArray(tp);
            return tp;
        });
    }
    
    /**
     * Get all treatments for a given appointment
     * @param a - The appointment to fetch the treatments for
     * @return Array of treatments that the given appointment has
     */
    public static Treatment[] getAppointmentTreatments(Appointment a) {
        ArrayList<Treatment> treatments = new ArrayList<Treatment>();
        return ConnectionManager.withStatement(
        		"SELECT t.* FROM treatments t " + 
				"INNER JOIN treatment_applications ta ON ta.treatment_name=t.name " +
        		"INNER JOIN appointments a ON a.date=ta.appointment_date " +
        		"WHERE a.partner=? AND a.start=? AND a.patient_id=?;",
        		(stmt)-> {
            stmt.setString(1, a.getPartner());
            stmt.setTime(2, Time.valueOf(a.getStart()));
            stmt.setLong(3, a.getPatientId());
        	ResultSet res = stmt.executeQuery();
            while (res.next()) {
                treatments.add(new Treatment(res.getString(1), res.getInt(2), res.getString(3)));
                System.out.println(res.getString(1) + " " + res.getInt(2) + " " + res.getString(3));
            }
            Treatment[] tp = new Treatment[treatments.size()];
            treatments.toArray(tp);
            return tp;
        });
    }
	
    /**
     * Creates a new entry in the database for a treatment application.
     * @param treatment - Treatment to link with the appointment
     * @param appointment - Appointment to link the treatment to
     * @param p - The partner that the appointment is with
     */
    public static void insertTreatmentApplication(Treatment treatment, Appointment appointment, Partner p) {
    	insertTreatmentApplication(treatment.getName(), appointment.getDate(), appointment.getStart(), p);
    }
    
    /**
     * Creates a new entry in the database for a treatment application.
     * @param treatmentName - The name of the treatment to link
     * @param date - The date of the appointment
     * @param time - The start time of the appointment 
     * @param p - The partner that the appointment is with
     */
	public static void insertTreatmentApplication(String treatmentName, LocalDate date, LocalTime time, Partner p) {
		ConnectionManager.withStatement("INSERT INTO treatment_applications VALUES (?,?,?,?)",(stmt)-> {
			Date appointmentDate = Date.valueOf(date);
			Time appointmentTime = Time.valueOf(time);
			String partner = p.toString();
			stmt.setString(1, treatmentName);
			stmt.setDate(2, appointmentDate);
			stmt.setTime(3, appointmentTime);
			stmt.setString(4, partner);
			stmt.executeUpdate();
			return null;
		});
	}
	
	/**
	 * Deletes a treatment application from the table
	 * @param treatmentName - The name of the treatment
	 * @param date - The date of the appointment
	 * @param time - The start time of the appointment
	 * @param p - The partner that the appointment is with
	 */
	public static void delete(String treatmentName, LocalDate date, LocalTime time, Partner p) {
		ConnectionManager.withStatement(
				"DELETE FROM treatment_applications WHERE " +
				"treatment_name=? AND appointment_date=? AND appointment_time=? AND partner=?",(stmt)-> {
			stmt.setString(1, treatmentName);
			stmt.setDate(2, Date.valueOf(date));
			stmt.setTime(3, Time.valueOf(time));
			stmt.setString(4, p.toString());
			stmt.executeUpdate();
			return null;
		});
	}
	
	/**
	 * Replaces a set of treatments with a new set of treatments for an appointment
	 * @param oldTreatments - The list of treatments to remove
	 * @param newTreatments - The list of treatments to add to the appointment
	 * @param date - The date of the appointment
	 * @param time - The start time of the appointment
	 * @param p - The partner that the appointment is with
	 */
	public static void replace(Treatment[] oldTreatments, Treatment[] newTreatments, LocalDate date, LocalTime time, Partner p) {
		for (Treatment t : oldTreatments) {
			delete(t.getName(), date, time, p);
		}
		for (Treatment t : newTreatments) {
			insertTreatmentApplication(t.getName(), date, time, p);
		}
	}
	
}
