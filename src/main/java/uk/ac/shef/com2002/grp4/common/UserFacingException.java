/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */
package uk.ac.shef.com2002.grp4.common;

import javax.swing.*;

/**
 * UserFacingException is a class of exceptions that should be shown to the user when thrown.
 */
public class UserFacingException extends java.lang.RuntimeException {

	/**
	 * Constructs a new user facing runtime exception with the specified detail message.
	 */
	public UserFacingException(String message) {
		super(message);
	}

	/**
	 * Shows the error this exception as a JDialog
	 */
	public void showError() {
		JOptionPane.showMessageDialog(null, getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}
}
