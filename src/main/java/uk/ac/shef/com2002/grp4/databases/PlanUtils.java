package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Plan;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Dan-L on 09/11/2016.
 */
public class PlanUtils {

    public static Plan getDetailsByName(String s) {
        return ConnectionManager.withStatement("SELECT * FROM treatment_plan WHERE name=?",(stmt)-> {
            stmt.setString(1, s);
            ResultSet res = stmt.executeQuery();
            return new Plan(s, res.getInt(2), res.getInt(3), res.getInt(4), res.getInt(5));
        });
    }

    public static Plan[] getTreatmentPlans() {
        ArrayList<Plan> plans = new ArrayList<Plan>();

        return ConnectionManager.withStatement("SELECT * FROM treatment_plan",(stmt)-> {
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                plans.add(new Plan(res.getString(1), res.getInt(2), res.getInt(3), res.getInt(4), res.getInt(5)));
            }
            Plan[] pl = new Plan[plans.size()];
            plans.toArray(pl);
            return pl;
        });
    }
}
