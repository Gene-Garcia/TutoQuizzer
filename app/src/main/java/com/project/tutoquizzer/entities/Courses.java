package com.project.tutoquizzer.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "Courses")
public class Courses {

    public Courses(){}

    public Courses(String name, String code) {
        Name = name;
        Code = code;
    }

    @PrimaryKey (autoGenerate = true)
    private int CourseId;

    private String Name;

    private String Code;

    public int getCourseId() {
        return CourseId;
    }

    public void setCourseId(int courseId) {
        CourseId = courseId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }
}
