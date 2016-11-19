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
     * This gets a treatment that a patient is undergoing.
     *
     * @param p - a patient_id
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
    
    public static Treatment[] getAppointmentTreatments(Appointment a) {
        ArrayList<Treatment> treatments = new ArrayList<Treatment>();
        return ConnectionManager.withStatement(
        		"SELECT t.* FROM treatment_applications t " + 
        		"INNER JOIN appointments a ON a.date=t.appointment_date " +
        		"WHERE a.practitioner=? AND a.start=? AND a.patient_id=?;",
        		(stmt)-> {
            stmt.setString(1, a.getPractitioner());
            stmt.setTime(2, Time.valueOf(a.getStart()));
            stmt.setLong(3, a.getPatientId());
        	ResultSet res = stmt.executeQuery();
            while (res.next()) {
                treatments.add(new Treatment(res.getString(1), res.getInt(2), res.getString(3)));
            }
            Treatment[] tp = new Treatment[treatments.size()];
            treatments.toArray(tp);
            return tp;
        });
    }
	
    public static void insertTreatmentApplication(Treatment treatment, Appointment appointment, Partner p) {
    	insertTreatmentApplication(treatment.getName(), appointment.getDate(), appointment.getStart(), p);
    }
    
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
}
