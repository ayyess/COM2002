package uk.ac.shef.com2002.grp4.data;

import uk.ac.shef.com2002.grp4.databases.PatientUtils;

import java.time.LocalDate;
import java.util.Optional;

public class Patient {

	private Optional<Long> id;
	private String title;
	private String forename;
	private String surname;
	private LocalDate dob;
	private String phoneNumber;
	
	private Address address;
	
	//TODO Add creation from db?
	
	public Patient(String title, String forename, String surname, LocalDate dob, String phoneNumber) {
		this(Optional.empty(),title,forename,surname,dob,phoneNumber);
		//TODO Fetch address
	}

	public Patient(long id, String title, String forename, String surname, LocalDate dob, String phoneNumber) {
		this(Optional.of(id),title,forename,surname,dob,phoneNumber);
		this.id = Optional.of(id);
		//TODO Fetch address
	}

	public Patient(Optional<Long> id, String title, String forename, String surname, LocalDate dob, String phoneNumber) {
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

	public LocalDate getDob() {
		return dob;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public long getID() {
		return id.get();
	}

	public void save() {
		if(id.isPresent()){
			PatientUtils.updatePatientByID(id.get(),title,forename,surname,phoneNumber);
		}else{
			id = Optional.of(PatientUtils.insertPatient(title,forename,surname,dob,phoneNumber,address.getId()));
		}
	}
}
