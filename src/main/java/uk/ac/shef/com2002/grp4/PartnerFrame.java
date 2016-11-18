package uk.ac.shef.com2002.grp4;

import java.awt.*;
import java.time.*;

import javax.swing.*;
import java.util.*;

import uk.ac.shef.com2002.grp4.calendar.CalendarClickListener;
import uk.ac.shef.com2002.grp4.calendar.CalendarView;

public class PartnerFrame extends JFrame {

	public PartnerFrame(Partner p) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800,600));
        
        JPanel calendar = new JPanel();
        JScrollPane scroll = new JScrollPane(calendar);

        ButtonGroup view = new ButtonGroup();
        JRadioButton day = new JRadioButton("Day");
        JRadioButton week = new JRadioButton("Week");
        view.add(day);
        view.add(week);
        day.setSelected(true);
        day.addActionListener((e) -> {
    		setCalendarMode(day.isSelected(), p, calendar);
        });
        week.addActionListener((e) -> {
    		setCalendarMode(day.isSelected(), p, calendar);
        });
        
        JPanel radioButtons = new JPanel();
        radioButtons.add(day);
        radioButtons.add(week);
        
        scroll.getVerticalScrollBar().setUnitIncrement(5);        
        add(radioButtons, BorderLayout.NORTH);
        add(scroll);
        
        setCalendarMode(true, p, calendar);
        
        pack();
        setVisible(true);
	}
	
	public void setCalendarMode(boolean day, Partner p, JPanel panel) {
		panel.removeAll();
		CalendarView.createCalendars(day, LocalDate.now(), p, panel, new ArrayList<CalendarClickListener>(), new ArrayList<CalendarClickListener>());
	}
	
	public static void main(String[] args) {
		new PartnerFrame(Partner.DENTIST);
	}
	
}
