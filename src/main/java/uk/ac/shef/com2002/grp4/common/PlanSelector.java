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

public class PlanSelector extends JPanel {
	JComboBox<Plan> planSelector;
	List<ChangeListener> changeListeners = new ArrayList<>();
	public PlanSelector() {
		this(PlanUtils.getTreatmentPlans());
	}

	public PlanSelector(List<Plan> treatmentPlans) {
		planSelector = new JComboBox<Plan>(treatmentPlans.toArray(new Plan[treatmentPlans.size()]));
		planSelector.setEditable(false);
		planSelector.setRenderer(new ListCellRenderer<Plan>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends Plan> list,Plan value, int index, boolean isSelected, boolean cellHasFocus) {
				return new JLabel(value.getName());
			}
		});
		planSelector.addItemListener((ItemEvent ev)-> {
			if (ev.getStateChange() == ItemEvent.SELECTED) {
				for (ChangeListener listener:changeListeners) {
					listener.stateChanged(new ChangeEvent(this));
				}
			}
		});

		add(planSelector);
	}

	public void addChangeListener(ChangeListener listener) {
		changeListeners.add(listener);
	}

	public Plan getSelectedItem() {
		return (Plan)planSelector.getSelectedItem();
	}
}
