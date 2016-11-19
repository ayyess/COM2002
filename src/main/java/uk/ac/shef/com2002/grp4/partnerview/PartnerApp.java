/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */
package uk.ac.shef.com2002.grp4.partnerview;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import uk.ac.shef.com2002.grp4.common.Partner;
import uk.ac.shef.com2002.grp4.common.calendar.AppointmentComp;
import uk.ac.shef.com2002.grp4.common.calendar.CalendarClickListener;
import uk.ac.shef.com2002.grp4.common.calendar.CalendarView;

public class PartnerApp extends JFrame {

	public PartnerApp(Partner p) {
		setDefaultCloseOperation(EXIT_ON_CLOSE); // TODO remove and replace with below once merged
		//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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
				AppointmentDetailsPartner f = new AppointmentDetailsPartner(PartnerApp.this, comp.getAppointment());
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
		new PartnerApp(Partner.DENTIST);
	}
	
}
