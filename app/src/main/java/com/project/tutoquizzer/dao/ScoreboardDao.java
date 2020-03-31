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

    @Query( "SELECT * FROM Scoreboard " )
    LiveData< List<Scoreboard> > getAllScoreboard();
}
