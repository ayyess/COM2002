/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.data;

import uk.ac.shef.com2002.grp4.databases.PatientUtils;
import uk.ac.shef.com2002.grp4.databases.AddressUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class Patient {

	private static final DateTimeFormatter dobFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy"); 

	private Optional<Long> id;
	private String title;
	private String forename;
	private String surname;
	private LocalDate dob;
	private String phoneNumber;
	
	private Long addressId;
	private Optional<Address> address;
	
	//TODO Add creation from db?
	
	public Patient(String title, String forename, String surname, LocalDate dob, String phoneNumber,long addressId) {
		this(Optional.empty(),title,forename,surname,dob,phoneNumber,addressId);
	}

	public Patient(long id, String title, String forename, String surname, LocalDate dob, String phoneNumber,long addressId) {
		this(Optional.of(id),title,forename,surname,dob,phoneNumber,addressId);
	}

	public Patient(Optional<Long> id, String title, String forename, String surname, LocalDate dob, String phoneNumber,long addressId) {
		this(id,title,forename,surname,dob,phoneNumber,Optional.empty(),addressId);
	}

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

	public String getFormattedDob() {
		return dob.format(dobFormatter);
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public long getID() {
		return id.get();
	}

	public Address getAddress(){
		if(address.isPresent()){
			return address.get();
		}else{
			Address addr = AddressUtils.getAddressByID(addressId);
			address = Optional.of(addr);
			return addr;
		}
	}

	public void save() {
		if(id.isPresent()){
			PatientUtils.updatePatientByID(id.get(),title,forename,surname,phoneNumber);
		}else{
			id = Optional.of(PatientUtils.insertPatient(title,forename,surname,dob,phoneNumber,getAddress().getId()));
		}
	}

	public void delete() {
		if(id.isPresent()) {
			PatientUtils.deleteByID(getID());
		}
		//else not in db
	}
}
