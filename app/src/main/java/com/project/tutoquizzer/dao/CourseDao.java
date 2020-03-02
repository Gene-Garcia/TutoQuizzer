package com.project.tutoquizzer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.project.tutoquizzer.entities.Courses;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert
    void insert(Courses course);

    @Update
    void update(Courses course);

    @Delete
    void delete(Courses course);

    @Query( "SELECT DISTINCT Courses.CourseId, Courses.Code, Courses.Name FROM Courses INNER JOIN Topics ON Courses.CourseId = Topics.CourseId " )
    LiveData< List<Courses> > getReferencedCourses();

    @Query( "SELECT * FROM Courses" )
    LiveData< List<Courses> > getAllCourses();
}
