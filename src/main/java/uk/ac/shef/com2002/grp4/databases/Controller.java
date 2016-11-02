package uk.ac.shef.com2002.grp4.databases;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class Controller {

    private Connection con = null;
    private Statement stmt = null;

    private AddressUtils address = null;
    private TreatmentPlanUtils treatmentPlan = null;
    private PatientPlanUtils patientPlan = null;

    //Gets connection with remote server
    public Controller() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team004?user=team004&password=492cebac");
            address = new AddressUtils(con);
            treatmentPlan = new TreatmentPlanUtils(con);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            if (con != null) {
                try {
                    con.close();
                }
                catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
