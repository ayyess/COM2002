package uk.ac.shef.com2002.grp4;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

import uk.ac.shef.com2002.grp4.data.Patient;
import uk.ac.shef.com2002.grp4.databases.PatientUtils;

public class PatientDetailsDialog extends BaseDialog implements ActionListener {
	private final JButton deleteButton;
	private final JButton closeButton;
	private Patient patient;
	private boolean modified;

	public PatientDetailsDialog(Patient patient, Component owner){
		super(owner,"Patient Details");
		this.patient = patient;
		JPanel detailsPanel = new PatientComponent(patient);
		detailsPanel.setBorder(BorderFactory.createTitledBorder("Details"));
		GridBagConstraints c = getBaseConstraints();
		c.gridwidth = 2;
		add(detailsPanel,c);
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
