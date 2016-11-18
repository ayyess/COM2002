/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.databases;

import uk.ac.shef.com2002.grp4.common.data.Patient;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.time.*;

/**
 * Used to control database interaction.
 * Specifically the patients table
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   1/11/2016
 */
public class PatientUtils {

	/**
	 * This find patients by their first name.
	 *
	 * @param firstName - any name
	 * @return an ArrayList of patients with the exact first name
	 */
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

	/**
	 * This find patients with a similar first name to the parameter provided.
	 *
	 * @param firstName - any name
	 * @return an ArrayList of patients with the similar first names
	 */
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

	/**
	 * This finds a patient by their patient_id.
	 *
	 * @param id - a patient_id
	 * @return - a new Patient object
	 */
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

	/**
	 * This updates a patients details by their patient_id
	 *
	 * @param id - a patient_id
	 * @param title - new title
	 * @param forename - new first name
	 * @param surname - new surname
	 * @param phone - new contact number
	 */
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

	/**
	 * This inserts a new patient into the database.
	 *
	 * @param title - title of the patient
	 * @param forename - first name of patient
	 * @param surname - surname of patient
	 * @param date - date of birth of patient
	 * @param phone - contact number for patient
	 * @param addressId - address ID of the patient's address
	 * @return the patient_id generated in the database
	 */
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

	/**
	 * This deletes a patient by the patient_id.
	 *
	 * @param id - the id of a patient to be deleted
	 */
	public static void deleteByID(long id) {
		ConnectionManager.withStatement("DELETE FROM patients WHERE id=?",(stmt)-> {
			stmt.setLong(1, id);
			stmt.executeUpdate();
			return null;
		});
	}
}
