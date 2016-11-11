package uk.ac.shef.com2002.grp4.data;

import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {

	private LocalDate date;
	private LocalTime start;
	private LocalTime end;
	private String practitioner;

	public Appointment(LocalDate date, LocalTime start, int duration, String practitioner) {
		this.date = date;
		this.start = start;
		System.out.println(duration);
		this.end = start.plusMinutes(duration);
		this.practitioner = practitioner;
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
	
	public int getDuration() {
		return (int) Duration.between(start, end).getSeconds()/60;
	}
}
