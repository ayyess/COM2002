package uk.ac.shef.com2002.grp4;

import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Component;
import java.awt.event.*;
import uk.ac.shef.com2002.grp4.databases.PlanUtils;
import uk.ac.shef.com2002.grp4.data.Plan;

public class PlanSelector extends JPanel{
	JComboBox<Plan> planSelector;
	List<ChangeListener> changeListeners = new ArrayList<>();
	public PlanSelector(){
		this(PlanUtils.getTreatmentPlans());
	}

	public PlanSelector(List<Plan> treatmentPlans){
		planSelector = new JComboBox<Plan>(treatmentPlans.toArray(new Plan[treatmentPlans.size()]));
		planSelector.setEditable(false);
		planSelector.setRenderer(new ListCellRenderer<Plan>(){
			@Override
			public Component getListCellRendererComponent(JList<? extends Plan> list,Plan value, int index, boolean isSelected, boolean cellHasFocus){
			return new JLabel(value.getName());
			}
		});
		planSelector.addItemListener((ItemEvent ev)->{
			if(ev.getStateChange() == ItemEvent.SELECTED){
				for(ChangeListener listener:changeListeners){
					listener.stateChanged(new ChangeEvent(this));
				}
			}
		});

		add(planSelector);
	}

	public void addChangeListener(ChangeListener listener){
		changeListeners.add(listener);
	}

	public Plan getSelectedItem(){
		return (Plan)planSelector.getSelectedItem();
	}
}
