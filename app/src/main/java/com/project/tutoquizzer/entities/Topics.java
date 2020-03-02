package com.project.tutoquizzer.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity (tableName = "Topics")
public class Topics {

    public Topics(){}

    public Topics(String topic, String meaning, int quarterId, int courseId, int yearId) {
        this.Topic = topic;
        this.Meaning = meaning;
        this.QuarterId = quarterId;
        this.CourseId = courseId;
        this.YearId = yearId;
    }

    @PrimaryKey (autoGenerate = true)
    private int TopicId;

    private String Topic;

    private String Meaning;

    private int QuarterId;

    private int CourseId;

    private int YearId;

    public int getTopicId() {
        return TopicId;
    }

    public void setTopicId(int topicId) {
        TopicId = topicId;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getMeaning() {
        return Meaning;
    }

    public void setMeaning(String meaning) {
        Meaning = meaning;
    }

    public int getQuarterId() {
        return QuarterId;
    }

    public void setQuarterId(int quarterId) {
        QuarterId = quarterId;
    }

    public int getCourseId() {
        return CourseId;
    }

    public void setCourseId(int courseId) {
        CourseId = courseId;
    }

    public int getYearId() {
        return YearId;
    }

    public void setYearId(int yearId) {
        YearId = yearId;
    }
}
