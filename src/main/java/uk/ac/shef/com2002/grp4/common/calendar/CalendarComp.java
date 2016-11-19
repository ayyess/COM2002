/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.calendar;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.ac.shef.com2002.grp4.common.Partner;
import uk.ac.shef.com2002.grp4.common.data.Appointment;
import uk.ac.shef.com2002.grp4.common.data.Patient;
import uk.ac.shef.com2002.grp4.common.databases.PatientUtils;
import uk.ac.shef.com2002.grp4.common.util.DPIScaling;

/**
 * Used to create a panel showing all appointments in a day.
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   03/11/2016
 */
public class CalendarComp extends JPanel {

	/** The date that the Calendar will return the appointments of. */
	LocalDate date;

	/** The layout used for the Calendar. */
	private final GridBagLayout layout;

	/** The divide of each appointment in minutes. */
	final static int DIV = 20;
	/** The vertical height in pixels of each slot. */
	final static int SLOT_SIZE = 30;
	/** The start time of the calendar. */
	final static LocalTime START = LocalTime.of(9,0);
	/** The end time of the calendar. */
	final static LocalTime END = LocalTime.of(17,0);
	/** This acts as padding above each appointment. */
	final static int HEADER_SIZE = 2;

	/** This is the color that a dentists appointment will show in. */
	private static final Color DENTIST_COLOUR = Color.getHSBColor(0.0f,0.5f,1.0f);//Light red
	/** This is the color that a dentists appointment will show in when marked as complete. */
	private static final Color DENTIST_COMPLETE_COLOUR = Color.getHSBColor(0.0f,0.5f,0.8f); //Darker red
	
	/** This is the color that a hygienists appointment will show in. */
	private static final Color HYGIENIST_COLOUR = Color.getHSBColor(0.69f,0.5f,1.0f); //Light blue
	/** This is the color that a hygienists appointment will show in when marked as complete. */
	private static final Color HYGIENIST_COMPLETE_COLOUR = Color.getHSBColor(0.69f,0.5f,0.8f); //Darker blue
	
	/** This is the color that a reserved appointment will show in. */
	private static final Color RESERVED_COLOUR = Color.GRAY;

	/** An ArrayList of AppointmentComp. */
	ArrayList<AppointmentComp> appointments = new ArrayList<AppointmentComp>();

	/** An ArrayList of filled Appointment listeners. */
	ArrayList<CalendarClickListener> appointmentListeners = new ArrayList<CalendarClickListener>();
	/** An ArrayList of empty slot listeners. */
	ArrayList<CalendarClickListener> slotListeners = new ArrayList<CalendarClickListener>();

	/** The color of the calendar. */
	Color calendarColor;

	/** The partner whose calendar is being viewed. */
	Partner partner;

	/**
	 * This listens for MouseEvents on booked appointments.
	 */
	private MouseAdapter appointmentAdapter = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			notifyAppointmentListeners(e);
		}
		public void mousePressed(MouseEvent e) {
			notifyAppointmentListeners(e);
		}
		public void mouseReleased(MouseEvent e) {
			notifyAppointmentListeners(e);
		}
	};

	/**
	 * This listens for MouseEvents on empty slots.
	 */
	private MouseAdapter slotAdapter = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			notifySlotListeners(e);
		}
		public void mousePressed(MouseEvent e) {
			notifySlotListeners(e);
		}
		public void mouseReleased(MouseEvent e) {
			notifySlotListeners(e);
		}
	};

	/**
	 * This constructor creates a panel containing all slots between a start time
	 * (constant START) and end time (constant END) for a particular partner.
	 *
	 * @param appointmentsOnDate - List of Appointments on a particular date
	 * @param date - The date for which the calendar should be generated
	 * @param partner - The partner for which the calendar should be generated
	 */
	public CalendarComp(List<Appointment> appointmentsOnDate,LocalDate date, Partner partner) {
		super();
		this.partner = partner;
		if (partner == Partner.DENTIST) calendarColor = DENTIST_COLOUR;
		else if (partner == Partner.HYGIENIST) calendarColor = HYGIENIST_COLOUR;
		layout = new GridBagLayout();
		layout.columnWidths = new int[] {1};
		layout.rowHeights = new int[(int)(HEADER_SIZE+(Duration.between(START,END).toMinutes()/DIV))];
		//set all slot sizes to SLOT_SIZE, later we just choose how many of these slots to use with gridheight
		Arrays.fill(layout.rowHeights, (int) (SLOT_SIZE*DPIScaling.get()));
		setLayout(layout);

		setAppointmentsAndDate(appointmentsOnDate,date);
	}

	/**
	 * This function shows all the slots in a vertical list with the
	 * booked slots appearing in a different colour.
	 */
	public void showAll() {
		removeAll();
		addHeaders();
		boolean[] times = new boolean[(int)(Duration.between(START,END).toMinutes()/DIV)];
		Arrays.fill(times,false);
		for (AppointmentComp a : appointments) {
			if (a.appointment.getPatientId() == 1) {
				a.setColor(RESERVED_COLOUR);
			} else {
				Color color = Color.CYAN;
				if (a.appointment.getPartner().endsWith(Partner.DENTIST.toString())) {
					color = a.appointment.isComplete() ? DENTIST_COMPLETE_COLOUR : DENTIST_COLOUR;  
				} else if (a.appointment.getPartner().endsWith(Partner.HYGIENIST.toString())) {
					color = a.appointment.isComplete() ? HYGIENIST_COMPLETE_COLOUR : HYGIENIST_COLOUR;
				}
				a.setColor(color);
			}
			a.removeMouseListener(appointmentAdapter);
			if (!a.start.isBefore(START)) {
				Duration gap = Duration.between(START,a.start);
				GridBagConstraints c = new GridBagConstraints();
				a.addMouseListener(appointmentAdapter);
				Arrays.fill(times,(int)gap.toMinutes()/DIV,(int)gap.plus(a.duration).toMinutes()/DIV,true);
				c.gridy = (int)(HEADER_SIZE+(gap.toMinutes()/DIV));
				c.gridheight = (int)a.duration.toMinutes()/DIV;
				c.weightx = 1.0;
				c.fill = GridBagConstraints.BOTH;
				add(a, c);
			}
		}
		for (int t = 0; t < times.length; t++) {
			if (!times[t]) {
				GridBagConstraints c = new GridBagConstraints();
				times[t] = true;
				c.gridy = HEADER_SIZE+t;
				c.weightx = 1.0;
				c.fill = GridBagConstraints.BOTH;
				EmptyAppointment gap = new EmptyAppointment(START.plusMinutes(t), partner);
				gap.addMouseListener(slotAdapter);
				add(gap, c);
			}
		}
	}
	
	/**
	 * This adds headers above the calendar to show exactly who's
	 * appointments are below.
	 */
	private void addHeaders() {
		//Add date
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("'<html>'eee'<br/>'dd-MM-yyyy'<html>'");
		JLabel date = new JLabel(this.date.format(formatter));
		date.setHorizontalAlignment(JLabel.CENTER);
		date.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(date, c);
		
		//Add dentist/hygienist label
		String p = partner.toString();
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		JLabel d = new JLabel(p);
		d.setOpaque(true);
		d.setHorizontalAlignment(JLabel.CENTER);
		d.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		d.setBackground(calendarColor);
		add(d, c);
	}
	
	/***
	 * This changes the appointments and the date that are being shown on
	 * this calendar object.
	 * 
	 * @param date
	 */
	public void setAppointmentsAndDate(List<Appointment> appointmentsOnDate,LocalDate date){
		this.date = date;
		appointments.clear();
		for (Appointment a : appointmentsOnDate) {
			if (a.getPartner().equals(partner.toString())) {
				appointments.add(new AppointmentComp(a));
			}
		}
		showAll();
	}
	
	/**
	 * This adds the given listener. When a empty slot is clicked the onClick/onPressed/onReleased
	 * method of the listener will be called. 
	 * @param l - Listener to add
	 */
	public void addSlotClickListener(CalendarClickListener l) {
		slotListeners.add(l);
	}
	
	/**
	 * This adds the given listener. When an appointment is clicked the onClick/onPressed/onReleased
	 * method of the listener will be called. 
	 * @param l - Listener to add
	 */
	public void addAppointmentClickListener(CalendarClickListener l) {
		appointmentListeners.add(l);
	}

	/**
	 * This listens for a MouseEvent on a booked Appointment listener.
	 *
	 * @param e - a MouseEvent
	 */
	public void notifyAppointmentListeners(MouseEvent e) {
		for (CalendarClickListener l : appointmentListeners) {
			l.notify(e);
		}
	}

	/**
	 * This listens for a MouseEvent on an empty slot.
	 *
	 * @param e - a MouseEvent
	 */
	public void notifySlotListeners(MouseEvent e) {
		for (CalendarClickListener l : slotListeners) {
			l.notify(e);
		}
	}

	/** This gets the date for which the calendar is showing. */
	public LocalDate getDate() {
		return date;
	}
	
}
