/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDate;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdatepicker.JDatePanel;
import org.jdatepicker.UtilDateModel;

import uk.ac.shef.com2002.grp4.calendar.CalendarView;
import uk.ac.shef.com2002.grp4.util.DPIScaling;

/**
 * Panel for appointment interaction workflow
 *
 * Created on 28/10/2016.
 */
public class AppointmentPanel extends JPanel {

	// TODO searching appointments
	// TODO creating appointments
	public AppointmentPanel() {
		super(new BorderLayout());

		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		
		CalendarView calendar = new CalendarView(LocalDate.now());
		
		UtilDateModel model = new UtilDateModel();
		JDatePanel datePanel = new JDatePanel(model);

		datePanel.getModel().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				UtilDateModel source = (UtilDateModel) e.getSource();
				calendar.setDate(LocalDate.of(source.getYear(), source.getMonth() + 1, source.getDay()));
			}
		});
		int dpiScaling = (int) DPIScaling.get();
		datePanel.setPreferredSize(new Dimension(200 * dpiScaling, 180 * dpiScaling));
		datePanel.setSize(new Dimension(200 * dpiScaling, 180 * dpiScaling));

		JPanel datePanelContainer = new JPanel(new BorderLayout());
		datePanelContainer.add(datePanel, BorderLayout.NORTH);
		leftPanel.add(datePanelContainer);
		this.add(leftPanel, BorderLayout.LINE_START);

		this.add(calendar, BorderLayout.CENTER);
	}
}
