package com.project.tutoquizzer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.project.tutoquizzer.entities.Courses;
import com.project.tutoquizzer.entities.Quarters;
import com.project.tutoquizzer.entities.SchoolYear;
import com.project.tutoquizzer.entities.Topics;
import com.project.tutoquizzer.viewmodels.CourseViewModel;
import com.project.tutoquizzer.viewmodels.QuarterViewModel;
import com.project.tutoquizzer.viewmodels.SchoolYearViewModel;
import com.project.tutoquizzer.viewmodels.TopicViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CourseViewModel cvm;
    private QuarterViewModel qvm;
    private SchoolYearViewModel syvm;
    private TopicViewModel tvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        setTitle("TutoQuizzer - Home");

        this.cvm = ViewModelProviders.of(this).get(CourseViewModel.class);
        this.tvm = ViewModelProviders.of(this).get(TopicViewModel.class);
        init();

        buttonListeners();

    }

    // Main Activity Components

    private Button btnQuiz, btnTutorial, btnAddCourse, btnAddTopic, btnDashboard;

    // End

    private void init(){

        btnQuiz         = findViewById(R.id.quizBtnMainAct);
        btnTutorial     = findViewById(R.id.tutorialBtnMainAct);
        btnAddCourse    = findViewById(R.id.addCourseBtnMainAct);
        btnAddTopic     = findViewById(R.id.addTopicBtnMainAct);
        btnDashboard    = findViewById(R.id.dashboardBtnMainAct);

    }

    private void buttonListeners(){

        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectCourseActivity.class);
                intent.putExtra(AppValues.INTENT_NAME_SELECT_COURSE, AppValues.REQ_CODE_QUIZ);
                startActivityForResult(intent, AppValues.REQ_CODE_QUIZ);
            }
        });

        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectCourseActivity.class);
                intent.putExtra(AppValues.INTENT_NAME_SELECT_COURSE, AppValues.REQ_CODE_TUTORIAL);
                startActivityForResult(intent, AppValues.REQ_CODE_TUTORIAL);
            }
        });

        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddCourseActivity.class);
                startActivityForResult(intent, AppValues.REQ_CODE_ADD_COURSE);

            }
        });

        btnAddTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, AddTopicActivity.class);
                startActivityForResult(intent, AppValues.REQ_CODE_ADD_TOPIC);

            }
        });

        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}
