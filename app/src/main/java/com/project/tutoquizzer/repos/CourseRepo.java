package com.project.tutoquizzer.repos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.project.tutoquizzer.dao.CourseDao;
import com.project.tutoquizzer.db.AppDatabase;
import com.project.tutoquizzer.entities.Courses;

import java.util.List;

public class CourseRepo {

    private CourseDao courseDao;

    public CourseRepo(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        this.courseDao = database.courseDao();
    }

    // Insert
    public void insert(Courses course){
        new InsertAsync(this.courseDao).execute(course);
    }

    private static class InsertAsync extends AsyncTask<Courses, Void, Void> {
        private CourseDao courseDao;

        private InsertAsync(CourseDao courseDao){
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Courses... courses) {
            this.courseDao.insert(courses[0]);
            return null;
        }
    }

    // Update
    public void update(Courses course){
        new UpdateAsync(this.courseDao).execute(course);
    }

    private static class UpdateAsync extends AsyncTask<Courses, Void, Void> {
        private CourseDao courseDao;

        private UpdateAsync(CourseDao courseDao){
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Courses... courses) {
            this.courseDao.update(courses[0]);
            return null;
        }
    }

    // Delete
    public void delete(Courses course){
        new DeleteAsync(this.courseDao).execute(course);
    }

    private static class DeleteAsync extends AsyncTask<Courses, Void, Void> {
        private CourseDao courseDao;

        private DeleteAsync(CourseDao courseDao){
            this.courseDao = courseDao;
        }

        @Override
        protected Void doInBackground(Courses... courses) {
            this.courseDao.delete(courses[0]);
            return null;
        }
    }

    // Read
    public LiveData<List<Courses>> getReferencedCourses(){
        return this.courseDao.getReferencedCourses();
    }

    public LiveData<List<Courses>> getAllCourses(){
        return this.courseDao.getAllCourses();
    }

}
