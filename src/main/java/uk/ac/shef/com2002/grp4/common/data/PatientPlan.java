/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.data;

import java.time.LocalDate;
import uk.ac.shef.com2002.grp4.common.databases.PatientPlanUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;

/**
 * Used to store the details of a Patient Plan temporarily
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   1/11/2016
 */
public class PatientPlan {
	/** This stores the ID of the patient that the plan relates to. */
    private long patientID;
	/** This stores the name of the Treatment Plan. */
    private String name;
	/** This stores the cost of the Treatment Plan. */
	private int cost;
	/* This stores the date that the plan was initiliased. */
    private LocalDate startDate;
	/** This stores the remaining Check Ups. */
    private int usedCheckUps;
	/** This stores the remaining Hygiene Visits. */
    private int usedHygiene;
	/** This stores the remaining Repairs. */
    private int usedRepairs;

	/** This stores the default plan that everyone receives. */
	private static final String DEFAULT_PLAN_NAME = "NHS";

	/**
	 * This constructor creates a Patient Plan object.
	 *
	 * @param id - the id of the patient
	 * @param name - the name of the treatment plan
	 * @param cost - the cost of the treatment plan
	 * @param startDate - the startDate of this plan
	 * @param checks - the remaining check ups of this plan
	 * @param hygienes - the remaining hygiene visits of this plan
	 * @param repairs - the remaining repairs of this plan
	 */
    public PatientPlan(long id, String name, int cost, LocalDate startDate, int checks, int hygienes, int repairs) {
        this.patientID = id;
        this.name = name;
        this.cost = cost;
        this.startDate = startDate;
        this.usedCheckUps = checks;
        this.usedHygiene = hygienes;
        this.usedRepairs = repairs;
    }

	/**
	 * This creates a default patient plan.
	 *
	 * @param p - a patient id
	 * @return a new empty Patient Plan
	 */
	public static PatientPlan defaultFor(Patient p){
		return new PatientPlan(
			p.getID(),
			DEFAULT_PLAN_NAME,
			0,
			LocalDate.now(),
			0,
			0,
			0
		);
	}

	/**
	 * This sets a patient to have a particular plan.
	 *
	 * @param plan - a Treatment Plan
	 */
	public void setPlan(Plan plan){
		this.name = plan.getName();
	}

	/**
	 * This updates the details of the Patient Plan permanently in the database.
	 */
	public void update(){
		PatientPlanUtils.updateById(patientID,name,startDate);
	}

	/**
	 * This tests if two patient plans are equal to each other.
	 *
	 * @param obj - an Object
	 * @return - a Boolean which is true if the two objects are equal
	 */
	@Override
	public boolean equals(final Object obj){
		if(obj instanceof PatientPlan){
			final PatientPlan other = (PatientPlan) obj;
			return new EqualsBuilder()
				.append(patientID, other.patientID)
				.append(name, other.name)
				.append(cost, other.cost)
				.append(startDate, other.startDate)
				.append(usedCheckUps, other.usedCheckUps)
				.append(usedHygiene, other.usedHygiene)
				.append(usedRepairs, other.usedRepairs)
				.isEquals();
		} else{
			return false;
		}
	}
}
