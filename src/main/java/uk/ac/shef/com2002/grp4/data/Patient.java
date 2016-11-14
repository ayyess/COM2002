package uk.ac.shef.com2002.grp4.data;

import uk.ac.shef.com2002.grp4.databases.ConnectionManager;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

public class Patient implements Saveable {

	private Optional<Long> id;
	private Optional<Address> address = Optional.empty();
	private String title;
	private String forename;
	private String surname;
	private LocalDate dob;
	private String phoneNumber;

	//TODO Add creation from db?
	
	public Patient(String title, String forename, String surname, LocalDate dob, String phoneNumber) {
		this(Optional.empty(),title,forename,surname,dob,phoneNumber,Optional.empty());
		//TODO Fetch address
	}

	public Patient(Optional<Long> id, String title, String forename, String surname, LocalDate dob, String phoneNumber, Optional<Address> address) {
		this.id = id;
		this.title = title;
		this.forename = forename;
		this.surname = surname;
		this.dob = dob;
		this.phoneNumber = phoneNumber;
		this.address = address;
	}

	public Patient(String title, String forename, String surname, LocalDate dob, String phoneNumber, Optional<Address> address) {
		this(Optional.empty(),title,forename,surname,dob,phoneNumber,address);
	}

	public Patient(long id, String title, String forename, String surname, LocalDate dob, String phoneNumber) {
		this(Optional.of(id),title,forename,surname,dob,phoneNumber);
		//TODO Fetch address
	}

	public Patient(Optional<Long> id, String title, String forename, String surname, LocalDate dob, String phoneNumber) {
		this(id,title,forename,surname,dob,phoneNumber,Optional.empty());
		//TODO Fetch address
	}

	public Address getAddress() {
		return address.orElse(null);
	}

	public void setAddress(Address address) {
		this.address = Optional.ofNullable(address);
	}
//TODO Add creation from db?


	public static List<Patient> findByFirstName(String firstName) {
		return ConnectionManager.withStatement("SELECT * FROM patients WHERE first_name=?",(stmt)->{
			stmt.setString(1,firstName);
			ResultSet res = stmt.executeQuery();
			List<Patient> patients = new ArrayList<>();
			while(res.next()){
				patients.add(new Patient(res.getLong(1),res.getString(2),res.getString(3),res.getString(4),res.getDate(5).toLocalDate(),res.getString(6)));
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
				patients.add(new Patient(res.getLong(1),res.getString(2),res.getString(3),res.getString(4), res.getDate(5).toLocalDate(),res.getString(6)));
			}
			return patients;
		});
	}

	public static Patient getByID(int id) {
		return ConnectionManager.withStatement("SELECT * FROM patients WHERE id=?",(stmt)-> {
			stmt.setInt(1, id);
			ResultSet res = stmt.executeQuery();
			return new Patient(res.getString(2), res.getString(3), res.getString(4), res.getDate(5).toLocalDate(), res.getString(6));
		});
	}

	@Override
	public void update() {
		ConnectionManager.withStatement("UPDATE patients SET title=?, first_name=?, surname=?, phone_number=? WHERE id=?",(stmt)-> {
			stmt.setString(1, title);
			stmt.setString(2, forename);
			stmt.setString(3, surname);
			stmt.setString(4, phoneNumber);
			stmt.setLong(5, id.get());
			stmt.executeUpdate();
			return null;
		});
	}

	@Override
	public void insert() {
		ConnectionManager.withStatement("INSERT INTO patients VALUES (DEFAULT,?,?,?,?,?,?)",(stmt)-> {
			stmt.setString(1, title);
			stmt.setString(2, forename);
			stmt.setString(3, surname);
			stmt.setDate(4, java.sql.Date.valueOf(dob));
			stmt.setString(5, phoneNumber);
			stmt.setLong(6, address.map(Address::getId).get());
			stmt.executeUpdate();
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			generatedKeys.next();
			id = Optional.of(generatedKeys.getLong(1));
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

	public LocalDate getDob() {
		return dob;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public long getID() {
		return id.get();
	}

	@Override
	public boolean isInDb() {
		return id.isPresent();
	}
}
