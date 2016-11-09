package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.PatientPlan;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class PatientPlanUtils {

    public static PatientPlan getPlanByPatientID(int id) {
        return ConnectionManager.withStatement("SELECT * FROM patient_plan JOIN treatment_plan ON patient_plan.plan_name = treatment_plan.name WHERE id=?",(stmt)-> {
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();
            return new PatientPlan(res.getInt(1),res.getString(2), res.getInt(7), res.getDate(3), res.getInt(4), res.getInt(5), res.getInt(6));
        });
    }

    public static void completeCheckUp(int id) {
        ConnectionManager.withStatement("UPDATE patient_plan SET used_checkups=used_checkups + 1 WHERE id=?",(stmt)-> {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return  null;
        });
    }

    public static void completeHygieneVisit(int id) {
        ConnectionManager.withStatement("UPDATE patient_plan SET used_hygiene_visits=used_hygiene_visits + 1 WHERE id=?",(stmt)-> {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return  null;
        });
    }

    public static void completeRepair(int id) {
        ConnectionManager.withStatement("UPDATE patient_plan SET used_repairs=used_repairs + 1 WHERE id=?",(stmt)-> {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return  null;
        });
    }

    public static void resetPlans() {
        ConnectionManager.withStatement("UPDATE patient_plan SET reset_date=NOW(), used_checkups=0, used_hygiene_visits=0, used_repairs=0 WHERE reset_date < DATE_SUB(NOW(),INTERVAL 1 YEAR)",(stmt)-> {
            stmt.executeUpdate();
            return  null;
        });
    }
}
