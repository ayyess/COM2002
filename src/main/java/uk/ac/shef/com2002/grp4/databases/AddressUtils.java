package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Address;

import java.sql.*;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class AddressUtils {
    public static Address getAddressByID(int id) {
        return ConnectionManager.withStatement("SELECT * FROM address WHERE id=?",(stmt)->{
            stmt.setInt(1,id);
            ResultSet res = stmt.executeQuery();
            return new Address(res.getInt(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6));
        });
    }

    public static int getAddressID(int houseNumber, String postcode) {
        return ConnectionManager.withStatement("SELECT id FROM address WHERE house_number=?, postcode=?",(stmt)->{
            stmt.setInt(1,houseNumber);
            stmt.setString(2,postcode);
            return stmt.executeQuery().getInt(1);
        });
    }

    public static void updateAddressByID(int id, int houseNumber, String street, String district, String city, String postcode) {
        ConnectionManager.withStatement("UPDATE address SET house_number=?, street=?, district=?, city=?, postcode=? WHERE id=?",(stmt)->{
            stmt.setInt(1, houseNumber);
            stmt.setString(2, street);
            stmt.setString(3, district);
            stmt.setString(4, city);
            stmt.setString(5, postcode);
            stmt.setInt(6, id);
            stmt.executeUpdate();
            return null;
        });
    }

    public static void insertAddress(int houseNumber, String street, String district, String city, String postcode) {
        ConnectionManager.withStatement("INSERT INTO address VALUES DEFAULT,?,?,?,?,?",(stmt)->{
            stmt.setInt(1,houseNumber);
            stmt.setString(2, street);
            stmt.setString(3, district);
            stmt.setString(4, city);
            stmt.setString(5, postcode);
            stmt.executeUpdate();
            return null;
        });
    }
}
