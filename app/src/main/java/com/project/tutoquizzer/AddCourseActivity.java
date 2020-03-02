package com.project.tutoquizzer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.tutoquizzer.entities.Courses;
import com.project.tutoquizzer.entities.Quarters;
import com.project.tutoquizzer.entities.SchoolYear;
import com.project.tutoquizzer.view.adapters.CoursesAdapter;
import com.project.tutoquizzer.viewmodels.CourseViewModel;
import com.project.tutoquizzer.viewmodels.QuarterViewModel;
import com.project.tutoquizzer.viewmodels.SchoolYearViewModel;

import java.util.List;

public class AddCourseActivity extends AppCompatActivity {

    private CourseViewModel cvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course_activity);

        setTitle("TutoQuizzer - Add Course");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.return_arrow);

        this.cvm = ViewModelProviders.of(this).get(CourseViewModel.class);

        init();

        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final CoursesAdapter coursesAdapter = new CoursesAdapter();
        recyclerView.setAdapter(coursesAdapter);

        this.cvm.getAllCourses().observe(this, new Observer<List<Courses>>() {
            @Override
            public void onChanged(List<Courses> courses) {
                // Update RecyclerView
                coursesAdapter.setCourse(courses);
            }
        });

        adapterListener(coursesAdapter);

        buttonListeners();
    }

    private void adapterListener(CoursesAdapter adapter){
        adapter.setOnItemClickListener(new CoursesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Courses course) {
                Toast.makeText(AddCourseActivity.this, course.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddCourseActivity.this, EditCourseActivity.class);
                intent.putExtra(AppValues.INTENT_HOLDER_ID_COURSE, course.getCourseId());
                intent.putExtra(AppValues.INTENT_HOLDER_COURSE_NAME, course.getName());
                intent.putExtra(AppValues.INTENT_HOLDER_COURSE_CODE, course.getCode());
                startActivityForResult(intent, AppValues.REQ_CODE_EDIT_COURSE);
            }
        });
    }

    // Components
    private RecyclerView recyclerView;

    private EditText courseNameET, courseCodeET;

    private Button addBtn;

    // End

    private void init(){
        recyclerView = findViewById(R.id.recycler_view_select_course_act);

        courseCodeET    = findViewById(R.id.courseCodeAddCourseEditText);
        courseNameET    = findViewById(R.id.courseNameAddCourseEditText);

        addBtn          = findViewById(R.id.addBtnAddCourseActivity);

    }

    private void buttonListeners(){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String courseName = courseNameET.getText().toString().trim();
                String courseCode = courseCodeET.getText().toString().trim();

                if(courseName == "" || courseCode == ""){
                    Toast.makeText(AddCourseActivity.this, "Fill out all the fields.", Toast.LENGTH_SHORT).show();
                }else{
                    Courses courses = new Courses(courseName, courseCode);
                    cvm.insert(courses);
                    Toast.makeText(AddCourseActivity.this, "Course Added.", Toast.LENGTH_SHORT).show();
                }

                clearTextViews();

            }
        });
    }

    private void  clearTextViews(){

        courseNameET.setText("");
        courseCodeET.setText("");

    }

}
