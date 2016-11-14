package uk.ac.shef.com2002.grp4.calendar;

import uk.ac.shef.com2002.grp4.data.Appointment;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

public class AppointmentComp extends JComponent {
	
	int start;
	int end;
	int duration;
	Appointment appointment;
	
	public AppointmentComp(Appointment appointment) {
		this.appointment = appointment;
		this.start = convertMins(appointment.getStart());
		this.end = convertMins(appointment.getEnd());
		this.duration = this.end - this.start;
		
		setLayout(new FlowLayout());
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		add(new JLabel(appointment.getStart().toString()));
		add(new JLabel(""+duration));
	}
	
	public Appointment getAppointment() {
		return appointment;
	}
	
	public static int convertMins(LocalTime time) {
		return time.getHour()*60+time.getMinute();
	}
}
