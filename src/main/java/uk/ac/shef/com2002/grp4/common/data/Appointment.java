/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.data;

import java.util.Objects;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Used to store the appointment details temporarily.
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   1/11/2016
 */
public class Appointment {

	/** Stores the date of the appointment. */
	private LocalDate date;
	/** Stores the start time of the appointment. */
	private LocalTime start;
	/** Stores the end time of the appointment. */
	private LocalTime end;
	/** Stores the partner of the appointment. */
	private String partner;
	/** Stores the id of the patient. */
	private long patientId;
	/** Stores whether the appointment was attended and finished */
	private boolean complete;

	/**
	 * This constructor creates an Appointment object.
	 *
	 * This sets the appointment to be incomplete by default.
	 *
	 * @param date - the date of the appointment
	 * @param start - the start time of the appointment
	 * @param duration - the duration of the appointment
	 * @param partner - the partner who is being seen
	 * @param patientId - the patient of the appointment
	 */
	public Appointment(LocalDate date, LocalTime start, int duration, String partner, long patientId) {
		this.date = date;
		this.start = start;
		this.end = start.plusMinutes(duration);
		this.partner = partner;
		this.patientId = patientId;
	}
	
	/**
	 * This constructor creates an Appointment object.
	 *
	 * @param date - the date of the appointment
	 * @param start - the start time of the appointment
	 * @param duration - the duration of the appointment
	 * @param partner - the partner who is being seen
	 * @param patientId - the patient of the appointment
	 * @param complete - the status of the appointment
	 */
	public Appointment(LocalDate date, LocalTime start, int duration, String partner, long patientId, boolean complete) {
		this(date, start, duration, partner, patientId);
		this.complete = complete;	
	}

	/**
	 * Sets this appointment to complete
	 * @param complete
	 */
	public void setComplete(boolean complete) {
		this.complete = complete;
	}
	
	/**
	 * This gets the date of the appointment.
	 * @return date
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * This gets the start time of the appointment.
	 * @return start
	 */
	public LocalTime getStart() {
		return start;
	}

	/**
	 * This gets the end of the appointment.
	 * @return end
	 */
	public LocalTime getEnd() {
		return end;
	}

	/**
	 * This gets the partner who is being seen.
	 * @return partner
	 */
	public String getPartner() {
		return partner;
	}

	/**
	 * This gets the id of the patient.
	 * @return patientId
	 */
	public long getPatientId() {
		return patientId;
	}

	/**
	 * This gets the duration of the appointment.
	 * @return duration
	 */
	public int getDuration() {
		return (int) Duration.between(start, end).getSeconds()/60;
	}
	
	/**
	 * @return if the appointment is complete 
	 */
	public boolean isComplete() {
		return complete;
	}

	/**
	 * This returns the object as a single string.
	 *
	 * @return a String representing the Appointment object
	 */
	@Override
	public String toString() {
		return "Appointment{" +
				"date=" + date +
				", start=" + start +
				", end=" + end +
				", partner='" + partner + '\'' +
				", patientId=" + patientId +
				'}';
	}

	/**
	 * This tests if two appointments are equal to each other.
	 *
	 * @param obj - an Object
	 * @return - a Boolean which is true if the two objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Appointment)){
			return false;
		}
		Appointment rhs = (Appointment) obj;
		return
			Objects.equals(date, rhs.date) &&
			Objects.equals(start, rhs.start) &&
			Objects.equals(end, rhs.end) &&
			Objects.equals(partner, rhs.partner) &&
			Objects.equals(patientId, rhs.patientId);
	}

}
