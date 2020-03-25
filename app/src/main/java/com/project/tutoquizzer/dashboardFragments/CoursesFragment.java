package com.project.tutoquizzer.dashboardFragments;


import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.tutoquizzer.EditCourseFragment;
import com.project.tutoquizzer.Personal.GSONHelper;
import com.project.tutoquizzer.Personal.User;
import com.project.tutoquizzer.R;
import com.project.tutoquizzer.RouteValues;
import com.project.tutoquizzer.entities.Courses;
import com.project.tutoquizzer.view.adapters.CoursesAdapter;
import com.project.tutoquizzer.viewmodels.CourseViewModel;
import com.project.tutoquizzer.viewmodels.TopicViewModel;

import java.util.ArrayList;
import java.util.List;

public class CoursesFragment extends Fragment {

    private CourseViewModel cvm;
    private TopicViewModel tvm;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_courses, container, false);

        init();

        this.cvm = ViewModelProviders.of(this).get(CourseViewModel.class);
        this.tvm = ViewModelProviders.of(this).get(TopicViewModel.class);

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

        addDataToRecyclerView(coursesAdapter);

        adapterListener(coursesAdapter);
        swipeListener(coursesAdapter);

    }

    private void addDataToRecyclerView(final CoursesAdapter coursesAdapter){
        this.cvm.getAllCourses().observe(getViewLifecycleOwner(), new Observer<List<Courses>>() {
            @Override
            public void onChanged(List<Courses> courses) {
                // Update RecyclerView
                coursesAdapter.setCourse(courses);
                courseCountTV.setText( Integer.toString(courses.size()) );
            }
        });
    }

    private void swipeListener(final CoursesAdapter adapter){

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {

                int courseId = adapter.getCourseAtPos( viewHolder.getAdapterPosition() ).getCourseId();
                tvm.getReferenceTopicCountByCourse(courseId).observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (Integer.parseInt( integer.toString() ) >= 1){
                            Toast.makeText(getContext(), "Error. This course has topic assigned to it. Delete topic(s) and try again.", Toast.LENGTH_SHORT).show();
                            addDataToRecyclerView(adapter);
                        }else{
                            new AlertDialog.Builder(getContext()).setTitle("Confirm Delete").setMessage("Are you sure you want to delete course?")
                                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            cvm.delete( adapter.getCourseAtPos( viewHolder.getPosition() ) );
                                            Toast.makeText(getContext(), "Course Deleted!", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            addDataToRecyclerView(adapter);
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert).show();
                        }
                    }
                });
            }

        }).attachToRecyclerView(recyclerView);

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
