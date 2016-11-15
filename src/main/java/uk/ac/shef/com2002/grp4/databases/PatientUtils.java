/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Patient;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.time.*;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class PatientUtils {

	public static List<Patient> findPatientByFirstName(String firstName) {
		return ConnectionManager.withStatement("SELECT * FROM patients WHERE first_name=?",(stmt)->{
			stmt.setString(1,firstName);
			ResultSet res = stmt.executeQuery();
			List<Patient> patients = new ArrayList<>();
			while(res.next()){
				patients.add(new Patient(res.getLong(1),res.getString(2),res.getString(3),res.getString(4), res.getDate(5).toLocalDate(),res.getString(6),res.getLong(7)));
			}
			return patients;
		});
	}

	public static List<Patient> fuzzyFindPatientByFirstName(String firstName) {
		return ConnectionManager.withStatement("SELECT * FROM patients WHERE first_name LIKE ?",(stmt)->{
			stmt.setString(1,"%"+firstName+"%");
			ResultSet res = stmt.executeQuery();
			List<Patient> patients = new ArrayList<>();
			while(res.next()){
				patients.add(new Patient(res.getLong(1),res.getString(2),res.getString(3),res.getString(4), res.getDate(5).toLocalDate(),res.getString(6),res.getLong(7)));
			}
			return patients;
		});
	}

	public static Patient getPatientByID(long id) {
		return ConnectionManager.withStatement("SELECT * FROM patients WHERE id=?",(stmt)-> {
			stmt.setLong(1, id);
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				return new Patient(res.getLong(1),res.getString(2), res.getString(3), res.getString(4), res.getDate(5).toLocalDate(), res.getString(6),res.getLong(7));
			} else {
				return null;
			}
		});
	}

	public static void updatePatientByID(long id, String title, String forename, String surname, String phone) {
		ConnectionManager.withStatement("UPDATE patients SET title=?, first_name=?, surname=?, phone_number=? WHERE id=?",(stmt)-> {
			stmt.setString(1, title);
			stmt.setString(2, forename);
			stmt.setString(3, surname);
			stmt.setString(4, phone);
			stmt.setLong(5, id);
			stmt.executeUpdate();
			return null;
		});
	}


	public static long insertPatient(String title, String forename, String surname, LocalDate date, String phone, long addressId) {
		return ConnectionManager.withStatement("INSERT INTO patients VALUES (DEFAULT,?,?,?,?,?,?)",(stmt)-> {
			java.sql.Date dob = java.sql.Date.valueOf(date);
			stmt.setString(1, title);
			stmt.setString(2, forename);
			stmt.setString(3, surname);
			stmt.setDate(4, dob);
			stmt.setString(5, phone);
			stmt.setLong(6, addressId);
			stmt.executeUpdate();
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			generatedKeys.next();
			return generatedKeys.getLong(1);
		});
	}


}
