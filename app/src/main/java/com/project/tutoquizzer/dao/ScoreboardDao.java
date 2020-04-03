package com.project.tutoquizzer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.project.tutoquizzer.entities.Scoreboard;
import com.project.tutoquizzer.viewmodels.ScoreboardQueryStorage;

import java.util.List;

@Dao
public interface ScoreboardDao {

    @Insert
    void insert(Scoreboard scoreboard);

    @Query( "SELECT AVG(Items) AS Items, AVG(Score) AS Score, DateAdded FROM scoreboard GROUP BY DateAdded, CourseCode HAVING CourseCode = :courseCode ORDER BY DateAdded ASC" )
    LiveData<List<ScoreboardQueryStorage>> getAvgByCourse(String courseCode);

    @Query( "SELECT DISTINCT CourseCode FROM Scoreboard" )
    LiveData<List<String>> getRecordedCourses();
}
