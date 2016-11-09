package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Treatment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Dan-L on 09/11/2016.
 */
public class TreatmentUtils {
    public static Treatment getCostByName(String s) {
        return ConnectionManager.withStatement("SELECT cost FROM treatment WHERE name=?",(stmt)-> {
            stmt.setString(1, s);
            ResultSet res = stmt.executeQuery();
            return new Treatment(s, res.getInt(1));
        });
    }

    public static  Treatment[] getTreatments() {
        ArrayList<Treatment> treatments = new ArrayList<Treatment>();
        return ConnectionManager.withStatement("SELECT cost FROM treatment",(stmt)-> {
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                treatments.add(new Treatment(res.getString(1), res.getInt(2)));
            }
            Treatment[] tp = new Treatment[treatments.size()];
            treatments.toArray(tp);
            return tp;
        });
    }
}
