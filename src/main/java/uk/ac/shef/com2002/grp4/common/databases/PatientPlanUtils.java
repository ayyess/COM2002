/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.databases;

import uk.ac.shef.com2002.grp4.common.data.PatientPlan;

import java.sql.ResultSet;
import java.time.LocalDate;

/**
 * Used to control database interaction.
 * Specifically the patient_plans table
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   1/11/2016
 */
public class PatientPlanUtils {

    /**
     * This retrieves a particular patient's plan from the database.
     *
     * @param id - a patient_id
     * @return a new PatientPlan object
     */
    public static PatientPlan getPlanByPatientID(long id) {
        return ConnectionManager.withStatement("SELECT * FROM patient_plans JOIN treatment_plans ON patient_plans.plan_name = treatment_plans.name WHERE patient_id=?",(stmt)-> {
            stmt.setLong(1, id);
            ResultSet res = stmt.executeQuery();
            return new PatientPlan(res.getInt(1),res.getString(2), res.getInt(7), res.getDate(3).toLocalDate(), res.getInt(4), res.getInt(5), res.getInt(6));
        });
    }

    /**
     * This increases the amount of used checkups on a Patient Plan.
     *
     * @param id - a patient_id
     */
    public static void completeCheckUp(long id) {
        ConnectionManager.withStatement("UPDATE patient_plans SET used_checkups=used_checkups + 1 WHERE patient_id=?",(stmt)-> {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            return  null;
        });
    }

    /**
     * This increases the amount of used hygiene visits on a Patient Plan.
     *
     * @param id - a patient_id
     */
    public static void completeHygieneVisit(long id) {
        ConnectionManager.withStatement("UPDATE patient_plans SET used_hygiene_visits=used_hygiene_visits + 1 WHERE patient_id=?",(stmt)-> {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            return  null;
        });
    }

    /**
     * This increases the amount of used repairs on a Patient Plan.
     *
     * @param id - a patient_id
     */
    public static void completeRepair(long id) {
        ConnectionManager.withStatement("UPDATE patient_plans SET used_repairs=used_repairs + 1 WHERE patient_id=?",(stmt)-> {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            return  null;
        });
    }

    /**
     * This resets the plan if a year has passed since it was last reset.
     */
    public static void resetPlans() {
        ConnectionManager.withStatement("UPDATE patient_plans SET reset_date=NOW(), used_checkups=0, used_hygiene_visits=0, used_repairs=0 WHERE reset_date < DATE_SUB(NOW(),INTERVAL 1 YEAR)",(stmt)-> {
            stmt.executeUpdate();
            return  null;
        });
    }

    /**
     * This updates a Patient Plan by the Patient's ID.
     *
     * @param id - a patient_id
     * @param name - new Treatment Plan name
     * @param startDate - new start date
     */
	public static void updateById(long id,String name, LocalDate startDate){
		ConnectionManager.withStatement("UPDATE patient_plans SET plan_name=?, reset_date=?, used_checkups=?, used_hygiene_visits=?, used_repairs=?, renew_date =? WHERE patient_id = ?",(stmt)-> {
			stmt.setString(1,name);
			stmt.setDate(2,java.sql.Date.valueOf(startDate));
			stmt.setLong(6,id);
            stmt.executeUpdate();
            return  null;
        });
	}
}
