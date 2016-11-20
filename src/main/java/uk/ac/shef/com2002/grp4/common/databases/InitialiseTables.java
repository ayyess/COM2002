/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.databases;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Used to initialise tables in the database.
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   2/11/2016
 */
public class InitialiseTables {

	//Gets the data from a file, whose name is passed in

	/**
	 * This gets the SQL table data from a file.
	 *
	 * @param fileName - file containing the SQL code
	 * @return an ArrayList of SQL strings
	 */
	private List<String> getTableData(String fileName) {
		List<String> statements = new ArrayList<String>();
		StringBuilder data = new StringBuilder();
		try {
			File file = new File(fileName);
			Scanner scanner = new Scanner(file);

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				if (line.isEmpty()) {
					statements.add(data.toString());
					data = new StringBuilder();
				} else {
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

	/**
	 * This pushes each SQL statement in file to the server.
	 *
	 * @param fileName - file containing the SQL code
	 */
	public void pushToServer(String fileName) {
		ConnectionManager.withTransaction((conn)-> {
			for (String statement: getTableData(fileName)) {
				PreparedStatement stmt = conn.prepareStatement(statement);
				stmt.execute();
			}
			return null;
		});
	}

	/**
	 * SecretaryApp method, simply for testing purposes.
	 *
	 * @param args - null
	 */
	public static void main(String[] args) {
		InitialiseTables it = new InitialiseTables();
		it.pushToServer("up.sql");
	}
}
