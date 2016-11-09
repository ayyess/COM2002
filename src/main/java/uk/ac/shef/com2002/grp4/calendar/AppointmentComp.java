package uk.ac.shef.com2002.grp4.calendar;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.DateFormatter;

import uk.ac.shef.com2002.grp4.data.Appointment;

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
