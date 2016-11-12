package uk.ac.shef.com2002.grp4.calendar;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	
	ArrayList<AppointmentComp> appointments = new ArrayList<AppointmentComp>();
	
	ArrayList<CalendarClickListener> appointmentListeners = new ArrayList<CalendarClickListener>();
	ArrayList<CalendarClickListener> slotListeners = new ArrayList<CalendarClickListener>();
	
	/** TODO Remove... This is an example for a empty slot listener */
	private CalendarClickListener calSlotClick = new CalendarClickListener() {
		
		@Override
		public void onRelease(MouseEvent e) {
			int time = ((EmptyAppointment)e.getSource()).getTime();
			System.out.println("Slot released " + e.getSource().getClass() + time);

		}
		
		@Override
		public void onPressed(MouseEvent e) {
			int time = ((EmptyAppointment)e.getSource()).getTime();
			System.out.println("Slot pressed " + e.getSource().getClass() + time);
			LocalTime localTime = LocalTime.of(0, 0).plusMinutes(time*20);
			
			AppointmentFrame f = new AppointmentFrame(null, date, localTime);
			f.setVisible(true);
			setDate(date); //refresh appointment list
			System.out.println("here");
		}
		
		@Override
		public void onClick(MouseEvent e) {
			int time = ((EmptyAppointment)e.getSource()).getTime();
			System.out.println("Slot click " + e.getSource().getClass() + time);
		}
	}; 
	
	/** TODO Remove... This is an example for an appointment listener */
	private CalendarClickListener calApointClick = new CalendarClickListener() {
		
		@Override
		public void onRelease(MouseEvent e) {
			System.out.println("Appointment released " + e.getSource().getClass());
		}
		
		@Override
		public void onPressed(MouseEvent e) {
			System.out.println("Appointment pressed " + e.getSource().getClass());
		}
		
		@Override
		public void onClick(MouseEvent e) {
			System.out.println("Appointment click " + e.getSource().getClass());
		}
	}; 
	
	private MouseAdapter appointmentAdapter = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			notifyAppointmentListener(e);
		}
		public void mousePressed(MouseEvent e) {
			notifyAppointmentListener(e);
		}
		public void mouseReleased(MouseEvent e) {
			notifyAppointmentListener(e);
		}
	};
	
	private MouseAdapter slotAdapter = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			notifySlotListener(e);
		}
		public void mousePressed(MouseEvent e) {
			notifySlotListener(e);
		}
		public void mouseReleased(MouseEvent e) {
			notifySlotListener(e);
		}
	};
	
	
	public CalendarComp(LocalDate date) {
		super();
		setDate(date);
		layout = new GridBagLayout();
		layout.columnWidths = new int[] {1};
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
		int[] times = new int[((END-START)*60)/DIV];
		for (AppointmentComp a : appointments) {
			if (a.start >= START) {
				a.addMouseListener(appointmentAdapter);
				GridBagConstraints c = new GridBagConstraints();
				int d = 0;
				c.gridx = 0;
				do {
					times[((a.start+d)/DIV)-(START*(60/DIV))] = 1;
					d += DIV;
				} while (d < a.duration);
				c.gridy = (a.start-START*60)/DIV;
				c.gridheight = d/DIV;
				c.weightx = 1.0;
				c.fill = GridBagConstraints.BOTH;
				add(a, c);
			}
		}
		for (int i = 0; i < times.length; i++) {
			if (times[i] == 0) {
				GridBagConstraints c = new GridBagConstraints();
				c.gridx = 0;
				times[i] = 1;
				c.gridy = i;
				c.weightx = 1.0;
				c.fill = GridBagConstraints.BOTH;
				EmptyAppointment gap = new EmptyAppointment(i+START*(60/DIV));
				gap.addMouseListener(slotAdapter);
				add(gap, c);
			}
		}
	}
	
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
	
	public void notifyAppointmentListener(MouseEvent e) {
		for (CalendarClickListener l : appointmentListeners) {
			l.notify(e);
		}
	}

	public void notifySlotListener(MouseEvent e) {
		for (CalendarClickListener l : slotListeners) {
			l.notify(e);
		}
	}
	
}
