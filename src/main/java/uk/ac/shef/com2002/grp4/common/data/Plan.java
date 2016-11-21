/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Used to store the Treatment Plan details temporarily
 * <br>
 *
 * @author Group 4
 * @version 1.0
 * @since 1/11/2016
 */
public class Plan {

	/**
	 * This stores the name of the plan.
	 */
	private String name;
	/**
	 * This stores the cost of the plan.
	 */
	private int cost;
	/**
	 * This stores the maximum number of checkups the plan contains.
	 */
	private int checkups;
	/**
	 * This stores the maximum number of hygiene visits the plan contains.
	 */
	private int hygieneVisits;
	/**
	 * This stores the maximum number of repairs the plan contains.
	 */
	private int repairs;

	/**
	 * This constructors creates a (treatment) Plan object.
	 *
	 * @param name     - the name of the treatment
	 * @param cost     - the cost of the treatment
	 * @param checkups - the number of checkups it covers
	 * @param hygiene  - the number of hygiene visits it covers
	 * @param repairs  - the number of repairs it covers
	 */
	public Plan(String name, int cost, int checkups, int hygiene, int repairs) {
		this.name = name;
		this.cost = cost;
		this.checkups = checkups;
		this.hygieneVisits = hygiene;
		this.repairs = repairs;
	}

	/**
	 * This gets the name of the Plan
	 *
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * This tests if two treatments are equal to each other.
	 *
	 * @param obj - an Object
	 * @return - a Boolean which is true if the two objects are equal
	 */
	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof Plan) {
			final Plan other = (Plan) obj;
			return new EqualsBuilder()
					.append(name, other.name)
					.append(cost, other.cost)
					.append(checkups, other.checkups)
					.append(hygieneVisits, other.hygieneVisits)
					.append(repairs, other.repairs)
					.isEquals();
		} else {
			return false;
		}
	}

	/**
	 * Returns a hash code for this object.
	 *
	 * @return - a hash code value for this object.
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(name)
				.append(cost)
				.append(checkups)
				.append(hygieneVisits)
				.append(repairs)
				.toHashCode();
	}

	/**
	 * @return The cost of this plan
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * @return The number of checkups this plan gives to the patient
	 */
	public int getCheckups() {
		return checkups;
	}

	/**
	 * @return The number of hygiene visits this plan gives to the patient
	 */
	public int getHygieneVisits() {
		return hygieneVisits;
	}

	/**
	 * @return The number of repair this plan gives to the patient
	 */
	public int getRepairs() {
		return repairs;
	}

}
