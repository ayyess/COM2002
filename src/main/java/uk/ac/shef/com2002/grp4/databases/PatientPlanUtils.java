package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.PatientPlan;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class PatientPlanUtils {

    private Connection con = null;
    private PreparedStatement stmt = null;

    public PatientPlanUtils(Connection con) {
        this.con = con;
    }

    public PatientPlan getPlanByPatientID(int id) {
        try {
            stmt = con.prepareStatement("SELECT * FROM patient_plan WHERE id=?");
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();
            stmt = con.prepareStatement("SELECT * FROM treatment_plan WHERE id=?");
            stmt.setInt(1, id);
            ResultSet res2 = stmt.executeQuery();
            return new PatientPlan(res.getInt(1),res.getString(2), res2.getInt(2), res.getDate(3), res.getInt(4), res.getInt(5), res.getInt(6));
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
        return null;
    }

    public void completeCheckUp(int id) {
        try {
            stmt = con.prepareStatement("SELECT used_checkups FROM patient_plan WHERE id=?");
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();
            int updatedAmount = res.getInt(1) + 1;
            stmt = con.prepareStatement("UPDATE patient_plan SET used_checkups=? WHERE id=?");
            stmt.setInt(1, updatedAmount);
            stmt.setInt(2, id);
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

    public void completeHygieneVisit(int id) {
        try {
            stmt = con.prepareStatement("SELECT used_hygiene_visits FROM patient_plan WHERE id=?");
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();
            int updatedAmount = res.getInt(1) + 1;
            stmt = con.prepareStatement("UPDATE patient_plan SET used_hygiene_visits=? WHERE id=?");
            stmt.setInt(1, updatedAmount);
            stmt.setInt(2, id);
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

    public void completeRepair(int id) {
        try {
            stmt = con.prepareStatement("SELECT used_repairs FROM patient_plan WHERE id=?");
            stmt.setInt(1, id);
            ResultSet res = stmt.executeQuery();
            int updatedAmount = res.getInt(1) + 1;
            stmt = con.prepareStatement("UPDATE patient_plan SET used_repairs=? WHERE id=?");
            stmt.setInt(1, updatedAmount);
            stmt.setInt(2, id);
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

    public void resetPlans() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        LocalDate resetDate = cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        try {
            stmt = con.prepareStatement("SELECT patient_id, plan_name, reset_date FROM patient_plan");
            ResultSet res = stmt.executeQuery();
            while (res.next()) {
                if(res.getDate(3).toLocalDate().equals(resetDate)) {
                    stmt = con.prepareStatement("SELECT checkups, hygiene_visits, repairs FROM treatment_plan WHERE name=?");
                    stmt.setString(1, res.getString(2));
                    ResultSet res2 = stmt.executeQuery();
                    stmt = con.prepareStatement("UPDATE patient_plan SET reset_date=?, used_checkups=?, used_hygiene_visits=?, used_repairs=? WHERE patient_id=?");
                    stmt.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                    stmt.setInt(2, res2.getInt(1));
                    stmt.setInt(3, res2.getInt(2));
                    stmt.setInt(4, res2.getInt(3));
                    stmt.setInt(5, res.getInt(1));
                    int count = stmt.executeUpdate();
                }
            }
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
