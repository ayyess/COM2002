package uk.ac.shef.com2002.grp4.calendar;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.DateFormatter;
import java.sql.Time;

public class EmptyAppointment extends JPanel {

	/* The slot counting from 0. Based on calculations done in CalendarComp
	 * Modify DIV and (24*60)/DIV to modify what this value means
	 * using (24*60)/20 means this slot is at time*20 min slot
	 */
	private int time;
	
	public EmptyAppointment(int time) {
		this.time = time;
		setBorder(BorderFactory.createLineBorder(Color.black));
		setBackground(Color.WHITE);

		//setLayout(new FlowLayout());
		//setBackground(Color.WHITE);
		//setBorder(BorderFactory.createLineBorder(Color.black));
		
		Time t = new Time(time * 60000 * 20); // Time will start from 1am due to timezones
		add(new JLabel(time + " " + t.toString()));
	}
	
	public int getTime() {
		return time;
	}
	
}
