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
import uk.ac.shef.com2002.grp4.common.databases.AddressUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

/**
 * This class is creates a dialog where the user can find a patient in the database.
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   11/11/2016
 */
public class FindAddressDialog extends BaseDialog implements ActionListener {
	/** A text field where the postcode can be entered. */
	private final JTextField postcodeField;
	/** A list of addresses. */
	private final JList<Address> searchResults;
	/** This stores the Address object of the selected address. */
	private Optional<Address> address = Optional.empty();
	/** A button to cancel the process. */
	private final JButton cancelButton;
	/** A button to select an address. */
	private final JButton selectButton;

	/**
	 * A constructor that creates the dialog box.
	 *
	 * @param owner - Takes a component that will parent the dialog
	 */
	public FindAddressDialog(Component owner) {
		super(owner,"Find address");

		Container contentPane = rootPane.getContentPane();

		postcodeField = new JTextField();
		postcodeField.addActionListener(this);
		addLabeledComponent("Postcode",postcodeField);

		searchResults = new JList<>(new DefaultListModel<>());
		GridBagConstraints c = getBaseConstraints();
		c.gridwidth = 2;

		contentPane.add(new JScrollPane(searchResults),c);

		nextRow();

		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		selectButton = new JButton("Select");
		selectButton.addActionListener(this);

		addButtons(cancelButton,selectButton);

		pack();
	}

	/**
	 * Listens for an action to be performed and completes a certain action.
	 *
	 * @param e - an ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == postcodeField) {
			List<Address> addresses = AddressUtils.findAddresses(postcodeField.getText());
			DefaultListModel<Address> model = (DefaultListModel<Address>) searchResults.getModel();
			model.clear();
			for (Address a : addresses) {
				model.addElement(a);
			}
		} else if (e.getSource() == selectButton) {
			address = Optional.ofNullable(searchResults.getSelectedValue());
			dispose();
		} else if (e.getSource() == cancelButton) {
			dispose();
		}
	}

	/**
	 * This gets the Address object.
	 * --Optional
	 *
	 * @return the Address object
	 */
	public Optional<Address> getAddress() {
		return address;
	}
}
