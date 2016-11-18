/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.data;

/**
 * Used to store the Treatment Plan details temporarily
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   1/11/2016
 */
public class Plan {

    /** This stores the name of the plan. */
    private String name;
    /** This stores the cost of the plan. */
    private int cost;
    /** This stores the maximum number of checkups the plan contains. */
    private int checkups;
    /** This stores the maximum number of hygiene visits the plan contains. */
    private int hygiene_visits;
    /** This stores the maximum number of repairs the plan contains. */
    private int repairs;

    /**
     * This constructors creates a (treatment) Plan object.
     *
     * @param name - the name of the treatment
     * @param cost - the cost of the treatment
     * @param checkups - the number of checkups it covers
     * @param hygiene - the number of hygiene visits it covers
     * @param repairs - the number of repairs it covers
     */
    public Plan(String name, int cost, int checkups, int hygiene, int repairs) {
        this.name = name;
        this.cost = cost;
        this.checkups = checkups;
        this.hygiene_visits = hygiene;
        this.repairs = repairs;
    }

    /**
     * This gets the name of the Plan
     * @return name
     */
	public String getName(){
		return name;
	}
}
