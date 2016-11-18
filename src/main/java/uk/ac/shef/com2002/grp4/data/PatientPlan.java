/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.data;

import java.time.LocalDate;
import uk.ac.shef.com2002.grp4.databases.PatientPlanUtils;

/**
 * Created by Dan-L on 09/11/2016.
 */
public class PatientPlan {
    private int patientID;
    private String name;
    private int cost;
    private LocalDate startDate;
    private int remCheckUps;
    private int remHygiene;
    private int remRepairs;

    public PatientPlan(int id, String name, int cost, LocalDate startDate, int checks, int hygienes, int repairs) {
        this.patientID = id;
        this.name = name;
        this.cost = cost;
        this.startDate = startDate;
        this.remCheckUps = checks;
        this.remHygiene = hygienes;
        this.remRepairs = repairs;
    }

	public void setPlan(Plan plan){
		this.name = plan.getName();
	}

	public void update(){
		PatientPlanUtils.updateById(patientID,name,cost,startDate,remCheckUps,remHygiene,remRepairs);
	}
}
