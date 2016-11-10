package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Appointment;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class AppointmentUtils {

    public static List<Appointment> getAppointmentByPatientID(int id) {
        List<Appointment> appointments = new ArrayList<>();
        return ConnectionManager.withStatement("SELECT * FROM appointment WHERE id=?",(stmt)->{
            stmt.setInt(1,id);
            ResultSet res = stmt.executeQuery();
            while(res.next()){
                appointments.add(new Appointment(res.getDate(1), res.getTime(4),res.getTime(5), res.getString(2)));
            }
            return appointments;
        });
    }
    
    public static List<Appointment> getAppointmentByDate(Date date) {
        List<Appointment> appointments = new ArrayList<>();
        return ConnectionManager.withStatement("SELECT * FROM appointment WHERE date=?",(stmt)-> {
            stmt.setDate(1, date);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                appointments.add(new Appointment(res.getDate(1), res.getTime(4),res.getTime(5), res.getString(2)));
            }
            return appointments;
        });
    }

	public static void insertAppointment(Date date, String practioner, int patient_id, Time start, Time duration) {
        ConnectionManager.withStatement("INSERT INTO appointment VALUES ?,?,?,?,?",(stmt)-> {
            java.sql.Date dob = new java.sql.Date(date.getTime());
            stmt.setDate(1, date);
            stmt.setString(2, practioner);
            stmt.setInt(3, patient_id);
            stmt.setTime(4, start);
            stmt.setInt(5, (int)duration.getTime());
            stmt.executeUpdate();
            return null;
        });
    }

    
}
