/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.data;

import uk.ac.shef.com2002.grp4.common.databases.AddressUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Optional;

/**
 * Used to store the address details temporarily
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   1/11/2016
 */
public class Address {

	/**
	 * This stores the address_id.
	 * --Optional
	 */
	private Optional<Long> id;
	/** This stores the house number. */
	private int houseNumber;
	/** This stores the street. */
	private String street;
	/** This stores the district. */
	private String district;
	/** This stores the city. */
	private String city;
	/** This stores the postcode. */
	private String postcode;

	//TODO add creation from db?

	/**
	 * This constructor create an Address object with no address_id.
	 * This is for when a new address is being created and it hasn't
	 * been assigned.
	 *
	 * @param houseNumber - the House Number
	 * @param street - the Street
	 * @param district - the District
	 * @param city - the City
	 * @param postcode - the Postcode
	 */
	public Address(int houseNumber, String street, String district, String city, String postcode) {
		this(Optional.empty(),houseNumber,street,district,city,postcode);
	}

	/**
	 * This constructor creates an Address object.
	 *
	 * @param id - the address_id
	 * @param houseNumber - the House Number
	 * @param street - the Street
	 * @param district - the District
	 * @param city - the City
	 * @param postcode - the Postcode
	 */
	public Address(Optional<Long> id,int houseNumber, String street, String district, String city, String postcode) {
		this.id = id;
		this.houseNumber = houseNumber;
		this.street = street;
		this.district = district;
		this.city = city;
		this.postcode = postcode;
	}

	/**
	 * This formats the String to include line breaks.
	 *
	 * @return a formatted String
	 */
	public String formatted() {
		return houseNumber + " " + street + "\n" + district + "\n" + city + "\n" + postcode;
	}

	/**
	 * This returns the object as a single string.
	 *
	 * @return a String representing the Address
	 */
	public String toString() {
		return houseNumber + " " + street + ", " + district + ", " + city + ", " + postcode;
	}

	/** This saves the address permanently in the database. */
	public void save() {
		if (id.isPresent()) {
			AddressUtils.updateAddressByID(id.get(),houseNumber,street,district,city,postcode);
		} else {
			id = Optional.of(AddressUtils.insertAddress(houseNumber,street,district,city,postcode));
		}
	}

	/** This gets the address_id.
	 * @return address_id
	 */
	public long getId() {
		return id.orElse(null);
	}

	/** This gets the house number.
	 * @return houseNumber
	 */
	public int getHouseNumber() {
		return houseNumber;
	}

	/** This gets the street.
	 * @return street
	 */
	public String getStreet() {
		return street;
	}

	/** This gets the district.
	 * @return district
	 */
	public String getDistrict() {
		return district;
	}

	/** This gets the city.
	 * @return city
	 */
	public String getCity() {
		return city;
	}

	/** This gets the postcode.
	 * @return postcode
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * This tests if two addresses are equal to each other.
	 *
	 * @param obj - an Object
	 * @return - a Boolean which is true if the two objects are equal
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Address) {
			final Address other = (Address) obj;
			return new EqualsBuilder()
			       .append(houseNumber, other.houseNumber)
			       .append(street, other.street)
			       .append(district, other.district)
			       .append(city, other.city)
			       .append(postcode, other.postcode)
			       .isEquals();
		} else {
			return false;
		}
	}

	/**
	 * Returns a hash code for this object.
	 *
	 * @return - a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
		       .append(houseNumber)
		       .append(street)
		       .append(district)
		       .append(city)
		       .append(postcode)
		       .toHashCode();
	}
}
