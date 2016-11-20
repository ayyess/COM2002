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
import java.util.Optional;

/**
 * Used to output address details to the GUI.
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   13/11/2016
 */
public class AddressComponent extends BaseInfoComponent {

	/** This is used to store the Address object. */
	private Optional<Address> address;

	/** This text field stores the house number. */
	private final JTextField houseNumberField;

	/** This text field stores the street name. */
	private final JTextField streetField;

	/** This text field stores the district. */
	private final JTextField districtField;

	/** This text field stores the city. */
	private final JTextField cityField;

	/** This text field stores the postcode. */
	private final JTextField postcodeField;


	/**
	 * This constructor takes an Address object and makes the component.
	 *
	 * @param address - an Address object
	 */
	public AddressComponent(Address address) {
		this(Optional.ofNullable(address));
	}

	/**
	 * This constructor optionally takes an Address object and makes the component.
	 *
	 * @param address - an Address object
	 *                --Optional
	 */
	public AddressComponent(Optional<Address> address) {
		super();

		houseNumberField = new JTextField(20);
		houseNumberField.setEditable(false);
		addLabeledComponent("House Number",houseNumberField);

		streetField = new JTextField(5);
		streetField.setEditable(false);
		addLabeledComponent("Street",streetField);

		districtField = new JTextField(5);
		districtField.setEditable(false);
		addLabeledComponent("District",districtField);

		cityField = new JTextField(5);
		cityField.setEditable(false);
		addLabeledComponent("City",cityField);

		postcodeField = new JTextField(5);
		postcodeField .setEditable(false);
		addLabeledComponent("Postcode",postcodeField );

		setAddress(address);

	}


	/**
	 * This sets the Address of the component.
	 *
	 * @param address - an Address object
	 */
	public void setAddress(Address address) {
		setAddress(Optional.ofNullable(address));
	}

	/**
	 * This sets the Address of the component.
	 *
	 * @param newAddress - an Address object
	 *                   --Optional
	 */
	public void setAddress(Optional<Address> newAddress) {
		address = newAddress;
		if (address.isPresent()) {
			houseNumberField.setText(Integer.toString(address.get().getHouseNumber()));
			streetField.setText(address.get().getStreet());
			districtField.setText(address.get().getDistrict());
			cityField.setText(address.get().getCity());
			postcodeField.setText(address.get().getPostcode());

		} else {
			houseNumberField.setText("");
			streetField.setText("");
			districtField.setText("");
			cityField.setText("");
			postcodeField.setText("");
		}
	}
}
