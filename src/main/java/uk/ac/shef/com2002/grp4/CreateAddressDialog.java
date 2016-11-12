package uk.ac.shef.com2002.grp4;

import uk.ac.shef.com2002.grp4.data.Address;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

/**
 * <Doc here>
 * <p>
 * Created on 11/11/2016.
 */
public class CreateAddressDialog extends BaseDialog implements ActionListener {

	private JButton cancelButton;
	private JButton createButton;
	private JTextField houseNumberField;
	private JTextField streetField;
	private JTextField districtField;
	private JTextField cityField;
	private JTextField postcodeField;
	private Optional<Address> address = Optional.empty();

	public CreateAddressDialog(Component c){
		super(c,"Create Address");
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		createButton = new JButton("Create");
		createButton.addActionListener(this);
		houseNumberField = new JTextField(20);
		streetField = new JTextField(20);
		districtField = new JTextField(20);
		cityField = new JTextField(20);
		postcodeField = new JTextField(20);
		addLabeledInput("House Number",houseNumberField);
		addLabeledInput("Street",streetField);
		addLabeledInput("District",districtField);
		addLabeledInput("City",cityField);
		addLabeledInput("Postcode",postcodeField);

		addButtons(cancelButton,createButton);

		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cancelButton){
			dispose();
		}else if(e.getSource() == createButton){
			Address address = new Address(Integer.parseInt(houseNumberField.getText()),
			                              streetField.getText(),
			                              districtField.getText(),
			                              cityField.getText(),
			                              postcodeField.getText());
			address.save();
			this.address = Optional.of(address);
			dispose();
		}
	}

	public Optional<Address> getAddress() {
		return address;
	}
}
