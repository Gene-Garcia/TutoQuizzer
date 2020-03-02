package com.project.tutoquizzer.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.project.tutoquizzer.entities.Courses;
import com.project.tutoquizzer.repos.CourseRepo;

import java.util.List;

public class CourseViewModel extends AndroidViewModel {

    private CourseRepo courseRepo;

    public CourseViewModel(@NonNull Application application) {
        super(application);
        this.courseRepo = new CourseRepo(application);
    }

    public void insert(Courses course){ this.courseRepo.insert(course); }
    public void update(Courses course){ this.courseRepo.update(course); }
    public void delete(Courses course){ this.courseRepo.delete(course); }

    public LiveData<List<Courses>> getReferencedCourses() { return this.courseRepo.getReferencedCourses(); }
    public LiveData<List<Courses>> getAllCourses() { return this.courseRepo.getAllCourses(); }
}
