package com.project.tutoquizzer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.tutoquizzer.entities.Courses;
import com.project.tutoquizzer.entities.Quarters;
import com.project.tutoquizzer.entities.SchoolYear;
import com.project.tutoquizzer.entities.Topics;
import com.project.tutoquizzer.viewmodels.CourseViewModel;
import com.project.tutoquizzer.viewmodels.QuarterViewModel;
import com.project.tutoquizzer.viewmodels.SchoolYearViewModel;
import com.project.tutoquizzer.viewmodels.TopicViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddTopicFragment extends Fragment {

    private CourseViewModel cvm;
    private QuarterViewModel qvm;
    private SchoolYearViewModel syvm;
    private TopicViewModel tvm;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_add_topic, container, false);
        this.cvm = ViewModelProviders.of(this).get(CourseViewModel.class);

        this.cvm = ViewModelProviders.of(this).get(CourseViewModel.class);
        this.qvm = ViewModelProviders.of(this).get(QuarterViewModel.class);
        this.syvm = ViewModelProviders.of(this).get(SchoolYearViewModel.class);
        this.tvm = ViewModelProviders.of(this).get(TopicViewModel.class);

        init();
        populateSpinner();
        buttonListeners();


        return this.rootView;
    }

    // Components

    private Button btnAdd;

    private Spinner courseSpinner, schoolYearSpinner, quarterSpinner;

    private EditText topicET, descriptionET;

    // End Components

    private void init(){
        btnAdd              = rootView.findViewById(R.id.addBtnAddTopicActivity);

        courseSpinner       = rootView.findViewById(R.id.coursesSpnrAddTopicActivity);
        schoolYearSpinner   = rootView.findViewById(R.id.schoolYearSpnrAddTopicActivity);
        quarterSpinner      = rootView.findViewById(R.id.quarterSpnrAddTopicActivity);

        topicET             = rootView.findViewById(R.id.topicAddTopicEditText);
        descriptionET       = rootView.findViewById(R.id.descriptionAddTopicEditText);


    }

    private void buttonListeners(){

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String topic = topicET.getText().toString().trim();
                String desc = descriptionET.getText().toString().trim();

                if (selectedQuarter == "Select item" || selectedSchoolYear == "Select item" || selectedCourse == "Select item" || topic.isEmpty() || desc.isEmpty()){
                    Toast.makeText(getContext(), "Fill out all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    selectedCourseId = courseMemory.get(selectedCourseId-1);
                    selectedSchoolYearId = schoolYearMemory.get(selectedSchoolYearId-1);
                    selectedQuarterId = quarterMemory.get(selectedQuarterId-1);

                    Topics topics = new Topics(topic, desc, selectedQuarterId, selectedCourseId, selectedSchoolYearId);
                    tvm.insert(topics);

                    Toast.makeText(getContext(), "Topic Successfully Inserted", Toast.LENGTH_SHORT).show();

                    topicET.setText("");
                    descriptionET.setText("");

                    // Add this to another button
                    courseSpinner.setSelection(0);
                    schoolYearSpinner.setSelection(0);
                    quarterSpinner.setSelection(0);

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
        cvm.getAllCourses().observe(getViewLifecycleOwner(), observer);

        ArrayAdapter<String> coursesAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_item, courses);
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
        syvm.getAllSchoolYears().observe(getViewLifecycleOwner(), observer1);

        ArrayAdapter<String> schoolYearAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_item, schoolYears);
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
        qvm.getAllQuarters().observe(getViewLifecycleOwner(), observer2);

        ArrayAdapter<String> quarterAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_item, quarter);
        quarterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quarterSpinner.setAdapter(quarterAdapter);

    }

    private void spinnerListeners(){
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                selectedCourse = item;
                selectedCourseId = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        schoolYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                selectedSchoolYear = item;
                selectedSchoolYearId = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        quarterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                selectedQuarter = item;
                selectedQuarterId = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
