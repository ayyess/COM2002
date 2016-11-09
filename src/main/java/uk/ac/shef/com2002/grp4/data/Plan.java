package uk.ac.shef.com2002.grp4.data;

/**
 * Created by Dan-L on 09/11/2016.
 */
public class Plan {
    private String name;
    private int cost;
    private int checkups;
    private int hygiene_visits;
    private int repairs;

    public Plan(String name, int cost, int checkups, int hygiene, int repairs) {
        this.name = name;
        this.cost = cost;
        this.checkups = checkups;
        this.hygiene_visits = hygiene;
        this.repairs = repairs;
    }
}
