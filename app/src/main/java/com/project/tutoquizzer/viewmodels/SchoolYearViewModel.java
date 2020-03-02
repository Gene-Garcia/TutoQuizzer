package com.project.tutoquizzer.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.project.tutoquizzer.entities.SchoolYear;
import com.project.tutoquizzer.repos.SchoolYearRepo;

import java.util.List;

public class SchoolYearViewModel extends AndroidViewModel {

    private SchoolYearRepo schoolYearRepo;

    public SchoolYearViewModel(@NonNull Application application) {
        super(application);
        this.schoolYearRepo = new SchoolYearRepo(application);
    }

    public void insert(SchoolYear schoolYear){ this.schoolYearRepo.insert(schoolYear); }
    public void update(SchoolYear schoolYear){ this.schoolYearRepo.update(schoolYear); }
    public void delete(SchoolYear schoolYear){ this.schoolYearRepo.delete(schoolYear); }

    public LiveData<List<SchoolYear>> getReferencedSchoolYears() { return this.schoolYearRepo.getReferencedSchoolYears(); }
    public LiveData<List<SchoolYear>> getAllSchoolYears() { return this.schoolYearRepo.getSchoolYears(); }

    public int getIdByYearTerm(int year, int term) {
        return this.schoolYearRepo.getIdByYearTerm(year, term);
    }
}
