package com.project.tutoquizzer.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "Scoreboard")
public class Scoreboard {

    public Scoreboard(int Score, int Items, String CourseCode, String QuarterName, String SchoolYearName, String DateAdded) {
        this.Score = Score;
        this.Items = Items;
        this.CourseCode = CourseCode;
        this.QuarterName = QuarterName;
        this.SchoolYearName = SchoolYearName;
        this.DateAdded = DateAdded;
    }

    @PrimaryKey (autoGenerate = true)
    private int scoreboardId;

    private int Score;
    private int Items;
    private String CourseCode;
    private String QuarterName;
    private String SchoolYearName;

    private String DateAdded;

    public int getScoreboardId() {
        return scoreboardId;
    }

    public void setScoreboardId(int scoreboardId) {
        this.scoreboardId = scoreboardId;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        this.Score = score;
    }

    public int getItems() {
        return Items;
    }

    public void setItems(int items) {
        this.Items = items;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String courseCode) {
        this.CourseCode = courseCode;
    }

    public String getQuarterName() {
        return QuarterName;
    }

    public void setQuarterName(String quarterName) {
        this.QuarterName = quarterName;
    }

    public String getSchoolYearName() {
        return SchoolYearName;
    }

    public void setSchoolYearName(String schoolYearName) {
        this.SchoolYearName = schoolYearName;
    }

    public String getDateAdded() {
        return DateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.DateAdded = dateAdded;
    }
}
