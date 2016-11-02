package uk.ac.shef.com2002.grp4.calendar;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.DateFormatter;


public class CalendarComp extends JComponent {

	GridBagLayout layout;
	
	final static int DIV = 20;
	final static int SLOT_SIZE = 30;
	
	ArrayList<Appointment> appointments = new ArrayList<Appointment>();
	
	public CalendarComp() {
		layout = new GridBagLayout();
		layout.columnWidths = new int[] {1};
		layout.rowHeights = new int[(24*60)/DIV];
		setLayout(layout);
		showAll();
	}
		
	public void showAll() {
		System.out.println("Ignore");
		removeAll();
		int[] times = new int[(24*60)/DIV];
		for (Appointment a : appointments) {
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = 0;
			int d = 0;
			do {
				times[(a.start+d)/DIV] = 1;
				d += DIV;
			} while (d < a.duration);
			c.gridy = a.start/DIV;
			System.out.println(a.start + ", " + a.end + " At" + c.gridy + " Span" + (((d/DIV)*10)+40));
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
				JPanel gap = new JPanel();
				gap.setBorder(BorderFactory.createLineBorder(Color.black));
				gap.setPreferredSize(new Dimension(100, SLOT_SIZE));
				gap.setMaximumSize(new Dimension(100, SLOT_SIZE));
				gap.setBackground(Color.WHITE);
				add(gap, c);
			}
		}
	}
	
	public void addAppointment(Appointment a) {
		appointments.add(a);
		showAll();
	}
	
}

class Appointment extends JComponent {
	
	Calendar startC;
	Calendar endC;
	
	int start;
	int end;
	int duration;
	
	public Appointment(Calendar start, Calendar end) {
		this.startC = start;
		this.endC = end;
		this.start = convertMins(start);
		this.end = convertMins(end);
		this.duration = this.end - this.start;
		
		setLayout(new FlowLayout());
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createLineBorder(Color.black));
		
		SimpleDateFormat f = new SimpleDateFormat("h:M");		
		add(new JLabel("S"+f.format(start.getTime())));
		add(new JLabel("D"+duration));
		add(new JLabel("E"+f.format(end.getTime())));
	}
	
	public static int convertMins(Calendar c) {
		return c.get(Calendar.HOUR)*60 + c.get(Calendar.MINUTE);
	}
	
	public static void main(String[] args) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JScrollPane p = new JScrollPane();
		CalendarComp c = new CalendarComp();
		Calendar calS = Calendar.getInstance();
		Calendar calE = Calendar.getInstance();
		calS.set(2016, 11, 2, 10, 40, 0);
		calE.set(2016, 11, 2, 11, 40, 0);
		c.addAppointment(new Appointment(calS,calE));
		calS.set(2016, 11, 2, 16, 0, 0);
		calE.set(2016, 11, 2, 16, 40, 0);
		c.addAppointment(new Appointment(calS,calE));
		calS.set(2016, 11, 2, 20, 0, 0);
		calE.set(2016, 11, 2, 21, 40, 0);
		c.addAppointment(new Appointment(calS,calE));
		p.setVerticalScrollBarPolicy(p.VERTICAL_SCROLLBAR_ALWAYS);
		p.getViewport().add(c);
		f.add(p);
		f.setVisible(true);
	}
	
}
