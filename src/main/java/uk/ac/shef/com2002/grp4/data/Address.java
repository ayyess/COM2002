package uk.ac.shef.com2002.grp4.data;

public class Address {

	private int houseNumber;
	private String street;
	private String district;
	private String city;
	private String postcode;

	//TODO add creation from db?
	
	public String toString() {
		return houseNumber + " " + street + ", " + district + ", " + city + ", " + postcode;
	}
	
}
