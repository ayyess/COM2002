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
import uk.ac.shef.com2002.grp4.common.databases.PatientUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

/**
 * This class is creates a dialog where the user can find a patient in the database.
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   11/11/2016
 */
public class FindPatientDialog extends BaseDialog implements ActionListener{

	/** A text field where a name can be entered. */
	private final JTextField nameField;
	/** A list of possible patients. */
	private final JList<Patient> searchResults;
	/** This stores the Patient object of the selected patient. */
	private Optional<Patient> patient = Optional.empty();
	/** A button to cancel the process. */
	private final JButton cancelButton;
	/** A button to select an address. */
	private final JButton selectButton;

	/**
	 * A constructor that creates the dialog box.
	 *
	 * @param owner - Takes a component that will parent the dialog
	 */
	public FindPatientDialog(Component owner) {
		super(owner,"Find Patient");

		Container contentPane = rootPane.getContentPane();

		nameField = new JTextField();
		nameField.addActionListener(this);
		addLabeledComponent("First name",nameField);

		searchResults = new JList<>(new DefaultListModel<>());
		GridBagConstraints c = getBaseConstraints();
		c.gridwidth = 2;

		contentPane.add(new JScrollPane(searchResults),c);

		nextRow();

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		selectButton = new JButton("Select");
		selectButton.addActionListener(this);

		addButtons(cancelButton,selectButton);

		pack();

	}

	/**
	 * This sets the patient to be the one that is passed to the method.
	 *
	 * @param p - a Patient
	 */
	public void setPatient(Patient p) {
		patient = Optional.ofNullable(p);
	}

	/**
	 * Listens for an action to be performed and completes a certain action.
	 *
	 * @param e - an ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		if(e.getSource() == nameField){
			List<Patient> patients = PatientUtils.fuzzyFindPatientByFirstName(nameField.getText());
			DefaultListModel<Patient> model = (DefaultListModel<Patient>) searchResults.getModel();
			model.clear();
			for(Patient p : patients){
				model.addElement(p);
			}
		}else if(e.getSource() == selectButton){
			patient = Optional.ofNullable(searchResults.getSelectedValue());
			dispose();
		}else if(e.getSource() == cancelButton){
			dispose();
		}
	}

	/**
	 * This gets the Patient object.
	 * --Optional
	 *
	 * @return the Patient object
	 */
	public Patient getPatient() {
		return patient.orElse(null);
	}
}
