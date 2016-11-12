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

import uk.ac.shef.com2002.grp4.data.Appointment;
import uk.ac.shef.com2002.grp4.PatientSelector;
import uk.ac.shef.com2002.grp4.data.Patient;

public class AppointmentFrame extends BaseDialog {

	private final LocalDate date;
	private final LocalTime time;
	private final JComboBox<String> practionerCombo;
	private final JComboBox<Integer> lengthCombo;
	private final PatientSelector patientSelector;

	public AppointmentFrame(Component owner, LocalDate date, LocalTime time) {
		super(owner,"Create Appointment");
		this.date = date;
		this.time = time;

		String[] items = {"PractionerA", "PractionerB"};
		practionerCombo = new JComboBox<>(items);

		addLabeledInput("Practitioner",practionerCombo);

		patientSelector = new PatientSelector();
		addLabeledInput("Patient",patientSelector);

		Integer[] lengthItems = {20, 40, 60};
		lengthCombo = new JComboBox<>(lengthItems);
		addLabeledInput("Duration",lengthCombo);

	JButton cancel_button = new JButton("Cancel");
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
		addButtons(cancel_button,save_button);
		pack();

	}
	void close(){
		setModal(true);
		//getOwner().setEnabled(true);
		AppointmentFrame.this.dispose();
	}

	void saveDetails() {
		AppointmentUtils.insertAppointment(date, (String)practionerCombo.getSelectedItem(), patientSelector.getPatient().map(Patient::getID).get(), time, Duration.ofMinutes(((Integer)lengthCombo.getSelectedItem())));
	}
}
