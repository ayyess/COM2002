/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.databases;

import uk.ac.shef.com2002.grp4.common.data.Address;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Used to control database interaction.
 * Specifically the addresses table
 * <br>
 *
 * @author Group 4
 * @version 1.0
 * @since 1/11/2016
 */
public class AddressUtils {
	/**
	 * This gets an Address from the Database by its ID.
	 *
	 * @param id - an address_id
	 * @return an Address object
	 */
	public static Address getAddressByID(long id) {
		return ConnectionManager.withStatement("SELECT * FROM addresses WHERE id=?", (stmt) -> {
			stmt.setLong(1, id);
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				return new Address(Optional.of(res.getLong(1)), res.getInt(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6));
			} else {
				return null;
			}
		});
	}

	/**
	 * This gets all Addresses with a particular postcode.
	 *
	 * @param postcode - a postcode
	 * @return - an ArrayList of Address(es)
	 */
	public static List<Address> findAddresses(String postcode) {
		List<Address> addresses = new ArrayList<>();
		ConnectionManager.withStatement("SELECT * FROM addresses WHERE postcode=?", (stmt) -> {
			stmt.setString(1, postcode);
			ResultSet res = stmt.executeQuery();
			while (res.next()) {
				addresses.add(new Address(Optional.of(res.getLong(1)), res.getInt(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6)));
			}
			return null;
		});
		return addresses;

	}

	/**
	 * This gets all Addresses with a particular postcode.
	 *fuzzyFindAddresses
	 * @param postcode - a postcode
	 * @return - an ArrayList of Address(es)
	 */
	public static List<Address> fuzzyFindAddresses(String postcode) {
		List<Address> addresses = new ArrayList<>();
		ConnectionManager.withStatement("SELECT * FROM addresses WHERE postcode LIKE ?", (stmt) -> {
			stmt.setString(1, "%"+postcode+"%");
			ResultSet res = stmt.executeQuery();
			while (res.next()) {
				addresses.add(new Address(Optional.of(res.getLong(1)), res.getInt(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6)));
			}
			return null;
		});
		return addresses;

	}

	/**
	 * This gets the id of an address by its house_number and postcode.
	 *
	 * @param houseNumber - a houseNumber
	 * @param postcode    - a postcode
	 * @return an ID
	 */
	public static int getAddressID(int houseNumber, String postcode) {
		return ConnectionManager.withStatement("SELECT id FROM addresses WHERE house_number=?, postcode=?", (stmt) -> {
			stmt.setInt(1, houseNumber);
			stmt.setString(2, postcode);
			ResultSet res = stmt.executeQuery();
			if (res.next()) {
				return res.getInt(1);
			} else {
				return null;
			}
		});
	}

	/**
	 * This updates the address in the database by its ID.
	 *
	 * @param id          - the id of the address to be updated
	 * @param houseNumber - the new house number
	 * @param street      - the new street
	 * @param district    - the new district
	 * @param city        - the new city
	 * @param postcode    - the new postcode
	 */
	public static void updateAddressByID(long id, int houseNumber, String street, String district, String city, String postcode) {
		ConnectionManager.withStatement("UPDATE addresses SET house_number=?, street=?, district=?, city=?, postcode=? WHERE id=?", (stmt) -> {
			stmt.setInt(1, houseNumber);
			stmt.setString(2, street);
			stmt.setString(3, district);
			stmt.setString(4, city);
			stmt.setString(5, postcode);
			stmt.setLong(6, id);
			stmt.executeUpdate();
			return null;
		});
	}

	/**
	 * This inserts a new address into the database.
	 *
	 * @param houseNumber - the house number
	 * @param street      - the street
	 * @param district    - the district
	 * @param city        - the city
	 * @param postcode    - the postcode
	 * @return the id of the new address
	 */
	public static long insertAddress(int houseNumber, String street, String district, String city, String postcode) {
		return ConnectionManager.withStatement("INSERT INTO addresses VALUES (DEFAULT,?,?,?,?,?)", (stmt) -> {
			stmt.setInt(1, houseNumber);
			stmt.setString(2, street);
			stmt.setString(3, district);
			stmt.setString(4, city);
			stmt.setString(5, postcode);
			stmt.executeUpdate();
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				return generatedKeys.getLong(1);
			} else {
				return null;
			}
		});
	}
}
