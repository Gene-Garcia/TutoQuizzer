package com.project.tutoquizzer.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity (tableName = "Scoreboard")
public class Scoreboard {

    public Scoreboard(int score, int items, String courseCode, String quarterName, String schoolYearName, Date dateAdded) {
        Score = score;
        Items = items;
        CourseCode = courseCode;
        QuarterName = quarterName;
        SchoolYearName = schoolYearName;
        DateAdded = dateAdded;
    }

    @PrimaryKey (autoGenerate = true)
    private int ScoreboardId;

    private int Score;
    private int Items;
    private String CourseCode;
    private String QuarterName;
    private String SchoolYearName;
    private Date DateAdded;

    public int getScoreboardId() {
        return ScoreboardId;
    }

    public void setScoreboardId(int scoreboardId) {
        ScoreboardId = scoreboardId;
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

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String courseCode) {
        CourseCode = courseCode;
    }

    public String getQuarterName() {
        return QuarterName;
    }

    public void setQuarterName(String quarterName) {
        QuarterName = quarterName;
    }

    public String getSchoolYearName() {
        return SchoolYearName;
    }

    public void setSchoolYearName(String schoolYearName) {
        SchoolYearName = schoolYearName;
    }

    public Date getDateAdded() {
        return DateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        DateAdded = dateAdded;
    }
}
