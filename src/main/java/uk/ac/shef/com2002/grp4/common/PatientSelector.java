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
 * <Doc here>
 * <p>
 * Created on 11/11/2016.
 */
public class PatientSelector extends JPanel implements ActionListener {
	private PatientComponent displayField;
	private JButton findPatientButton;
	private JButton createPatientButton;

	private Optional<Patient> patient = Optional.empty();

	public PatientSelector() {
		super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,0,0,0);
		c.gridwidth = 2;
		c.gridx = 0;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		displayField = new PatientComponent(Optional.empty());
		displayField.setBorder(BorderFactory.createTitledBorder("Patient"));
		add(displayField,c);

		c.weighty = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		findPatientButton = new JButton("Find");
		findPatientButton.addActionListener(this);
		add(findPatientButton,c);
		c.gridx = 1;
		createPatientButton = new JButton("Create");
		createPatientButton.addActionListener(this);
		add(createPatientButton,c);
	}

	public void setPatient(Patient patient) {
		setPatient(Optional.ofNullable(patient));
	}

	public void setPatient(Optional<Patient> patient) {
		this.patient = patient;
		displayField.setPatient(patient);
		//FIXME there has to be a better way to get the dialog to resize if needed
		SwingUtilities.getWindowAncestor(this).pack();
	}

	public Optional<Patient> getPatient() {
		return patient;
	}

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
