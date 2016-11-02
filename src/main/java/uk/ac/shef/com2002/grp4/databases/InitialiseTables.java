package uk.ac.shef.com2002.grp4.databases;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class InitialiseTables {

    private Connection con = null;
    private PreparedStatement stmt = null;

    //Gets connection with remote server
    public InitialiseTables(Connection con) {
	    this.con = con;
    }

    //Gets the data from a file, whose name is passed in
    private List<String> getTableData(String fileName) {
        List<String> statements = new ArrayList<String>();
        StringBuilder data = new StringBuilder();
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.isEmpty()){
                    statements.add(data.toString());
                    data = new StringBuilder();
                }else {
                    data.append(line);
                    data.append("\r\n");
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return statements;
    }

    //Pushes data from a file to the remote server
    public void pushToServer(String fileName) {
        try {
            con.setAutoCommit(false);
            for(String statement: getTableData(fileName)) {
                stmt = con.prepareStatement(statement);
                stmt.execute();
            }
            con.commit();
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            try {
                con.rollback();
            } catch (SQLException e) {
                //if we can't rollback then somethings very wrong anyway
            }
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
	Connection con = null;
	try {
            con = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team004?user=team004&password=492cebac");
		InitialiseTables it = new InitialiseTables(con);
		it.pushToServer("up.sql");

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
