/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */
package uk.ac.shef.com2002.grp4.partnerview;

import uk.ac.shef.com2002.grp4.common.Partner;
import uk.ac.shef.com2002.grp4.common.calendar.AppointmentComp;
import uk.ac.shef.com2002.grp4.common.calendar.CalendarClickListener;
import uk.ac.shef.com2002.grp4.common.calendar.CalendarView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

public class PartnerApp extends JFrame {

	JRadioButton day, week;
	CalendarView calendar;
	JScrollPane scroll;
	ButtonGroup view;

	JPanel buttonPanel;
	JButton bookDayButton;

	Partner partner;

	public PartnerApp(Partner partner) {
		setDefaultCloseOperation(EXIT_ON_CLOSE); // TODO remove and replace with below once merged
		//setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(800, 600));
		this.partner = partner;

		calendar = new CalendarView(partner);

		scroll = new JScrollPane(calendar);
		calendar.setDate(LocalDate.now());

		day = new JRadioButton("Day");
		day.setSelected(true);
		day.addActionListener((e) -> calendar.setView(day.isSelected()));
		week = new JRadioButton("Week");
		week.addActionListener((e) -> calendar.setView(day.isSelected()));

		view = new ButtonGroup();
		view.add(day);
		view.add(week);


		buttonPanel = new JPanel();
		buttonPanel.add(day);
		buttonPanel.add(week);
		bookDayButton = new JButton("Book reserved day");
		bookDayButton.addActionListener((e) -> bookDay());
		buttonPanel.add(bookDayButton);

		scroll.getVerticalScrollBar().setUnitIncrement(5);
		scroll.getVerticalScrollBar().setPreferredSize(new Dimension(25, 0));
		add(buttonPanel, BorderLayout.NORTH);
		add(scroll);

		calendar.addAppointmentClickListener(new CalendarClickListener() {

			public void onRelease(MouseEvent e) {
			}

			public void onPressed(MouseEvent e) {
				AppointmentComp comp = (AppointmentComp) e.getSource();
				AppointmentDetailsPartner f = new AppointmentDetailsPartner(PartnerApp.this, comp.getAppointment());
				f.setVisible(true);
				calendar.setDate(LocalDate.now());
			}

			public void onClick(MouseEvent e) {
			}
		});

		calendar.setView(day.isSelected());

		pack();

		//this makes it open centred
		setLocationRelativeTo(null);

		setVisible(true);
	}

	public void bookDay() {
		BookDayDialog bookDayDialog = new BookDayDialog(this, partner);
		bookDayDialog.setVisible(true);
	}

}
