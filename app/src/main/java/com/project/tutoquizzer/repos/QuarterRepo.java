package com.project.tutoquizzer.repos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.project.tutoquizzer.dao.QuarterDao;
import com.project.tutoquizzer.db.AppDatabase;
import com.project.tutoquizzer.entities.Quarters;

import java.util.List;

public class QuarterRepo {

    private QuarterDao quarterDao;

    public QuarterRepo(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        this.quarterDao = database.quarterDao();
    }

    // Insert
    public void insert(Quarters quarter){
        new InsertAsync(this.quarterDao).execute(quarter);
    }

    public int getIdByName(String name) {
        return this.quarterDao.getIdByName(name);
    }

    private static class InsertAsync extends AsyncTask<Quarters, Void, Void> {
        private QuarterDao quarterDao;

        private InsertAsync(QuarterDao quarterDao){
            this.quarterDao = quarterDao;
        }

        @Override
        protected Void doInBackground(Quarters... quarter) {
            this.quarterDao.insert(quarter[0]);
            return null;
        }
    }

    // Update
    public void update(Quarters quarter){
        new UpdateAsync(this.quarterDao).execute(quarter);
    }

    private static class UpdateAsync extends AsyncTask<Quarters, Void, Void> {
        private QuarterDao quarterDao;

        private UpdateAsync(QuarterDao quarterDao){
            this.quarterDao = quarterDao;
        }

        @Override
        protected Void doInBackground(Quarters... quarter) {
            this.quarterDao.update(quarter[0]);
            return null;
        }
    }

    // Delete
    public void delete(Quarters quarter){
        new UpdateAsync(this.quarterDao).execute(quarter);
    }

    private static class DeleteAsync extends AsyncTask<Quarters, Void, Void> {
        private QuarterDao quarterDao;

        private DeleteAsync(QuarterDao quarterDao){
            this.quarterDao = quarterDao;
        }

        @Override
        protected Void doInBackground(Quarters... quarter) {
            this.quarterDao.delete(quarter[0]);
            return null;
        }
    }

    // Read
    public LiveData<List<Quarters>> getReferencedQuarters(){
        return this.quarterDao.getReferencedQuarters();
    }

    public LiveData<List<Quarters>> getAllQuarters(){
        return this.quarterDao.getAllQuarters();
    }

}
