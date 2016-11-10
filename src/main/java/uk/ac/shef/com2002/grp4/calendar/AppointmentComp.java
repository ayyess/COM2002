package uk.ac.shef.com2002.grp4.calendar;

import uk.ac.shef.com2002.grp4.data.Appointment;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalTime;

public class AppointmentComp extends JComponent {
	
	int start;
	int end;
	int duration;
	
	public AppointmentComp(Appointment appointment) {
		this.start = convertMins(appointment.getStart());
		this.end = convertMins(appointment.getEnd());
		this.duration = this.end - this.start;
		
		setLayout(new FlowLayout());
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		SimpleDateFormat f = new SimpleDateFormat("h:M");		
		add(new JLabel("S"+appointment.getStart().toString()));
		add(new JLabel("D"+duration));
		add(new JLabel("E"+appointment.getEnd().toString()));
	}
	
	public static int convertMins(LocalTime time) {
		return time.getHour()*60+time.getMinute();
	}
}
