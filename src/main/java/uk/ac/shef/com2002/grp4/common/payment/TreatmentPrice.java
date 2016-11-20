package uk.ac.shef.com2002.grp4.common.payment;

import uk.ac.shef.com2002.grp4.common.data.Treatment;
import uk.ac.shef.com2002.grp4.common.data.TreatmentApplication;
import uk.ac.shef.com2002.grp4.common.util.CostUtil;

/**
 * Class to hold/show details about the price of a treatment as well as 
 * to store store the link between Treatment and TreatmentApplication without
 * having multiple calls to the database 
 */
class TreatmentPrice {
	/** Name of the treatment */
	String name;
	/** Cost of this treatment */
	int cost;
	/** Price of the treatment after discounts from treatment plans */
	int discountedCost;
	/** The treatment link */
	Treatment treatment;
	/** The treatment application link */
	TreatmentApplication treatmentApplication;
	
	/**
	 * Constructs a new treatment price to store and show the treatment and its cost
	 * @param name String - The name of the treatment
	 * @param cost int - The cost of the treatment
	 * @param discountedCost int - The cost after the healthcare plan has been deducted
	 * @param t Treatment - The treatment
	 * @param ta TreatmentApplication - The treatment application
	 */
	public TreatmentPrice(String name, int cost, int discountedCost, Treatment t, TreatmentApplication ta) {
		this.name = name;
		this.cost = cost;
		this.discountedCost = discountedCost;
		this.treatment = t;
		this.treatmentApplication = ta;
	}
	
	public String toString() {
		return treatmentApplication.getCount() + "x" + name + " " + CostUtil.costToDecimalString(discountedCost);
	}
	
	public String toStringWithoutPrice() {
		return treatmentApplication.getCount() + "x" + name;
	}
	
}