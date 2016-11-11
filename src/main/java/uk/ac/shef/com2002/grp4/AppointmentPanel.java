package uk.ac.shef.com2002.grp4;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.UtilDateModel;
import uk.ac.shef.com2002.grp4.calendar.AppointmentComp;
import uk.ac.shef.com2002.grp4.calendar.CalendarComp;
import uk.ac.shef.com2002.grp4.data.Appointment;
import uk.ac.shef.com2002.grp4.databases.AppointmentUtils;
import uk.ac.shef.com2002.grp4.util.DPIScaling;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

/**
 * Panel for appointment interaction workflow
 *
 * Created on 28/10/2016.
 */
public class AppointmentPanel extends JPanel {
	//TODO searching appointments
	//TODO creating appointments
	public AppointmentPanel () {
		super(new BorderLayout());
		UtilDateModel model = new UtilDateModel();
		//model.setDate(20,04,2014);
		// Need this...
		{

			JDatePanel datePanel = new JDatePanel(model);
			datePanel.getModel().addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						UtilDateModel source = (UtilDateModel) e.getSource();
						System.out.println("here");
						System.out.println(source.getValue());
						// AppointmentPanel.this.updateDat
					}
				});
			int dpiScaling = (int)DPIScaling.get();
			datePanel.setPreferredSize(new Dimension(200*dpiScaling,180*dpiScaling));
			datePanel.setSize(new Dimension(200*dpiScaling,180*dpiScaling));

			JPanel datePanelContainer = new JPanel(new BorderLayout());
			datePanelContainer.add(datePanel, BorderLayout.NORTH);
			this.add(datePanelContainer ,BorderLayout.LINE_START);
		}
		//this.add(new JLabel ("Hello") ,BorderLayout.LINE_END);

		{
			JScrollPane scroll = new JScrollPane();
			CalendarComp calendar = new CalendarComp(LocalDate.now());
			//FIXME Probably not the correct way to connect but it works for now
			//TODO add date lookup based on selected date in JDatePicker
			List<Appointment> appointments = AppointmentUtils.getAppointmentByDate(LocalDate.now());
			for (Appointment a : appointments) {
				calendar.addAppointment(new AppointmentComp(a));
			}
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scroll.getViewport().add(calendar);

			//p.setPreferredSize(new Dimension(200, 600));
			this.add(scroll, BorderLayout.CENTER);
		}
	}
}
