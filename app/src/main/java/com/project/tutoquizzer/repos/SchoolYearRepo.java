package com.project.tutoquizzer.repos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.project.tutoquizzer.dao.QuarterDao;
import com.project.tutoquizzer.dao.SchoolYearDao;
import com.project.tutoquizzer.db.AppDatabase;
import com.project.tutoquizzer.entities.Quarters;
import com.project.tutoquizzer.entities.SchoolYear;

import java.util.List;

public class SchoolYearRepo {

    private SchoolYearDao schoolYearDao;

    public SchoolYearRepo(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        this.schoolYearDao = database.schoolYearDao();
    }

    // Insert
    public void insert(SchoolYear schoolYear){
        new InsertAsync(this.schoolYearDao).execute(schoolYear);
    }

    public int getIdByYearTerm(int year, int term) {
        return this.schoolYearDao.getIdByYearTerm(year, term);
    }

    private static class InsertAsync extends AsyncTask<SchoolYear, Void, Void> {
        private SchoolYearDao schoolYearDao;

        private InsertAsync(SchoolYearDao schoolYearDao){
            this.schoolYearDao = schoolYearDao;
        }

        @Override
        protected Void doInBackground(SchoolYear... schoolYear) {
            this.schoolYearDao.insert(schoolYear[0]);
            return null;
        }
    }

    // Update
    public void update(SchoolYear schoolYear){
        new UpdateAsync(this.schoolYearDao).execute(schoolYear);
    }

    private static class UpdateAsync extends AsyncTask<SchoolYear, Void, Void> {
        private SchoolYearDao schoolYearDao;

        private UpdateAsync(SchoolYearDao schoolYearDao){
            this.schoolYearDao = schoolYearDao;
        }

        @Override
        protected Void doInBackground(SchoolYear... schoolYear) {
            this.schoolYearDao.update(schoolYear[0]);
            return null;
        }
    }

    // Delete
    public void delete(SchoolYear schoolYear){
        new DeleteAsync(this.schoolYearDao).execute(schoolYear);
    }

    private static class DeleteAsync extends AsyncTask<SchoolYear, Void, Void> {
        private SchoolYearDao schoolYearDao;

        private DeleteAsync(SchoolYearDao schoolYearDao){
            this.schoolYearDao = schoolYearDao;
        }

        @Override
        protected Void doInBackground(SchoolYear... schoolYear) {
            this.schoolYearDao.delete(schoolYear[0]);
            return null;
        }
    }

    // Read
    public LiveData<List<SchoolYear>> getReferencedSchoolYears(){
        return this.schoolYearDao.getReferencedSchoolYear();
    }

    public LiveData<List<SchoolYear>> getSchoolYears(){
        return this.schoolYearDao.getAllSchoolYear();
    }

}
