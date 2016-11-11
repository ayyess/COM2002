package uk.ac.shef.com2002.grp4.data;

import uk.ac.shef.com2002.grp4.databases.ConnectionManager;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Patient {

	private Optional<Long> id;
	private String title;
	private String forename;
	private String surname;
	private String dob;
	private String phoneNumber;
	
	private Address address;
	
	//TODO Add creation from db?
	
	public Patient(String title, String forename, String surname, String dob, String phoneNumber) {
		this(Optional.<Long>empty(),title,forename,surname,dob,phoneNumber);
		//TODO Fetch address
	}

	public Patient(long id, String title, String forename, String surname, String dob, String phoneNumber) {
		this(Optional.of(id),title,forename,surname,dob,phoneNumber);
		this.id = Optional.of(id);
		//TODO Fetch address
	}

	public Patient(Optional<Long> id, String title, String forename, String surname, String dob, String phoneNumber) {
		this.title = title;
		this.forename = forename;
		this.surname = surname;
		this.dob = dob;
		this.phoneNumber = phoneNumber;
		this.id = id;
		//TODO Fetch address
	}

	public static List<Patient> findByFirstName(String firstName) {
		return ConnectionManager.withStatement("SELECT * FROM patients WHERE first_name=?",(stmt)->{
			stmt.setString(1,firstName);
			ResultSet res = stmt.executeQuery();
			List<Patient> patients = new ArrayList<>();
			while(res.next()){
				patients.add(new Patient(res.getInt(1),res.getString(2),res.getString(3),res.getString(4), res.getString(5),res.getString(6)));
			}
			return patients;
		});
	}

	public static List<Patient> fuzzyFindByFirstName(String firstName) {
		return ConnectionManager.withStatement("SELECT * FROM patients WHERE first_name LIKE ?",(stmt)->{
			stmt.setString(1,"%"+firstName+"%");
			ResultSet res = stmt.executeQuery();
			List<Patient> patients = new ArrayList<>();
			while(res.next()){
				patients.add(new Patient(res.getInt(1),res.getString(2),res.getString(3),res.getString(4), res.getString(5),res.getString(6)));
			}
			return patients;
		});
	}

	public static Patient getByID(int id) {
		return ConnectionManager.withStatement("SELECT * FROM patients WHERE id=?",(stmt)-> {
			stmt.setInt(1, id);
			ResultSet res = stmt.executeQuery();
			return new Patient(res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6));
		});
	}

	public static void updateByID(int id, String title, String forename, String surname, int phone) {
		ConnectionManager.withStatement("UPDATE patients SET title=?, first_name=?, surname=?, phone_number=? WHERE id=?",(stmt)-> {
			stmt.setString(1, title);
			stmt.setString(2, forename);
			stmt.setString(3, surname);
			stmt.setInt(4, phone);
			stmt.setInt(5, id);
			stmt.executeUpdate();
			return null;
		});
	}


	public static void insert(String title, String forename, String surname, LocalDate date, String phone, long addressId) {
		ConnectionManager.withStatement("INSERT INTO patients VALUES (DEFAULT,?,?,?,?,?,?)",(stmt)-> {
			java.sql.Date dob = java.sql.Date.valueOf(date);
			stmt.setString(1, title);
			stmt.setString(2, forename);
			stmt.setString(3, surname);
			stmt.setDate(4, dob);
			stmt.setString(5, phone);
			stmt.setLong(6, addressId);
			stmt.executeUpdate();
			return null;
		});
	}
	
	public String getName() {
		return title + " " + forename + " " + surname;
	}
	
	public String toString() {
		return getName() + " " + dob + " " + phoneNumber;
	}

	public String getTitle() {
		return title;
	}

	public String getForename() {
		return forename;
	}

	public String getSurname() {
		return surname;
	}

	public String getDob() {
		return dob;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public long getID() {
		return id.get();
	}
}
