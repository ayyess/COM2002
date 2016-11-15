/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Address;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class AddressUtils {
    public static Address getAddressByID(long id) {
        return ConnectionManager.withStatement("SELECT * FROM addresses WHERE id=?",(stmt)->{
            stmt.setLong(1,id);
            ResultSet res = stmt.executeQuery();
			res.next();
            return new Address(Optional.of(res.getLong(1)),res.getInt(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6));
        });
    }

	public static List<Address> findAddresses(String postcode) {
		List<Address> addresses = new ArrayList<>();
		ConnectionManager.withStatement("SELECT * FROM addresses WHERE postcode=?",(stmt)->{
            stmt.setString(1,postcode);
            ResultSet res = stmt.executeQuery();
			while(res.next()){
				addresses.add(new Address(Optional.of(res.getLong(1)),res.getInt(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6)));
			}
			return null;
        });
		return addresses;

	}

    public static int getAddressID(int houseNumber, String postcode) {
        return ConnectionManager.withStatement("SELECT id FROM addresses WHERE house_number=?, postcode=?",(stmt)->{
            stmt.setInt(1,houseNumber);
            stmt.setString(2,postcode);
            return stmt.executeQuery().getInt(1);
        });
    }

    public static void updateAddressByID(long id, int houseNumber, String street, String district, String city, String postcode) {
        ConnectionManager.withStatement("UPDATE addresses SET house_number=?, street=?, district=?, city=?, postcode=? WHERE id=?",(stmt)->{
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

    public static long insertAddress(int houseNumber, String street, String district, String city, String postcode) {
        return ConnectionManager.withStatement("INSERT INTO addresses VALUES (DEFAULT,?,?,?,?,?)",(stmt)->{
            stmt.setInt(1,houseNumber);
            stmt.setString(2, street);
            stmt.setString(3, district);
            stmt.setString(4, city);
            stmt.setString(5, postcode);
            stmt.executeUpdate();
			ResultSet generatedKeys = stmt.getGeneratedKeys();
			generatedKeys.next();
            return generatedKeys.getLong(1);
        });
    }
}
