/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.data;

import java.util.Objects;

/**
 * Used to store the Treatment details temporarily
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   1/11/2016
 */
public class Treatment {

	/** This stores the name of the treatment. */
	private String name;
	/** This stores the cost of the treatment. */
	private int cost;
	/** This stores the type of treatment it is. */
	private String type;

	/**
	 * This constructor creates a Treatment object.
	 *
	 * @param name - the name of the treatment
	 * @param cost - the cost of the treatment
	 * @param type - the type of treatment it is
	 */
	public Treatment(String name, int cost, String type) {
		this.name = name;
		this.cost = cost;
		this.type = type;
	}

	/**
	 * This gets the cost of the Treatment
	 * @return cost
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * This tests if two treatments are equal to each other.
	 *
	 * @param obj - an Object
	 * @return - a Boolean which is true if the two objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Address)){
			return false;
		}
		Treatment rhs = (Treatment) obj;
		return
			Objects.equals(name, rhs.name) &&
			cost == rhs.cost &&
			Objects.equals(type, rhs.type);
	}
	
	public String toString() {
		return name + " " + cost;
	}
}
