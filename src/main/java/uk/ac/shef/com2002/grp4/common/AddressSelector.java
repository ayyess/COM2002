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
 * This class allows the user to select an address if known already, and therefore
 * in the database. Or the user can choose to create a new address.
 * <br>
 *
 * @author Group 4
 * @version 1.4
 * @since 08/11/2016
 */
public class AddressSelector extends JPanel implements ActionListener {

	/**
	 * This stores the area where the output will be displayed.
	 */
	private AddressComponent displayField;

	/**
	 * This stores the find address button.
	 */
	private JButton findAddressButton;

	/**
	 * This stores the create address button.
	 */
	private JButton createAddressButton;

	/**
	 * This stores the address if it exists.
	 */
	private Optional<Address> address = Optional.empty();

	/**
	 * This constructor initialises the address selector output.
	 */
	public AddressSelector() {
		super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0, 0, 0, 0);
		c.gridwidth = 2;
		c.gridx = 0;
		c.weighty = 1;
		c.fill = GridBagConstraints.BOTH;
		displayField = new AddressComponent(Optional.empty());
		displayField.setEnabled(false);
		add(displayField, c);

		c.weighty = 0;
		c.gridwidth = 1;
		c.gridx = 0;
		findAddressButton = new JButton("Find");
		findAddressButton.addActionListener(this);
		add(findAddressButton, c);
		c.gridx = 1;
		createAddressButton = new JButton("Create");
		createAddressButton.addActionListener(this);
		add(createAddressButton, c);
	}

	/**
	 * This function gets the Address.
	 * <br>
	 *
	 * @return an Address object if it exists.
	 */
	public Optional<Address> getAddress() {
		return address;
	}

	/**
	 * This function sets the address and gets the addresses details.
	 * <br>
	 *
	 * @param address - This is (optionally) an Address object. It may or may not exist.
	 */
	public void setAddress(Optional<Address> address) {
		this.address = address;
		displayField.setAddress(address);
		//FIXME there has to be a better way to get the dialog to resize if needed
		SwingUtilities.getWindowAncestor(this).pack();
	}

	/**
	 * This function behaves if a certain action is performed.
	 * <br>
	 * If the find address button is pressed, it creates a new finder dialog
	 * and outputs the address.
	 * If the create address button is pressed, it creates a new creator dialog
	 * and allows the user to create a new address.
	 *
	 * @param e - an ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == findAddressButton) {
			FindAddressDialog finder = new FindAddressDialog(this);
			finder.setVisible(true);
			setAddress(finder.getAddress());
		} else if (e.getSource() == createAddressButton) {
			CreateAddressDialog creator = new CreateAddressDialog(this);
			creator.setVisible(true);
			setAddress(creator.getAddress());
		}
	}
}
