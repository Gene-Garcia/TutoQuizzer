package com.project.tutoquizzer.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.project.tutoquizzer.entities.Topics;
import com.project.tutoquizzer.viewmodels.TopicQueryStorage;

import java.util.List;

@Dao
public interface TopicDao {

    @Insert
    void insert(Topics topic);

    @Update
    void update(Topics topic);

    @Delete
    void delete(Topics topic);

    @Query( "DELETE FROM Topics" )
    void deleteAll();

    @Query( "SELECT * FROM Topics WHERE CourseId = :courseId and YearId = :yearId and QuarterId = :quarterId" )
    LiveData<List<Topics>> getTopics(int courseId, int yearId, int quarterId);

    @Query( "SELECT * FROM Topics" )
    LiveData< List<Topics> > getAllTopics();

    @Query( "SELECT COUNT() FROM Topics WHERE CourseId = :courseId " )
    LiveData<Integer> getReferenceTopicCountByCourse(int courseId);

    @Query( "SELECT COUNT() FROM Topics WHERE YearId = :yearId " )
    LiveData<Integer> getReferenceTopicCountBySchoolYear(int yearId);

    @Query( "SELECT COUNT() FROM Topics WHERE QuarterId = :quarterId " )
    LiveData<Integer> getReferenceTopicCountByQuarter(int quarterId);

    @Query( "  SELECT COUNT(Topic) as TCount, CourseId FROM Topics Group BY CourseId" )
    LiveData<List<TopicQueryStorage>> getCountPerTopic();

}
