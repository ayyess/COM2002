package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Patient;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class PatientUtils {

    public static List<Patient> findPatientByFirstName(String firstName) {
        return ConnectionManager.withStatement("SELECT * FROM patient WHERE forname=?",(stmt)->{
            stmt.setString(1,firstName);
            ResultSet res = stmt.executeQuery();
            List<Patient> patients = new ArrayList<>();
            while(res.next()){
                patients.add(new Patient(res.getInt(1),res.getString(2),res.getString(3),res.getString(4), res.getString(5),res.getString(6)));
            }
            return patients;
        });
    }

    public static Patient getPatientByID(int id) {
        return ConnectionManager.withStatement("SELECT * FROM patient WHERE id=?",(stmt)-> {
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();
            return new Patient(res.getString(2), res.getString(3), res.getString(4), res.getString(5), res.getString(6));
        });
    }

    public static void updatePatientByID(int id, String title, String forename, String surname, int phone) {
        ConnectionManager.withStatement("UPDATE patient SET title=?, forname=?, surname=?, phone_number=? WHERE id=?",(stmt)-> {
            stmt.setString(1, title);
            stmt.setString(2, forename);
            stmt.setString(3, surname);
            stmt.setInt(4, phone);
            stmt.setInt(5, id);
            stmt.executeUpdate();
            return null;
        });
    }


    public static void insertPatient(String title, String forename, String surname, Date date, String phone, int address_id) {
        ConnectionManager.withStatement("INSERT INTO patient VALUES (DEFAULT,?,?,?,?,?,?)",(stmt)-> {
            java.sql.Date dob = new java.sql.Date(date.getTime());
            stmt.setString(1, title);
            stmt.setString(2, forename);
            stmt.setString(3, surname);
            stmt.setDate(4, dob);
            stmt.setString(5, phone);
            stmt.setInt(6, address_id);
            stmt.executeUpdate();
            return null;
        });
    }


}
