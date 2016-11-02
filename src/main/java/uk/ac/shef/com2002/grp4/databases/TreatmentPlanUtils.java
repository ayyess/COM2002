package uk.ac.shef.com2002.grp4.databases;

import java.sql.Connection;
import java.sql.Statement;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class TreatmentPlanUtils {

    private Connection con = null;
    private Statement stmt = null;

    public TreatmentPlanUtils(Connection con) {
        this.con = con;
    }
}
