/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common;

import org.jdatepicker.JDatePicker;
import uk.ac.shef.com2002.grp4.common.data.Address;
import uk.ac.shef.com2002.grp4.common.data.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.time.*;
import java.util.Optional;

/**
 * This class is creates a dialog where the user can input a new patient.
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   11/11/2016
 */
public class CreatePatientDialog extends BaseDialog implements ActionListener {

	/** This stores the number of rows in the dialog. */
	private int row = 0;
	/** A button to create the patient. */
	private JButton createButton;
	/** A button to cancel the process. */
	private JButton cancelButton;
	/** A field to store the title of the patient. */
	private JTextField titleField;
	/** A field to store the forename of the patient. */
	private JTextField forenameField;
	/** A field to store the surname of the patient. */
	private JTextField surnameField;
	/** A field to store the date of birth of the patient. */
	private JDatePicker dobField;
	/** A field to store the contact number of the patient. */
	private JTextField phoneField;
	/** A selector to choose the address of the patient. */
	private AddressSelector addressField;

	/**
	 * This stores the Patient object.
	 * --Optional
	 */
	private Optional<Patient> patient = Optional.empty();

	/**
	 * A constructor that creates the dialog box.
	 *
	 * @param owner - Takes a component that will parent the dialog
	 */
	public CreatePatientDialog(Component owner){
		super(owner,"Create Patient");

		Container contentPane = rootPane.getContentPane();

		titleField = new JTextField();
		forenameField = new JTextField();
		surnameField = new JTextField();
		dobField = new JDatePicker();
		phoneField = new JTextField();
		phoneField.setInputVerifier(new InputVerifier() {
			@Override
			public boolean verify(JComponent input) {
				JTextField field = (JTextField)input;
				return field.getText().matches("^[0-9 \\-]+$");
			}
		});
		addressField = new AddressSelector();
		addLabeledComponent("Title",titleField);
		addLabeledComponent("Forename",forenameField);
		addLabeledComponent("Surname",surnameField);
		addLabeledComponent("DoB",dobField);
		addLabeledComponent("Phone number",phoneField);
		addLabeledComponent("Address",addressField);

		createButton = new JButton("Create");
		createButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		addButtons(cancelButton,createButton);

		pack();
	}

	/**
	 * Listens for an action to be performed and completes a certain action.
	 *
	 * @param e - an ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == createButton){
			String title = titleField.getText();
			String forename = forenameField.getText();
			String surname = surnameField.getText();
			Calendar cal = (Calendar) dobField.getModel().getValue();
			if(cal == null){
				JOptionPane.showMessageDialog(this,"You must select a date","Validation error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			LocalDate dob = LocalDate.of(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
			String phoneNumber = phoneField.getText();
			Optional<Address> optAddress = addressField.getAddress();

			if(!optAddress.isPresent()) {
				JOptionPane.showMessageDialog(this, "You must select an address", "Validation error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Patient patient = new Patient(Optional.empty(),title,forename,surname,dob,phoneNumber,optAddress,optAddress.get().getId());
			patient.save();
			this.patient = Optional.of(patient);

			dispose();

		}else if(e.getSource() == cancelButton){
			cancel();
		}
	}

	/**
	 * This gets the Patient object.
	 * --Optional
	 *
	 * @return the Patient object
	 */
	public Optional<Patient> getPatient() {
		return patient;
	}

}
