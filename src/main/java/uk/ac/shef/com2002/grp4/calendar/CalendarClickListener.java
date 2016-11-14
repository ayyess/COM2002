/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.calendar;

import java.awt.event.MouseEvent;

public abstract class CalendarClickListener {

	public abstract void onClick(MouseEvent e);
	
	public abstract void onPressed(MouseEvent e);
	
	public abstract void onRelease(MouseEvent e);
	
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
