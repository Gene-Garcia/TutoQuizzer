package com.project.tutoquizzer;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.project.tutoquizzer.Personal.GSONHelper;
import com.project.tutoquizzer.Personal.User;
import com.project.tutoquizzer.dashboardFragments.CoursesFragment;
import com.project.tutoquizzer.dashboardFragments.QuarterFragment;
import com.project.tutoquizzer.dashboardFragments.SchoolYearFragment;
import com.project.tutoquizzer.dashboardFragments.TopicsFragment;

import org.w3c.dom.Text;

public class DashboardFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        init();
        buttonListeners();
        displayPersonalDetails(GSONHelper.loadData(getContext()));

        return this.rootView;
    }

    private void displayPersonalDetails(User user){
        nameTV.setText(user.getFirstName() + " " + user.getLastName());
        schoolTV.setText(user.getSchool());
    }

    private void buttonListeners(){
        coursesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reRoute( new CoursesFragment() );
            }
        });

        schoolYearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reRoute( new SchoolYearFragment() );
            }
        });

        quarterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reRoute( new QuarterFragment() );
            }
        });

        topicsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reRoute( new TopicsFragment() );
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reRoute( new EditPersonalInformationFragment());
            }
        });
    }

    private void reRoute(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    // Components

    private Button coursesBtn, schoolYearBtn, quarterBtn, topicsBtn;

    private TextView nameTV, schoolTV;

    private ImageButton updateBtn;

    private void init(){
        coursesBtn      = rootView.findViewById(R.id.coursesBtnD);
        schoolYearBtn   = rootView.findViewById(R.id.schoolYearBtnD);
        quarterBtn      = rootView.findViewById(R.id.quarterBtnD);
        topicsBtn       = rootView.findViewById(R.id.topicsBtnD);
        updateBtn       = rootView.findViewById(R.id.updateBtnD);

        nameTV          = rootView.findViewById(R.id.tvNameD);
        schoolTV        = rootView.findViewById(R.id.tvSchoolD);
    }

}
