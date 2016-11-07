package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Address;
import uk.ac.shef.com2002.grp4.data.Appointment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class AppointmentUtils {
    private Connection con = null;
    private Statement stmt = null;

    public AppointmentUtils(Connection con) {
        this.con = con;
    }


    public Appointment[] getAppointmentByPatientID(int id) {
        Appointment[] appointments;
        ResultSet res;
        try {
            stmt = con.createStatement();
            res = stmt.executeQuery("SELECT * FROM appointment WHERE id="+id);
            ResultSet res_size = stmt.executeQuery("SELECT COUNT(*) FROM appointment WHERE id="+id);
            appointments = new Appointment[res_size.getInt(1)];
            for (int i=0; i < appointments.length; i++) {
                //appointments[i] = new Appointment(res.getDate(1), res.getTime(4),res.getTime(5), res.getString(2));
            }
            return appointments;
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
}
