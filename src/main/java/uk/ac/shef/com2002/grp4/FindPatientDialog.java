/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4;

import uk.ac.shef.com2002.grp4.data.Patient;
import uk.ac.shef.com2002.grp4.databases.PatientUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

public class FindPatientDialog extends BaseDialog implements ActionListener{

	private final JTextField nameField;
	private final JList<Patient> searchResults;
	private Optional<Patient> patient = Optional.empty();
	private final JButton cancelButton;
	private final JButton selectButton;

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

	public void setPatient(Patient p) {
		patient = Optional.ofNullable(p);
	}

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

	public Patient getPatient() {
		return patient.orElse(null);
	}
}
