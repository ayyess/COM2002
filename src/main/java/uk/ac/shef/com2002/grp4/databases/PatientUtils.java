package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Address;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class PatientUtils {
    private Connection con = null;
    private Statement stmt = null;

    public PatientUtils(Connection con) {
        this.con = con;
    }

    public Address getPatientByID(int id) {
        ResultSet res;
        /*
        try {
            stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM patient WHERE id="+id);
            return new Patient();
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
        */
        return null;
    }
}
