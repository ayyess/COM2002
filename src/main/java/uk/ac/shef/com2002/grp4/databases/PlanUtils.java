package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Plan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Dan-L on 09/11/2016.
 */
public class PlanUtils {
    private Connection con = null;
    private PreparedStatement stmt = null;

    public PlanUtils(Connection con) {
        this.con = con;
    }

    public Plan getDetailsByName(String s) {
        try {
            stmt = con.prepareStatement("SELECT * FROM treatment_plan WHERE name=?");
            stmt.setString(1, s);
            ResultSet res = stmt.executeQuery();
            return new Plan(s, res.getInt(2), res.getInt(3), res.getInt(4), res.getInt(5));
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

    public Plan[] getTreatmentPlans() {
        ArrayList<Plan> plans = new ArrayList<Plan>();
        ResultSet res;
        try {
            stmt = con.prepareStatement("SELECT * FROM treatment_plan");
            res = stmt.executeQuery();
            while (res.next()) {
                plans.add(new Plan(res.getString(1), res.getInt(2), res.getInt(3), res.getInt(4), res.getInt(5)));
            }
            Plan[] pl = new Plan[plans.size()];
            plans.toArray(pl);
            return pl;
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
