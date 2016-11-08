package uk.ac.shef.com2002.grp4;

import java.awt.BorderLayout;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Properties;

import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.UtilDateModel;

import uk.ac.shef.com2002.grp4.calendar.AppointmentComp;
import uk.ac.shef.com2002.grp4.calendar.CalendarComp;
import uk.ac.shef.com2002.grp4.data.Appointment;

/**
 * Panel for appointment interaction workflow
 *
 * Created on 28/10/2016.
 */
public class AppointmentPanel extends JPanel {
	public class DateLabelFormatter extends AbstractFormatter {

		private String datePattern = "yyyy-MM-dd";
		private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}

			return "";
		}

	}
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
			JScrollPane p = new JScrollPane();
			CalendarComp c = new CalendarComp();
			c.addAppointment(new AppointmentComp(new Appointment(LocalDate.now(), LocalTime.of(12, 0), LocalTime.of(13, 0), "Dentist")));
			c.addAppointment(new AppointmentComp(new Appointment(LocalDate.now(), LocalTime.of(14, 30), LocalTime.of(15, 0), "Dentist")));
			c.addAppointment(new AppointmentComp(new Appointment(LocalDate.now(), LocalTime.of(16, 20), LocalTime.of(16, 50), "Dentist")));
			p.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			p.getViewport().add(c);

			//p.setPreferredSize(new Dimension(200, 600));
			this.add(p, BorderLayout.CENTER);
		}
	}
}
