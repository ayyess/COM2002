package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Appointment;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.sql.ResultSet;
import java.sql.Date;
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
                appointments.add(new Appointment(res.getDate(1).toLocalDate(), res.getTime(4).toLocalTime(),res.getInt(5), res.getString(2)));
            }
            return appointments;
        });
    }
    
    public static List<Appointment> getAppointmentByDate(LocalDate date) {
		java.sql.Date sqlDate = java.sql.Date.valueOf(date);
        List<Appointment> appointments = new ArrayList<>();
        return ConnectionManager.withStatement("SELECT * FROM appointment WHERE date=?",(stmt)-> {
            stmt.setDate(1, sqlDate);
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                appointments.add(new Appointment(res.getDate(1).toLocalDate(), res.getTime(4).toLocalTime(),res.getInt(5), res.getString(2)));
            }
            return appointments;
        });
    }

	public static void insertAppointment(LocalDate date, String practioner, int patient_id, LocalTime start, Duration duration) {
		java.sql.Date sqlDate = java.sql.Date.valueOf(date);
		java.sql.Time sqlStartTime = java.sql.Time.valueOf(start);

		ConnectionManager.withStatement("INSERT INTO appointment VALUES (?,?,?,?,?)",(stmt)-> {
            stmt.setDate(1, sqlDate);
            stmt.setString(2, practioner);
            stmt.setInt(3, patient_id);
            stmt.setTime(4, sqlStartTime);
            stmt.setInt(5, (int)duration.toMinutes());
            stmt.executeUpdate();
            return null;
        });
    }

    
}
