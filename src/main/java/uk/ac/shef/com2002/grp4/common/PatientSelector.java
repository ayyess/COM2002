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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

/**
 * This class allows the user to select a patient if known already, and therefore
 * in the database. Or the user can choose to create a new patient.
 * <br>
 *
 * @author Group 4
 * @version 1.4
 * @since 08/11/2016
 */
public class PatientSelector extends JPanel implements ActionListener {

	/**
	 * This stores the area where the output will be displayed.
	 */
	private PatientComponent displayField;

	/**
	 * This stores the find patient button.
	 */
	private JButton findPatientButton;
	/**
	 * This stores the create patient button.
	 */
	private JButton createPatientButton;

	/**
	 * This stores the patient if it exists.
	 */
	private Optional<Patient> patient = Optional.empty();

	/**
	 * This constructor initialises the patient selector output.
	 */
	public PatientSelector() {
		super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 0, 0, 0);
		c.gridwidth = 2;
		c.gridx = 0;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		displayField = new PatientComponent(Optional.empty());
		displayField.setBorder(BorderFactory.createTitledBorder("Patient"));
		add(displayField, c);

		c.weighty = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		findPatientButton = new JButton("Find");
		findPatientButton.addActionListener(this);
		add(findPatientButton, c);
		c.gridx = 1;
		createPatientButton = new JButton("Create");
		createPatientButton.addActionListener(this);
		add(createPatientButton, c);
	}

	/**
	 * This function gets the Patient.
	 * <br>
	 *
	 * @return a Patient object if it exists.
	 */
	public Optional<Patient> getPatient() {
		return patient;
	}

	/**
	 * @see PatientSelector#setPatient(Optional<Patient>)
	 * @param patient
	 */
	public void setPatient(Patient patient) {
		setPatient(Optional.ofNullable(patient));
	}

	/**
	 * This function sets the patient and gets the addresses details.
	 * <br>
	 *
	 * @param patient - This is (optionally) a Patient object. It may or may not exist.
	 */
	public void setPatient(Optional<Patient> patient) {
		this.patient = patient;
		displayField.setPatient(patient);
		//FIXME there has to be a better way to get the dialog to resize if needed
		SwingUtilities.getWindowAncestor(this).pack();
	}

	/**
	 * This function behaves if a certain action is performed.
	 * <br>
	 * If the find address button is pressed, it creates a new finder dialog
	 * and outputs the patient.
	 * If the create address button is pressed, it creates a new creator dialog
	 * and allows the user to create a new patient.
	 *
	 * @param e - an ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == findPatientButton) {
			FindPatientDialog finder = new FindPatientDialog(this);
			finder.setVisible(true);
			setPatient(finder.getPatient());
		} else if (e.getSource() == createPatientButton) {
			CreatePatientDialog creator = new CreatePatientDialog(this);
			creator.setVisible(true);
			setPatient(creator.getPatient());
		}
	}
}
