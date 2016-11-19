/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.calendar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.*;

import uk.ac.shef.com2002.grp4.common.BaseDialog;
import uk.ac.shef.com2002.grp4.common.Partner;
import uk.ac.shef.com2002.grp4.common.PatientSelector;
import uk.ac.shef.com2002.grp4.common.UserFacingException;
import uk.ac.shef.com2002.grp4.common.databases.AppointmentUtils;
import uk.ac.shef.com2002.grp4.common.data.Patient;

/**
 * Dialog used to create an appointment
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   06/11/2016
 */
public class AppointmentCreationFrame extends BaseDialog {

	/** The date of an appointment. */
	private final LocalDate date;

	/** The time of an appointment. */
	private final LocalTime time;

	/** The partner whom the patient will see. */
	private final Partner partner;

	/**
	 * The JComboBox that will store the possible lengths of
	 * appointment.
	 */
	private final JComboBox<Integer> lengthCombo;

	/**
	 * The object that will let the user select a particular
	 * patient.
	 */
	private final PatientSelector patientSelector;

	/**
	 * This constructor creates the frame where the user can create an
	 * appointment for a patient.
	 *
	 * @param owner - This sets what the dialog is created relative to. If set to
	 *              null, it is created relative to the centre of the screen.
	 * @param date - The date to which the appointment will be created.
	 * @param time - The time to which the appointment will be created.
	 * @param partner - The partner that the patient will see as default in the selector.
	 */
	public AppointmentCreationFrame(Component owner, LocalDate date, LocalTime time, Partner partner) {
		super(owner,"Create Appointment");
		this.date = date;
		this.time = time;
		this.partner = partner;
		
		patientSelector = new PatientSelector();
		addLabeledComponent("Patient",patientSelector);

		Integer[] lengthItems = {20, 40, 60};
		lengthCombo = new JComboBox<>(lengthItems);
		addLabeledComponent("Duration",lengthCombo);

	JButton cancel_button = new JButton("Cancel");
		cancel_button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae){
                    close();
                }
            });
		JButton save_button = new JButton("Save");
		save_button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae){
					try {
						saveDetails();
						close();
					} catch (UserFacingException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
                }
            });
		addButtons(cancel_button,save_button);
		pack();

	}

	/** This disposes of the frame. */
	void close(){
		setModal(true);
		//getOwner().setEnabled(true);
		AppointmentCreationFrame.this.dispose();
	}

	/** This saves the appointment details and puts it into the database. */
	void saveDetails() {
		AppointmentUtils.insertAppointment(date, partner.toString(), patientSelector.getPatient().map(Patient::getID).get(), time, Duration.ofMinutes(((Integer)lengthCombo.getSelectedItem())), false);
	}
}
