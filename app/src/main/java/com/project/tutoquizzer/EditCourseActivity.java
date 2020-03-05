package com.project.tutoquizzer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.tutoquizzer.entities.Courses;
import com.project.tutoquizzer.viewmodels.CourseViewModel;

public class EditCourseActivity extends AppCompatActivity {

    private CourseViewModel cvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_course_activity);

        setTitle("TutoQuizzer - Edit Course");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.return_arrow);

        this.cvm = ViewModelProviders.of(this).get(CourseViewModel.class);

        Intent intent = getIntent();
        course = new Courses( intent.getStringExtra(AppValues.INTENT_HOLDER_COURSE_NAME), intent.getStringExtra(AppValues.INTENT_HOLDER_COURSE_CODE) );
        course.setCourseId(intent.getIntExtra(AppValues.INTENT_HOLDER_ID_COURSE, -1));

        init();

        displayData();
        buttonListeners();
        navigationListener();
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

                Intent intent = new Intent(EditCourseActivity.this, AddCourseActivity.class);
                startActivityForResult(intent, AppValues.REQ_CODE_ADD_COURSE);

            }
        });
    }

    private Courses course;

    // Components

    private EditText courseName, courseCode;

    private Button saveEditBtn;

    // For Navigation
    private BottomNavigationView bottomNavigationView;

    // End

    private void init(){
        courseName  = findViewById(R.id.courseNameEditCourseEditText);
        courseCode  = findViewById(R.id.courseCodeEditCourseEditText);

        saveEditBtn = findViewById(R.id.saveEditBtnEditCourseAct);

        bottomNavigationView = findViewById(R.id.menuAct);
    }

    // For Navigation
    private void navigationListener(){

        bottomNavigationView.setSelectedItemId(R.id.addCourseMenu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.homeMenu:
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivityForResult( intent, AppValues.REQ_CODE_HOME);
                        return true;

                    case R.id.quizMenu:
                        Intent intent1 = new Intent(getApplicationContext(), SelectCourseActivity.class);
                        intent1.putExtra(AppValues.INTENT_NAME_SELECT_COURSE, AppValues.REQ_CODE_QUIZ);
                        startActivityForResult(intent1, AppValues.REQ_CODE_QUIZ);
                        return true;

                    case R.id.tutorialMenu:
                        Intent intent2 = new Intent(getApplicationContext(), SelectCourseActivity.class);
                        intent2.putExtra(AppValues.INTENT_NAME_SELECT_COURSE, AppValues.REQ_CODE_TUTORIAL);
                        startActivityForResult(intent2, AppValues.REQ_CODE_TUTORIAL);
                        return true;

                    case R.id.addCourseMenu:
                        Intent intent3 = new Intent(getApplicationContext(), AddCourseActivity.class);
                        startActivityForResult(intent3, AppValues.REQ_CODE_ADD_COURSE);
                        return true;

                    case R.id.addTopicMenu:
                        Intent intent4 = new Intent(getApplicationContext(), AddTopicActivity.class);
                        startActivityForResult(intent4, AppValues.REQ_CODE_ADD_TOPIC);
                        return true;
                }

                return false;
            }
        });

    }
}
