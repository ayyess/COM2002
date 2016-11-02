package uk.ac.shef.com2002.grp4.data;

import java.util.Calendar;

public class Patient {

	private String title;
	private String forename;
	private String surname;
	private String dob;
	private String phoneNumber;
	
	private Address address;
	
	//TODO Add creation from db?
	
	public Patient(String title, String forename, String surname, String dob, String phoneNumber) {
		this.title = title;
		this.forename = forename;
		this.surname = surname;
		this.dob = dob;
		this.phoneNumber = phoneNumber;
		//TODO Fetch address
	}
	
	public String getName() {
		return title + " " + forename + " " + surname;
	}
	
	public String toString() {
		return getName() + " " + dob + " " + phoneNumber;
	}
	
}
