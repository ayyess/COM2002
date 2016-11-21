/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.databases;

import javax.swing.*;
import java.sql.*;

/**
 * Functional interface to {@link ConnectionManager}
 */
interface With<T, R, EXCEPTION_TYPE extends Exception> {
	R with(T t) throws EXCEPTION_TYPE;
}

/**
 * Manages the connection between the Utility Classes and the Database server.
 * <br>
 *
 * @author Group 4
 * @version 1.0
 * @since 1/11/2016
 */
public class ConnectionManager {
	/**
	 * Wraps the passed lambda in try with resources blocks to provide a simplified sql interface
	 * @param with - the callback to receive the open connection
	 */
	private static <RETURN> RETURN withConnection(With<Connection, RETURN, SQLException> with) {
		//try with resources will auto close the connection
		try (Connection conn =
					 DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk/team004?user=team004&password=492cebac")) {
			return with.with(conn);
		} catch (SQLException ex) {
			ex.printStackTrace();
			if (ex instanceof com.mysql.jdbc.exceptions.jdbc4.CommunicationsException) {
				JOptionPane.showMessageDialog(null,
						"Failed to connect to the database (check vpn connection)",
						"Database error",
						JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
		return null;
	}
	/**
	 * Wraps the passed lambda in try with resources blocks to provide a simplified sql interface
	 * @param with - the callback to receive the transaction ready connection
	 */
	public static <RETURN> RETURN withTransaction(With<Connection, RETURN, SQLException> with) {
		return withConnection((conn) -> {
			try {
				conn.setAutoCommit(false);
				RETURN ret = with.with(conn);
				conn.commit();
				return ret;
			} catch (SQLException ex) {
				//if we fail, rollback, then rethrow so withConnection catches it
				conn.rollback();
				throw ex;
			}
		});
	}
	/**
	 * Wraps the passed lambda in try with resources blocks to provide a simplified sql interface
	 * @param sql - the sql string to prepare 
	 * @param with - the callback to receive the prepared statement
	 */
	public static <RETURN> RETURN withStatement(String sql, With<PreparedStatement, RETURN, SQLException> with) {
		return withConnection((conn) -> {
			//try with resources will auto close the statement
			try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				return with.with(stmt);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			return null;
		});
	}
}
