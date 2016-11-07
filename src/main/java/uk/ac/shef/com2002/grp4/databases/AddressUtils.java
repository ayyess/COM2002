package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Address;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class AddressUtils {

    private Connection con = null;
    private Statement stmt = null;

    public AddressUtils(Connection con) {
        this.con = con;
    }


    public Address getAddressByID(int id) {
        ResultSet res;
        try {
            stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM address WHERE id="+id);
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
            stmt = con.createStatement();
            return stmt.executeQuery("SELECT id FROM address WHERE house_number=" + houseNumber + ", postcode=" + postcode).getInt(1);
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
            stmt = con.createStatement();
            int count = stmt.executeUpdate("UPDATE address SET house_number =" +
                    houseNumber + ", street =" + street + ", district =" +
                    district + ", city =" + city + ", postcode =" + postcode +"WHERE id=" + id);

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
            stmt = con.createStatement();
            int new_id = stmt.executeQuery("SELECT MAX(id) FROM address").getInt(1) + 1;
            int count = stmt.executeUpdate("INSERT INTO address VALUES " +
                            new_id + "," + houseNumber + "," + street + "," + district + "," + city + "," + postcode);

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
