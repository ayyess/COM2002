package uk.ac.shef.com2002.grp4.databases;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class AddressUtils {

    private Connection con = null;
    private Statement stmt = null;

    //Gets connection with remote server
    public AddressUtils(Connection con) {
        this.con = con;
    }

    public Address getAddressByID(int id) {
        try {
            stmt = con.createStatement();
            resultSet res = stmt.executeQuery("SELECT * FROM address WHERE id="+id);
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

        return new Address(res.getInt(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6));
    }
}
