package uk.ac.shef.com2002.grp4;

import javax.swing.*;
import java.util.Optional;
import uk.ac.shef.com2002.grp4.data.Address;

public class AddressComponent extends BaseInfoComponent{
	private Optional<Address> address;

	private final JTextField houseNumberField;

	private final JTextField streetField;

	private final JTextField districtField;

	private final JTextField cityField;

	private final JTextField postcodeField;


	public AddressComponent(Address address){
		this(Optional.ofNullable(address));
	}
	public AddressComponent(Optional<Address> address){
		super();

		houseNumberField = new JTextField("");
		houseNumberField.setEditable(false);
		addLabeledComponent("House Number",houseNumberField);

		streetField = new JTextField("");
		streetField.setEditable(false);
		addLabeledComponent("Street",streetField);

		districtField = new JTextField("");
		districtField.setEditable(false);
		addLabeledComponent("District",districtField);

		cityField = new JTextField("");
		cityField.setEditable(false);
		addLabeledComponent("City",cityField);

		postcodeField = new JTextField("");
		postcodeField .setEditable(false);
		addLabeledComponent("Postcode",postcodeField );

		setAddress(address);

	}


	public void setAddress(Address address){
		setAddress(Optional.ofNullable(address));
	}

	public void setAddress(Optional<Address> newAddress){
		address = newAddress;
		if(address.isPresent()){
			houseNumberField.setText(Integer.toString(address.get().getHouseNumber()));
			streetField.setText(address.get().getStreet());
			districtField.setText(address.get().getDistrict());
			cityField.setText(address.get().getCity());
			postcodeField.setText(address.get().getPostcode());

		}else{
			houseNumberField.setText("");
			streetField.setText("");
			districtField.setText("");
			cityField.setText("");
			postcodeField.setText("");
		}
	}
}
