package uk.ac.shef.com2002.grp4.partnerview;

import org.jdatepicker.JDatePicker;
import uk.ac.shef.com2002.grp4.common.BaseDialog;
import uk.ac.shef.com2002.grp4.common.Partner;
import uk.ac.shef.com2002.grp4.common.UserFacingException;
import uk.ac.shef.com2002.grp4.common.databases.AppointmentUtils;
import uk.ac.shef.com2002.grp4.common.databases.PatientUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Used to book whole days to prevent appointments from being made 
 * while the partner is not available
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   13/11/2016
 */
public class BookDayDialog extends BaseDialog {

	/** The date picker to select which days to book */
	final JDatePicker datePicker;
	/** Spinner to select the number of days to book stating from the date chosen */
	final JSpinner daysSpinner;
	/** Simple integer number model for the spinner */
	final SpinnerNumberModel dayModel;
	
	/** Cancel button to cancel the dialog if needed */
	final JButton cancel;
	/** Book button to confirm the reservation of the days */
	final JButton book;
	/** The partner attempting to book days */
	Partner partner;
	
	/**
	 * Constructs a new day booking dialog for the given partner
	 * @param c Frame - The parent frame   
	 * @param partner Partner - The partner who is booking
	 */
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
				try {
					book();
					dispose();
				} catch (UserFacingException exception) {
					exception.showError();
				}
			}
		});
		
		addButtons(book, cancel);
		pack();
	}
	
	/**
	 * Creates a Reserved appointment within the database using the 'reserved' patient
	 */
	void book() {
		LocalDate date = LocalDate.of(datePicker.getModel().getYear(), datePicker.getModel().getMonth()+1, datePicker.getModel().getDay());
		for (int i = 0; i < dayModel.getNumber().intValue(); i++) {
			AppointmentUtils.insertAppointment(date.plusDays(i), partner.toString(), PatientUtils.getReservedPatient().getId(), LocalTime.of(9, 0), Duration.ofHours(8), false);
		}
	}
	
}
