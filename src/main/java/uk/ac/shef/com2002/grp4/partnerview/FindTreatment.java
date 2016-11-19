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

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import uk.ac.shef.com2002.grp4.common.BaseDialog;
import uk.ac.shef.com2002.grp4.common.data.Treatment;
import uk.ac.shef.com2002.grp4.common.databases.TreatmentUtils;

public class FindTreatment extends BaseDialog {

	Treatment[] selectedTreatments;
	
	final JList<Treatment> treatmentList;
	final DefaultListModel<Treatment> treatmentModel;
	
	public FindTreatment(Component owner) {
		super(owner, "Treatments");
		treatmentList = new JList<Treatment>();
		treatmentModel = new DefaultListModel<Treatment>();
		treatmentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		treatmentList.setModel(treatmentModel);
		
		Treatment[] treatments = TreatmentUtils.getTreatments();
		for (int i = 0; i < treatments.length; i++) {
			treatmentModel.add(i, treatments[i]);
		}
		
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
		
		addLabeledComponent("Treatments", treatmentList);
		
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
