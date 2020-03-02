package com.project.tutoquizzer.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.project.tutoquizzer.entities.Quarters;
import com.project.tutoquizzer.repos.QuarterRepo;

import java.util.List;

public class QuarterViewModel extends AndroidViewModel {

    private QuarterRepo quarterRepo;

    public QuarterViewModel(@NonNull Application application) {
        super(application);
        this.quarterRepo = new QuarterRepo(application);
    }

    public void insert(Quarters quarter){ this.quarterRepo.insert(quarter); }
    public void update(Quarters quarter){ this.quarterRepo.update(quarter); }
    public void delete(Quarters quarter){ this.quarterRepo.delete(quarter); }

    public LiveData<List<Quarters>> getReferencedQuarters() { return this.quarterRepo.getReferencedQuarters(); }
    public LiveData<List<Quarters>> getAllQuarters() { return this.quarterRepo.getAllQuarters(); }

    public int getIdByName(String name) {
        return this.quarterRepo.getIdByName(name);
    }
}
