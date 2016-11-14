package uk.ac.shef.com2002.grp4.data;

import uk.ac.shef.com2002.grp4.databases.ConnectionManager;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Dan-L on 09/11/2016.
 */
public class Plan {
    private String name;
    private int cost;
    private int checkups;
    private int hygiene_visits;
    private int repairs;

    public Plan(String name, int cost, int checkups, int hygiene, int repairs) {
        this.name = name;
        this.cost = cost;
        this.checkups = checkups;
        this.hygiene_visits = hygiene;
        this.repairs = repairs;
    }

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
