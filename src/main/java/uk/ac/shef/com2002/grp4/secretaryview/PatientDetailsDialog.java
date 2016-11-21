/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */
package uk.ac.shef.com2002.grp4.secretaryview;

import uk.ac.shef.com2002.grp4.common.BaseDialog;
import uk.ac.shef.com2002.grp4.common.PatientComponent;
import uk.ac.shef.com2002.grp4.common.PlanSelector;
import uk.ac.shef.com2002.grp4.common.data.Patient;
import uk.ac.shef.com2002.grp4.common.data.PatientPlan;
import uk.ac.shef.com2002.grp4.common.data.Treatment;
import uk.ac.shef.com2002.grp4.common.databases.TreatmentApplicationUtils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

/**
 * A dialog showing a patients details
 */
public class PatientDetailsDialog extends BaseDialog implements ActionListener {

	/** The button the delete the patient */
	private final JButton deleteButton;
	/** The button to close this dialog */
	private final JButton closeButton;
	/** The panel to select the patients plan */
	private final PlanSelector planSelector;
	/** The scrollable pane that holds the patient treatment history */
	private final JScrollPane listScroll;
	/** The patients treatment history */
	private final JList<Treatment> pastTreatmentList;
	/** The model linking treatements to their corresponding swing components */
	private final DefaultListModel<Treatment> treatmentListModel;
	/** The patient whose details to query */
	private Patient patient;
	/** Flag to mark when the patients details have been changed in */
	private boolean modified;

	/**
	 * Construct a dialog showing a patients details
	 * @param patient the patient to query
	 * @param owner the component to attach this dialog to
	 */
	public PatientDetailsDialog(Patient patient, Component owner) {
		super(owner, "Patient Details");
		this.patient = patient;
		JPanel detailsPanel = new PatientComponent(patient);
		detailsPanel.setBorder(BorderFactory.createTitledBorder("Details"));
		GridBagConstraints c = getBaseConstraints();
		c.gridwidth = 2;
		add(detailsPanel, c);

		planSelector = new PlanSelector();
		addLabeledComponent("Plan", planSelector);
		//set selector to initial value
		{
			PatientPlan plan = patient.getPatientPlan();
			planSelector.setSelectedItem(plan.getPlan());
		}
		planSelector.addChangeListener((ChangeEvent ev) -> {
			PatientPlan plan = patient.getPatientPlan();
			plan.setPlan(planSelector.getSelectedItem());
			plan.update();
			modified = true;
		});
		nextRow();
		add(new JLabel("Treatments:"), getBaseConstraints());
		nextRow();
		c = getBaseConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 2;
		pastTreatmentList = new JList<>();
		listScroll = new JScrollPane(pastTreatmentList);
		treatmentListModel = new DefaultListModel<>();

		Treatment[] treatments = TreatmentApplicationUtils.getPatientTreatments(patient);
		for (int i = 0; i < treatments.length; i++) {
			treatmentListModel.add(i, treatments[i]);
		}

		pastTreatmentList.setVisibleRowCount(3);
		pastTreatmentList.setLayoutOrientation(JList.VERTICAL);
		pastTreatmentList.setModel(treatmentListModel);
		add(listScroll, c);

		nextRow();

		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(this);
		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		addButtons(deleteButton, closeButton);

		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == deleteButton) {
			patient.delete();
			modified = true;
			dispose();
		} else if (e.getSource() == closeButton) {
			dispose();
		}
	}

	/**
	 * Check if the patient was modified
	 * @return  true if the patient was modified
	 */
	public boolean getModified() {
		return modified;
	}
}
