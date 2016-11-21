/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */
package uk.ac.shef.com2002.grp4.common;

import uk.ac.shef.com2002.grp4.common.data.Plan;
import uk.ac.shef.com2002.grp4.common.databases.PlanUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Swing component to select a patient plan
 */
public class PlanSelector extends JPanel {
	JComboBox<Plan> planSelector;
	List<ChangeListener> changeListeners = new ArrayList<>();

	/**
	 * Construct getting available plans from the db
	 */
	public PlanSelector() {
		this(PlanUtils.getTreatmentPlans());
	}

	/**
	 * Construct with available planns being those passed in
	 * @param treatmentPlans The plans to allow choosing from
	 */
	public PlanSelector(List<Plan> treatmentPlans) {
		planSelector = new JComboBox<>(treatmentPlans.toArray(new Plan[treatmentPlans.size()]));
		planSelector.setEditable(false);
		planSelector.setRenderer((list, value, index, isSelected, cellHasFocus) -> new JLabel(value.getName()));
		planSelector.addItemListener((ItemEvent ev) -> {
			if (ev.getStateChange() == ItemEvent.SELECTED) {
				for (ChangeListener listener : changeListeners) {
					listener.stateChanged(new ChangeEvent(this));
				}
			}
		});

		add(planSelector);
	}

	/**
	 * Add a listener for changes in the selected plan
	 * @param listener the listner to add
	 */
	public void addChangeListener(ChangeListener listener) {
		changeListeners.add(listener);
	}

	/**
	 * Get the plan shown as selected
	 * @return the selected plan
	 */
	public Plan getSelectedItem() {
		return (Plan) planSelector.getSelectedItem();
	}

	/**
	 * set the plan shown as selected
	 * @param plan the plan to be shown as selected
	 */

	public void setSelectedItem(Plan plan) {
		planSelector.setSelectedItem(plan);
	}
}
