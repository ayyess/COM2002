package uk.ac.shef.com2002.grp4;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import uk.ac.shef.com2002.grp4.calendar.AppointmentComp;
import uk.ac.shef.com2002.grp4.calendar.AppointmentFrame;
import uk.ac.shef.com2002.grp4.calendar.CalendarClickListener;
import uk.ac.shef.com2002.grp4.calendar.CalendarView;

public class PartnerFrame extends JFrame {

	public PartnerFrame(Partner p) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(800,600));
        
        CalendarView calendar = new CalendarView(p);
        JScrollPane scroll = new JScrollPane(calendar);
        calendar.setDate(LocalDate.now());
        ButtonGroup view = new ButtonGroup();
        JRadioButton day = new JRadioButton("Day");
        JRadioButton week = new JRadioButton("Week");
        view.add(day);
        view.add(week);
        day.setSelected(true);
        day.addActionListener((e) -> {
        	calendar.setView(day.isSelected());
        });
        week.addActionListener((e) -> {
        	calendar.setView(day.isSelected());
        });
        
        JPanel radioButtons = new JPanel();
        radioButtons.add(day);
        radioButtons.add(week);
        
        scroll.getVerticalScrollBar().setUnitIncrement(5);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(25, 0));
        add(radioButtons, BorderLayout.NORTH);
        add(scroll);
        
        calendar.addAppointmentClickListener(new CalendarClickListener() {

			public void onRelease(MouseEvent e) {}
			
			public void onPressed(MouseEvent e) {
				AppointmentComp comp = (AppointmentComp)e.getSource();
				AppointmentDetailsPartner f = new AppointmentDetailsPartner(PartnerFrame.this, comp.getAppointment());
				f.setVisible(true);
				calendar.setDate(LocalDate.now());
			}
			public void onClick(MouseEvent e) {}
		}); 
        
        calendar.setView(day.isSelected());
        
        pack();
        setVisible(true);
	}
	
	public static void main(String[] args) {
		new PartnerFrame(Partner.DENTIST);
	}
	
}
