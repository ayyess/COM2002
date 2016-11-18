/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.data;

import java.time.LocalDate;
import uk.ac.shef.com2002.grp4.databases.PatientPlanUtils;
import java.util.Objects;

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
	private int cost;
    private LocalDate startDate;
    private int remCheckUps;
    private int remHygiene;
    private int remRepairs;

	private static final String DEFAULT_PLAN_NAME = "NHS";

    public PatientPlan(long id, String name, int cost, LocalDate startDate, int checks, int hygienes, int repairs) {
        this.patientID = id;
        this.name = name;
        this.cost = cost;
        this.startDate = startDate;
        this.remCheckUps = checks;
        this.remHygiene = hygienes;
        this.remRepairs = repairs;
    }

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

	public void setPlan(Plan plan){
		this.name = plan.getName();
	}

	public void update(){
		PatientPlanUtils.updateById(patientID,name,startDate,remCheckUps,remHygiene,remRepairs);
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Address)){
			return false;
		}
		PatientPlan rhs = (PatientPlan) obj;
		return
			patientID == rhs.patientID &&
			Objects.equals(name, rhs.name) &&
			cost == rhs.cost &&
			Objects.equals(startDate, rhs.startDate) &&
			remCheckUps == rhs.remCheckUps &&
			remHygiene == rhs.remHygiene &&
			remRepairs == rhs.remRepairs;
	}
}
