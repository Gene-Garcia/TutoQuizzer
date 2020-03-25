package com.project.tutoquizzer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.tutoquizzer.entities.Courses;
import com.project.tutoquizzer.entities.Quarters;
import com.project.tutoquizzer.entities.SchoolYear;
import com.project.tutoquizzer.view.adapters.CoursesAdapter;
import com.project.tutoquizzer.view.adapters.SchoolYearAdapter;
import com.project.tutoquizzer.viewmodels.CourseViewModel;
import com.project.tutoquizzer.viewmodels.SchoolYearViewModel;

import java.util.List;

public class AddSchoolYearFragment extends Fragment {

    private SchoolYearViewModel syvm;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_add_school_year, container, false);
        this.syvm = ViewModelProviders.of(this).get(SchoolYearViewModel.class);

        init();

        recyclerView.setLayoutManager( new LinearLayoutManager(rootView.getContext()));
        recyclerView.setHasFixedSize(true);

        final SchoolYearAdapter adapter = new SchoolYearAdapter();
        recyclerView.setAdapter(adapter);

        this.syvm.getAllSchoolYears().observe(getViewLifecycleOwner(), new Observer<List<SchoolYear>>() {
            @Override
            public void onChanged(List<SchoolYear> schoolYears) {
                // Update RecyclerView
                adapter.setSchoolYears(schoolYears);
            }
        });

        adapterListener(adapter);

        buttonListeners();

        return this.rootView;
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

                EditSchoolYearFragment fragment = new EditSchoolYearFragment();
                fragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();

            }
        });
    }

    // Components
    private RecyclerView recyclerView;

    private EditText yearET, termET;

    private Button addBtn;

    // End

    private void init(){
        recyclerView    = rootView.findViewById(R.id.recycler_view_fragment_school_year);

        yearET          = rootView.findViewById(R.id.yearAddSchoolYearEditText);
        termET          = rootView.findViewById(R.id.termAddSchoolYearEditText);

        addBtn          = rootView.findViewById(R.id.addSchoolYearBtn);

    }

    private void buttonListeners(){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String year = yearET.getText().toString().trim();
                String term = termET.getText().toString().trim();

                if(year.isEmpty() || term.isEmpty()){
                    Toast.makeText(getContext(), "Fill out all the fields.", Toast.LENGTH_SHORT).show();
                }else{
                    SchoolYear schoolYear = new SchoolYear(Integer.parseInt(year), Integer.parseInt(term));
                    syvm.insert(schoolYear);
                    Toast.makeText(getContext(), "School Year Added.", Toast.LENGTH_SHORT).show();
                    clearTextViews();
                }


            }
        });
    }

    private void  clearTextViews(){
        yearET.setText("");
        termET.setText("");
    }

}
