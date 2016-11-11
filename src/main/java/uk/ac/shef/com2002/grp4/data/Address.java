package uk.ac.shef.com2002.grp4.data;

import uk.ac.shef.com2002.grp4.databases.AddressUtils;

import java.util.Optional;

public class Address {

	private Optional<Long> id;
	private int houseNumber;
	private String street;
	private String district;
	private String city;
	private String postcode;

	//TODO add creation from db?

	public Address(int houseNumber, String street, String district, String city, String postcode) {
		this(Optional.empty(),houseNumber,street,district,city,postcode);
	}
	
	public Address(Optional<Long> id,int houseNumber, String street, String district, String city, String postcode) {
		this.id = id;
		this.houseNumber = houseNumber;
		this.street = street;
		this.district = district;
		this.city = city;
		this.postcode = postcode;
	}
	
	public String toString() {
		return houseNumber + " " + street + ", " + district + ", " + city + ", " + postcode;
	}

	public void save() {
		if(id.isPresent()){
			AddressUtils.updateAddressByID(id.get(),houseNumber,street,district,city,postcode);
		}else{
			id = Optional.of(AddressUtils.insertAddress(houseNumber,street,district,city,postcode));
		}
	}

	public Optional<Long> getId() {
		return id;
	}
}
