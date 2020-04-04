package com.project.tutoquizzer.viewmodels;

public class DailyScoreStorage {

    private int Score;
    private int Items;
    private String DateAdded;

    public DailyScoreStorage(int score, int items, String dateAdded) {
        Score = score;
        Items = items;
        DateAdded = dateAdded;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public int getItems() {
        return Items;
    }

    public void setItems(int items) {
        Items = items;
    }

    public String getDateAdded() {
        return DateAdded;
    }

    public void setDateAdded(String dateAdded) {
        DateAdded = dateAdded;
    }
}
