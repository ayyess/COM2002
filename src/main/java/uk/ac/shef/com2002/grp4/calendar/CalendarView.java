package uk.ac.shef.com2002.grp4.calendar;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalDate;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class CalendarView extends JPanel {

	/* True if day false if week */
	private boolean day = false; 
	
	private LocalDate startDate; 
	
	JTabbedPane tabbedPane = new JTabbedPane();
	
	JPanel dentistPanel = new JPanel();
	JPanel hygienstPanel = new JPanel();
	
	public CalendarView(LocalDate startDate) {
		this.startDate = startDate;
		this.setLayout(new BorderLayout());
		
		tabbedPane.addTab("Dentist", createScrollPane(dentistPanel));
		tabbedPane.addTab("Hygienist", createScrollPane(hygienstPanel));
		this.add(tabbedPane, BorderLayout.CENTER);
		setView(day);
	}
	
	public void setDate(LocalDate date) {
		startDate = date;
		setView(day);
	}
	
	public void setView(boolean day) {
		this.day = day;
		createCalendars(day, startDate, Practitioner.DENTIST, dentistPanel);
		createCalendars(day, startDate, Practitioner.HYGIENIST, hygienstPanel);
	}
	
	public static void createCalendars(boolean day, LocalDate date, Practitioner p, JPanel panel) {
		CalendarComp[] calendars = new CalendarComp[7];
		panel.removeAll();
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] {1,1,1,1,1,1,1};
		layout.columnWeights = new double[] {1,1,1,1,1,1,1};
		panel.setLayout(layout);
		if (day) { 
			GridBagConstraints constraints = new GridBagConstraints();
			calendars[0] = new CalendarComp(date, p);
			constraints.gridx = 0;
			constraints.gridwidth = 7;
			constraints.fill = GridBagConstraints.BOTH;
			panel.add(calendars[0], constraints);
		} else {
			for (int i = 0; i < calendars.length; i++) {
				GridBagConstraints constraints = new GridBagConstraints();
				CalendarComp c = new CalendarComp(date.plusDays(i), p);
				calendars[i] = c;
				constraints.gridx = i;
				constraints.fill = GridBagConstraints.BOTH;
				panel.add(c, constraints);
			}
		}
		panel.revalidate();
	}
	
	private static JScrollPane createScrollPane(JPanel child) {
		JScrollPane scroll = new JScrollPane();
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.getVerticalScrollBar().setUnitIncrement(5);
		scroll.getViewport().add(child);
		return scroll;
	}
	
}
