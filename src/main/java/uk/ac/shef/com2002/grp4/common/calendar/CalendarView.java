/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.calendar;

import uk.ac.shef.com2002.grp4.common.Partner;
import uk.ac.shef.com2002.grp4.common.data.Appointment;
import uk.ac.shef.com2002.grp4.common.databases.AppointmentUtils;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Used to view multiple calendars alongside one another
 * <br>
 *
 * @author Group 4
 * @version 1.0
 * @since 03/11/2016
 */
public class CalendarView extends JPanel {

	/**
	 * A JTabbedPane which will hold the different partners calendars.
	 */
	JTabbedPane tabbedPane = new JTabbedPane();
	/**
	 * A JPanel which will hold the dentists calendar(s).
	 */
	JPanel dentistPanel = new JPanel(new GridBagLayout());
	/**
	 * A JPanel which will hold the hygienists calendar(s).
	 */
	JPanel hygienstPanel = new JPanel(new GridBagLayout());
	/**
	 * An array of CalendarComp which will store the dentists calendars.
	 */
	CalendarComp[] dentist;
	/**
	 * An array of CalendarComp which will store the hygienists calendars.
	 */
	CalendarComp[] hygienist;
	/**
	 * An ArrayList of filled Appointment listeners.
	 */
	List<CalendarClickListener> appointmentListeners = new ArrayList<>();
	/**
	 * An ArrayList of empty slot listeners.
	 */
	List<CalendarClickListener> slotListeners = new ArrayList<>();
	/**
	 * This controls what sort of view is shown.
	 * <p>
	 * True = a day view
	 * False = a week view
	 */
	private boolean day = false;
	/**
	 * The start date of the calendar view.
	 */
	private LocalDate startDate;

	/**
	 * This constructor creates a CalendarView based on a
	 * particular start date.
	 *
	 * @param startDate - The date for which the view will start
	 */
	public CalendarView(LocalDate startDate) {
		this.startDate = startDate;
		this.setLayout(new BorderLayout());
		tabbedPane.addTab(Partner.DENTIST.toString(), createScrollPane(dentistPanel));
		tabbedPane.addTab(Partner.HYGIENIST.toString(), createScrollPane(hygienstPanel));
		this.add(tabbedPane, BorderLayout.CENTER);
		setView(day);
	}

	/**
	 * This constructor creates a CalendarView based on a
	 * particular partner.
	 *
	 * @param p - The partner for which the calendar show
	 */
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

	/**
	 * This creates a horizontal list of calendars.
	 *
	 * @param day                  - a Boolean
	 *                             True = a day view
	 *                             False = a week view
	 * @param date                 - Date that the calendar should generate from
	 * @param p                    - Partner whose appointments need to be viewed
	 * @param panel                - a JPanel which the whole calendar will be put on
	 * @param slotListeners        - an ArrayList of listeners for empty slots
	 * @param appointmentListeners - an ArrayList of listeners for booked appointments
	 * @return an Array of CalendarComp
	 */
	public static CalendarComp[] createCalendars(boolean day, LocalDate date, Partner p, JPanel panel, List<CalendarClickListener> slotListeners, List<CalendarClickListener> appointmentListeners) {
		CalendarComp[] calendars = new CalendarComp[5];
		LocalDate startDate = date.minusDays(date.getDayOfWeek().ordinal());
		panel.removeAll();
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[]{1, 1, 1, 1, 1};
		layout.columnWeights = new double[]{1, 1, 1, 1, 1};
		panel.setLayout(layout);
		if (day) {
			List<Appointment> appointments = AppointmentUtils.getAppointmentByDate(startDate);
			GridBagConstraints constraints = new GridBagConstraints();
			calendars[0] = new CalendarComp(appointments.stream()
					.filter((appt) -> appt.getPartner().equals(p.toString()))
					.collect(Collectors.toList())
					, startDate, p);
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
			List<Appointment> appointments = AppointmentUtils.getAppointmentByDateRange(startDate, startDate.plusDays(calendars.length - 1));
			for (int i = 0; i < calendars.length; i++) {
				LocalDate dayDate = startDate.plusDays(i);
				GridBagConstraints constraints = new GridBagConstraints();
				//filter the appointments as required in java
				CalendarComp c = new CalendarComp(appointments.stream().filter((appt) ->
						appt.getPartner().equals(p.toString())
								&& appt.getDate().equals(dayDate)
				).collect(Collectors.toList()), dayDate, p);
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

	/**
	 * Creates a scrollpane for the child element
	 *
	 * @param child - JPanel which needs to be scrolled.
	 * @return a JScrollPane
	 */
	private static JScrollPane createScrollPane(JPanel child) {
		JScrollPane scroll = new JScrollPane();
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.getVerticalScrollBar().setUnitIncrement(5);
		scroll.getViewport().add(child);
		return scroll;
	}

	/**
	 * This sets the style of view for the calendar.
	 *
	 * @param day - a boolean
	 *            True = a day view
	 *            False = a week view
	 */
	public void setView(boolean day) {
		this.day = day;
		dentist = createCalendars(day, startDate, Partner.DENTIST, dentistPanel, slotListeners, appointmentListeners);
		hygienist = createCalendars(day, startDate, Partner.HYGIENIST, hygienstPanel, slotListeners, appointmentListeners);
	}

	/**
	 * This adds the given listener to all empty slots within the calendars.
	 * When an empty slot is clicked the onClick/onPressed/onReleased
	 * method of the listener will be called.
	 *
	 * @param l - Listener to add
	 */
	public void addSlotClickListener(CalendarClickListener l) {
		slotListeners.add(l);
	}

	/**
	 * This adds the given listener to all appointments within the calendars.
	 * When an appointment is clicked the onClick/onPressed/onReleased
	 * method of the listener will be called.
	 *
	 * @param l - Listener to add
	 */
	public void addAppointmentClickListener(CalendarClickListener l) {
		appointmentListeners.add(l);
	}

	/**
	 * This gets the start date of the calendar.
	 */
	public LocalDate getDate() {
		return startDate;
	}

	/**
	 * This sets the date for which the calendar is starting at.
	 *
	 * @param date - a start date
	 */
	public void setDate(LocalDate date) {
		startDate = date;
		setView(day);
	}

}
