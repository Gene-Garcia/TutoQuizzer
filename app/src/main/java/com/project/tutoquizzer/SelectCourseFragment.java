package com.project.tutoquizzer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.tutoquizzer.entities.Courses;
import com.project.tutoquizzer.entities.Quarters;
import com.project.tutoquizzer.entities.SchoolYear;
import com.project.tutoquizzer.viewmodels.CourseViewModel;
import com.project.tutoquizzer.viewmodels.QuarterViewModel;
import com.project.tutoquizzer.viewmodels.SchoolYearViewModel;

import java.util.ArrayList;
import java.util.List;

public class SelectCourseFragment extends Fragment {

    private CourseViewModel cvm;
    private QuarterViewModel qvm;
    private SchoolYearViewModel syvm;

    private View rootView;

    private boolean isForQuiz = false;
    private int numberOfItems;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_select_course, container, false);

        this.cvm = ViewModelProviders.of(this).get(CourseViewModel.class);
        this.qvm = ViewModelProviders.of(this).get(QuarterViewModel.class);
        this.syvm = ViewModelProviders.of(this).get(SchoolYearViewModel.class);

        init();

        Bundle bundle = getArguments();

        if (bundle.get(RouteValues.HOME_TO_SELECT_COURSE_ACCESS).equals(RouteValues.QUIZ_SELECTED)){
            // Quiz button selected
            isForQuiz = true;
            numberItemsSpinner.setVisibility(rootView.getVisibility());
            numberItemsTV.setVisibility(rootView.getVisibility());
        }

        populateSpinner();
        buttonListeners();

        //on request, if REQUSTCODEQUIZ show another SPINNER for number of items,
        //else if REQUESTCODETUTORIAL DONT SHOW

        return this.rootView;
    }

    // Components

    private Button btnSelect;

    private Spinner courseSpinner, schoolYearSpinner, quarterSpinner, numberItemsSpinner;

    private TextView numberItemsTV;

    // For Navigation
    private BottomNavigationView bottomNavigationView;

    // End Components

    private void init(){
        numberItemsTV       = rootView.findViewById(R.id.textView);

        btnSelect           = rootView.findViewById(R.id.selectBtnSelectCourseAct);

        courseSpinner       = rootView.findViewById(R.id.coursesSpnrSelectCourseAct);
        schoolYearSpinner   = rootView.findViewById(R.id.schoolYearSpnrSelectCourseAct);
        quarterSpinner      = rootView.findViewById(R.id.quarterSpnrSelectCourseAct);
        numberItemsSpinner  = rootView.findViewById(R.id.numberItemsSpnrSelectCourseAct);

        numberItemsTV.setVisibility(View.INVISIBLE);
        numberItemsSpinner.setVisibility(View.INVISIBLE);
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
                    Toast.makeText(getContext(), "Fill out all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    determineIds();

                    Bundle bundle = new Bundle();

                    bundle.putString(RouteValues.COURSE_NAME_KEY, selectedCourse);
                    bundle.putString(RouteValues.QUARTER_NAME_KEY, selectedQuarter);
                    bundle.putString(RouteValues.SCHOOL_YEAR_NAME_KEY, selectedSchoolYear);

                    bundle.putInt(RouteValues.COURSE_ID_KEY, selectedCourseId);
                    bundle.putInt(RouteValues.QUARTER_ID_KEY, selectedQuarterId);
                    bundle.putInt(RouteValues.SCHOOL_YEAR_ID_KEY, selectedSchoolYearId);

                    if (isForQuiz) {

                        bundle.putString(RouteValues.SELECT_COURSE_RE_ROUTE, RouteValues.QUIZ_SELECTED);

                        bundle.putInt(RouteValues.NUMBER_ITEMS_KEY, numberOfItems);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        QuizFragment quizFragment = new QuizFragment();
                        quizFragment.setArguments(bundle);

                        fragmentTransaction.replace(R.id.fragment_container, quizFragment);
                        fragmentTransaction.commit();


                    } else {

                        bundle.putString(RouteValues.SELECT_COURSE_RE_ROUTE, RouteValues.TUTORIAL_SELECTED);

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                        TutorialFragment tutorialFragment = new TutorialFragment();
                        tutorialFragment.setArguments(bundle);

                        fragmentTransaction.replace(R.id.fragment_container, tutorialFragment);
                        fragmentTransaction.commit();

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
        cvm.getReferencedCourses().observe(getViewLifecycleOwner(), observer);

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
        syvm.getReferencedSchoolYears().observe(getViewLifecycleOwner(), observer1);

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
        qvm.getReferencedQuarters().observe(getViewLifecycleOwner(), observer2);

        ArrayAdapter<String> quarterAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_item, quarter);
        quarterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quarterSpinner.setAdapter(quarterAdapter);

        List<String> items = new ArrayList<String>();
        items.add("5");
        items.add("10");
        items.add("15");
        items.add("20");
        items.add("25");
        items.add("30");
        ArrayAdapter<String> numberItemAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_item, items);
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
