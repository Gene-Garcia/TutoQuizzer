package com.project.tutoquizzer.repos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;

import com.project.tutoquizzer.dao.TopicDao;
import com.project.tutoquizzer.db.AppDatabase;
import com.project.tutoquizzer.entities.Topics;

import java.util.List;

public class TopicRepo {

    private TopicDao topicDao;

    public TopicRepo(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        this.topicDao = database.topicDao();
    }

    // Insert
    public void insert(Topics topic){
        new InsertAsync(this.topicDao).execute(topic);
    }

    private static class InsertAsync extends AsyncTask<Topics, Void, Void> {
        private TopicDao topicDao;

        private InsertAsync(TopicDao topicDao){
            this.topicDao = topicDao;
        }

        @Override
        protected Void doInBackground(Topics... topic) {
            this.topicDao.insert(topic[0]);
            return null;
        }
    }

    // Update
    public void update(Topics topic){
        new UpdateAsync(this.topicDao).execute(topic);
    }

    private static class UpdateAsync extends AsyncTask<Topics, Void, Void> {
        private TopicDao topicDao;

        private UpdateAsync(TopicDao topicDao){
            this.topicDao = topicDao;
        }

        @Override
        protected Void doInBackground(Topics... topic) {
            this.topicDao.update(topic[0]);
            return null;
        }
    }

    // Delete
    public void delete(Topics topic){
        new DeleteAsync(this.topicDao).execute(topic);
    }

    private static class DeleteAsync extends AsyncTask<Topics, Void, Void> {
        private TopicDao topicDao;

        private DeleteAsync(TopicDao topicDao){
            this.topicDao = topicDao;
        }

        @Override
        protected Void doInBackground(Topics... topic) {
            this.topicDao.delete(topic[0]);
            return null;
        }
    }

    public void deleteAll() {

    }

    private static class DeleteAllAsync extends AsyncTask<Void, Void, Void>{

        private TopicDao topicDao;

        private DeleteAllAsync(TopicDao topicDao){ this.topicDao = topicDao; }

        @Override
        protected Void doInBackground(Void... voids) {
            this.topicDao.deleteAll();
            return null;
        }
    }

    // Read
    public LiveData<List<Topics>> getSelectedTopics(int courseId, int yearId, int quarterId){
        return this.topicDao.getTopics(courseId, yearId, quarterId);
    }

    public LiveData<List<Topics>> getAllTopics(){
        return this.topicDao.getAllTopics();
    }

    public LiveData<Integer> getReferenceTopicCountByCourse(int courseId){
        return this.topicDao.getReferenceTopicCountByCourse(courseId);
    }

    public LiveData<Integer> getReferenceTopicCountBySchoolYear(int schoolYearId){
        return this.topicDao.getReferenceTopicCountBySchoolYear(schoolYearId);
    }

    public LiveData<Integer> getReferenceTopicCountByQuarter(int quarterId){
        return this.topicDao.getReferenceTopicCountByQuarter(quarterId);
    }

}
