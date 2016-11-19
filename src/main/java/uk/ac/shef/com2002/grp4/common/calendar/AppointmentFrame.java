/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.calendar;

import uk.ac.shef.com2002.grp4.common.BaseDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import uk.ac.shef.com2002.grp4.common.databases.AppointmentUtils;
import uk.ac.shef.com2002.grp4.common.databases.PatientUtils;
import uk.ac.shef.com2002.grp4.common.data.Patient;
import uk.ac.shef.com2002.grp4.common.data.Appointment;

/**
 * This is used to view the details of a booked
 * appointment slot
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   03/11/2016
 */
public class AppointmentFrame extends BaseDialog {

	/** A single booked Appointment. */
	private final Appointment appointment;

	/**
	 * This constructor creates a frame which contains the booked appointment
	 * details and allows the user to delete this appointment if they wish to.
	 *
	 * @param owner - This sets what the dialog is created relative to. If set to
	 *              null, it is created relative to the centre of the screen.
	 * @param appointment
	 */
	public AppointmentFrame(Component owner, Appointment appointment) {
		super(owner,"View Appointment");
		this.appointment = appointment;

		Patient patient = PatientUtils.getPatientByID(appointment.getPatientId());

		addLabeledComponent("Partner", new JLabel(appointment.getPartner()));
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

	/** This disposes of the frame. */
	void close(){
		setModal(true);
		//getOwner().setEnabled(true);
		AppointmentFrame.this.dispose();
	}

	/** This deletes an appointment in the database. */
	void delete() {
		AppointmentUtils.deleteAppointment(appointment);
	}
}
