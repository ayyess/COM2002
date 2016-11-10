package uk.ac.shef.com2002.grp4.data;

import java.util.Optional;

public class Patient {

	private Optional<Integer> id;
	private String title;
	private String forename;
	private String surname;
	private String dob;
	private String phoneNumber;
	
	private Address address;
	
	//TODO Add creation from db?
	
	public Patient(String title, String forename, String surname, String dob, String phoneNumber) {
		this(Optional.<Integer>empty(),title,forename,surname,dob,phoneNumber);
		//TODO Fetch address
	}

	public Patient(int id, String title, String forename, String surname, String dob, String phoneNumber) {
		this(Optional.of(id),title,forename,surname,dob,phoneNumber);
		this.id = Optional.of(id);
		//TODO Fetch address
	}

	public Patient(Optional<Integer> id, String title, String forename, String surname, String dob, String phoneNumber) {
		this.title = title;
		this.forename = forename;
		this.surname = surname;
		this.dob = dob;
		this.phoneNumber = phoneNumber;
		this.id = id;
		//TODO Fetch address
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

	public int getID() {
		return id.get();
	}
}
