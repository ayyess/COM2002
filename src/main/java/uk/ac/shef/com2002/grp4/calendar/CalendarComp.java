package uk.ac.shef.com2002.grp4.calendar;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JComponent;

public class CalendarComp extends JComponent {

	GridBagLayout layout;
	
	final static int DIV = 20;
	final static int SLOT_SIZE = 30;
	
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
	
	
	public CalendarComp() {
		layout = new GridBagLayout();
		layout.columnWidths = new int[] {1};
		layout.rowHeights = new int[(24*60)/DIV];
		setLayout(layout);
		
		addAppointmentClickListener(calApointClick);
		addSlotClickListener(calSlotClick);
		
		showAll();
	}
		
	public void showAll() {
		removeAll();
		int[] times = new int[(24*60)/DIV];
		for (AppointmentComp a : appointments) {
			a.addMouseListener(appointmentAdapter);
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			int d = 0;
			do {
				times[(a.start+d)/DIV] = 1;
				d += DIV;
			} while (d < a.duration);
			c.gridy = a.start/DIV;
			c.gridheight = d/DIV;
			//c.weighty = d/DIV;
			int height = (d/DIV)*SLOT_SIZE;
			height = height < SLOT_SIZE ? SLOT_SIZE: height;
			Dimension size = new Dimension(100, height);
			//a.setMinimumSize(size);
			a.setPreferredSize(size);
			//a.setSize(size);
			c.gridwidth = 1;
			c.fill = c.BOTH;
			add(a, c);
		}
		for (int i = 0; i < times.length; i++) {
			if (times[i] == 0) {
				GridBagConstraints c = new GridBagConstraints();
				c.gridx = 0;
				times[i] = 1;
				c.gridy = i;
				c.gridheight = 1;
				c.gridwidth = 1;
				c.fill = c.BOTH;
				EmptyAppointment gap = new EmptyAppointment(i, 100, SLOT_SIZE);
				gap.addMouseListener(slotAdapter);
				add(gap, c);
			}
		}
	}
	
	public void addAppointment(AppointmentComp a) {
		appointments.add(a);
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
