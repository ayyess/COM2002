/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */
package uk.ac.shef.com2002.grp4.common;

import uk.ac.shef.com2002.grp4.common.data.Patient;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

/**
 * Patient details component used within patient details frame
 * <br>
 *
 * @author Group 4
 * @since 15/11/2016
 */
public class PatientComponent extends BaseInfoComponent {
	/**
	 * Text box for patient's title
	 */
	private final JTextField titleField;
	/**
	 * Text box for patient's first name
	 */
	private final JTextField firstNameField;
	/**
	 * Text box for patient's last name
	 */
	private final JTextField lastNameField;
	/**
	 * Text box for patient's date of birth.
	 * Selected with a JDatePicker
	 */
	private final JTextField dobField;
	/**
	 * Text box for patient's phone number
	 */
	private final JTextField phoneNumberField;
	/**
	 * Address details panel
	 */
	private final AddressComponent addressField;
	/**
	 * The optional patient
	 */
	private Optional<Patient> patient;

	/**
	 * Construct a patient component that filled with a patients details.
	 * A null patient will give empty fields.
	 * @param patient the patient whose details to use
	 */
	public PatientComponent(Patient patient) {
		this(Optional.ofNullable(patient));
	}

	/**
	 * Construct a patient component that filled with a patients details.
	 * A null patient will give empty fields.
	 * @param patient the patient whose details to use
	 */
	public PatientComponent(Optional<Patient> patient) {
		super();

		titleField = new JTextField(10);
		titleField.setEditable(false);
		addLabeledComponent("Title", titleField);

		firstNameField = new JTextField(10);
		firstNameField.setEditable(false);
		addLabeledComponent("First Name", firstNameField);

		lastNameField = new JTextField(10);
		lastNameField.setEditable(false);
		addLabeledComponent("Last Name", lastNameField);

		dobField = new JTextField(5);
		dobField.setEditable(false);
		addLabeledComponent("Date of Birth", dobField);

		phoneNumberField = new JTextField(5);
		phoneNumberField.setEditable(false);
		addLabeledComponent("Phone Number", phoneNumberField);

		addressField = new AddressComponent(Optional.empty());
		addressField.setBorder(BorderFactory.createTitledBorder("Address"));
		GridBagConstraints c = getBaseConstraints();
		c.gridwidth = 2;
		add(addressField, c);

		setPatient(patient);
	}

	/**
	 * Update the fields with a ptients details
	 * A null patient resets the fields to empty
	 * @param patient the patient whose details to use
	 */
	public void setPatient(Patient patient) {
		setPatient(Optional.ofNullable(patient));
	}

	/**
	 * Update the fields with a ptients details
	 * A null patient resets the fields to empty
	 * @param patient the patient whose details to use
	 */
	public void setPatient(Optional<Patient> patient){
		this.patient = patient;
		if(patient.isPresent()){
			titleField.setText(patient.get().getTitle());
			firstNameField.setText(patient.get().getForename());
			lastNameField.setText(patient.get().getSurname());
			dobField.setText(patient.get().getFormattedDob());
			phoneNumberField.setText(patient.get().getPhoneNumber());
			addressField.setAddress(patient.map(Patient::getAddress));
		} else {
			titleField.setText("");
			firstNameField.setText("");
			lastNameField.setText("");
			dobField.setText("");
			addressField.setAddress(Optional.empty());
		}
	}
}
