package uk.ac.shef.com2002.grp4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.Calendar;

import org.jdatepicker.*;
import uk.ac.shef.com2002.grp4.databases.PatientUtils;
import uk.ac.shef.com2002.grp4.util.DPIScaling;

public class CreatePatientDialog extends JDialog implements ActionListener {
	private int row = 0;
	private JButton createButton;
	private JTextField titleField;
	private JTextField forenameField;
	private JTextField surnameField;
	private JDatePicker dobField;
	private JTextField phoneField;

	protected GridBagConstraints getBaseConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		c.gridy=row;
		return c;
	}
	protected void addLabeledInput(String label,JComponent input){
		Container contentPane = rootPane.getContentPane();
		GridBagConstraints c = getBaseConstraints();

		c.fill=GridBagConstraints.HORIZONTAL;
		c.anchor=GridBagConstraints.EAST;
		contentPane.add(new JLabel(label+":"),c);
		c.anchor=GridBagConstraints.WEST;
		contentPane.add(input,c);

		row++;
	}
	public CreatePatientDialog(JFrame owner){
		super(owner,"Create Patient",false);
		Container contentPane = rootPane.getContentPane();
		contentPane.setLayout(new GridBagLayout());

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
		addLabeledInput("Title",titleField);
		addLabeledInput("Forename",forenameField);
		addLabeledInput("Surname",surnameField);
		addLabeledInput("DoB",dobField);
		addLabeledInput("Phone number",phoneField);

		GridBagConstraints c = getBaseConstraints();

		c.gridwidth=2;
		createButton = new JButton("Create");
		createButton.addActionListener(this);
		contentPane.add(createButton,c);

		row++;

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
			Date dob = new Date(cal.getTimeInMillis());
			String phoneNumber = phoneField.getText();
			int addressId = -1;//FIXME need to provide a way to search for, or create an address
			PatientUtils.insertPatient(title,forename,surname,dob,phoneNumber,addressId);
		}
	}
}
