package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Patient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class PatientUtils {
    private Connection con = null;
    private Statement stmt = null;

    public PatientUtils(Connection con) {
        this.con = con;
    }

    public Patient getPatientByID(int id) {
        ResultSet res;
        try {
            stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM patient WHERE id="+id);
            return new Patient(res.getString(2),res.getString(3),res.getString(4), res.getString(5),res.getString(6));
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public void updatePatientByID(int id, String title, String forename, String surname, int phone) {
        try {
            stmt = con.createStatement();
            int count = stmt.executeUpdate("UPDATE patient SET title =" +
                    title + ", forname =" + forename + ", surname =" +
                    surname + ", phone_number =" + phone + "WHERE id=" + id);

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void insertPatient(String title, String forename, String surname, Date date, int phone, int address_id) {
        try {
            stmt = con.createStatement();
            int new_id = stmt.executeQuery("SELECT MAX(id) FROM patient").getInt(1) + 1;
            java.sql.Date dob = new java.sql.Date(date.getTime());
            int count = stmt.executeUpdate("INSERT INTO patient VALUES " +
                    new_id + "," + title + "," + forename + "," + surname + "," + dob + "," + phone + "," + address_id);

        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            if (stmt != null) {
                try {
                    stmt.close();
                }
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
