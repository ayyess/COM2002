/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.calendar;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

public class EmptyAppointment extends JPanel {

	/* The slot counting from 0. Based on calculations done in CalendarComp
	 * Modify DIV and (24*60)/DIV to modify what this value means
	 * using (24*60)/20 means this slot is at time*20 min slot
	 */
	private int time;
	
	public EmptyAppointment(int time) {
		this.time = time;
		setBorder(BorderFactory.createLineBorder(Color.black));
		setBackground(Color.WHITE);
		
		LocalTime t = LocalTime.of(0,0).plusMinutes(time * 20);
		add(new JLabel(t.toString()));
	}
	
	public int getTime() {
		return time;
	}
	
}
