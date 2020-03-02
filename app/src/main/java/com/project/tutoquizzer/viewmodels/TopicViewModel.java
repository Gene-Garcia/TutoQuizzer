package com.project.tutoquizzer.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.project.tutoquizzer.entities.Topics;
import com.project.tutoquizzer.repos.TopicRepo;

import java.util.List;

public class TopicViewModel extends AndroidViewModel {

    private TopicRepo topicRepo;

    public TopicViewModel(@NonNull Application application) {
        super(application);
        this.topicRepo = new TopicRepo(application);
    }

    public void insert(Topics topic){ this.topicRepo.insert(topic); }
    public void update(Topics topic){ this.topicRepo.update(topic); }
    public void delete(Topics topic){ this.topicRepo.delete(topic); }
    public void deleteAll() { this.topicRepo.deleteAll(); }

    public LiveData<List<Topics>> getSelectedTopics(int courseId, int yearId, int quarter) { return this.topicRepo.getSelectedTopics(courseId, yearId, quarter); }
    public LiveData<List<Topics>> getAllTopics() { return this.topicRepo.getAllTopics(); }

}
