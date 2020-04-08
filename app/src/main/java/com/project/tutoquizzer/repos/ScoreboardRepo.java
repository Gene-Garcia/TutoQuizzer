package com.project.tutoquizzer.repos;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.project.tutoquizzer.dao.ScoreboardDao;
import com.project.tutoquizzer.db.AppDatabase;
import com.project.tutoquizzer.entities.Scoreboard;
import com.project.tutoquizzer.viewmodels.DailyScoreStorage;
import com.project.tutoquizzer.viewmodels.DailyUsageStorage;
import com.project.tutoquizzer.viewmodels.ScoreAccumulationStorage;

import java.util.List;

public class ScoreboardRepo {

    private ScoreboardDao scoreboardDao;

    public ScoreboardRepo(Application application){
        AppDatabase database = AppDatabase.getInstance(application);
        this.scoreboardDao = database.scoreboardDao();
    }

    // Insert
    public void insert(Scoreboard scoreboard){
        new InsertAsync(this.scoreboardDao).execute(scoreboard);
    }

    private static class InsertAsync extends AsyncTask<Scoreboard, Void, Void> {
        private ScoreboardDao scoreboardDao;

        private InsertAsync(ScoreboardDao scoreboardDao){
            this.scoreboardDao = scoreboardDao;
        }

        @Override
        protected Void doInBackground(Scoreboard... scoreboards) {
            this.scoreboardDao.insert(scoreboards[0]);
            return null;
        }
    }

    // Read

    public LiveData<List<ScoreAccumulationStorage>> getAvgByCourse(String courseCode){
        return this.scoreboardDao.getAvgByCourse(courseCode);
    }

    public LiveData<List<String>> getRecordedCourses(){
        return this.scoreboardDao.getRecordedCourses();
    }

    public LiveData<List<DailyScoreStorage>> getDailyScore(){
        return this.scoreboardDao.getDailyScore();
    }

    public LiveData<List<DailyUsageStorage>> getDailyUsage(){
        return this.scoreboardDao.getDailyUsage();
    }

}
