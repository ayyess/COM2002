/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.databases;

import uk.ac.shef.com2002.grp4.common.data.Plan;

import java.sql.ResultSet;
import java.util.*;

/**
 * Used to control database interaction.
 * Specifically the treatment_plans table
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   1/11/2016
 */
public class PlanUtils {

    /**
     * This will get the details of a TreatmentPlan by the plan name.
     *
     * @param s - the plan name
     * @return a new Plan object
     */
    public static Plan getDetailsByName(String s) {
        return ConnectionManager.withStatement("SELECT * FROM treatment_plans WHERE name=?",(stmt)-> {
            stmt.setString(1, s);
            ResultSet res = stmt.executeQuery();
            return new Plan(s, res.getInt(2), res.getInt(3), res.getInt(4), res.getInt(5));
        });
    }

    /**
     * This will get a list of available TreatmentPlans.
     *
     * @return an ArrayList containing all TreatmentPlans
     */
    public static List<Plan> getTreatmentPlans() {
        ArrayList<Plan> plans = new ArrayList<Plan>();

        ConnectionManager.withStatement("SELECT * FROM treatment_plans",(stmt)-> {
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                plans.add(new Plan(res.getString(1), res.getInt(2), res.getInt(3), res.getInt(4), res.getInt(5)));
            }
            return null;
        });
		return plans;
    }
}
