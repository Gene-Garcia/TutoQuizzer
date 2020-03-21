package com.project.tutoquizzer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.project.tutoquizzer.entities.Courses;
import com.project.tutoquizzer.viewmodels.CourseViewModel;

public class EditCourseActivity extends Fragment {

    private CourseViewModel cvm;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_edit_course, container, false);

        this.cvm = ViewModelProviders.of(this).get(CourseViewModel.class);

        Bundle bundle = getArguments();

        course = new Courses(
                bundle.getString(RouteValues.COURSE_NAME_KEY),
                bundle.getString(RouteValues.COURSE_CODE_KEY));

        course.setCourseId(bundle.getInt(RouteValues.COURSE_ID_KEY));

        init();

        displayData();
        buttonListeners();

        return this.rootView;
    }

    private void displayData(){
        courseName.setText(course.getName());
        courseCode.setText(course.getCode());
    }

    private void buttonListeners(){
        saveEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                course.setName( courseName.getText().toString().trim() );
                course.setCode( courseCode.getText().toString().trim() );
                cvm.update(course);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                AddCourseFragment addCourseFragment = new AddCourseFragment();

                fragmentTransaction.replace(R.id.fragment_container, addCourseFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private Courses course;

    // Components

    private EditText courseName, courseCode;

    private Button saveEditBtn;

    // End

    private void init(){
        courseName  = rootView.findViewById(R.id.courseNameEditCourseEditText);
        courseCode  = rootView.findViewById(R.id.courseCodeEditCourseEditText);

        saveEditBtn = rootView.findViewById(R.id.saveEditBtnEditCourseAct);
    }

}
