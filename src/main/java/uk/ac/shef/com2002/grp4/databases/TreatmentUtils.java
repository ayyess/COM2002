/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.databases;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import uk.ac.shef.com2002.grp4.data.Patient;
import uk.ac.shef.com2002.grp4.data.Treatment;

/**
 * Used to control database interaction.
 * Specifically the treatments and treatment_applications tables
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   1/11/2016
 */
public class TreatmentUtils {
    /**
     * This gets the details of a treatment by the treatment name.
     *
     * @param s - the treatment name
     * @return a new Treatment object
     */
    public static Treatment getDetailsByName(String s) {
        return ConnectionManager.withStatement("SELECT cost, treatment_type FROM treatments WHERE name=?",(stmt)-> {
            stmt.setString(1, s);
            ResultSet res = stmt.executeQuery();
            return new Treatment(s, res.getInt(1), res.getString(2));
        });
    }

    /**
     * This gets the details of all available treatments
     *
     * @return an Array of Treatment
     */
    public static  Treatment[] getTreatments() {
        ArrayList<Treatment> treatments = new ArrayList<Treatment>();
        return ConnectionManager.withStatement("SELECT * FROM treatments",(stmt)-> {
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                treatments.add(new Treatment(res.getString(1), res.getInt(2), res.getString(3)));
            }
            Treatment[] tp = new Treatment[treatments.size()];
            treatments.toArray(tp);
            return tp;
        });
    }

    /**
     * This gets a treatment that a patient is undergoing.
     *
     * @param p - a patient_id
     * @return an Array of Treatment
     */
    public static  Treatment[] getPatientTreatments(Patient p) {
        ArrayList<Treatment> treatments = new ArrayList<Treatment>();
        return ConnectionManager.withStatement(
        		"SELECT t.* FROM treatment_applications t " + 
        		"INNER JOIN appointments a ON a.date=t.appointment_date " + 
        		"INNER JOIN patients p ON p.id=a.patient_id " + 
        		"WHERE p.id=?;",
        		(stmt)-> {
            stmt.setLong(1, p.getID());
        	ResultSet res = stmt.executeQuery();
            while (res.next()) {
                treatments.add(new Treatment(res.getString(1), res.getInt(2), res.getString(3)));
            }
            Treatment[] tp = new Treatment[treatments.size()];
            treatments.toArray(tp);
            return tp;
        });
    }
    
}
