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
