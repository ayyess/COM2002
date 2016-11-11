package uk.ac.shef.com2002.grp4;

import uk.ac.shef.com2002.grp4.data.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.GridLayout;

class PatientButton extends JButton {
	public Patient patient;
	public PatientButton (Patient patient) {
		super(patient.getName() + " - " + patient.getDob());
		this.patient = patient;
	};
}

public class FindPatientDialog extends JDialog {

	JTextField textField = new JTextField("");

	JScrollPane listScroller;
	JPanel patientList = new JPanel();

	public FindPatientDialog(JFrame parent) {
		super(parent);
		
		setLayout(new FlowLayout());
		setBackground(Color.WHITE);
		

		textField.setPreferredSize(new Dimension(100,100));
		this.add(new JLabel("First name"));
		this.add(textField);

		JButton search_button = new JButton("Search");
		search_button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae){
                    search();
                }
            });
		this.add(search_button);

		patientList.setLayout(new GridLayout(0,1));
		listScroller = new JScrollPane(patientList);
		listScroller.setPreferredSize(new Dimension(500, 500));
		this.add(listScroller);
		
		pack();
		setModal(true);
		setVisible(true);

	}
	void close(){
		setModal(true);
		//getOwner().setEnabled(true);
		this.dispose();
	}
	public Patient patient;

	public void setPatient(Patient p) {
		patient = p;
	}
	
	void search() {
		String searchTerm = textField.getText();
		List<Patient> patients = Patient.fuzzyFindByFirstName(searchTerm);

		patientList.removeAll();
		for (Patient patient : patients) {
			JButton patient_button = new PatientButton(patient);
			patient_button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae){
						PatientButton button = (PatientButton)ae.getSource();
						setPatient(button.patient);
						close();
					}
				});
			patientList.add(patient_button);
				}
		pack();
	}
}
