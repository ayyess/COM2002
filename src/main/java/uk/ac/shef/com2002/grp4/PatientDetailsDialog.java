package uk.ac.shef.com2002.grp4;

import java.awt.*;
import javax.swing.*;
import uk.ac.shef.com2002.grp4.data.Patient;

public class PatientDetailsDialog extends BaseDialog{
	private Patient patient;
	public PatientDetailsDialog(Patient patient, Component owner){
		super(owner,"Patient Details");
		this.patient = patient;
		JPanel detailsPanel = new PatientComponent(patient);
		detailsPanel.setBorder(BorderFactory.createTitledBorder("Details"));
		
		add(detailsPanel);
		pack();
	}
}
