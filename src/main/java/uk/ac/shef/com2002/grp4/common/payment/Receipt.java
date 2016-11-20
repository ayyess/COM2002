/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.payment;

import uk.ac.shef.com2002.grp4.common.data.Patient;
import uk.ac.shef.com2002.grp4.common.data.PatientPlan;
import uk.ac.shef.com2002.grp4.common.data.Treatment;
import uk.ac.shef.com2002.grp4.common.data.TreatmentApplication;
import uk.ac.shef.com2002.grp4.common.util.CostUtil;

import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.HashMap;
import java.util.List;
/**
 * Used to print patient a receipt.
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   1/11/2016
 */
public class Receipt implements Printable {

	private final static String REPAIR = "REPAIR";
	private final static String HYGIENE = "HYGIENE";
	private final static String CHECKUP = "CHECKUP";
	
	/** This stores the Patient object. */
	private Patient patient;
	/** The amount to be paid. */
	private int paidToday;
	/** This stores the treatments being invoiced for. */
	private List<TreatmentPrice> payingTreatments;
	
	/** This stores the treatments to pay after today. */
	private List<TreatmentPrice> treatmentsLeft;
	
	private PatientPlan plan;

	/**
	 * This constructor creates a new receipt object which can be used to invoice a
	 * customer for their treatments.
	 *
	 * @param patient - a Patient
	 * @param treatmentApplications - an ArrayList of Treatment
	 * @param paidToday - amount to be paid today
	 */
	public Receipt(Patient patient, PatientPlan plan, List<TreatmentPrice> payingTreatments, List<TreatmentPrice> treatmentsLeft, int paidToday) {
		this.patient = patient;
		this.plan = plan;
		this.payingTreatments = payingTreatments;
		this.treatmentsLeft = treatmentsLeft;
		this.paidToday = paidToday;
	}

	/** Prints the receipt. */
	public void print() {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
	}

	/**
	 * Prints the receipt in a particular format.
	 *
	 * @param g - the Graphics to draw the receipt on to
	 * @param pageFormat - The format of the page to print on to
	 * @param pageIndex - The index of which page to draw
	 * @return PAGE_EXISTS or NO_SUCH_PAGE  
	 * @throws PrinterException
	 */
	@Override
	public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
		boolean validPlan = plan.checkPlanValid();
		if (pageIndex != 0) {
			return NO_SUCH_PAGE; // Fail
		}
		if (pageFormat.getOrientation() != PageFormat.PORTRAIT) {
			return NO_SUCH_PAGE; // Fail
		}
		
		Graphics2D g2 = (Graphics2D) g;
		g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		
		int width = (int) pageFormat.getImageableWidth();
		int height = (int) pageFormat.getImageableHeight();
		FontMetrics fm = g.getFontMetrics();
		Font font = g.getFont();
		
		int lineHeight = font.getSize()*2; 
		
		int linePos = 0;
		
		g2.setColor(Color.BLACK);
		g2.fillRect(0, 0, width, font.getSize());
		linePos += lineHeight;
		
		g2.drawString(patient.getName(), 0, linePos);
		linePos += lineHeight;
		
		int savings = 0; 
		int subTotal = 0;
		
		int i = 0;
		
		HashMap<Integer, TreatmentApplication> modelMap = new HashMap<Integer, TreatmentApplication>(); 
		
		for (TreatmentPrice tp : payingTreatments) {
			// Deduct savings
			TreatmentApplication ta = tp.treatmentApplication;
			Treatment t = tp.treatment;
			ta.setTreatment(t);
			if (t.getType().equals("REPAIR")) {
				int repairs = plan.getRemainingRepairs();
				if (validPlan && repairs>0) {
					plan.useRepair();
					savings += ta.getCount()*t.getCost();
				}
				subTotal += ta.getCount()*t.getCost();
			} else if (t.getType().equals("HYGIENE")) {
				int hygiene = plan.getRemainingHygieneVisits();
				if (validPlan && hygiene>0) {
					plan.useHygiene();
					savings += ta.getCount()*t.getCost();
				}
				subTotal += ta.getCount()*t.getCost();
			} else if (t.getType().equals("CHECKUP")) {
				int checkups = plan.getRemainingCheckups();
				if (validPlan && checkups>0) {
					plan.useCheckup();
					savings += ta.getCount()*t.getCost();
				}
				subTotal += ta.getCount()*t.getCost();
			} else {
				subTotal += ta.getCount()*t.getCost();
			}
			modelMap.put(i, ta);
			
			g2.drawString(tp.toStringWithoutPrice(), 0, linePos);
			String costString = CostUtil.costToDecimalString(t.getCost()*ta.getCount());
			g2.drawString(costString, width-fm.stringWidth(costString), linePos);
			linePos += lineHeight;
		}
		
		g2.drawString("Sub-Total:", 0, linePos);
		String preSavingCostString = CostUtil.costToDecimalString(subTotal); 
		g2.drawString(preSavingCostString, width-fm.stringWidth(preSavingCostString), linePos);
		linePos += lineHeight;
		
		
		if (savings > 0) {
			g2.drawString("Savings:", 0, linePos);
			String savingsString = CostUtil.costToDecimalString(savings); 
			g2.drawString(savingsString, width-fm.stringWidth(savingsString), linePos);
			linePos += lineHeight;
		}
		
		g2.drawString("Total:", 0, linePos);
		String totalString = CostUtil.costToDecimalString(subTotal-savings);
		g2.drawString(totalString, width-fm.stringWidth(totalString), linePos);
		linePos += lineHeight;
		
		g2.drawString("Paid today:", 0, linePos);
		String costString = CostUtil.costToDecimalString(paidToday);
		g2.drawString(costString, width-fm.stringWidth(costString), linePos);
		linePos += lineHeight;
		
		g2.drawString("Remaining:", 0, linePos);
		linePos += lineHeight;
		for (TreatmentPrice tp : treatmentsLeft) {
			g2.drawString(tp.toStringWithoutPrice(), fm.stringWidth("Remaining:")+1 , linePos);
			linePos += lineHeight;
		}
		
		plan.update();
		
		return PAGE_EXISTS; //Success
	}
	
	Treatment getByName(List<Treatment> treatments, String name) {
		for (Treatment t : treatments) {
			if (t.getName().equalsIgnoreCase(name)) {
				return t;
			}
		}
		return null;
	}

}
