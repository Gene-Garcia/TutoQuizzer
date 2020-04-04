package com.project.tutoquizzer.viewmodels;

public class DailyScoreStorage {

    private int Score;
    private int Items;
    private String DateAdded;

    public DailyScoreStorage(int Score, int Items, String DateAdded) {
        this.Score = Score;
        this.Items = Items;
        this.DateAdded = DateAdded;
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
