package uk.ac.shef.com2002.grp4.databases;

import uk.ac.shef.com2002.grp4.data.Appointment;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class Controller {

    private Connection con = null;
    private Statement stmt = null;

    private PatientUtils patient = null;
    private AddressUtils address = null;
    private PlanUtils plan = null;
    private AppointmentUtils appointment = null;

    //Gets connection with remote server
    public Controller() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team004?user=team004&password=492cebac");
            patient = new PatientUtils(con);
            address = new AddressUtils(con);
            plan = new PlanUtils(con);
            appointment = new AppointmentUtils(con);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public PatientUtils getPatientUtils() { return patient; }
    public AddressUtils getAddressUtils() { return address; }
    public PlanUtils getPlanUtils() { return plan; }
    public AppointmentUtils getAppointmentUtils() { return appointment; }

    public void newPatient(String title, String forename, String surname, Date date, int phone, int houseNumber, String street, String district, String city, String postcode) {
        address.insertAddress(houseNumber, street, district, city, postcode);
        patient.insertPatient(title, forename, surname, date, phone, address.getAddressID(houseNumber, postcode));
    }

    public void close() {
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
