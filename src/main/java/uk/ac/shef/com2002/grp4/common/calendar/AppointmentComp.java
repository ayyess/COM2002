/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.calendar;

import uk.ac.shef.com2002.grp4.common.data.Appointment;

import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalTime;

/**
 * Used to manage an appointment slot on the calendar.
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   03/11/2016
 */
public class AppointmentComp extends JPanel {

	/** The start of an appointment. */
	LocalTime start;

	/** The end of an appointment. */
	LocalTime end;

	/** The duration of an appointment. */
	Duration duration;

	/** A single Appointment. */
	Appointment appointment;

	/** A JLabel to manage when the slot starts. */
	JLabel startLabel;

	/** A JLabel to manage the duration of the slot. */
	JLabel durationLabel;

	/**
	 * This constructor takes an appointment and creates the box that
	 * will appear on the calendar output.
	 *
	 * @param appointment - an Appointment slot
	 */
	public AppointmentComp(Appointment appointment) {
		this.appointment = appointment;
		this.start = appointment.getStart();
		this.end = appointment.getEnd();
		this.duration = Duration.between(this.start,this.end);

		setLayout(new FlowLayout());
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(Color.black));

		startLabel = new JLabel(appointment.getStart().toString());
		durationLabel = new JLabel(""+duration);

		startLabel.setHorizontalAlignment(JLabel.CENTER);
		durationLabel.setHorizontalAlignment(JLabel.CENTER);

		add(startLabel);
		add(durationLabel);
	}

	/**
	 * This function sets the background color of the appointment slot.
	 *
	 * @param c - a Color
	 */
	public void setColor(Color c) {
		setBackground(c);
	}

	/**
	 * This function gets the appointment the slot is representing.
	 *
	 * @return an Appointment
	 */
	public Appointment getAppointment() {
		return appointment;
	}

}
