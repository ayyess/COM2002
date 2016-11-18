/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.calendar;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.border.Border;

import uk.ac.shef.com2002.grp4.Partner;
import uk.ac.shef.com2002.grp4.data.Appointment;
import uk.ac.shef.com2002.grp4.databases.AppointmentUtils;

public class CalendarView extends JPanel {

	/* True if day false if week */
	private boolean day = false; 
	
	private LocalDate startDate; 
	
	JTabbedPane tabbedPane = new JTabbedPane();
	
	JPanel dentistPanel = new JPanel(new GridBagLayout());
	JPanel hygienstPanel = new JPanel(new GridBagLayout());
	
	CalendarComp[] dentist;
	CalendarComp[] hygienist;
	
	List<CalendarClickListener> appointmentListeners = new ArrayList<CalendarClickListener>();
	List<CalendarClickListener> slotListeners = new ArrayList<CalendarClickListener>();
	
	
	public CalendarView(LocalDate startDate) {
		this.startDate = startDate;
		this.setLayout(new BorderLayout());
		tabbedPane.addTab(Partner.DENTIST.toString(), createScrollPane(dentistPanel));
		tabbedPane.addTab(Partner.HYGIENIST.toString(), createScrollPane(hygienstPanel));
		this.add(tabbedPane, BorderLayout.CENTER);
		setView(day);
	}
	
	public CalendarView(Partner p) {
		this.setLayout(new BorderLayout());
		switch (p) {
		case DENTIST:
			add(dentistPanel, BorderLayout.CENTER);
			break;
		case HYGIENIST:
			add(hygienstPanel, BorderLayout.CENTER);
			break;
		default:
			break;
		}
	}
	
	public void setDate(LocalDate date) {
		startDate = date;
		setView(day);
	}
	
	public void setView(boolean day) {
		this.day = day;
		dentist = createCalendars(day, startDate, Partner.DENTIST, dentistPanel, slotListeners, appointmentListeners);
		hygienist = createCalendars(day, startDate, Partner.HYGIENIST, hygienstPanel, slotListeners, appointmentListeners);
	}
	
	/**
	 * Adds the given listener to all empty slots within the calendars. 
	 * When an empty slot is clicked the onClick/onPressed/onReleased 
	 * method of the listener will be called. 
	 * @param l - Listener to add
	 */
	public void addSlotClickListener(CalendarClickListener l) {
		slotListeners.add(l);
	}
	
	/**
	 * Adds the given listener to all appointments within the calendars. 
	 * When an appointment is clicked the onClick/onPressed/onReleased 
	 * method of the listener will be called. 
	 * @param l - Listener to add
	 */
	public void addAppointmentClickListener(CalendarClickListener l) {
		appointmentListeners.add(l);
	}
	
	public LocalDate getDate() {
		return startDate;
	}
	
	public static CalendarComp[] createCalendars(boolean day, LocalDate date, Partner p, JPanel panel, List<CalendarClickListener> slotListeners, List<CalendarClickListener> appointmentListeners) {
		CalendarComp[] calendars = new CalendarComp[7];
		panel.removeAll();
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] {1,1,1,1,1,1,1};
		layout.columnWeights = new double[] {1,1,1,1,1,1,1};
		panel.setLayout(layout);
		if (day) {
			List<Appointment> appointments = AppointmentUtils.getAppointmentByDate(date);
			GridBagConstraints constraints = new GridBagConstraints();
			calendars[0] = new CalendarComp(appointments.stream()
											.filter((appt)->appt.getPractitioner().equals(p.toString()))
											.collect(Collectors.toList())
											,date, p);
			for (CalendarClickListener slotL : slotListeners) {
				calendars[0].addSlotClickListener(slotL);
			}
			for (CalendarClickListener appointL : appointmentListeners) {
				calendars[0].addAppointmentClickListener(appointL);
			}
			constraints.gridx = 0;
			constraints.gridwidth = 7;
			constraints.fill = GridBagConstraints.BOTH;
			panel.add(calendars[0], constraints);
		} else {
			//get *all* required appointments once to reduce waiting on network (up to 7x speedup)
			List<Appointment> appointments = AppointmentUtils.getAppointmentByDateRange(date,date.plusDays(calendars.length-1));
			for (int i = 0; i < calendars.length; i++) {
				LocalDate dayDate = date.plusDays(i);
				GridBagConstraints constraints = new GridBagConstraints();
				//filter the appointments as required in java
				CalendarComp c = new CalendarComp(appointments.stream().filter((appt)->
					appt.getPractitioner().equals(p.toString())
							&& appt.getDate().equals(dayDate)
				).collect(Collectors.toList()),dayDate, p);
				calendars[i] = c;
				for (CalendarClickListener slotL : slotListeners) {
					calendars[i].addSlotClickListener(slotL);
				}
				for (CalendarClickListener appointL : appointmentListeners) {
					calendars[i].addAppointmentClickListener(appointL);
				}
				constraints.gridheight = 1;
				constraints.gridx = i;
				constraints.fill = GridBagConstraints.BOTH;
				panel.add(c, constraints);
			}
		}
		panel.revalidate();
		return calendars;
	}
	
	private static JScrollPane createScrollPane(JPanel child) {
		JScrollPane scroll = new JScrollPane();
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.getVerticalScrollBar().setUnitIncrement(5);
		scroll.getViewport().add(child);
		return scroll;
	}
	
}
