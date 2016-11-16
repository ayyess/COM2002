/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.calendar;

import uk.ac.shef.com2002.grp4.data.Appointment;
import uk.ac.shef.com2002.grp4.databases.AppointmentUtils;

import java.awt.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class CalendarView extends JPanel {

	/* True if day false if week */
	private boolean day = false; 
	
	private LocalDate startDate; 
	
	JTabbedPane tabbedPane = new JTabbedPane();
	
	JPanel dentistPanel = new JPanel(new GridBagLayout());
	JPanel hygienstPanel = new JPanel(new GridBagLayout());
	
	public CalendarView(LocalDate startDate) {
		this.startDate = startDate;
		this.setLayout(new BorderLayout());
		
		tabbedPane.addTab("Dentist", createScrollPane(dentistPanel));
		tabbedPane.addTab("Hygienist", createScrollPane(hygienstPanel));
		this.add(tabbedPane, BorderLayout.CENTER);
		setView(day);
	}
	
	public void setDate(LocalDate date) {
		startDate = date;
		setView(day);
	}
	
	public void setView(boolean day) {
		this.day = day;
		createCalendars(day, startDate, Practitioner.DENTIST, dentistPanel);
		createCalendars(day, startDate, Practitioner.HYGIENIST, hygienstPanel);
	}
	
	public static void createCalendars(boolean day, LocalDate date, Practitioner p, JPanel panel) {
		CalendarComp[] calendars = new CalendarComp[7];
		panel.removeAll();
		GridBagLayout layout = new GridBagLayout();
		layout.columnWidths = new int[] {1,1,1,1,1,1,1};
		layout.columnWeights = new double[] {1,1,1,1,1,1,1};
		panel.setLayout(layout);
		if (day) {
			List<Appointment> appointments = AppointmentUtils.getAppointmentByDate(date);
			GridBagConstraints constraints = new GridBagConstraints();
			calendars[0] = new CalendarComp(appointments.stream().filter((appt)->appt.getPractitioner().toUpperCase().equals(p.toString())).collect(Collectors.toList()), date, p);
			constraints.gridx = 0;
			constraints.gridwidth = 7;
			constraints.fill = GridBagConstraints.BOTH;
			panel.add(calendars[0], constraints);
		} else {
			List<Appointment> appointments = AppointmentUtils.getAppointmentByDateRange(date,date.plusDays(calendars.length-1));
			System.out.println();
			for (int i = 0; i < calendars.length; i++) {
				LocalDate dayDate = date.plusDays(i);
				GridBagConstraints constraints = new GridBagConstraints();
				CalendarComp c = new CalendarComp(appointments.stream().filter((appt)->
					appt.getPractitioner().toUpperCase().equals(p.toString())
							&& appt.getDate().equals(dayDate)
				).collect(Collectors.toList()),dayDate, p);
				calendars[i] = c;
				constraints.gridheight = 1;
				constraints.gridx = i;
				constraints.fill = GridBagConstraints.BOTH;
				panel.add(c, constraints);
			}
		}
		panel.revalidate();
	}
	
	private static JScrollPane createScrollPane(JPanel child) {
		JScrollPane scroll = new JScrollPane();
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.getVerticalScrollBar().setUnitIncrement(5);
		scroll.getViewport().add(child);
		return scroll;
	}
	
}
