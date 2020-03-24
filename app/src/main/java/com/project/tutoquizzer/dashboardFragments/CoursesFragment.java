package com.project.tutoquizzer.dashboardFragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.project.tutoquizzer.EditCourseFragment;
import com.project.tutoquizzer.Personal.GSONHelper;
import com.project.tutoquizzer.Personal.User;
import com.project.tutoquizzer.R;
import com.project.tutoquizzer.RouteValues;
import com.project.tutoquizzer.entities.Courses;
import com.project.tutoquizzer.view.adapters.CoursesAdapter;
import com.project.tutoquizzer.viewmodels.CourseViewModel;

import java.util.List;

public class CoursesFragment extends Fragment {

    private CourseViewModel cvm;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_courses, container, false);

        init();

        this.cvm = ViewModelProviders.of(this).get(CourseViewModel.class);

        displayPersonalDetails(GSONHelper.loadData(getContext()));

        initRecyclerView();

        return this.rootView;
    }

    private void displayPersonalDetails(User user){
        nameTV.setText(user.getFirstName() + " " + user.getLastName());
        schoolTV.setText(user.getSchool());
    }

    private void adapterListener(CoursesAdapter adapter){
        adapter.setOnItemClickListener(new CoursesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Courses course) {

                Bundle bundle = new Bundle();

                bundle.putInt       (RouteValues.COURSE_ID_KEY, course.getCourseId());
                bundle.putString    (RouteValues.COURSE_NAME_KEY, course.getName());
                bundle.putString    (RouteValues.COURSE_CODE_KEY, course.getCode());
                bundle.putBoolean   (RouteValues.IS_FOR_DASHBOARD, true);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                EditCourseFragment editCourseFragment = new EditCourseFragment();
                editCourseFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_container, editCourseFragment);
                fragmentTransaction.commit();

            }
        });
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager( new LinearLayoutManager(rootView.getContext()));
        recyclerView.setHasFixedSize(true);

        final CoursesAdapter coursesAdapter = new CoursesAdapter();
        recyclerView.setAdapter(coursesAdapter);

        this.cvm.getAllCourses().observe(getViewLifecycleOwner(), new Observer<List<Courses>>() {
            @Override
            public void onChanged(List<Courses> courses) {
                // Update RecyclerView
                coursesAdapter.setCourse(courses);
                courseCountTV.setText( Integer.toString(courses.size()) );
            }
        });

        adapterListener(coursesAdapter);

    }

    // Components

    private RecyclerView recyclerView;

    private EditText courseCountTV;

    private TextView nameTV, schoolTV;

    private void init(){
        courseCountTV = rootView.findViewById(R.id.coursesCountTV);

        recyclerView = rootView.findViewById(R.id.recycler_view_select_course_act);

        nameTV = rootView.findViewById(R.id.tvNameD);
        schoolTV = rootView.findViewById(R.id.tvSchoolD);
    }

}
