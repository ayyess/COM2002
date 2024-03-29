/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.secretaryview;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.UtilDateModel;
import uk.ac.shef.com2002.grp4.common.calendar.*;
import uk.ac.shef.com2002.grp4.common.util.DPIScaling;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Panel for appointment interaction workflow
 *
 * The panel shows a date picker and a calendar view of appointments.
 * The date picker updates the calendar's date.
 * <p>
 * Created on 28/10/2016.
 */
public class AppointmentPanel extends JPanel {

	/**
	 * Constructs a panel showing a date picker and a view of the calendar
	 */
	public AppointmentPanel() {
		super(new BorderLayout());

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

		CalendarView calendar = new CalendarView(LocalDate.now());

		UtilDateModel model = new UtilDateModel();
		JDatePanel datePanel = new JDatePanel(model);

		datePanel.getModel().addChangeListener(e -> {
			UtilDateModel source = (UtilDateModel) e.getSource();
			calendar.setDate(LocalDate.of(source.getYear(), source.getMonth() + 1, source.getDay()));
		});
		int dpiScaling = (int) DPIScaling.get();
		datePanel.setPreferredSize(new Dimension(200 * dpiScaling, 180 * dpiScaling));
		datePanel.setSize(new Dimension(200 * dpiScaling, 180 * dpiScaling));

		JPanel datePanelContainer = new JPanel(new BorderLayout());
		datePanelContainer.add(datePanel, BorderLayout.NORTH);
		leftPanel.add(datePanelContainer);
		this.add(leftPanel, BorderLayout.LINE_START);

		this.add(calendar, BorderLayout.CENTER);

		calendar.addSlotClickListener(new CalendarClickListener() {

			public void onRelease(MouseEvent e) {
				EmptyAppointment slot = (EmptyAppointment) e.getSource();
				CalendarComp c = ((CalendarComp) slot.getParent());
				LocalTime time = slot.getTime();
				AppointmentCreationFrame f = new AppointmentCreationFrame(calendar, c.getDate(), time, slot.getPartner());
				f.setVisible(true);
				//Update
				calendar.setDate(calendar.getDate());
			}

			public void onClick(MouseEvent e) {
			}

			public void onPressed(MouseEvent e) {
			}

		});

		calendar.addAppointmentClickListener(new CalendarClickListener() {

			public void onRelease(MouseEvent e) {
				//Show details about the appointment maybe?
			}

			public void onPressed(MouseEvent e) {
				AppointmentComp comp = (AppointmentComp) e.getSource();
				AppointmentFrame f = new AppointmentFrame(calendar, comp.getAppointment());
				f.setVisible(true);
				//Update
				calendar.setDate(calendar.getDate());
			}

			public void onClick(MouseEvent e) {
			}
		});

		calendar.setDate(LocalDate.now());

	}

}
