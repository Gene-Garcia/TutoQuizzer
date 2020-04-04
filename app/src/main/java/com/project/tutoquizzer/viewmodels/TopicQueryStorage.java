package com.project.tutoquizzer.viewmodels;

import androidx.room.Entity;

@Entity(tableName = "TopicQueryStorage")
public class TopicQueryStorage {

    private int TCount;
    private int CourseId;

    public TopicQueryStorage(int TCount, int CourseId) {
        this.TCount = TCount;
        this.CourseId = CourseId;
    }

    public int getTCount() {
        return TCount;
    }

    public int getCourseId() {
        return CourseId;
    }
}
