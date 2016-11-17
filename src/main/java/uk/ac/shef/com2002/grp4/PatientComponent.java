package uk.ac.shef.com2002.grp4;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Optional;
import uk.ac.shef.com2002.grp4.data.Patient;
import uk.ac.shef.com2002.grp4.data.Treatment;
import uk.ac.shef.com2002.grp4.databases.TreatmentUtils;

public class PatientComponent extends BaseInfoComponent{
	private Optional<Patient> patient;
	private final JTextField titleField;
	private final JTextField firstNameField;
	private final JTextField lastNameField;
	private final JTextField dobField;
	private final AddressComponent addressField;
	
	public PatientComponent(Patient patient){
		this(Optional.ofNullable(patient));
	}
	public PatientComponent(Optional<Patient> patient){
		super();

		titleField = new JTextField(5);
		titleField.setEditable(false);
		addLabeledComponent("Title",titleField);

		firstNameField = new JTextField(5);
		firstNameField.setEditable(false);
		addLabeledComponent("First Name",firstNameField);

		lastNameField = new JTextField(5);
		lastNameField.setEditable(false);
		addLabeledComponent("Last Name",lastNameField);

		dobField = new JTextField(5);
		dobField.setEditable(false);
		addLabeledComponent("Date of Birth",dobField);

		addressField = new AddressComponent(Optional.empty());
		addressField.setBorder(BorderFactory.createTitledBorder("Address"));
		GridBagConstraints c = getBaseConstraints();
		c.gridwidth = 2;
		add(addressField,c);
		
		setPatient(patient);
	}

	public void setPatient(Patient patient){
		setPatient(Optional.ofNullable(patient));
	}

	public void setPatient(Optional<Patient> newPatient){
		this.patient = newPatient;
		if(patient.isPresent()){
			titleField.setText(patient.get().getTitle());
			firstNameField.setText(patient.get().getForename());
			lastNameField.setText(patient.get().getSurname());
			dobField.setText(patient.get().getFormattedDob());
			addressField.setAddress(patient.map(Patient::getAddress));
		}else{
			titleField.setText("");
			firstNameField.setText("");
			lastNameField.setText("");
			dobField.setText("");
			addressField.setAddress(Optional.empty());
		}
	}
}
