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

    /*
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
    }
    */
}
