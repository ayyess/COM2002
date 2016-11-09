package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Treatment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Dan-L on 09/11/2016.
 */
public class TreatmentUtils {

    private Connection con = null;
    private PreparedStatement stmt = null;

    public TreatmentUtils(Connection con) {
        this.con = con;
    }

    public Treatment getCostByName(String s) {
        try {
            stmt = con.prepareStatement("SELECT cost FROM treatment WHERE name=?");
            stmt.setString(1, s);
            ResultSet res = stmt.executeQuery();
            return new Treatment(s, res.getInt(1));
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

    public Treatment[] getTreatments() {
        ArrayList<Treatment> treatments = new ArrayList<Treatment>();
        ResultSet res;
        try {
            stmt = con.prepareStatement("SELECT * FROM treatment");
            res = stmt.executeQuery();
            while (res.next()) {
                treatments.add(new Treatment(res.getString(1), res.getInt(2)));
            }
            Treatment[] tp = new Treatment[treatments.size()];
            treatments.toArray(tp);
            return tp;
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
}
