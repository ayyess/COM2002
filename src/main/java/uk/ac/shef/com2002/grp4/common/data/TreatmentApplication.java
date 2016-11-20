package uk.ac.shef.com2002.grp4.common.data;

import uk.ac.shef.com2002.grp4.common.Partner;
import uk.ac.shef.com2002.grp4.common.databases.TreatmentUtils;

import java.time.LocalDate;
import java.time.LocalTime;

public class TreatmentApplication {

	Treatment treatment;
	
	LocalDate date;
	LocalTime time;
	Partner partner;
	int count;
	boolean paid = false;
	
	public TreatmentApplication(Treatment treatment, Appointment appointment, int count) {
		this.treatment = treatment;
		this.date = appointment.getDate();
		this.time = appointment.getStart();
		this.partner = Partner.valueOfIngnoreCase(appointment.getPartner());
		this.count = count;
	}
	
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

	public String getTreatmentName() {
		return treatment.getName();
	}

	public LocalDate getDate() {
		return date;
	}

	public LocalTime getTime() {
		return time;
	}

	public Partner getPartner() {
		return partner;
	}

	public int getCount() {
		return count;
	}
	
	public String toString() {
		return count + "x" + getTreatmentName();
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public TreatmentApplication clone() {
		return new TreatmentApplication(getTreatmentName(), getDate(), getTime(), getPartner().toString(), getCount());
	}

	public void setTreatment(Treatment t) {
		this.treatment = t;
	}
	
	public int getCost() {
		if (treatment != null) {
			return treatment.getCost();
		}
		treatment = TreatmentUtils.getDetailsByName(getTreatmentName());
		return treatment.getCost();
	}

}
