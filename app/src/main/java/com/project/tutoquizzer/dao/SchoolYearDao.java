package com.project.tutoquizzer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.project.tutoquizzer.entities.SchoolYear;

import java.util.List;

@Dao
public interface SchoolYearDao {

    @Insert
    void insert(SchoolYear schoolYear);

    @Update
    void update(SchoolYear schoolYear);

    @Delete
    void delete(SchoolYear schoolYear);

    @Query( "SELECT DISTINCT SchoolYear.YearId, SchoolYear.Year, SchoolYear.Term FROM SchoolYear INNER JOIN Topics ON SchoolYear.YearId = Topics.YearId " )
    LiveData<List<SchoolYear>> getReferencedSchoolYear();

    @Query("SELECT * FROM SchoolYear ORDER BY Year, Term")
    LiveData< List<SchoolYear> > getAllSchoolYear();

    @Query(" SELECT YearId FROM SchoolYear WHERE Year = :year AND Term = :term ")
    int getIdByYearTerm(int year, int term);
}
