/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.calendar;

import uk.ac.shef.com2002.grp4.common.Partner;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

/**
 * Used to make an empty appointment
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   12/11/2016
 */
public class EmptyAppointment extends JPanel {

	/* The slot counting from 0. Based on calculations done in CalendarComp
	 * Modify DIV and (24*60)/DIV to modify what this value means
	 * using (24*60)/20 means this slot is at time*20 min slot
	 */

	/** Stores a time for which the empty appointment will be made. */
	private LocalTime time;
	/** Stores a Partner for which the empty appointment will be made. */
	private Partner partner;

	/**
	 * Constructor that creates an EmptyAppointment panel.
	 *
	 * @param time - time as an integer
	 * @param partner - a Partner
	 */
	public EmptyAppointment(LocalTime time, Partner partner) {
		this.time = time;
		this.partner = partner;
		setBorder(BorderFactory.createLineBorder(Color.black));
		setBackground(Color.WHITE);
		
		add(new JLabel(time.toString()));
	}

	/** Gets the time of the empty appointment. */
	public LocalTime getTime() {
		return time;
	}

	/** Gets the partner of the empty appointment. */
	public Partner getPartner() {
		return partner;
	}
}
