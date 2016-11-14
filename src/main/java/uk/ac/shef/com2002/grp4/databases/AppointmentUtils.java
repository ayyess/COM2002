/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

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

	public static void insertAppointment(LocalDate date, String practioner, long patient_id, LocalTime start, Duration duration) {
		java.sql.Date sqlDate = java.sql.Date.valueOf(date);
		java.sql.Time sqlStartTime = java.sql.Time.valueOf(start);

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
