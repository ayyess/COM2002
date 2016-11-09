package uk.ac.shef.com2002.grp4.databases;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Dan-L on 02/11/2016.
 */
public class InitialiseTables {

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
        ConnectionManager.withTransaction((conn)->{
            for(String statement: getTableData(fileName)) {
                PreparedStatement stmt = conn.prepareStatement(statement);
                stmt.execute();
            }
            return null;
        });
    }

    public static void main(String[] args) {
		InitialiseTables it = new InitialiseTables();
		it.pushToServer("up.sql");
    }
}
