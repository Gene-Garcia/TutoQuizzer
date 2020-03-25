package com.project.tutoquizzer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.tutoquizzer.entities.Courses;
import com.project.tutoquizzer.view.adapters.CoursesAdapter;
import com.project.tutoquizzer.viewmodels.CourseViewModel;

import java.util.List;

public class AddCourseFragment extends Fragment {

    private CourseViewModel cvm;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_add_course, container, false);
        this.cvm = ViewModelProviders.of(this).get(CourseViewModel.class);

        init();

        recyclerView.setLayoutManager( new LinearLayoutManager(rootView.getContext()));
        recyclerView.setHasFixedSize(true);

        final CoursesAdapter coursesAdapter = new CoursesAdapter();
        recyclerView.setAdapter(coursesAdapter);

        this.cvm.getAllCourses().observe(getViewLifecycleOwner(), new Observer<List<Courses>>() {
            @Override
            public void onChanged(List<Courses> courses) {
                // Update RecyclerView
                coursesAdapter.setCourse(courses);
            }
        });

        adapterListener(coursesAdapter);

        buttonListeners();

        return this.rootView;
    }


    private void adapterListener(CoursesAdapter adapter){
        adapter.setOnItemClickListener(new CoursesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Courses course) {

                Bundle bundle = new Bundle();

                bundle.putInt       (RouteValues.COURSE_ID_KEY, course.getCourseId());
                bundle.putString    (RouteValues.COURSE_NAME_KEY, course.getName());
                bundle.putString    (RouteValues.COURSE_CODE_KEY, course.getCode());

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                EditCourseFragment editCourseFragment = new EditCourseFragment();
                editCourseFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_container, editCourseFragment);
                fragmentTransaction.commit();

            }
        });
    }

    // Components
    private RecyclerView recyclerView;

    private EditText courseNameET, courseCodeET;

    private Button addBtn;

    // End

    private void init(){
        recyclerView = rootView.findViewById(R.id.recycler_view_select_course_act);

        courseCodeET    = rootView.findViewById(R.id.courseCodeAddCourseEditText);
        courseNameET    = rootView.findViewById(R.id.courseNameAddCourseEditText);

        addBtn = rootView.findViewById(R.id.addCourseBtn);

    }

    private void buttonListeners(){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String courseName = courseNameET.getText().toString().trim();
                String courseCode = courseCodeET.getText().toString().trim();

                if(courseName.isEmpty() || courseCode.isEmpty()){
                    Toast.makeText(getContext(), "Fill out all the fields.", Toast.LENGTH_SHORT).show();
                }else{
                    Courses courses = new Courses(courseName, courseCode);
                    cvm.insert(courses);
                    Toast.makeText(getContext(), "Course Added.", Toast.LENGTH_SHORT).show();
                    clearTextViews();
                }


            }
        });
    }

    private void  clearTextViews(){

        courseNameET.setText("");
        courseCodeET.setText("");

    }

}
