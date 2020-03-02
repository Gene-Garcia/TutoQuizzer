package com.project.tutoquizzer.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "Quarters")
public class Quarters {

    public Quarters(){}

    public Quarters(String name) {
        Name = name;
    }

    @PrimaryKey(autoGenerate = true)
    private int QuarterId;

    private String Name;

    public int getQuarterId() {
        return QuarterId;
    }

    public void setQuarterId(int quarterId) {
        QuarterId = quarterId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

}
