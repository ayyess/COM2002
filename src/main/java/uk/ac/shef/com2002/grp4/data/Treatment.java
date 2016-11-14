/* This file is part of Grp4 Dental Care System.
 * To ensure compliance with the GNU General Public License. This System
 * is for private, educational use. It will not be released publicly and will
 * solely be viewed by those marking the COM2002 assignment.
 *
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.data;

public class Treatment {

	private String name;
	private int cost;
	private String type;

	public Treatment(String name, int cost, String type) {
		this.name = name;
		this.cost = cost;
		this.type = type;
	}
	
	public int getCost() {
		return cost;
	}
	
}
