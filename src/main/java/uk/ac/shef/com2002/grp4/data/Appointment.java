package uk.ac.shef.com2002.grp4.data;

import java.util.Calendar;

public class Appointment {

	private Calendar start;
	private Calendar end;
	private int duration;

	public Appointment(Calendar start, Calendar end, int duration) {
		this.start = start;
		this.end = end;
		this.duration = duration;
	}
	
}
