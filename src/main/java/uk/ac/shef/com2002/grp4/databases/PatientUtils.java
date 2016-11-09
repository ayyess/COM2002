package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Patient;

import java.sql.*;
import java.util.Date;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class PatientUtils {
    private Connection con = null;
    private PreparedStatement stmt = null;

    public PatientUtils(Connection con) {
        this.con = con;
    }

    public Patient getPatientByID(int id) {
        ResultSet res;
        try {
            stmt = con.prepareStatement("SELECT * FROM patient WHERE id=?");
            stmt.setInt(1, id);
            res = stmt.executeQuery();
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
            stmt = con.prepareStatement("UPDATE patient SET title=?, forname=?, surname=?, phone_number=? WHERE id=?");
            stmt.setString(1, title);
            stmt.setString(2, forename);
            stmt.setString(3, surname);
            stmt.setInt(4, phone);
            stmt.setInt(5, id);
            int count = stmt.executeUpdate();
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
            stmt = con.prepareStatement("SELECT MAX(id) FROM patient");
            int new_id = stmt.executeQuery().getInt(1) + 1;
            java.sql.Date dob = new java.sql.Date(date.getTime());
            stmt = con.prepareStatement("INSERT INTO patient VALUES ?,?,?,?,?,?,?");
            stmt.setInt(1, new_id);
            stmt.setString(2, title);
            stmt.setString(3, forename);
            stmt.setString(4, surname);
            stmt.setDate(5, dob);
            stmt.setInt(6, phone);
            stmt.setInt(7, address_id);
            int count = stmt.executeUpdate();
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
