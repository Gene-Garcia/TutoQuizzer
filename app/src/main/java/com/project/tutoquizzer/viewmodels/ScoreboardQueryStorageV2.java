package com.project.tutoquizzer.viewmodels;

import androidx.room.Entity;

@Entity(tableName = "ScoreboardQueryStorage")
public class ScoreboardQueryStorageV2 {

    public ScoreboardQueryStorageV2(int Score, String CourseCode) {
        this.Score = Score;
        this.CourseCode = CourseCode;
    }

    private int Score;

    private String CourseCode;

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String courseCode) {
        this.CourseCode = courseCode;
    }
}