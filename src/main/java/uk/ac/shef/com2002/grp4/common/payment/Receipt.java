/* This file is part of Grp4 Dental Care System.
 * This system is for private, educational use. It should solely be viewed by those
 * marking the COM2002 assignment.
 * Unauthorised copying or editing of this file is strictly prohibited.
 *
 * This system uses GPL-licensed software.
 * Visit <http://www.gnu.org/licenses/> to see the license.
 */

package uk.ac.shef.com2002.grp4.common.payment;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.List;

import uk.ac.shef.com2002.grp4.common.data.Patient;
import uk.ac.shef.com2002.grp4.common.data.Treatment;
/**
 * Used to print patient a receipt.
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   1/11/2016
 */
public class Receipt implements Printable {

	/** This stores the Patient object. */
	private Patient patient;
	/** This stores the cost of the treatments. */
	private int cost;
	/** This stores the treatments being invoiced for. */
	private List<Treatment> treatments;

	/**
	 * This constructor creates a new receipt object which can be used to invoice a
	 * customer for their treatments.
	 *
	 * @param patient - a Patient
	 * @param treatments - an ArrayList of Treatment
	 * @param cost - amount owed to the practice
	 */
	public Receipt(Patient patient, List<Treatment> treatments, int cost) {
		this.patient = patient;
		this.treatments = treatments;
		this.cost = cost;
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
		
		int total = 0;
		
		for (Treatment t : treatments) {
			g2.drawString(t.toString(), 0, linePos);
			total += t.getCost();
			String costString = costToDecimalString(t.getCost());
			g2.drawString(costString, width-fm.stringWidth(costString), linePos);
			linePos += lineHeight;
		}
		
		g2.drawString("Sub-Total:", 0, linePos);
		String preSavingCostString = costToDecimalString(total); 
		g2.drawString(preSavingCostString, width-fm.stringWidth(preSavingCostString), linePos);
		linePos += lineHeight;
		
		int savings = 100; //TODO change to savings calculation for treatment plan
		
		//TODO calculate savings
		if (savings > 0) {
			g2.drawString("Savings:", 0, linePos);
			String savingsString = costToDecimalString(savings); 
			g2.drawString(savingsString, width-fm.stringWidth(savingsString), linePos);
			linePos += lineHeight;
		}
		
		//g2.drawString("Total:", 0, linePos);
		//String totalString = costToDecimalString(total*savings);
		//g2.drawString(totalString, width-fm.stringWidth(totalString), linePos);
		//inePos += lineHeight;
		
		g2.drawString("Paid today:", 0, linePos);
		String costString = costToDecimalString(cost);
		g2.drawString(costString, width-fm.stringWidth(costString), linePos);
		linePos += lineHeight;
		
		int remaining = (total - savings) - cost;
		
		g2.drawString("Remaining:", 0, linePos);
		String remainingCostString = costToDecimalString(remaining);
		g2.drawString(remainingCostString, width-fm.stringWidth(remainingCostString), linePos);
		linePos += lineHeight;
		
		return PAGE_EXISTS; //Success
	}

	/**
	 * This converts the cost of a treatment to a string.
	 * @param cost - the cost as an integer
	 * @return a String representation of the cost
	 */
	private String costToDecimalString(int cost) {
		return '\u00A3' + String.valueOf(cost / 100.0);
	}
	
}
