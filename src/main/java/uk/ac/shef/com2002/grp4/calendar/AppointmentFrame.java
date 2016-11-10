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

public class AppointmentFrame extends JDialog {
	
	int start;
	int end;
	int duration;

	JTextField patientID = new JTextField("");
	
	public AppointmentFrame(JFrame parent, Date date) {
		super(parent);
		
		setLayout(new FlowLayout());
		setBackground(Color.WHITE);
		

		String[] items = {"PractionerA", "PractionerB"};
		JComboBox<String> combo = new JComboBox<>(items);
		patientID.setPreferredSize(new Dimension(100,100));
		this.add(new JLabel("Practioner"));
		this.add(combo);
		this.add(new JLabel("Patient"));
		this.add(patientID);
		JButton patient_button = new JButton("Find patient");
		this.add(patient_button);
		patient_button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae){
					FindCustomerDialog dialog = new FindCustomerDialog(null);
					System.out.println("here");
					System.out.println(dialog.patient);
					System.out.println(dialog.patient.getID());
					patientID.setText(Integer.toString(dialog.patient.getID()));
				}
			});

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
		
	}
}
