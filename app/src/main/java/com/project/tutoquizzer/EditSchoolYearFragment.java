package com.project.tutoquizzer;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.project.tutoquizzer.R;
import com.project.tutoquizzer.dashboardFragments.SchoolYearFragment;
import com.project.tutoquizzer.entities.SchoolYear;
import com.project.tutoquizzer.viewmodels.SchoolYearViewModel;

public class EditSchoolYearFragment extends Fragment {

    private SchoolYearViewModel syvm;

    private SchoolYear schoolYear;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_edit_school_year, container, false);

        syvm = ViewModelProviders.of(this).get(SchoolYearViewModel.class);

        Bundle bundle = getArguments();

        schoolYear = new SchoolYear(
                bundle.getInt(RouteValues.SCHOOLYEAR_YEAR_KEY),
                bundle.getInt(RouteValues.SCHOOLYEAR_TERM_KEY));

        schoolYear.setYearId(bundle.getInt(RouteValues.SCHOOLYEAR_ID_KEY));

        init();

        displayData();
        buttonListener();

        return this.rootView;
    }

    private void displayData(){
        termET.setText( String.valueOf(schoolYear.getTerm()) );
        yearET.setText( String.valueOf(schoolYear.getYear()) );
    }

    private void buttonListener(){
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                schoolYear.setTerm( Integer.parseInt(termET.getText().toString()) );
                schoolYear.setYear( Integer.parseInt(yearET.getText().toString()) );
                syvm.update(schoolYear);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                SchoolYearFragment schoolYearFragment = new SchoolYearFragment();

                fragmentTransaction.replace(R.id.fragment_container, schoolYearFragment);
                fragmentTransaction.commit();
            }
        });
    }

    // Components

    private EditText termET, yearET;

    private Button saveBtn;

    private void init(){
        termET = rootView.findViewById(R.id.termEditSchoolYearEditText);
        yearET = rootView.findViewById(R.id.yearEditSchoolYearEditText);

        saveBtn = rootView.findViewById(R.id.saveEditBtnSchoolYearCourse);
    }
}
