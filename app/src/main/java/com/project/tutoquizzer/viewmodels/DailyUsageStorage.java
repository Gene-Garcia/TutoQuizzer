package com.project.tutoquizzer.viewmodels;

public class DailyUsageStorage {

    private int Number;
    private String DateAdded;

    public DailyUsageStorage(int Number, String DateAdded) {
        this.Number = Number;
        this.DateAdded = DateAdded;
    }

    public int getNumber() {
        return Number;
    }

    public void setNumber(int number) {
        Number = number;
    }

    public String getDateAdded() {
        return DateAdded;
    }

    public void setDateAdded(String dateAdded) {
        DateAdded = dateAdded;
    }
}
