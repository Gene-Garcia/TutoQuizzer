package com.project.tutoquizzer.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.project.tutoquizzer.entities.Scoreboard;
import com.project.tutoquizzer.repos.ScoreboardRepo;

import java.util.List;

public class ScoreboardViewModel extends AndroidViewModel {

    private ScoreboardRepo scoreboardRepo;

    public ScoreboardViewModel(@NonNull Application application) {
        super(application);
        this.scoreboardRepo = new ScoreboardRepo(application);
    }

    public void insert(Scoreboard scoreboard){ this.scoreboardRepo.insert(scoreboard); }

    public LiveData<List<ScoreboardQueryStorage>> getAvgByCourse(String courseCode) { return this.scoreboardRepo.getAvgByCourse(courseCode); }
    public LiveData<List<String>> getRecordedCourses() { return this.scoreboardRepo.getRecordedCourses(); }
}
