package uk.ac.shef.com2002.grp4.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class SQLController {

    private Connection con = null;
    private Statement stmt = null;

    private PatientUtils patient = null;
    private AddressUtils address = null;
    private PatientPlanUtils patientPlan = null;
    private AppointmentUtils appointment = null;
    private PlanUtils plan = null;
    private TreatmentUtils treatment = null;

    //Gets connection with remote server
    public SQLController() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team004?user=team004&password=492cebac");
            patient = new PatientUtils(con);
            address = new AddressUtils(con);
            patientPlan = new PatientPlanUtils(con);
            appointment = new AppointmentUtils(con);
            plan = new PlanUtils(con);
            treatment = new TreatmentUtils(con);
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public PatientUtils getPatientUtils() { return patient; }
    public AddressUtils getAddressUtils() { return address; }
    public PatientPlanUtils getPatientPlanUtils() { return patientPlan; }
    public AppointmentUtils getAppointmentUtils() { return appointment; }
    public PlanUtils getPlanUtils() { return plan; }
    public TreatmentUtils getTreatmentUtils() { return treatment; }

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
