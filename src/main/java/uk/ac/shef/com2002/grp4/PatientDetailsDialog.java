package uk.ac.shef.com2002.grp4;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

import uk.ac.shef.com2002.grp4.data.Patient;
import uk.ac.shef.com2002.grp4.data.Treatment;
import uk.ac.shef.com2002.grp4.databases.PatientUtils;
import uk.ac.shef.com2002.grp4.databases.TreatmentUtils;

public class PatientDetailsDialog extends BaseDialog implements ActionListener {
	private final JButton deleteButton;
	private final JButton closeButton;
	private Patient patient;
	private boolean modified;
	private final PlanSelector planSelector;

	private final JScrollPane listScroll;
	private final JList<Treatment> pastTreatmentList;
	private final DefaultListModel<Treatment> treatmentListModel;

	public PatientDetailsDialog(Patient patient, Component owner){
		super(owner,"Patient Details");
		this.patient = patient;
		JPanel detailsPanel = new PatientComponent(patient);
		detailsPanel.setBorder(BorderFactory.createTitledBorder("Details"));
		GridBagConstraints c = getBaseConstraints();
		c.gridwidth = 2;
		add(detailsPanel,c);
		
		planSelector = new PlanSelector();
		addLabeledComponent("Plan",planSelector);
		planSelector.addChangeListener((ChangeEvent ev)->{
			PatientPln plan = patient.getPatientPlan();
			plan.setPlan(planSelector.getSelectedValue());
			plan.update();
		});
		nextRow();
		add(new JLabel("Treatments:"),getBaseConstraints());
		nextRow();
		c = getBaseConstraints();
		c.fill = c.BOTH;
		c.gridwidth = 2;
		pastTreatmentList = new JList<Treatment>();
		listScroll = new JScrollPane(pastTreatmentList);
		treatmentListModel = new DefaultListModel<Treatment>();
		
		Treatment[] treatments = TreatmentUtils.getPatientTreatments(patient); 
		for (int i = 0; i < treatments.length; i++) {
			treatmentListModel.add(i, treatments[i]);
		}
		
		pastTreatmentList.setVisibleRowCount(3);
		pastTreatmentList.setLayoutOrientation(JList.VERTICAL);
		pastTreatmentList.setModel(treatmentListModel);
		add(listScroll,c);
		
		nextRow();

		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		addButtons(deleteButton,closeButton);
		
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == deleteButton){
			patient.delete();
			modified = true;
			dispose();
		}else if(e.getSource() == closeButton){
			dispose();
		}
	}

	public boolean getModified() {
		return modified;
	}
}
