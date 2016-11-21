/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */
package uk.ac.shef.com2002.grp4.common;

import java.util.Locale;

public enum Partner {
	DENTIST("Dentist"),
	HYGIENIST("Hygienist");

	/** Member to hold the name */
	private String name;

	/** constructor to set the string */
	Partner(String name) {
		this.name = name;
	}
	
	/** the toString just returns the given name */
	@Override
	public String toString() {
		return name;
	}
	
	/** Same as value of but ignores the case of the given String */
	public static Partner valueOfIngnoreCase(String p) {
		return valueOf(p.toUpperCase(Locale.ENGLISH));
	}
	
}
