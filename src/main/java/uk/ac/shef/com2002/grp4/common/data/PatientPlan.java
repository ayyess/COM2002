/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.data;

import uk.ac.shef.com2002.grp4.common.databases.PatientPlanUtils;
import uk.ac.shef.com2002.grp4.common.databases.PlanUtils;

import java.time.LocalDate;
import uk.ac.shef.com2002.grp4.common.databases.PatientPlanUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    /** This stores the date that the plan was initiliased. */
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
	 * @param startDate - the startDate of this plan
	 * @param checks - the remaining check ups of this plan
	 * @param hygienes - the remaining hygiene visits of this plan
	 * @param repairs - the remaining repairs of this plan
	 */
    public PatientPlan(long id, String name, LocalDate startDate, int checks, int hygienes, int repairs) {
        this.patientID = id;
        this.name = name;
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
		PatientPlanUtils.updateById(patientID, name, this);
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
				.append(startDate, other.startDate)
				.append(usedCheckUps, other.usedCheckUps)
				.append(usedHygiene, other.usedHygiene)
				.append(usedRepairs, other.usedRepairs)
				.isEquals();
		} else{
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
			.append(patientID)
			.append(name)
			.append(startDate)
			.append(usedCheckUps)
			.append(usedHygiene)
			.append(usedRepairs)
			.toHashCode();
	}

	/** 
	 * @return The number of used check ups, that this patient has used 
	 */
	public int getUsedCheckUps() {
		return usedCheckUps;
	}

	/** 
	 * @return The number of used hygiene treatments, that this patient has used 
	 */
	public int getUsedHygieneVisits() {
		return usedHygiene;
	}

	/** 
	 * @return The number of used repair treatments, that this patient has used 
	 */
	public int getUsedRepairs() {
		return usedRepairs;
	}
	
	public void useCheckup() {
		usedCheckUps += 1;
	}
	
	public void useHygiene() {
		usedHygiene += 1;
	}
	
	public void useRepair() {
		usedRepairs += 1;
	}
	
	public int getRemainingCheckups() {
		if (name.equals(DEFAULT_PLAN_NAME)) return 0;
		Plan plan = PlanUtils.getDetailsByName(name);
		if (plan == null) return 0;
		return plan.getCheckups() - getUsedCheckUps();
	}
	
	public int getRemainingHygieneVisits() {
		if (name.equals(DEFAULT_PLAN_NAME)) return 0;
		Plan plan = PlanUtils.getDetailsByName(name);
		if (plan == null) return 0;
		return plan.getHygieneVisits() - getUsedHygieneVisits();
	}
	
	public int getRemainingRepairs() {
		if (name.equals(DEFAULT_PLAN_NAME)) return 0;
		Plan plan = PlanUtils.getDetailsByName(name);
		if (plan == null) return 0;
		return plan.getRepairs() - getUsedRepairs();
	}
	
	public boolean checkPlanValid() {
		if (name.equals(DEFAULT_PLAN_NAME)) return false;
		Plan plan = PlanUtils.getDetailsByName(name);
		if (plan == null) return false;
		if (getStartDate().plusYears(1).isBefore(LocalDate.now())) {
			return false;
		}
		return true;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

}
