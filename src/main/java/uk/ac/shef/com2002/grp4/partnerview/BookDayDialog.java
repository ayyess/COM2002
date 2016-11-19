package uk.ac.shef.com2002.grp4.partnerview;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import org.jdatepicker.JDatePicker;

import uk.ac.shef.com2002.grp4.common.BaseDialog;
import uk.ac.shef.com2002.grp4.common.Partner;
import uk.ac.shef.com2002.grp4.common.databases.AppointmentUtils;

public class BookDayDialog extends BaseDialog {

	final JDatePicker datePicker;
	final JSpinner daysSpinner;
	final SpinnerNumberModel dayModel;
	
	final JButton cancel;
	final JButton book;
	
	Partner partner;
	
	public BookDayDialog(Frame c, Partner partner) {
		super(c, "Book day");
		this.partner = partner;
		
		datePicker = new JDatePicker();
		addLabeledComponent("Start date", datePicker);
		dayModel = new SpinnerNumberModel(1, 1, 7, 1);
		daysSpinner = new JSpinner(dayModel);
		addLabeledComponent("Number of days", daysSpinner);
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
		
		book = new JButton("Book");
		book.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				book();
				dispose();
			}
		});
		
		addButtons(book, cancel);
		pack();
	}
	
	void book() {
		LocalDate date = LocalDate.of(datePicker.getModel().getYear(), datePicker.getModel().getMonth()+1, datePicker.getModel().getDay());
		for (int i = 0; i < dayModel.getNumber().intValue(); i++) {
			AppointmentUtils.insertAppointment(date.plusDays(i), partner.toString(), 0l, LocalTime.of(9, 0), Duration.ofHours(8), false);
		}
	}
	
}