package uk.ac.shef.com2002.grp4.calendar;

import uk.ac.shef.com2002.grp4.FindPatientDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;

import uk.ac.shef.com2002.grp4.databases.AppointmentUtils;
import uk.ac.shef.com2002.grp4.data.Patient;

public class AppointmentFrame extends JDialog {
	
	LocalDate date;
	LocalTime time;
	JComboBox<String> practionerCombo;
	JComboBox<Integer> lengthCombo;
	Patient patient;
	JTextField patientName;

	public void setPatient(Patient patient) {
		if (patient == null) {
			return;
		}
		this.patient = patient;
		patientName.setText(patient.getName());
		
	}
	
	public AppointmentFrame(JFrame parent, LocalDate date, LocalTime time) {
		super(parent);
		this.date = date;
		this.time = time;
		
		setLayout(new FlowLayout());
		setBackground(Color.WHITE);
	

		String[] items = {"PractionerA", "PractionerB"};
		practionerCombo = new JComboBox<>(items);
		patientName = new JTextField("");
		patientName.setPreferredSize(new Dimension(100,100));
		patientName.setEditable(false);
		
		this.add(new JLabel("Practioner"));
		this.add(practionerCombo);
		this.add(new JLabel("Patient"));
		this.add(patientName);
		JButton patient_button = new JButton("Find patient");
		this.add(patient_button);
		patient_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae){
					FindPatientDialog dialog = new FindPatientDialog(null);
					setPatient(dialog.patient);
				}
			});
		Integer[] lengthItems = {20, 40, 60};
		lengthCombo = new JComboBox<>(lengthItems);
		this.add(lengthCombo);

	JButton cancel_button = new JButton("Cancel");
		this.add(cancel_button);
		cancel_button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae){
                    close();
                }
            });
		JButton save_button = new JButton("Save");
		save_button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae){
					saveDetails();
                    close();
                }
            });
		this.add(save_button);
		pack();
		setVisible(true);

	}
	void close(){
		setModal(true);
		//getOwner().setEnabled(true);
		AppointmentFrame.this.dispose();
	}

	void saveDetails() {
		AppointmentUtils.insertAppointment(date, (String)practionerCombo.getSelectedItem(), patient.getID(), time, Duration.ofMinutes(((Integer)lengthCombo.getSelectedItem())));
	}
}
