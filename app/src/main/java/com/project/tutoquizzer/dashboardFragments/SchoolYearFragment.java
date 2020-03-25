package com.project.tutoquizzer.dashboardFragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.project.tutoquizzer.AddSchoolYearFragment;
import com.project.tutoquizzer.EditSchoolYearFragment;
import com.project.tutoquizzer.Personal.GSONHelper;
import com.project.tutoquizzer.Personal.User;
import com.project.tutoquizzer.R;
import com.project.tutoquizzer.RouteValues;
import com.project.tutoquizzer.entities.Courses;
import com.project.tutoquizzer.entities.SchoolYear;
import com.project.tutoquizzer.view.adapters.CoursesAdapter;
import com.project.tutoquizzer.view.adapters.SchoolYearAdapter;
import com.project.tutoquizzer.viewmodels.CourseViewModel;
import com.project.tutoquizzer.viewmodels.SchoolYearViewModel;
import com.project.tutoquizzer.viewmodels.TopicViewModel;

import org.w3c.dom.Text;

import java.util.List;

public class SchoolYearFragment extends Fragment {

    private SchoolYearViewModel syvm;
    private TopicViewModel tvm;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_school_year, container, false);

        init();

        syvm = ViewModelProviders.of(this).get(SchoolYearViewModel.class);
        tvm = ViewModelProviders.of(this).get(TopicViewModel.class);

        displayPersonalDetails(GSONHelper.loadData(getContext()));

        initRecyclerView();
        buttonListeners();

        return this.rootView;
    }

    private void displayPersonalDetails(User user){
        nameTV.setText(user.getFirstName() + " " + user.getLastName());
        schoolTV.setText(user.getSchool());
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager( new LinearLayoutManager(rootView.getContext()));
        recyclerView.setHasFixedSize(true);

        final SchoolYearAdapter schoolYearAdapter = new SchoolYearAdapter();
        recyclerView.setAdapter(schoolYearAdapter);

        addDataToRecyclerView(schoolYearAdapter);

        adapterListener(schoolYearAdapter);
        swipeListener(schoolYearAdapter);
    }

    private void addDataToRecyclerView(final SchoolYearAdapter adapter){
        this.syvm.getAllSchoolYears().observe(getViewLifecycleOwner(), new Observer<List<SchoolYear>>() {
            @Override
            public void onChanged(List<SchoolYear> schoolYears) {
                // Update RecyclerView
                adapter.setSchoolYears(schoolYears);
                schoolYearCountET.setText( Integer.toString(schoolYears.size()) );
            }
        });
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

    private void swipeListener(final SchoolYearAdapter adapter){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {

                int yearId = adapter.getSchoolYearAtPos( viewHolder.getAdapterPosition() ).getYearId();

                tvm.getReferenceTopicCountBySchoolYear(yearId).observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        if (Integer.parseInt( integer.toString() ) >= 1){
                            Toast.makeText(getContext(), "Error. This has topic assigned to it. Delete topic(s) and try again.", Toast.LENGTH_SHORT).show();
                            addDataToRecyclerView(adapter);
                        }else{
                            new AlertDialog.Builder(getContext()).setTitle("Confirm Delete").setMessage("Are you sure you want to delete??")
                                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            syvm.delete( adapter.getSchoolYearAtPos( viewHolder.getPosition() ) );
                                            Toast.makeText(getContext(), "School Year Deleted!", Toast.LENGTH_SHORT).show();
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

    private void buttonListeners(){
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragment_container, new AddSchoolYearFragment());
                fragmentTransaction.commit();
            }
        });
    }

    // Components

    private RecyclerView recyclerView;

    private EditText schoolYearCountET;

    private TextView nameTV, schoolTV;

    private ImageButton addBtn;

    private void init(){
        schoolYearCountET   = rootView.findViewById(R.id.schoolYearCountTV);

        recyclerView        = rootView.findViewById(R.id.recycler_view_fragment_school_year);

        nameTV              = rootView.findViewById(R.id.tvNameD);
        schoolTV            = rootView.findViewById(R.id.tvSchoolD);

        addBtn              = rootView.findViewById(R.id.addSchoolYearD);
    }

}
