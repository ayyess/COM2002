package uk.ac.shef.com2002.grp4.data;

import uk.ac.shef.com2002.grp4.databases.ConnectionManager;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Created by Dan-L on 09/11/2016.
 */
public class PatientPlan{
    private long patientID;
    private String name;
    private int cost;
    private LocalDate startDate;
    private int remCheckUps;
    private int remHygiene;
    private int remRepairs;

    public PatientPlan(long patientID, String name, int cost, LocalDate startDate, int checks, int hygienes, int repairs) {
        this.patientID = patientID;
        this.name = name;
        this.cost = cost;
        this.startDate = startDate;
        this.remCheckUps = checks;
        this.remHygiene = hygienes;
        this.remRepairs = repairs;
    }

	public static PatientPlan getByPatientID(int id) {
		return ConnectionManager.withStatement("SELECT * FROM patient_plans JOIN treatment_plans ON patient_plans.plan_name = treatment_plans.name WHERE id=?",(stmt)-> {
			stmt.setInt(1, id);
			ResultSet res = stmt.executeQuery();
			return new PatientPlan(res.getLong(1),res.getString(2), res.getInt(7), res.getDate(3).toLocalDate(), res.getInt(4), res.getInt(5), res.getInt(6));
		});
	}

	public static void completeCheckUp(int id) {
		ConnectionManager.withStatement("UPDATE patient_plans SET used_checkups=used_checkups + 1 WHERE id=?",(stmt)-> {
			stmt.setInt(1, id);
			stmt.executeUpdate();
			return  null;
		});
	}

	public static void completeHygieneVisit(int id) {
		ConnectionManager.withStatement("UPDATE patient_plans SET used_hygiene_visits=used_hygiene_visits + 1 WHERE id=?",(stmt)-> {
			stmt.setInt(1, id);
			stmt.executeUpdate();
			return  null;
		});
	}

	public static void completeRepair(int id) {
		ConnectionManager.withStatement("UPDATE patient_plans SET used_repairs=used_repairs + 1 WHERE id=?",(stmt)-> {
			stmt.setInt(1, id);
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
}
