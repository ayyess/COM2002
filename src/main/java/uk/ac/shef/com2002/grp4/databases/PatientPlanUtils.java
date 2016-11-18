/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.PatientPlan;

import java.sql.ResultSet;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class PatientPlanUtils {

    public static PatientPlan getPlanByPatientID(long id) {
        return ConnectionManager.withStatement("SELECT * FROM patient_plans JOIN treatment_plans ON patient_plans.plan_name = treatment_plans.name WHERE patient_id=?",(stmt)-> {
            stmt.setLong(1, id);
            ResultSet res = stmt.executeQuery();
            return new PatientPlan(res.getInt(1),res.getString(2), res.getInt(7), res.getDate(3).toLocalDate(), res.getInt(4), res.getInt(5), res.getInt(6));
        });
    }

    public static void completeCheckUp(long id) {
        ConnectionManager.withStatement("UPDATE patient_plans SET used_checkups=used_checkups + 1 WHERE patient_id=?",(stmt)-> {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            return  null;
        });
    }

    public static void completeHygieneVisit(long id) {
        ConnectionManager.withStatement("UPDATE patient_plans SET used_hygiene_visits=used_hygiene_visits + 1 WHERE patient_id=?",(stmt)-> {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            return  null;
        });
    }

    public static void completeRepair(long id) {
        ConnectionManager.withStatement("UPDATE patient_plans SET used_repairs=used_repairs + 1 WHERE patient_id=?",(stmt)-> {
            stmt.setLong(1, id);
            stmt.executeUpdate();
            return  null;
        });
    }

    public static void resetPlans() {
        ConnectionManager.withStatement("UPDATE patient_plans SET reset_date=NOW(), used_checkups=0, used_hygiene_visits=0, used_repairs=0 WHERE reset_date < DATE_SUB(NOW(),INTERVAL 1 YEAR)",(stmt)-> {
            stmt.executeUpdate();
            return  null;
        });
    }


	public static void updateById(long id,String name, int cost, LocalDate startDate, int remCheckups, int remHygiene, int remRepairs){
		ConnectionManager.withStatement("UPDATE patient_plans SET plan_name=?, used_checkups=?, used_hygiene_visits=?, used_repairs=?, renew_date =? WHERE patient_id = ?",(stmt)-> {
			stmt.setString(1,name);
			stmt.setInt(2,cost);
			stmt.setDate(3,java.sql.Date.valueOf(startDate));
			stmt.setInt(4,remCheckups);
			stmt.setInt(5,remHygiene);
			stmt.setInt(6,remRepairs);
			stmt.setLong(7,id);
            stmt.executeUpdate();
            return  null;
        });
	}
}
