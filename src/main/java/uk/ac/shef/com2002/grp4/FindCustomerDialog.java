package uk.ac.shef.com2002.grp4.calendar;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.*;
import javax.swing.text.DateFormatter;
import java.util.Date;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import uk.ac.shef.com2002.grp4.data.Appointment;
import uk.ac.shef.com2002.grp4.data.Patient;
import uk.ac.shef.com2002.grp4.databases.PatientUtils;
import java.util.List;

class PatientButton extends JButton {
	public Patient patient;
	public PatientButton (Patient patient) {
		super(patient.getName() + " - " + patient.getDob());
		this.patient = patient;
	};
}

public class FindCustomerDialog extends JDialog {

	JPanel customerList = new JPanel();
	JTextField textField = new JTextField("");
	
	public FindCustomerDialog(JFrame parent) {
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
		List<Patient> patients = PatientUtils.findPatientByFirstName(searchTerm);

		customerList.removeAll();
		for (Patient patient : patients) {
			JButton patient_button = new PatientButton(patient);
			patient_button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent ae){
						PatientButton button = (PatientButton)ae.getSource();
						setPatient(button.patient);
						close();
					}
				});
			customerList.add(patient_button);
				}
	}
}
