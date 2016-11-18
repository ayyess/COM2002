/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.data;

import uk.ac.shef.com2002.grp4.common.databases.PatientUtils;
import uk.ac.shef.com2002.grp4.common.databases.PatientPlanUtils;
import uk.ac.shef.com2002.grp4.common.databases.AddressUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

/**
 * Used to store the patient details temporarily
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   1/11/2016
 */
public class Patient {

	/**
	 * This ensures the date of birth is in a particular format
	 */
	private static final DateTimeFormatter dobFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy");

	/**
	 * This stores the patient id.
	 * --Optional
	 */
	private Optional<Long> id;
	/** This stores the title of the patient. */
	private String title;
	/** This stores the forename of the patient. */
	private String forename;
	/** This stores the surname of the patient. */
	private String surname;
	/** This stores the date of birth of the patient. */
	private LocalDate dob;
	/** This stores the phone number of the patient. */
	private String phoneNumber;

	/** This stores the id of the address of the patient. */
	private Long addressId;
	/**
	 * This stores the patients Address object
	 * --Optional
	 */
	private Optional<Address> address;
	
	//TODO Add creation from db?

	/**
	 * This constructor create a Patient object with no patient_id.
	 * This is for when a new patient is being created and it hasn't
	 * been assigned.
	 *
	 * @param title - title of patient
	 * @param forename - forename of patient
	 * @param surname - surname of patient
	 * @param dob - date of birth of patient
	 * @param phoneNumber - contact number of patient
	 * @param addressId - address id of patient
	 */
	public Patient(String title, String forename, String surname, LocalDate dob, String phoneNumber,long addressId) {
		this(Optional.empty(),title,forename,surname,dob,phoneNumber,addressId);
	}

	/**
	 * This constructor creates a Patient object.
	 *
	 * @param id - id of patient
	 * @param title - title of patient
	 * @param forename - forename of patient
	 * @param surname - surname of patient
	 * @param dob - date of birth of patient
	 * @param phoneNumber - contact number of patient
	 * @param addressId - address id of patient
	 */
	public Patient(long id, String title, String forename, String surname, LocalDate dob, String phoneNumber,long addressId) {
		this(Optional.of(id),title,forename,surname,dob,phoneNumber,addressId);
	}

	/**
	 * This creates a Patient object when their id may or may not exist.
	 *
	 * @param id - id of patient
	 *           --Optional
	 * @param title - title of patient
	 * @param forename - forename of patient
	 * @param surname - surname of patient
	 * @param dob - date of birth of patient
	 * @param phoneNumber - contact number of patient
	 * @param addressId - address id of patient
	 */
	public Patient(Optional<Long> id, String title, String forename, String surname, LocalDate dob, String phoneNumber,long addressId) {
		this(id,title,forename,surname,dob,phoneNumber,Optional.empty(),addressId);
	}

	/**
	 *
	 * @param id - id of patient
	 *           --Optional
	 * @param title - title of patient
	 * @param forename - forename of patient
	 * @param surname - surname of patient
	 * @param dob - date of birth of patient
	 * @param phoneNumber - contact number of patient
	 * @param address - full Address object of patient
	 *                --Optional
	 * @param addressId - address id of patient
	 */
	public Patient(Optional<Long> id, String title, String forename, String surname, LocalDate dob, String phoneNumber, Optional<Address> address,long addressId) {
		this.title = title;
		this.forename = forename;
		this.surname = surname;
		this.dob = dob;
		this.phoneNumber = phoneNumber;
		this.id = id;
		this.address = address;
		this.addressId = addressId;
		//TODO Fetch address
	}

	/**
	 * This gets the patient's whole name as a String.
	 *
	 * @return a formatted String containing their title, forename and surname
	 */
	public String getName() {
		return title + " " + forename + " " + surname;
	}

	/**
	 * This gets a String representation of the Patient object.
	 *
	 * @return a String representing Patient
	 */
	public String toString() {
		return getName() + " " + dob + " " + phoneNumber;
	}

	/**
	 * This gets the title of the patient.
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * This gets the forename of the patient.
	 * @return forename
	 */
	public String getForename() {
		return forename;
	}

	/**
	 * This gets the surname of the patient.
	 * @return surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * This gets the date of birth of the patient.
	 * @return dob
	 */
	public LocalDate getDob() {
		return dob;
	}

	/**
	 * This gets the date of birth in the form dd-MM-yyyy
	 * @return a formatted date
	 */
	public String getFormattedDob() {
		return dob.format(dobFormatter);
	}

	/**
	 * This gets the contact number of the patient.
	 * @return phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * This gets the id if it exists.
	 * @return id
	 */
	public long getID() {
		return id.get();
	}

	/**
	 * This gets the Patient Plan if the patient has one.
	 * @return the Patient Plan
	 */
	public Optional<PatientPlan> getPatientPlan(){
		return id.map((id)->{
			return PatientPlanUtils.getPlanByPatientID(id);
		});
	}

	/**
	 * This gets the Address object that relates to the patient.
	 * @return an Address object
	 */
	public Address getAddress(){
		if(address.isPresent()){
			return address.get();
		}else{
			Address addr = AddressUtils.getAddressByID(addressId);
			address = Optional.of(addr);
			return addr;
		}
	}

	/**
	 * This saves the details of the Patient permanently in the database.
	 */
	public void save() {
		if(id.isPresent()){
			PatientUtils.updatePatientByID(id.get(),title,forename,surname,phoneNumber);
		}else{
			id = Optional.of(PatientUtils.insertPatient(title,forename,surname,dob,phoneNumber,getAddress().getId()));
		}
	}

	/**
	 * This deletes a patient from the database.
	 */
	public void delete() {
		if(id.isPresent()) {
			PatientUtils.deleteByID(getID());
		}
		//else not in db
	}
}
