/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
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
		addLabeledComponent("House Number",houseNumberField);
		addLabeledComponent("Street",streetField);
		addLabeledComponent("District",districtField);
		addLabeledComponent("City",cityField);
		addLabeledComponent("Postcode",postcodeField);

		addButtons(cancelButton,createButton);

		pack();
	}

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

	public Optional<Address> getAddress() {
		return address;
	}
}
