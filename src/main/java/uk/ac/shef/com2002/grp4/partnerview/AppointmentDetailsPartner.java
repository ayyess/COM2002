/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */
package uk.ac.shef.com2002.grp4.partnerview;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import uk.ac.shef.com2002.grp4.common.BaseDialog;
import uk.ac.shef.com2002.grp4.common.Partner;
import uk.ac.shef.com2002.grp4.common.data.Appointment;
import uk.ac.shef.com2002.grp4.common.data.Patient;
import uk.ac.shef.com2002.grp4.common.data.Treatment;
import uk.ac.shef.com2002.grp4.common.databases.AppointmentUtils;
import uk.ac.shef.com2002.grp4.common.databases.PatientUtils;
import uk.ac.shef.com2002.grp4.common.databases.TreatmentApplicationUtils;

public class AppointmentDetailsPartner extends BaseDialog {
	
	/** The appointment this dialog will show/modify the details of */
	private final Appointment appointment;
	/** Check box for setting/seeing the completion status of the appointment */
	JCheckBox completedCheck = new JCheckBox("Completed");
	/** Scroll pane for the list treatments this appointment has */
	JScrollPane treatmentScroll;
	/** The List of treatments that this appointment has */
	JList<Treatment> treatmentList = new JList<Treatment>();
	/** Model for the list of appointments */
	DefaultListModel<Treatment> model = new DefaultListModel<Treatment>();
	/** Treatments this appointment had when the dialog was opened */
	Treatment[] oldTreatments = null;
	/** The new set of treatments this appointment will have */
	Treatment[] newTreatments = null;
	/** The partner of this appointment */
	Partner partner;
	
	/**
	 * Constructs a new dialog to modify the appointment and assign new treatments to the appointment.
	 * 
	 * @param owner - The parent frame for this dialog
	 * @param appointment - The appointment to show details on
	 */
	public AppointmentDetailsPartner(Frame owner, Appointment appointment) {
		super(owner, "Appointment");
		this.appointment = appointment;
		
		//Fetch the patient for the appointment from the database
		Patient patient = PatientUtils.getPatientByID(appointment.getPatientId());
		this.partner = Partner.valueOfIngnoreCase(appointment.getPartner());
		
		addLabeledComponent("Partner", new JLabel(appointment.getPartner()));
		addLabeledComponent("Patient", new JLabel(patient.getName()));
		addLabeledComponent("Duration", new JLabel(Integer.toString(appointment.getDuration()) + " minutes"));
		
		//Fetch and add all of the treatments this appointment has if any
		treatmentList.setModel(model);
		oldTreatments = TreatmentApplicationUtils.getAppointmentTreatments(appointment);
		for (int i = 0; i < oldTreatments.length; i++) {
			model.add(i, oldTreatments[i]);
		}
		
		treatmentScroll = new JScrollPane(treatmentList);
		addLabeledComponent("Treatments", treatmentScroll);
		
		//Show the completion field
		completedCheck.setSelected(appointment.isComplete());
		addLabeledComponent("Completed", completedCheck);
		
		JButton setTreatmentButton = new JButton("Set Treatments");
		setTreatmentButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setTreatments();
			}
		});
	
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				save();
				dispose();
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				cancel();
				dispose();
			}
		});
		addButtons(setTreatmentButton, saveButton, cancelButton);
		pack();
	}
	
	/**
	 * Deletes the appointment from the database
	 * This is not used so that only the secretary can modify the calendar
	 */
	void delete() {
		AppointmentUtils.deleteAppointment(appointment);
	}

	/**
	 * Sets the treatments for this appointment by opening the treatment finding dialog
	 * <p>
	 * This does not save the changes it only changes the list for this dialog.
	 * Call {@code save()} to save the changes and close the dialog
	 */
	void setTreatments() {
		FindTreatment treatmentDialog = new FindTreatment(this, oldTreatments);
		treatmentDialog.setVisible(true);
		if (treatmentDialog.wasCanceled()) {
			//If the user decided to cancel don't make any changes
			return;
		}
		newTreatments = treatmentDialog.selectedTreatments;
		model.removeAllElements();
		if (newTreatments != null) {
			int start = model.getSize();
			for (int i = 0; i < newTreatments.length; i++) {
				
				model.add(start+i, newTreatments[i]);
			}
		}
		oldTreatments = newTreatments.clone();
	}

	/**
	 * Save the changes if any and close the dialog
	 */
	void save() {
		//Update appointment to be complete
		AppointmentUtils.updateCompleteAppointment(appointment, completedCheck.isSelected());
		if (newTreatments == null) {
			//Message to say no changes
			return;
		}
		//Save the treatments to the database
		TreatmentApplicationUtils.replace(oldTreatments, newTreatments, appointment.getDate(), appointment.getStart(), partner);
	}
	
}
