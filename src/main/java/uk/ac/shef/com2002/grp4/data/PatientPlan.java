package uk.ac.shef.com2002.grp4.data;

import java.sql.Date;
import java.time.LocalDate;

/**
 * Created by Dan-L on 09/11/2016.
 */
public class PatientPlan {
    private String name;
    private int cost;
    private LocalDate startDate;
    private int remCheckUps;
    private int remHygiene;
    private int remRepairs;

    public PatientPlan(String name, int cost, Date startDate, int checks, int hygienes, int repairs) {
        this.name = name;
        this.cost = cost;
        this.startDate = startDate.toLocalDate();
        this.remCheckUps = checks;
        this.remHygiene = hygienes;
        this.remRepairs = repairs;
    }

    public PatientPlan(String name, int cost, LocalDate startDate, int checks, int hygienes, int repairs) {
        this.name = name;
        this.cost = cost;
        this.startDate = startDate;
        this.remCheckUps = checks;
        this.remHygiene = hygienes;
        this.remRepairs = repairs;
    }
}
