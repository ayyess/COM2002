package uk.ac.shef.com2002.grp4.calendar;

import uk.ac.shef.com2002.grp4.BaseDialog;
import uk.ac.shef.com2002.grp4.FindPatientDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;

import uk.ac.shef.com2002.grp4.PatientSelector;
import uk.ac.shef.com2002.grp4.databases.AppointmentUtils;
import uk.ac.shef.com2002.grp4.databases.PatientUtils;;
import uk.ac.shef.com2002.grp4.data.Patient;
import uk.ac.shef.com2002.grp4.data.Appointment;


public class AppointmentFrame extends BaseDialog {

	private final Appointment appointment;

	public AppointmentFrame(Component owner, Appointment appointment) {
		super(owner,"View Appointment");
		this.appointment = appointment;

		Patient patient = PatientUtils.getPatientByID(appointment.getPatientId());

		addLabeledComponent("Practitioner", new JLabel(appointment.getPractitioner()));
		addLabeledComponent("Patient", new JLabel(patient.getName()));
		addLabeledComponent("Duration", new JLabel(Integer.toString(appointment.getDuration()) + " minutes"));

		JButton delete_button = new JButton("Delete");
		delete_button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae){
                    delete();
					close();
                }
            });
		JButton close_button = new JButton("Close");
		close_button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae){
                    close();
                }
            });
		addButtons(delete_button, close_button);
		pack();

	}
	void close(){
		setModal(true);
		//getOwner().setEnabled(true);
		AppointmentFrame.this.dispose();
	}

	void delete() {
		AppointmentUtils.deleteAppointment(appointment);
	}
}
