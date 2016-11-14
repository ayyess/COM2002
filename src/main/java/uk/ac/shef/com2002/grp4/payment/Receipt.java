package uk.ac.shef.com2002.grp4.payment;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;

import uk.ac.shef.com2002.grp4.data.Patient;
import uk.ac.shef.com2002.grp4.data.Treatment;

public class Receipt implements Printable {

	private Patient patient;
	private int cost;
	private ArrayList<Treatment> treatments;
	
	public Receipt(Patient patient, ArrayList<Treatment> treatments, int cost) {
		this.patient = patient;
		this.treatments = treatments;
		this.cost = cost;
	}
	
	public void print() {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
	}

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
		
		//return NO_SUCH_PAGE; // Fail
		//return PAGE_EXISTS; //Success
		return PAGE_EXISTS; //Success
	}
	
	private String costToDecimalString(int cost) {
		return String.valueOf(cost / 100.0);
	}
	
}
