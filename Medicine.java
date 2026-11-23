package com.example.dawaatracker;

public class Medicine {

    private int id;
    private String name;
    private String dose;
    private int timesPerDay;
    private String notes;

    public Medicine(int id, String name, String dose, int timesPerDay, String notes) {
        this.id = id;
        this.name = name;
        this.dose = dose;
        this.timesPerDay = timesPerDay;
        this.notes = notes;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getDose() { return dose; }
    public int getTimesPerDay() { return timesPerDay; }
    public String getNotes() { return notes; }
}
