package uk.ac.shef.com2002.grp4.data;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class Appointment {

	private Date date;
	private Calendar start;
	private Calendar end;
	private String practitioner;

	public Appointment(Date date, Calendar start, Calendar end, String practitioner) {
		this.date = date;
		this.start = start;
		this.end = end;
		this.practitioner = practitioner;
	}
}
