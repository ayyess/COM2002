package uk.ac.shef.com2002.grp4.calendar;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

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
	}
	
	public int getTime() {
		return time;
	}
	
}
