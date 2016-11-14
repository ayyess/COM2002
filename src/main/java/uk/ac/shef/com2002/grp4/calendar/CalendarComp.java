package uk.ac.shef.com2002.grp4.calendar;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import uk.ac.shef.com2002.grp4.data.Appointment;
import uk.ac.shef.com2002.grp4.databases.AppointmentUtils;

public class CalendarComp extends JPanel {

	LocalDate date;
	
	GridBagLayout layout;
	
	final static int DIV = 20;
	final static int SLOT_SIZE = 30;
	final static int START = 9;
	final static int END = 17;
	final static int HEADER_SIZE = 2;
	
	private static final Color DENTIST_COLOUR = Color.getHSBColor(0.0f,0.5f,1.0f);//Light red
	private static final Color HYGIENIST_COLOUR = Color.getHSBColor(0.69f,0.5f,1.0f);//Light blue 
	
	ArrayList<AppointmentComp> appointments = new ArrayList<AppointmentComp>();
	
	ArrayList<CalendarClickListener> appointmentListeners = new ArrayList<CalendarClickListener>();
	ArrayList<CalendarClickListener> slotListeners = new ArrayList<CalendarClickListener>();
	
	Color calendarColor;
	Practitioner practitioner;
	
	private CalendarClickListener calSlotClick = new CalendarClickListener() {
		
		public void onRelease(MouseEvent e) {
			int time = ((EmptyAppointment)e.getSource()).getTime();
			LocalTime localTime = LocalTime.of(0, 0).plusMinutes(time*20);
			
			AppointmentFrame f = new AppointmentFrame(CalendarComp.this, date, localTime);
			f.setVisible(true);
			setDate(date); //refresh appointment list
		}
		
		public void onClick(MouseEvent e) {}
		public void onPressed(MouseEvent e) {}
		
	}; 
	
	private CalendarClickListener calApointClick = new CalendarClickListener() {

		public void onRelease(MouseEvent e) {
			//Show details about the appointment maybe?
		}
		
		public void onPressed(MouseEvent e) {}
		public void onClick(MouseEvent e) {}
	}; 
	
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
	
	
	public CalendarComp(LocalDate date, Practitioner practitioner) {
		super();
		this.practitioner = practitioner;
		if (practitioner == Practitioner.DENTIST) calendarColor = DENTIST_COLOUR;
		else if (practitioner == Practitioner.HYGIENIST) calendarColor = HYGIENIST_COLOUR;
		setDate(date);
		layout = new GridBagLayout();
		layout.columnWidths = new int[] {1, 1};
		layout.rowHeights = new int[((END-START)*60)/DIV];
		//set all slot sizes to SLOT_SIZE, later we just choose how many of these slots to use with gridheight
		Arrays.fill(layout.rowHeights,SLOT_SIZE);
		setLayout(layout);
		
		addAppointmentClickListener(calApointClick);
		addSlotClickListener(calSlotClick);
		
		showAll();
	}
		
	public void showAll() {
		removeAll();
		addHeaders();
		int[] times = new int[((END-START)*60)/DIV];
		for (AppointmentComp a : appointments) {
			if (a.start >= START) {
				a.addMouseListener(appointmentAdapter);
				GridBagConstraints c = new GridBagConstraints();
				int d = 0;
				do {
					times[((a.start+d)/DIV)-(START*(60/DIV))] = 1;
					d += DIV;
				} while (d < a.duration);
				c.gridy = HEADER_SIZE+(a.start-START*60)/DIV;
				c.gridheight = d/DIV;
				c.weightx = 1.0;
				c.fill = GridBagConstraints.BOTH;
				add(a, c);
			}
		}
		for (int t = 0; t < times.length; t++) {
			if (times[t] == 0) {
				GridBagConstraints c = new GridBagConstraints();
				times[t] = 1;
				c.gridy = HEADER_SIZE+t;
				c.weightx = 1.0;
				c.fill = GridBagConstraints.BOTH;
				EmptyAppointment gap = new EmptyAppointment(t+START*(60/DIV));
				gap.addMouseListener(slotAdapter);
				add(gap, c);
			}
		}
	}
	
	/***
	 * Adds creates two headers for this calendar.
	 * Headers are Dentist | Hygienist
	 */
	private void addHeaders() {
		//Add date
		GridBagConstraints c = new GridBagConstraints();
		c.fill = c.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		JLabel date = new JLabel(this.date.toString());
		date.setHorizontalAlignment(JLabel.CENTER);
		date.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		add(date, c);
		
		//Add dentist/hygienist label
		String p = ""; 
		if (practitioner == Practitioner.DENTIST) p = "Dentist";
		else if (practitioner == Practitioner.HYGIENIST) p = "Hygienist";
		
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		JLabel d = new JLabel(p);
		d.setOpaque(true);
		d.setHorizontalAlignment(JLabel.CENTER);
		d.setBackground(calendarColor);
		add(d, c);
	}
	
	/***
	 * Changes the date for this calender and fetches the new appointment information
	 * 
	 * @param date
	 */
	public void setDate(LocalDate date) {
		this.date = date;
		List<Appointment> appointmentsOnDate = AppointmentUtils.getAppointmentByDate(date);
		appointments.clear();
		for (Appointment a : appointmentsOnDate) {
			appointments.add(new AppointmentComp(a));
		}
		showAll();
	}
	
	/**
	 * Adds the given listener. When a empty slot is clicked the onClick/onPressed/onReleased 
	 * method of the listener will be called. 
	 * @param l - Listener to add
	 */
	public void addSlotClickListener(CalendarClickListener l) {
		slotListeners.add(l);
	}
	
	/**
	 * Adds the given listener. When an appointment is clicked the onClick/onPressed/onReleased 
	 * method of the listener will be called. 
	 * @param l - Listener to add
	 */
	public void addAppointmentClickListener(CalendarClickListener l) {
		appointmentListeners.add(l);
	}
	
	public void notifyAppointmentListeners(MouseEvent e) {
		for (CalendarClickListener l : appointmentListeners) {
			l.notify(e);
		}
	}

	public void notifySlotListeners(MouseEvent e) {
		for (CalendarClickListener l : slotListeners) {
			l.notify(e);
		}
	}
	
}

enum Practitioner {
	DENTIST, HYGIENIST;
}
