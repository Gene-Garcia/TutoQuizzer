package com.project.tutoquizzer.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "SchoolYear")
public class SchoolYear {

    public SchoolYear(){}

    public SchoolYear(int year, int term) {
        Year = year;
        Term = term;
    }

    @PrimaryKey(autoGenerate = true)
    private int YearId;

    private int Year;

    private int Term;

    public int getYearId() {
        return YearId;
    }

    public void setYearId(int yearId) {
        YearId = yearId;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public int getTerm() {
        return Term;
    }

    public void setTerm(int term) {
        Term = term;
    }
}
