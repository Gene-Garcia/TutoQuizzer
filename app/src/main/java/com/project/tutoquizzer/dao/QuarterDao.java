package com.project.tutoquizzer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.project.tutoquizzer.entities.Quarters;

import java.util.List;

@Dao
public interface QuarterDao {

    @Insert
    void insert(Quarters quarter);

    @Update
    void update(Quarters quarter);

    @Delete
    void delete(Quarters quarter);

    @Query("SELECT DISTINCT Quarters.QuarterId, Quarters.Name FROM Quarters INNER JOIN Topics ON Quarters.QuarterId = Topics.QuarterId")
    LiveData<List<Quarters>> getReferencedQuarters();

    @Query("SELECT * FROM Quarters")
    LiveData< List<Quarters> > getAllQuarters();

    @Query("SELECT QuarterId FROM QUARTERS WHERE Name = :name")
    int getIdByName(String name);
}
