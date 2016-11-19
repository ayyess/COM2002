/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */
package uk.ac.shef.com2002.grp4.partnerview;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import uk.ac.shef.com2002.grp4.common.BaseDialog;
import uk.ac.shef.com2002.grp4.common.data.Treatment;
import uk.ac.shef.com2002.grp4.common.databases.TreatmentUtils;

public class FindTreatment extends BaseDialog {

	/** The list of selected treatments, used when dialog has closed */
	Treatment[] selectedTreatments;
	
	/** List showing all possible treatments to select from */
	final JList<Treatment> treatmentList;
	/** The model for the list of treatments */
	final DefaultListModel<Treatment> treatmentModel;
	/** Scroll pane for the list of treatments */
	final JScrollPane scroll;
	
	/**
	 * Constructs a new dialog to select a list of possible treatments
	 * @param owner - The parent component that owns this dialog
	 */
	public FindTreatment(Component owner) {
		super(owner, "Treatments");
		treatmentList = new JList<Treatment>();
		treatmentModel = new DefaultListModel<Treatment>();
		treatmentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
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
	public FindTreatment(Component owner, Treatment[] treatments) {
		this(owner);
		int[] selected = new int[treatments.length];
		for (int i = 0; i < selected.length; i++) {
			selected[i] = -1;
		}
		for (int i = 0; i < treatmentModel.getSize(); i++) {
			for (int j = 0; j < treatments.length; j++) {
				if (treatmentModel.get(i).equals(treatments[j])) {
					selected[j] = i;
				}
			}
		}
		
		treatmentList.setSelectedIndices(selected);
	}
	
	void done(){
		setModal(true);
		int[] selected = treatmentList.getSelectedIndices();
		selectedTreatments = new Treatment[selected.length];
		for (int i = 0; i < selected.length; i++) {
			selectedTreatments[i] = treatmentModel.get(selected[i]);
		}
		this.dispose();
	}

}