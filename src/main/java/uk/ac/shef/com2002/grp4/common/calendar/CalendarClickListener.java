/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.calendar;

import java.awt.event.MouseEvent;

/**
 * Used to listen for clicks on the calendar.
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   03/11/2016
 */
public abstract class CalendarClickListener {

	/**
	 * Listens for a mouse click
	 * @param e - a MouseEvent
	 */
	public abstract void onClick(MouseEvent e);

	/**
	 * Listens for a mouse press
	 * @param e - a MouseEvent
	 */
	public abstract void onPressed(MouseEvent e);

	/**
	 * Listens for a mouse release
	 * @param e - a MouseEvent
	 */
	public abstract void onRelease(MouseEvent e);

	/**
	 * Listens for a MouseEvent and based on its ID, complete
	 * a certain action.
	 *
	 * @param e - a MouseEvent
	 */
	public void notify(MouseEvent e) {
		switch (e.getID()) {
		case MouseEvent.MOUSE_CLICKED:
			onClick(e);
			break;
		case MouseEvent.MOUSE_PRESSED:
			onPressed(e);
			break;
		case MouseEvent.MOUSE_RELEASED:
			onRelease(e);
			break;
		}
	}
	
}
