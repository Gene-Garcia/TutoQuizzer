package com.project.tutoquizzer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.tutoquizzer.entities.Courses;
import com.project.tutoquizzer.entities.Quarters;
import com.project.tutoquizzer.entities.SchoolYear;
import com.project.tutoquizzer.entities.Topics;
import com.project.tutoquizzer.view.adapters.CoursesAdapter;
import com.project.tutoquizzer.viewmodels.CourseViewModel;
import com.project.tutoquizzer.viewmodels.QuarterViewModel;
import com.project.tutoquizzer.viewmodels.SchoolYearViewModel;

import java.util.ArrayList;
import java.util.List;

public class SelectCourseActivity extends AppCompatActivity {

    private CourseViewModel cvm;
    private QuarterViewModel qvm;
    private SchoolYearViewModel syvm;

    private boolean isForQuiz = false;
    private int numberOfItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_course_activity);

        setTitle("TutoQuizzer - Select Course");

        this.cvm = ViewModelProviders.of(this).get(CourseViewModel.class);
        this.qvm = ViewModelProviders.of(this).get(QuarterViewModel.class);
        this.syvm = ViewModelProviders.of(this).get(SchoolYearViewModel.class);

        init();

        Intent intent = getIntent();
        if (intent.getIntExtra(AppValues.INTENT_NAME_SELECT_COURSE, 0) == AppValues.REQ_CODE_QUIZ){
            numberItemsSpinner.setVisibility(View.VISIBLE);
            numberItemsTV.setVisibility(View.VISIBLE);
            isForQuiz = true;
        }

        populateSpinner();
        buttonListeners();
        navigationListener();

        //on request, if REQUSTCODEQUIZ show another SPINNER for number of items,
        //else if REQUESTCODETUTORIAL DONT SHOW

    }

    // Components

    private Button btnSelect;

    private Spinner courseSpinner, schoolYearSpinner, quarterSpinner, numberItemsSpinner;

    private TextView numberItemsTV;

    // For Navigation
    private BottomNavigationView bottomNavigationView;

    // End Components

    private void init(){
        numberItemsTV       = findViewById(R.id.textView);

        btnSelect           = findViewById(R.id.selectBtnSelectCourseAct);

        courseSpinner       = findViewById(R.id.coursesSpnrSelectCourseAct);
        schoolYearSpinner   = findViewById(R.id.schoolYearSpnrSelectCourseAct);
        quarterSpinner      = findViewById(R.id.quarterSpnrSelectCourseAct);
        numberItemsSpinner  = findViewById(R.id.numberItemsSpnrSelectCourseAct);

        numberItemsSpinner  .setVisibility(View.INVISIBLE);
        numberItemsTV       .setVisibility(View.INVISIBLE);

        // For Navigation
        bottomNavigationView = findViewById(R.id.menuAct);
    }

    // For Navigation
    private void navigationListener(){

       // bottomNavigationView.setSelectedItemId(R.id.homeMenu);

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

    private void determineIds(){
        selectedCourseId        = courseMemory.get(selectedCourseId-1);
        selectedSchoolYearId    = schoolYearMemory.get(selectedSchoolYearId-1);
        selectedQuarterId       = quarterMemory.get(selectedQuarterId-1);

        courseSpinner       .setSelection(0);
        schoolYearSpinner   .setSelection(0);
        quarterSpinner      .setSelection(0);
    }

    private void buttonListeners(){
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selectedQuarter == "Select item" || selectedSchoolYear == "Select item" || selectedCourse == "Select item"){
                    Toast.makeText(SelectCourseActivity.this, "Fill out all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    determineIds();

                    if (isForQuiz) {

                        Intent intent = new Intent(SelectCourseActivity.this, QuizActivity.class);
                        intent.putExtra(AppValues.INTENT_HOLDER_NUMITEMS, numberOfItems);

                        intent.putExtra(AppValues.INTENT_HOLDER_COURSE, selectedCourse);
                        intent.putExtra(AppValues.INTENT_HOLDER_SCHOOLYEAR, selectedSchoolYear);
                        intent.putExtra(AppValues.INTENT_HOLDER_QUARTER, selectedQuarter);

                        intent.putExtra(AppValues.INTENT_HOLDER_ID_COURSE, selectedCourseId);
                        intent.putExtra(AppValues.INTENT_HOLDER_ID_SCHOOLYEAR, selectedSchoolYearId);
                        intent.putExtra(AppValues.INTENT_HOLDER_ID_QUARTER, selectedQuarterId);

                        startActivityForResult(intent, AppValues.REQ_CODE_QUIZ);
                    } else {
                        Intent intent = new Intent(SelectCourseActivity.this, TutorialActivity.class);

                        intent.putExtra(AppValues.INTENT_HOLDER_COURSE, selectedCourse);
                        intent.putExtra(AppValues.INTENT_HOLDER_SCHOOLYEAR, selectedSchoolYear);
                        intent.putExtra(AppValues.INTENT_HOLDER_QUARTER, selectedQuarter);

                        intent.putExtra(AppValues.INTENT_HOLDER_ID_COURSE, selectedCourseId);
                        intent.putExtra(AppValues.INTENT_HOLDER_ID_SCHOOLYEAR, selectedSchoolYearId);
                        intent.putExtra(AppValues.INTENT_HOLDER_ID_QUARTER, selectedQuarterId);

                        startActivityForResult(intent, AppValues.REQ_CODE_TUTORIAL);
                    }
                }
            }
        });
    }


    // Select Values from Spinners

    private String selectedCourse;
    private String selectedSchoolYear;
    private String selectedQuarter;

    // id memory storage
    private List<Integer> courseMemory      = new ArrayList<>();
    private List<Integer> schoolYearMemory  = new ArrayList<>();
    private List<Integer> quarterMemory     = new ArrayList<>();

    private int selectedCourseId;
    private int selectedSchoolYearId;
    private int selectedQuarterId;

    // End

    private void populateSpinner(){

        spinnerListeners();

        final List<String> courses = new ArrayList<String>();
        courses.add("Select item");
        Observer<List<Courses>> observer = new Observer<List<Courses>>() {
            @Override
            public void onChanged(@Nullable List<Courses> courses1) {
                for (int i = 0; i < courses1.size(); i++){
                    courses.add( courses1.get(i).getCode() + " - " + courses1.get(i).getName());
                    courseMemory.add(courses1.get(i).getCourseId());
                }
            }
        };
        cvm.getReferencedCourses().observe(SelectCourseActivity.this, observer);

        ArrayAdapter<String> coursesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, courses);
        coursesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(coursesAdapter);

        //---

        final List<String> schoolYears = new ArrayList<String>();
        schoolYears.add("Select item");
        Observer<List<SchoolYear>> observer1 = new Observer<List<SchoolYear>>() {
            @Override
            public void onChanged(@Nullable List<SchoolYear> schoolYears1) {
                for (int i = 0; i < schoolYears1.size(); i++){
                    schoolYears.add( "Year " + schoolYears1.get(i).getYear() + " Term " + schoolYears1.get(i).getTerm());
                    schoolYearMemory.add(schoolYears1.get(i).getYearId());
                }
            }
        };
        syvm.getReferencedSchoolYears().observe(SelectCourseActivity.this, observer1);

        ArrayAdapter<String> schoolYearAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, schoolYears);
        schoolYearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        schoolYearSpinner.setAdapter(schoolYearAdapter);

        //---

        final List<String> quarter = new ArrayList<String>();
        quarter.add("Select item");
        Observer<List<Quarters>> observer2 = new Observer<List<Quarters>>() {
            @Override
            public void onChanged(@Nullable List<Quarters> quarters) {
                for (int i = 0; i < quarters.size(); i++){
                    quarter.add( quarters.get(i).getName());
                    quarterMemory.add(quarters.get(i).getQuarterId());
                }
            }
        };
        qvm.getReferencedQuarters().observe(SelectCourseActivity.this, observer2);

        ArrayAdapter<String> quarterAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, quarter);
        quarterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quarterSpinner.setAdapter(quarterAdapter);

        List<String> items = new ArrayList<String>();
        items.add("5");
        items.add("10");
        items.add("15");
        items.add("20");
        items.add("25");
        items.add("30");
        ArrayAdapter<String> numberItemAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        numberItemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        numberItemsSpinner.setAdapter(numberItemAdapter);

    }

    private void spinnerListeners(){
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                selectedCourseId = position;
                selectedCourse = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        schoolYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                selectedSchoolYearId = position;
                selectedSchoolYear = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        quarterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                selectedQuarterId = position;
                selectedQuarter = item;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (isForQuiz){
            numberItemsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    numberOfItems = Integer.valueOf(parent.getItemAtPosition(position).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }


}
