package uk.ac.shef.com2002.grp4.data;

import uk.ac.shef.com2002.grp4.databases.ConnectionManager;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Treatment {

	private String name;
	private int cost;
	private String type;

	public Treatment(String name, int cost, String type) {
		this.name = name;
		this.cost = cost;
		this.type = type;
	}

	public static Treatment getByName(String s) {
		return ConnectionManager.withStatement("SELECT * FROM treatments WHERE name=?",(stmt)-> {
			stmt.setString(1, s);
			ResultSet res = stmt.executeQuery();
			return new Treatment(res.getString(1), res.getInt(2), res.getString(3));
		});
	}

	public static List<Treatment> getAll() {
		List<Treatment> treatments = new ArrayList<Treatment>();
		ConnectionManager.withStatement("SELECT * FROM treatments",(stmt)-> {
			ResultSet res = stmt.executeQuery();
			while (res.next()) {
				treatments.add(new Treatment(res.getString(1), res.getInt(2), res.getString(3)));
			}
			return null;
		});
		return treatments;
	}
	
}
