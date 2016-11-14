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
		
		startLabel.setOpaque(true);
		startLabel.setHorizontalAlignment(JLabel.CENTER);
		durationLabel.setOpaque(true);
		durationLabel.setHorizontalAlignment(JLabel.CENTER);
		
		add(startLabel);
		add(durationLabel);
	}
	
	public void setColor(Color c) {
		setBackground(c);
		if (startLabel != null) startLabel.setBackground(c);
		if (durationLabel != null) durationLabel.setBackground(c);
	}
	
	public Appointment getAppointment() {
		return appointment;
	}
	
	public static int convertMins(LocalTime time) {
		return time.getHour()*60+time.getMinute();
	}
}
