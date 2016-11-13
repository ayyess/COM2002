package uk.ac.shef.com2002.grp4.calendar;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalDate;

import javax.swing.JPanel;

public class CalendarView extends JPanel {

	/* True if day false if week */
	private boolean day = false; 
	
	private LocalDate startDate; 
	
	public CalendarComp[] calendars = new CalendarComp[7];
	
	public CalendarView(LocalDate startDate) {
		this.startDate = startDate;
		createCalendars();
	}
	
	public void setDate(LocalDate date) {
		startDate = date;
		createCalendars();
	}
	
	public void setView(boolean day) {
		this.day = day;
		createCalendars();
	}
	
	public void createCalendars() {
		this.removeAll();
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] {1,1,1,1,1,1,1};
		layout.columnWeights = new double[] {1,1,1,1,1,1,1};
		this.setLayout(layout);
		if (day) { 
			GridBagConstraints constraints = new GridBagConstraints();
			calendars = new CalendarComp[7];
			calendars[0] = new CalendarComp(startDate);
			constraints.gridx = 0;
			constraints.gridwidth = 7;
			this.add(calendars[0], constraints);
		} else {
			for (int i = 0; i < calendars.length; i++) {
				GridBagConstraints constraints = new GridBagConstraints();
				CalendarComp c = new CalendarComp(startDate.plusDays(i));
				calendars[i] = c;
				constraints.gridx = i;
				this.add(c, constraints);
			}
		}
	}
	
}
