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

public class Appointment extends JComponent {
	
	Calendar startC;
	Calendar endC;
	
	int start;
	int end;
	int duration;
	
	public Appointment(Calendar start, Calendar end) {
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
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JScrollPane p = new JScrollPane();
		CalendarComp c = new CalendarComp();
		Calendar calS = Calendar.getInstance();
		Calendar calE = Calendar.getInstance();
		calS.set(2016, 11, 2, 10, 40, 0);
		calE.set(2016, 11, 2, 11, 40, 0);
		c.addAppointment(new Appointment(calS,calE));
		calS.set(2016, 11, 2, 16, 0, 0);
		calE.set(2016, 11, 2, 16, 40, 0);
		c.addAppointment(new Appointment(calS,calE));
		calS.set(2016, 11, 2, 20, 0, 0);
		calE.set(2016, 11, 2, 21, 40, 0);
		c.addAppointment(new Appointment(calS,calE));
		p.setVerticalScrollBarPolicy(p.VERTICAL_SCROLLBAR_ALWAYS);
		p.getViewport().add(c);
		f.add(p);
		f.setVisible(true);
	}
	
}
