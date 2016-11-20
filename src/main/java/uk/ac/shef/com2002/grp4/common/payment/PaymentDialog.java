/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.payment;

import uk.ac.shef.com2002.grp4.common.BaseDialog;
import uk.ac.shef.com2002.grp4.common.data.Patient;
import uk.ac.shef.com2002.grp4.common.data.PatientPlan;
import uk.ac.shef.com2002.grp4.common.data.Treatment;
import uk.ac.shef.com2002.grp4.common.data.TreatmentApplication;
import uk.ac.shef.com2002.grp4.common.databases.PatientPlanUtils;
import uk.ac.shef.com2002.grp4.common.databases.TreatmentApplicationUtils;
import uk.ac.shef.com2002.grp4.common.databases.TreatmentUtils;
import uk.ac.shef.com2002.grp4.common.util.CostUtil;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to manage a patient's payment.
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   14/11/2016
 */
public class PaymentDialog extends BaseDialog {

	/**
	 * Amount the patient is going to pay today
	 */
	int payAmount = 0;
	
	/**
	 * This creates a new PaymentDialog.
	 *
	 * @param o - a Component The parent component
	 * @param p - a Patient The patient that is paying
	 */
	public PaymentDialog(Component o, Patient p) {
		super(o, "Payment");
		
		addLabeledComponent("Patient", new JLabel(p.getName()));

		
		TreatmentApplication[] treatmentApplications = TreatmentApplicationUtils.getRemainingTreatments(p);
		DefaultListModel<TreatmentPrice> model = new DefaultListModel<TreatmentPrice>();
		
		//Fetch the plan and if there isn't one then just use the default one
		PatientPlan pl = PatientPlanUtils.getPlanByPatientID(p.getID());
		PatientPlan plan;
		if (pl == null) {
			plan = PatientPlan.defaultFor(p);
		} else {
			plan = pl;
		}
		
		Treatment[] treatments = TreatmentUtils.getTreatments(); 
		
		
		
		int total = 0;
		int savings = 0;
		boolean validPlan = plan.checkPlanValid();
		
		int i = 0;
		
		int repairs = plan.getRemainingRepairs();
		int hygiene = plan.getRemainingHygieneVisits();
		int checkups = plan.getRemainingCheckups();
		
		for (TreatmentApplication ta : treatmentApplications) {
			// Deduct savings
			int cost = 0;
			Treatment t = getByName(treatments, ta.getTreatmentName());
			ta.setTreatment(t);
			if (t.getType().equals("REPAIR")) {
				if (validPlan && repairs>0) {
					repairs -= 1;
					savings += t.getCost();
					cost = 0;
				} else  {
					total += t.getCost();
					cost = t.getCost();
				}
			} else if (t.getType().equals("HYGIENE")) {
				if (validPlan && hygiene>0) {
					hygiene -= 1;
					savings += t.getCost();
					cost = 0;
				} else  {
					total += t.getCost();
					cost = t.getCost();
				} 
			} else if (t.getType().equals("CHECKUP")) {
				if (validPlan && checkups>0) {
					checkups -= 1;
					savings += t.getCost();
					cost = 0;
				} else  {
					total += t.getCost();
					cost = t.getCost();
				} 
			} else {
				total += t.getCost();
			}
			TreatmentPrice tp = new TreatmentPrice(t.getName(), t.getCost(), cost, t, ta);
			model.add(i++, tp);
		}
		
		
		
		JList<TreatmentPrice> treatmentList = new JList<TreatmentPrice>();
		treatmentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		treatmentList.setModel(model);
		
		addLabeledComponent("Treatments", treatmentList);
		
		addLabeledComponent("Plan", new JLabel(plan.toString()));
		
		JTextField payAmountText = new JTextField(4);
		addLabeledComponent("Amount to pay", payAmountText);
		
		JTextField owedAmountText = new JTextField(4);
		addLabeledComponent("Total owed", owedAmountText);
		owedAmountText.setText(CostUtil.costToDecimalString(total));
		
		treatmentList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int[] selected = treatmentList.getSelectedIndices();
				int cost = 0;
				for (int s = 0; s < selected.length; s++) {
					TreatmentPrice tp = model.get(selected[s]);
					cost+= tp.discountedCost * tp.treatmentApplication.getCount();
				}
				
				payAmount = cost;
				payAmountText.setText(CostUtil.costToDecimalString(cost));
			}
		});
		
		JButton pay = new JButton("Pay");
		pay.addActionListener((e) -> {
			//Print dialog
			int[] selected = treatmentList.getSelectedIndices();
			List<TreatmentPrice> payingTreatments = new ArrayList<TreatmentPrice>();
			
			for (int s = 0; s < selected.length; s++) {
				payingTreatments.add(model.get(selected[s]));
			}
			
			List<TreatmentPrice> treatmentsLeft = new ArrayList<TreatmentPrice>();
			for (int m = 0; m < model.size(); m++) {
				boolean found = false;
				for (int s = 0; s < selected.length; s++) {
					if (m == selected[s]) {
						found = true;
						break;
					}
				}
				if (!found) {
					treatmentsLeft.add(model.get(m));
				}
			}
			
			Receipt r = new Receipt(p, plan, payingTreatments, treatmentsLeft, payAmount);
			//new PrintPreview(r);
		});
		
		JButton close = new JButton("Close");
		close.addActionListener((e) -> {
			dispose();
		});
		
		addButtons(pay, close);
		pack();
	}
	
	/**
	 * Searches through the list of treatments and returns that
	 * first one that has the same name.
	 * @param treatments Treatment[] - The list to look through 
	 * @param name String - The name of the treatment to search for
	 * @return
	 */
	Treatment getByName(Treatment[] treatments, String name) {
		for (Treatment t : treatments) {
			if (t.getName().equalsIgnoreCase(name)) {
				return t;
			}
		}
		return null;
	}

}
