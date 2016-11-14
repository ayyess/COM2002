/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.data;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {

	private LocalDate date;
	private LocalTime start;
	private LocalTime end;
	private String practitioner;
	private long patientId;

	public Appointment(LocalDate date, LocalTime start, int duration, String practitioner, long patientId) {
		this.date = date;
		this.start = start;
		System.out.println(duration);
		this.end = start.plusMinutes(duration);
		this.practitioner = practitioner;
		this.patientId = patientId;
	}

	public LocalDate getDate() {
		return date;
	}

	public LocalTime getStart() {
		return start;
	}
	
	public LocalTime getEnd() {
		return end;
	}

	public String getPractitioner() {
		return practitioner;
	}

	public long getPatientId() {
		return patientId;
	}
	
	public int getDuration() {
		return (int) Duration.between(start, end).getSeconds()/60;
	}
}
