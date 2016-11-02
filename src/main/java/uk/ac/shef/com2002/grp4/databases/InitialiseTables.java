package uk.ac.shef.com2002.grp4.databases;

import com.sun.org.apache.xml.internal.security.Init;

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
public class InitialiseTables {

    private Connection con = null;
    private Statement stmt = null;

    //Gets connection with remote server
    public InitialiseTables() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team004?user=team004&password=492cebac");
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

    //Gets the data from a file, whose name is passed in
    private String getTableData(String fileName) {
        StringBuilder data = new StringBuilder();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                data.append(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return data.toString();
    }

    //Pushes data from a file to the remote server
    public void pushToServer(String fileName) {
        String dataStream = getTableData("up.sql");
        try {
            stmt = con.createStatement();
            stmt.executeQuery(dataStream);
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

    public static void main(String[] args) {
        InitialiseTables it = new InitialiseTables();
        it.pushToServer("up.sql");
    }
}
