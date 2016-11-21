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
import uk.ac.shef.com2002.grp4.common.UserFacingException;
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
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to manage a patient's payment.
 * <br>
 *
 * @author Group 4
 * @version 1.0
 * @since 14/11/2016
 */
public class PaymentDialog extends BaseDialog {

	/**
	 * Amount the patient is going to pay today
	 */
	int payAmount = 0;

	/**
	 * Total cost for the patient's treatments
	 */
	int total = 0;

	/**
	 * The patient who's details are being shown
	 */
	Patient patient;

	/**
	 * Treatments to show
	 */
	TreatmentApplication[] treatmentApplications;
	DefaultListModel<TreatmentPrice> model;

	/**
	 * The patient's health care plan
	 */
	PatientPlan plan;

	/**
	 * This creates a new PaymentDialog.
	 *
	 * @param o - a Component The parent component
	 * @param p - a Patient The patient that is paying
	 */
	public PaymentDialog(Component o, Patient p) {
		super(o, "Payment");
		this.patient = p;

		addLabeledComponent("Patient", new JLabel(p.getName()));


		model = new DefaultListModel<>();

		addTreatments();

		JList<TreatmentPrice> treatmentList = new JList<>();
		treatmentList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		treatmentList.setModel(model);

		addLabeledComponent("Treatments", treatmentList);

		addLabeledComponent("Plan", new JLabel(plan.toString()));

		JTextField payAmountText = new JTextField(4);
		addLabeledComponent("Amount to pay", payAmountText);

		JTextField owedAmountText = new JTextField(4);
		addLabeledComponent("Total owed", owedAmountText);
		owedAmountText.setText(CostUtil.costToDecimalString(total));

		treatmentList.addListSelectionListener(e -> {
			int[] selected = treatmentList.getSelectedIndices();
			int cost = 0;
			for (int aSelected : selected) {
				TreatmentPrice tp = model.get(aSelected);
				cost += tp.discountedCost * tp.treatmentApplication.getCount();
			}

			payAmount = cost;
			payAmountText.setText(CostUtil.costToDecimalString(cost));
		});

		JButton pay = new JButton("Pay");
		pay.addActionListener((e) -> {
			//Print dialog
			int[] selected = treatmentList.getSelectedIndices();
			List<TreatmentPrice> payingTreatments = new ArrayList<>();

			for (int aSelected : selected) {
				payingTreatments.add(model.get(aSelected));
			}

			List<TreatmentPrice> treatmentsLeft = new ArrayList<>();
			for (int m = 0; m < model.size(); m++) {
				boolean found = false;
				for (int aSelected : selected) {
					if (m == aSelected) {
						found = true;
						break;
					}
				}
				if (!found) {
					treatmentsLeft.add(model.get(m));
				}
			}

			Receipt r = new Receipt(p, plan, payingTreatments, treatmentsLeft, payAmount);
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintable(r);
			try {
				job.print();
			} catch (PrinterException pe) {
				throw new UserFacingException(pe.getLocalizedMessage());
			}
			//new PrintPreview(r);
			addTreatments();
		});

		JButton close = new JButton("Close");
		close.addActionListener((e) -> dispose());

		addButtons(pay, close);
		pack();
	}

	/**
	 * Searches through the list of treatments and returns that
	 * first one that has the same name.
	 *
	 * @param treatments Treatment[] - The list to look through
	 * @param name       String - The name of the treatment to search for
	 * @return Treatment
	 */
	Treatment getByName(Treatment[] treatments, String name) {
		for (Treatment t : treatments) {
			if (t.getName().equalsIgnoreCase(name)) {
				return t;
			}
		}
		return null;
	}

	public void addTreatments() {
		model.removeAllElements();
		treatmentApplications = TreatmentApplicationUtils.getRemainingTreatments(patient);
		//Fetch the plan and if there isn't one then just use the default one
		PatientPlan pl = PatientPlanUtils.getPlanByPatientID(patient.getId());
		if (pl == null) {
			plan = PatientPlan.defaultFor(patient);
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
			switch (t.getType()) {
				case "REPAIR":
				case "HYGIENE":
				case "CHECKUP":
					if (validPlan && repairs > 0) {
						repairs -= 1;
						savings += t.getCost();
						cost = 0;
					} else {
						total += t.getCost();
						cost = t.getCost();
					}
					break;
				default:
					total += t.getCost();
					break;
			}
			TreatmentPrice tp = new TreatmentPrice(t.getName(), t.getCost(), cost, t, ta);
			model.add(i++, tp);
		}
	}

}
