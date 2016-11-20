/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */
package uk.ac.shef.com2002.grp4.partnerview;

import uk.ac.shef.com2002.grp4.common.BaseDialog;
import uk.ac.shef.com2002.grp4.common.data.Treatment;
import uk.ac.shef.com2002.grp4.common.databases.TreatmentUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Used to select treatments for a certain appointment
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   13/11/2016
 */
public class FindTreatment extends BaseDialog {

	/** The list of selected treatments, used when dialog has closed */
	Treatment selected;
	
	/** List showing all possible treatments to select from */
	final JList<Treatment> treatmentList;
	/** The model for the list of treatments */
	final DefaultListModel<Treatment> treatmentModel;
	/** Scroll pane for the list of treatments */
	final JScrollPane scroll;
	
	final SpinnerNumberModel spinnerModel;
	final JSpinner countSpinner;
	
	int count;
	
	
	/**
	 * Constructs a new dialog to select a list of possible treatments
	 * @param owner - The parent component that owns this dialog
	 */
	public FindTreatment(Component owner) {
		super(owner, "Treatments");
		treatmentList = new JList<Treatment>();
		treatmentModel = new DefaultListModel<Treatment>();
		treatmentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		treatmentList.setModel(treatmentModel);
		
		// Fetch all of the possible treatments and add them to the list
		Treatment[] treatments = TreatmentUtils.getTreatments();
		for (int i = 0; i < treatments.length; i++) {
			treatmentModel.add(i, treatments[i]);
		}
		
		// Make it so that when a item is single clicked it toggles the selection of it
		treatmentList.setSelectionModel(new DefaultListSelectionModel() {
		    @Override
		    public void setSelectionInterval(int first, int second) {
		        if (super.isSelectedIndex(first)) {
		            super.removeSelectionInterval(first, second);
		        } else {
		            super.addSelectionInterval(first, second);
		        }
		        fireValueChanged(first, second);
		    }
		});
		
		scroll = new JScrollPane(treatmentList);
		
		addLabeledComponent("Treatments", scroll);
		
		JButton cancelButton = new JButton("Close");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				cancel();
			}
		});
		JButton doneButton = new JButton("Done");
		doneButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae){
				done();
			}
		});
		
		spinnerModel = new SpinnerNumberModel(1, 1, 100, 1);
		countSpinner = new JSpinner();
		
		addButtons(cancelButton,doneButton);
		pack();
	}
	
	/**
	 * Constructs a dialog to select a list of treatments.
	 * <p>
	 * The given list will set the list of selected items
	 * @param owner - The parent component of this dialog
	 * @param treatments - The treatments to select by default
	 */
	public FindTreatment(Component owner, Treatment defaultTreatment) {
		this(owner);
		for (int i = 0; i < treatmentModel.getSize(); i++) {
			if (treatmentModel.get(i).equals(defaultTreatment)) {
				treatmentList.setSelectedIndex(i);
				break;
			}
		}
	}
	
	/**
	 * Updates the selected list and closes the dialog
	 */
	void done(){
		setModal(true);
		count = spinnerModel.getNumber().intValue();
		selected = treatmentList.getSelectedValue();
		this.dispose();
	}

}
