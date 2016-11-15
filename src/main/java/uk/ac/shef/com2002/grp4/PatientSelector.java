/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4;

import uk.ac.shef.com2002.grp4.data.Patient;

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

	public PatientSelector(){
		super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,0,0,0);
		c.gridheight = 2;
		c.gridy = 0;
		c.gridx = 0;
		c.weightx = 1;
		c.fill = GridBagConstraints.BOTH;
		displayField = new PatientComponent(Optional.empty());
		add(displayField,c);

		c.weightx = 0;
		c.gridheight = 1;
		c.gridx = 1;
		findPatientButton = new JButton("Find");
		findPatientButton.addActionListener(this);
		add(findPatientButton,c);
		c.gridy = 1;
		createPatientButton = new JButton("Create");
		createPatientButton.addActionListener(this);
		add(createPatientButton,c);
	}

	public void setPatient(Patient patient){
		setPatient(Optional.ofNullable(patient));
	}

	public void setPatient(Optional<Patient> patient){
		this.patient = patient;
		displayField.setPatient(patient);
		//FIXME there has to be a better way to get the dialog to resize if needed
		SwingUtilities.getWindowAncestor(this).pack();
	}

	public Optional<Patient> getPatient(){
		return patient;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == findPatientButton){
			FindPatientDialog finder = new FindPatientDialog(this);
			finder.setVisible(true);
			setPatient(finder.getPatient());
		}else if(e.getSource() == createPatientButton){
			CreatePatientDialog creator = new CreatePatientDialog(this);
			creator.setVisible(true);
			setPatient(creator.getPatient());
		}
	}
}
