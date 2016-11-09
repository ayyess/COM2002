package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Address;
import uk.ac.shef.com2002.grp4.data.Appointment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class AppointmentUtils {
    private Connection con = null;
    private PreparedStatement stmt = null;

    public AppointmentUtils(Connection con) {
        this.con = con;
    }


    public Appointment[] getAppointmentByPatientID(int id) {
        Appointment[] appointments;
        try {
            stmt = con.prepareStatement("SELECT * FROM appointment WHERE id=?");
            stmt.setInt(1,id);
            ResultSet res = stmt.executeQuery();
            stmt = con.prepareStatement("SELECT COUNT(*) FROM appointment WHERE id=?");
            stmt.setInt(1,id);
            ResultSet res_size = stmt.executeQuery();
            appointments = new Appointment[res_size.getInt(1)];
            for (int i=0; i < appointments.length; i++) {
                appointments[i] = new Appointment(res.getDate(1), res.getTime(4),res.getTime(5), res.getString(2));
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
    
    public Appointment[] getAppointmentByDate(Date date) {
    	ArrayList<Appointment> appointments = new ArrayList<Appointment>(); 
        ResultSet res;
        try {
            stmt = con.prepareStatement("SELECT * FROM appointment WHERE date=?");
            stmt.setDate(1, date);
            res = stmt.executeQuery();
            while (res.next()) {
            	appointments.add(new Appointment(res.getDate(1), res.getTime(4),res.getTime(5), res.getString(2)));
            }
            Appointment[] ap = new Appointment[appointments.size()];
            appointments.toArray(ap);
            return ap;
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
