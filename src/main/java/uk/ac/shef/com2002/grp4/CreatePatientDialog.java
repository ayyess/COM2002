package uk.ac.shef.com2002.grp4;

import org.jdatepicker.JDatePicker;
import uk.ac.shef.com2002.grp4.data.Address;
import uk.ac.shef.com2002.grp4.data.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.time.*;
import java.util.Optional;

public class CreatePatientDialog extends BaseDialog implements ActionListener {
	private int row = 0;
	private JButton createButton;
	private JButton cancelButton;
	private JTextField titleField;
	private JTextField forenameField;
	private JTextField surnameField;
	private JDatePicker dobField;
	private JTextField phoneField;
	private AddressSelector addressField;

	private Optional<Patient> patient = Optional.empty();

	public CreatePatientDialog(Component owner){
		super(owner,"Create Patient");

		Container contentPane = rootPane.getContentPane();

		titleField = new JTextField();
		forenameField = new JTextField();
		surnameField = new JTextField();
		dobField = new JDatePicker();
		phoneField = new JTextField();
		phoneField.setInputVerifier(new InputVerifier() {
			@Override
			public boolean verify(JComponent input) {
				JTextField field = (JTextField)input;
				return field.getText().matches("^[0-9 \\-]+$");
			}
		});
		addressField = new AddressSelector();
		addLabeledInput("Title",titleField);
		addLabeledInput("Forename",forenameField);
		addLabeledInput("Surname",surnameField);
		addLabeledInput("DoB",dobField);
		addLabeledInput("Phone number",phoneField);
		addLabeledInput("Address",addressField);

		createButton = new JButton("Create");
		createButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		addButtons(cancelButton,createButton);

		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == createButton){
			String title = titleField.getText();
			String forename = forenameField.getText();
			String surname = surnameField.getText();
			Calendar cal = (Calendar) dobField.getModel().getValue();
			if(cal == null){
				JOptionPane.showMessageDialog(this,"You must select a date","Validation error",JOptionPane.ERROR_MESSAGE);
				return;
			}
			System.out.println(cal);
			LocalDate dob = LocalDate.of(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
			String phoneNumber = phoneField.getText();
			Optional<Long> optAddress = addressField.getAddress();

			if(!optAddress.isPresent()) {
				JOptionPane.showMessageDialog(this, "You must select an address", "Validation error", JOptionPane.ERROR_MESSAGE);
				return;
			}

			Patient patient = new Patient(title,forename,surname,dob,phoneNumber,optAddress);
			patient.save();
			this.patient = Optional.of(patient);

			dispose();

		}else if(e.getSource() == cancelButton){
			dispose();
		}
	}

	public Optional<Patient> getPatient() {
		return patient;
	}
}
