/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common;

import uk.ac.shef.com2002.grp4.common.data.Address;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

/**
 * This class is creates a dialog where the user can input a new address.
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   11/11/2016
 */
public class CreateAddressDialog extends BaseDialog implements ActionListener {

	/** A button to cancel the process. */
	private JButton cancelButton;
	/** A buttom to create the address. */
	private JButton createButton;
	/** A field to store the house number. */
	private JTextField houseNumberField;
	/** A field to store the street. */
	private JTextField streetField;
	/** A field to store the district. */
	private JTextField districtField;
	/** A field to store the city. */
	private JTextField cityField;
	/** A field to store the postcode. */
	private JTextField postcodeField;
	/** A field to store the Address object.
	 * --Optional
	 */
	private Optional<Address> address = Optional.empty();

	/**
	 * A constructor that creates the dialog box.
	 *
	 * @param c - Takes a component that will parent the dialog
	 */
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
		addLabeledComponent("House Number",houseNumberField);
		addLabeledComponent("Street",streetField);
		addLabeledComponent("District",districtField);
		addLabeledComponent("City",cityField);
		addLabeledComponent("Postcode",postcodeField);

		addButtons(cancelButton,createButton);

		pack();
	}

	/**
	 * Listens for an action to be performed and completes a certain action.
	 *
	 * @param e - an ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cancelButton){
			cancel();
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

	/**
	 * This gets the Address object.
	 * --Optional
	 *
	 * @return the Address object/
	 */
	public Optional<Address> getAddress() {
		return address;
	}
}
