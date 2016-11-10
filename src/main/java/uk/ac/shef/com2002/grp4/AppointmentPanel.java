package uk.ac.shef.com2002.grp4;

import java.awt.BorderLayout;
import java.sql.Date;
import java.util.Calendar;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import uk.ac.shef.com2002.grp4.calendar.AppointmentComp;
import uk.ac.shef.com2002.grp4.calendar.CalendarComp;
import uk.ac.shef.com2002.grp4.data.Appointment;
import uk.ac.shef.com2002.grp4.databases.AppointmentUtils;

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
			Properties p = new Properties();
			p.put("text.today", "Today");
			p.put("text.month", "Month");
			p.put("text.year", "Year");
			JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
			datePanel.getModel().addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						UtilDateModel source = (UtilDateModel) e.getSource();
						System.out.println("here");
						System.out.println(source.getValue());
						// AppointmentPanel.this.updateDat
					}
				});
			this.add(datePanel ,BorderLayout.LINE_START);
		}
		//this.add(new JLabel ("Hello") ,BorderLayout.LINE_END);

		{
			JScrollPane scroll = new JScrollPane();
			Calendar currenttime = Calendar.getInstance();
			Date date = new Date((currenttime.getTime()).getTime());

			CalendarComp calendar = new CalendarComp(date);
			//FIXME Probably not the correct way to connect but it works for now
			//TODO add date lookup based on selected date in JDatePicker
			List<Appointment> appointments = AppointmentUtils.getAppointmentByDate(
					Date.valueOf(LocalDate.now()));
			for (Appointment a : appointments) {
				calendar.addAppointment(new AppointmentComp(a));
			}
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scroll.getViewport().add(calendar);

			this.add(scroll, BorderLayout.CENTER);
		}
	}
}
