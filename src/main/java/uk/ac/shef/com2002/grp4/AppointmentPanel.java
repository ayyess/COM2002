package uk.ac.shef.com2002.grp4;

import java.awt.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import java.util.Calendar;
import java.util.Date;
import org.jdatepicker.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import uk.ac.shef.com2002.grp4.calendar.CalendarComp;
import uk.ac.shef.com2002.grp4.calendar.AppointmentComp;

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

			JDatePanel datePanel = new JDatePanel(model);
			datePanel.getModel().addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						UtilDateModel source = (UtilDateModel) e.getSource();
						System.out.println("here");
						System.out.println(source.getValue());
						// AppointmentPanel.this.updateDat
					}
				});
			datePanel.setPreferredSize(new Dimension(200*3,180*3));
			datePanel.setSize(new Dimension(200*3,180*3));

			JPanel datePanelContainer = new JPanel(new BorderLayout());
			datePanelContainer.add(datePanel, BorderLayout.NORTH);
			this.add(datePanelContainer ,BorderLayout.LINE_START);
		}
		//this.add(new JLabel ("Hello") ,BorderLayout.LINE_END);

		{
			JScrollPane p = new JScrollPane();
			CalendarComp c = new CalendarComp();
			Calendar calS = Calendar.getInstance();
			Calendar calE = Calendar.getInstance();
			calS.set(2016, 11, 2, 10, 40, 0);
			calE.set(2016, 11, 2, 11, 40, 0);
			c.addAppointment(new AppointmentComp(calS,calE));
			calS.set(2016, 11, 2, 16, 0, 0);
			calE.set(2016, 11, 2, 16, 40, 0);
			c.addAppointment(new AppointmentComp(calS,calE));
			calS.set(2016, 11, 2, 20, 0, 0);
			calE.set(2016, 11, 2, 21, 40, 0);
			c.addAppointment(new AppointmentComp(calS,calE));
			p.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			p.getViewport().add(c);

			//p.setPreferredSize(new Dimension(200, 600));
			this.add(p, BorderLayout.CENTER);
		}
	}
}
