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
	
	private static final Color DENTIST_COLOUR = new Color(255, 0, 0);
	private static final Color HYGIENIST_COLOUR = new Color(0, 0, 255);
	
	ArrayList<AppointmentComp> appointments = new ArrayList<AppointmentComp>();
	
	ArrayList<CalendarClickListener> appointmentListeners = new ArrayList<CalendarClickListener>();
	ArrayList<CalendarClickListener> slotListeners = new ArrayList<CalendarClickListener>();
	
	private CalendarClickListener calSlotClick = new CalendarClickListener() {
		
		public void onRelease(MouseEvent e) {}
		public void onClick(MouseEvent e) {}
		
		public void onPressed(MouseEvent e) {
			int time = ((EmptyAppointment)e.getSource()).getTime();
			LocalTime localTime = LocalTime.of(0, 0).plusMinutes(time*20);
			
			AppointmentFrame f = new AppointmentFrame(CalendarComp.this, date, localTime);
			f.setVisible(true);
			setDate(date); //refresh appointment list
		}
		
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
	
	
	public CalendarComp(LocalDate date) {
		super();
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
		int[][] times = new int[2][((END-START)*60)/DIV];
		for (AppointmentComp a : appointments) {
			Practitioner p = Practitioner.valueOf(a.getAppointment().getPractitioner().toUpperCase());
			if (a.start >= START) {
				a.addMouseListener(appointmentAdapter);
				if (p == Practitioner.DENTIST) {
					a.setBackground(DENTIST_COLOUR);
				} else {
					a.setBackground(HYGIENIST_COLOUR);
				}
				GridBagConstraints c = new GridBagConstraints();
				int d = 0;
				c.gridx = p.ordinal();
				do {
					times[p.ordinal()][((a.start+d)/DIV)-(START*(60/DIV))] = 1;
					d += DIV;
				} while (d < a.duration);
				c.gridy = HEADER_SIZE+(a.start-START*60)/DIV;
				c.gridheight = d/DIV;
				c.weightx = 1.0;
				c.fill = GridBagConstraints.BOTH;
				add(a, c);
			}
		}
		for (int i = 0; i < times.length; i++) {
			for (int t = 0; t < times[0].length; t++) {
				if (times[i][t] == 0) {
					GridBagConstraints c = new GridBagConstraints();
					c.gridx = i;
					times[i][t] = 1;
					c.gridy = HEADER_SIZE+t;
					c.weightx = 1.0;
					c.fill = GridBagConstraints.BOTH;
					EmptyAppointment gap = new EmptyAppointment(t+START*(60/DIV));
					gap.addMouseListener(slotAdapter);
					add(gap, c);
				}
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
		
		//Add dentist/hygienist labels
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 1;
		JLabel d = new JLabel("Dentist");
		d.setOpaque(true);
		d.setBackground(DENTIST_COLOUR);
		add(d, c);
		
		c.gridx = 1;
		JLabel h = new JLabel("Hygienist");
		h.setOpaque(true);
		h.setBackground(HYGIENIST_COLOUR);
		add(h, c);
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
