/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4;

import uk.ac.shef.com2002.grp4.data.Address;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

/**
 * This class allows the user to select whether a patients address is known already, and therefore
 * in the database. Or the user can choose to create a new address.
 * <br>
 * @author  Group 4
 * @version 1.4
 * @since   08/11/2016
 */
public class AddressSelector extends JPanel implements ActionListener {

	/** This stores the area where the output will be displayed. */
	private JTextArea displayField;

	/** This stores the find address button. */
	private JButton findAddressButton;

	/** This stores the create address button. */
	private JButton createAddressButton;

	/** This stores the address if it exists. */
	private Optional<Address> address = Optional.empty();

	/**
	 * This constructor initialises the address selector output.
	 */
	public AddressSelector(){
		super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,0,0,0);
		c.gridheight = 2;
		c.gridy = 0;
		c.gridx = 0;
		c.weightx = 1;
		c.fill = GridBagConstraints.BOTH;
		displayField = new JTextArea("");
		displayField.setEnabled(false);
		add(displayField,c);

		c.weightx = 0;
		c.gridheight = 1;
		c.gridx = 1;
		findAddressButton = new JButton("Find");
		findAddressButton.addActionListener(this);
		add(findAddressButton,c);
		c.gridy = 1;
		createAddressButton = new JButton("Create");
		createAddressButton.addActionListener(this);
		add(createAddressButton,c);
	}

	/**
	 * This function sets the address and gets the addresses details.
	 * <br>
	 * @param address - This is (optionally) an Address object. It may or may not exist.
	 */
	public void setAddress(Optional<Address> address){
		this.address = address;
		if(address.isPresent()) {
			displayField.setText(address.get().formatted());
		} else{
			displayField.setText("");
		}
		//FIXME there has to be a better way to get the dialog to resize if needed
		SwingUtilities.getWindowAncestor(this).pack();
	}

	/**
	 * This function gets the Address.
	 * <br>
	 * @return an Address object if it exists.
	 */
	public Optional<Address> getAddress(){
		return address;
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
		if(e.getSource() == findAddressButton){
			FindAddressDialog finder = new FindAddressDialog(this);
			finder.setVisible(true);
			setAddress(finder.getAddress());
		} else if(e.getSource() == createAddressButton){
			CreateAddressDialog creator = new CreateAddressDialog(this);
			creator.setVisible(true);
			setAddress(creator.getAddress());
		}
	}
}
