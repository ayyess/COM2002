package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Address;

import java.sql.*;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class AddressUtils {

    private Connection con = null;
    private PreparedStatement stmt = null;

    public AddressUtils(Connection con) {
        this.con = con;
    }


    public Address getAddressByID(int id) {
        ResultSet res;
        try {
            stmt = con.prepareStatement("SELECT * FROM address WHERE id=?");
            stmt.setInt(1,id);
            res = stmt.executeQuery();
            return new Address(res.getInt(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6));
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public int getAddressID(int houseNumber, String postcode) {
        try {
            stmt = con.prepareStatement("SELECT id FROM address WHERE house_number=?, postcode=?");
            stmt.setInt(1,houseNumber);
            stmt.setString(2,postcode);
            return stmt.executeQuery().getInt(1);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    public void updateAddressByID(int id, int houseNumber, String street, String district, String city, String postcode) {
        try {
            stmt = con.prepareStatement("UPDATE address SET house_number=?, street=?, district=?, city=?, postcode=? WHERE id=?");
            stmt.setInt(1, houseNumber);
            stmt.setString(2, street);
            stmt.setString(3, district);
            stmt.setString(4, city);
            stmt.setString(5, postcode);
            stmt.setInt(6, id);
            int count = stmt.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void insertAddress(int houseNumber, String street, String district, String city, String postcode) {
        try {
            stmt = con.prepareStatement("SELECT MAX(id) FROM address");
            int new_id = stmt.executeQuery().getInt(1) + 1;
            stmt = con.prepareStatement("INSERT INTO address VALUES ?,?,?,?,?,?");
            stmt.setInt(1, new_id);
            stmt.setInt(2,houseNumber);
            stmt.setString(3, street);
            stmt.setString(4, district);
            stmt.setString(5, city);
            stmt.setString(6, postcode);
            int count = stmt.executeUpdate();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
