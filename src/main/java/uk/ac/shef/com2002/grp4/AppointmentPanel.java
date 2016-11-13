package uk.ac.shef.com2002.grp4;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDate;

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
		leftPanel.setLayout(new FlowLayout());
		
		JScrollPane scroll = new JScrollPane();
		CalendarView calendar = new CalendarView(LocalDate.now());
		
		UtilDateModel model = new UtilDateModel();
		JDatePanel datePanel = new JDatePanel(model);

		ButtonGroup viewButtons = new ButtonGroup();
		JRadioButton dayView = new JRadioButton("Day");
		JRadioButton weekView = new JRadioButton("Week");

		dayView.addActionListener((e) -> {
			calendar.setView(dayView.isSelected());
		});
		weekView.setSelected(true);
		weekView.addActionListener((e) -> {
			calendar.setView(dayView.isSelected());
		});
		
		viewButtons.add(dayView);
		viewButtons.add(weekView);

		leftPanel.add(dayView, BorderLayout.LINE_START);
		leftPanel.add(weekView, BorderLayout.LINE_START);

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

		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.getVerticalScrollBar().setUnitIncrement(5);
		scroll.getViewport().add(calendar);
		this.add(scroll, BorderLayout.CENTER);
	}
}
