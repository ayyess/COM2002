package uk.ac.shef.com2002.grp4;

import uk.ac.shef.com2002.grp4.data.Patient;

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
		addLabeledInput("First name",nameField);

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
