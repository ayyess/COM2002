package uk.ac.shef.com2002.grp4.partnerview;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;

import uk.ac.shef.com2002.grp4.common.BaseDialog;
import uk.ac.shef.com2002.grp4.common.data.Appointment;
import uk.ac.shef.com2002.grp4.common.data.Patient;
import uk.ac.shef.com2002.grp4.common.data.Treatment;
import uk.ac.shef.com2002.grp4.common.databases.AppointmentUtils;
import uk.ac.shef.com2002.grp4.common.databases.PatientUtils;

public class AppointmentDetailsPartner extends BaseDialog {
	
	private final Appointment appointment;
	JCheckBox completedCheck = new JCheckBox("Completed");
	JList<Treatment> treatmentList = new JList<Treatment>();
	
	public AppointmentDetailsPartner(Frame owner, Appointment appointment) {
		super(owner, "Appointment");
		this.appointment = appointment;
		
		Patient patient = PatientUtils.getPatientByID(appointment.getPatientId());
		
		addLabeledComponent("Practitioner", new JLabel(appointment.getPractitioner()));
		addLabeledComponent("Patient", new JLabel(patient.getName()));
		addLabeledComponent("Duration", new JLabel(Integer.toString(appointment.getDuration()) + " minutes"));
		
		addLabeledComponent("Treatments", treatmentList);
		addLabeledComponent("Completed", completedCheck);
		
		JButton addTreatmentButton = new JButton("Add Treatment");
		addTreatmentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addTreatments();
			}
		});
	
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				save();
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				cancel();
			}
		});
		addButtons(addTreatmentButton, saveButton, cancelButton);
		pack();
	}
	
	void delete() {
		AppointmentUtils.deleteAppointment(appointment);
	}

	void addTreatments() {
		FindTreatment treatmentDialog = new FindTreatment(this);
		treatmentDialog.setVisible(true);
		Treatment[] treatments = treatmentDialog.selectedTreatments;
		DefaultListModel<Treatment> model = new DefaultListModel<Treatment>();
		treatmentList.setModel(model);
		if (treatments != null) {
			for (int i = 0; i < treatments.length; i++) {
				model.add(i, treatments[i]);
			}
		}
		
	}

	void save() {
		
	}
	
}
