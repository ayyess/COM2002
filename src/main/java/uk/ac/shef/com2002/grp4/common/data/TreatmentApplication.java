package uk.ac.shef.com2002.grp4.common.data;

import uk.ac.shef.com2002.grp4.common.Partner;
import uk.ac.shef.com2002.grp4.common.databases.TreatmentUtils;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Used to link treatments to appointments with a quantity
 * <br>
 * @author  Group 4
 * @version 1.0
 * @since   16/11/2016
 */
public class TreatmentApplication {

	/** The treatment this application relates to */
	Treatment treatment;
	
	/** The date of the appointment where this treatment was performed */
	LocalDate date;
	/** The time of the appointment where this treatment was performed */
	LocalTime time;
	/** The partner that the appointment was with */
	Partner partner;
	/** The number of this treatment type was performed */
	int count;
	/** Whether the treatment has been paid for */
	boolean paid = false;
	
	/**
	 * Constructs a new treatment application from a treatment and a appointment
	 * @param treatment Treatment - One of the treatments that were performed in this appointment 
	 * @param appointment Appointment - The appointment this treatment was performed
	 * @param count int - The number of this type of treatment performed
	 */
	public TreatmentApplication(Treatment treatment, Appointment appointment, int count) {
		this.treatment = treatment;
		this.date = appointment.getDate();
		this.time = appointment.getStart();
		this.partner = Partner.valueOfIngnoreCase(appointment.getPartner());
		this.count = count;
	}
	
	/**
	 * Constructs a new TreatmentApplication with the details given 
	 * @param treatmentName String - The name of the treatment performed
	 * @param date LocalDate - The date of the appointment
	 * @param time LocalTime - The time of the appointment
	 * @param partner String - The partner this appointment was with
	 * @param count int - The number of this treatment performed
	 */
	public TreatmentApplication(String treatmentName, LocalDate date, LocalTime time, String partner, int count) {
		this.treatment = new Treatment(treatmentName, -1, null);
		this.date = date;
		this.time = time;
		this.partner = Partner.valueOfIngnoreCase(partner);
		this.count = count;
	}
	
	public TreatmentApplication(String treatmentName, LocalDate date, LocalTime time, String partner, int count, boolean paid) {
		this.treatment = new Treatment(treatmentName, -1, null);
		this.date = date;
		this.time = time;
		this.partner = Partner.valueOfIngnoreCase(partner);
		this.count = count;
		this.paid = paid;
	}
	
	public TreatmentApplication(Treatment treatment, LocalDate date, LocalTime time, Partner partner, int count) {
		this.treatment = treatment;
		this.date = date;
		this.time = time;
		this.partner = partner;
		this.count = count;
	}

	/** 
	 * @return The treatment name
	 */
	public String getTreatmentName() {
		return treatment.getName();
	}

	/**
	 * @return The Date that the appointment was performed on
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * @return The Time that the appointment was performed on
	 */
	public LocalTime getTime() {
		return time;
	}

	/**
	 * @return The partner that the treatment was by
	 */
	public Partner getPartner() {
		return partner;
	}

	/**
	 * @return The number of treatments of this type were performed
	 */
	public int getCount() {
		return count;
	}
	
	public String toString() {
		return count + "x" + getTreatmentName();
	}

	/**
	 * Updates the number of treatments performed
	 * @param count int - The new number of treatments performed
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public TreatmentApplication clone() {
		return new TreatmentApplication(getTreatmentName(), getDate(), getTime(), getPartner().toString(), getCount());
	}

	/**
	 * Used to change the treatment link when linked with the 
	 * database treatment with all information
	 * @param t Treatment - The new treatment for this treatment application
	 */
	public void setTreatment(Treatment t) {
		this.treatment = t;
	}
	
	/**
	 * @return The cost of this treatment for this appointment <b>not</b> 
	 * multiplied by the number of treatments  
	 */
	public int getCost() {
		if (treatment != null) {
			return treatment.getCost();
		}
		treatment = TreatmentUtils.getDetailsByName(getTreatmentName());
		return treatment.getCost();
	}

}
