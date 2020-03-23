package com.project.tutoquizzer.dashboardFragments;

import android.content.Context;
import android.net.Uri;
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

import com.project.tutoquizzer.EditSchoolYearFragment;
import com.project.tutoquizzer.R;
import com.project.tutoquizzer.RouteValues;
import com.project.tutoquizzer.entities.Courses;
import com.project.tutoquizzer.entities.SchoolYear;
import com.project.tutoquizzer.view.adapters.CoursesAdapter;
import com.project.tutoquizzer.view.adapters.SchoolYearAdapter;
import com.project.tutoquizzer.viewmodels.CourseViewModel;
import com.project.tutoquizzer.viewmodels.SchoolYearViewModel;

import java.util.List;

public class SchoolYearFragment extends Fragment {

    private SchoolYearViewModel syvm;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_school_year, container, false);

        init();

        syvm = ViewModelProviders.of(this).get(SchoolYearViewModel.class);

        initRecyclerView();

        return this.rootView;
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager( new LinearLayoutManager(rootView.getContext()));
        recyclerView.setHasFixedSize(true);

        final SchoolYearAdapter schoolYearAdapter = new SchoolYearAdapter();
        recyclerView.setAdapter(schoolYearAdapter);

        this.syvm.getAllSchoolYears().observe(getViewLifecycleOwner(), new Observer<List<SchoolYear>>() {
            @Override
            public void onChanged(List<SchoolYear> schoolYears) {
                // Update RecyclerView
                schoolYearAdapter.setSchoolYears(schoolYears);
                schoolYearCountET.setText( Integer.toString(schoolYears.size()) );
            }
        });

        adapterListener(schoolYearAdapter);
    }

    private void adapterListener(SchoolYearAdapter adapter){

        adapter.setOnItemClickListener(new SchoolYearAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(SchoolYear schoolYear) {
                Bundle bundle = new Bundle();

                bundle.putInt       (RouteValues.SCHOOL_YEAR_ID_KEY, schoolYear.getYearId());
                bundle.putInt       (RouteValues.SCHOOL_YEAR_TERM_KEY, schoolYear.getTerm());
                bundle.putInt       (RouteValues.SCHOOL_YEAR_YEAR_KEY, schoolYear.getYear());

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                EditSchoolYearFragment editSchoolYearFragment = new EditSchoolYearFragment();
                editSchoolYearFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_container, editSchoolYearFragment);
                fragmentTransaction.commit();
            }
        });

    }

    // Components

    private RecyclerView recyclerView;

    private EditText schoolYearCountET;

    private void init(){
        schoolYearCountET = rootView.findViewById(R.id.schoolYearCountTV);

        recyclerView = rootView.findViewById(R.id.recycler_view_fragment_school_year);
    }

}
