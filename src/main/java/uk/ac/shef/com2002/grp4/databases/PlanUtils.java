/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Plan;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Dan-L on 09/11/2016.
 */
public class PlanUtils {

    public static Plan getDetailsByName(String s) {
        return ConnectionManager.withStatement("SELECT * FROM treatment_plans WHERE name=?",(stmt)-> {
            stmt.setString(1, s);
            ResultSet res = stmt.executeQuery();
            return new Plan(s, res.getInt(2), res.getInt(3), res.getInt(4), res.getInt(5));
        });
    }

    public static Plan[] getTreatmentPlans() {
        ArrayList<Plan> plans = new ArrayList<Plan>();

        return ConnectionManager.withStatement("SELECT * FROM treatment_plans",(stmt)-> {
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
