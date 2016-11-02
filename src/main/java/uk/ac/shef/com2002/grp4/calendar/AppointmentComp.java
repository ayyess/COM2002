package uk.ac.shef.com2002.grp4.calendar;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.DateFormatter;

public class AppointmentComp extends JComponent {
	
	Calendar startC;
	Calendar endC;
	
	int start;
	int end;
	int duration;
	
	public AppointmentComp(Calendar start, Calendar end) {
		this.startC = start;
		this.endC = end;
		this.start = convertMins(start);
		this.end = convertMins(end);
		this.duration = this.end - this.start;
		
		setLayout(new FlowLayout());
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		SimpleDateFormat f = new SimpleDateFormat("h:M");		
		add(new JLabel("S"+f.format(start.getTime())));
		add(new JLabel("D"+duration));
		add(new JLabel("E"+f.format(end.getTime())));
	}
	
	public static int convertMins(Calendar c) {
		return c.get(Calendar.HOUR)*60 + c.get(Calendar.MINUTE);
	}
}
