package com.project.tutoquizzer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.project.tutoquizzer.entities.Courses;
import com.project.tutoquizzer.entities.Scoreboard;

import java.util.List;

@Dao
public interface ScoreboardDao {

    @Insert
    void insert(Scoreboard scoreboard);

    @Query( "SELECT AVG(items) AS Items, AVG(score) AS Score, DateAdded FROM scoreboard GROUP BY DateAdded, CourseCode HAVING CourseCode = :courseCode ORDER BY DateAdded ASC" )
    LiveData<List<Integer>> getAvgByCourse(String courseCode);

    @Query( "SELECT DISTINCT CourseCode FROM Scoreboard" )
    LiveData<List<String>> getRecordedCourses();
}
