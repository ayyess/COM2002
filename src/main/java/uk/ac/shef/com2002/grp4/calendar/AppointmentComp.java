/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.calendar;

import uk.ac.shef.com2002.grp4.data.Appointment;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

public class AppointmentComp extends JPanel {
	
	int start;
	int end;
	int duration;
	Appointment appointment;
	
	JLabel startLabel;
	JLabel durationLabel;
	
	public AppointmentComp(Appointment appointment) {
		this.appointment = appointment;
		this.start = convertMins(appointment.getStart());
		this.end = convertMins(appointment.getEnd());
		this.duration = this.end - this.start;
		
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
	
	public void setColor(Color c) {
		setBackground(c);
	}
	
	public Appointment getAppointment() {
		return appointment;
	}
	
	public static int convertMins(LocalTime time) {
		return time.getHour()*60+time.getMinute();
	}
}
