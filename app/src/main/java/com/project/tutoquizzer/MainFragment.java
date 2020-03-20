package com.project.tutoquizzer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.tutoquizzer.viewmodels.CourseViewModel;
import com.project.tutoquizzer.viewmodels.QuarterViewModel;
import com.project.tutoquizzer.viewmodels.SchoolYearViewModel;
import com.project.tutoquizzer.viewmodels.TopicViewModel;

public class MainFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_main, container, false);

        init();

        return this.rootView;
    }

    // Main Activity Components

    // For Navigation
    private BottomNavigationView bottomNavigationView;

    // End

    private void init(){
        // For Navigation
        bottomNavigationView = rootView.findViewById(R.id.menuAct);
    }

    // For Navigation
    /*
    private void navigationListener(){

        bottomNavigationView.setSelectedItemId(R.id.homeMenu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.homeMenu:
                        Intent intent = new Intent(getApplicationContext(), MainFragment.class);
                        startActivityForResult( intent, AppValues.REQ_CODE_HOME);
                        return true;

                    case R.id.quizMenu:
                        Intent intent1 = new Intent(getApplicationContext(), SelectCourseFragment.class);
                        intent1.putExtra(AppValues.INTENT_NAME_SELECT_COURSE, AppValues.REQ_CODE_QUIZ);
                        startActivityForResult(intent1, AppValues.REQ_CODE_QUIZ);
                        return true;

                    case R.id.tutorialMenu:
                        Intent intent2 = new Intent(getApplicationContext(), SelectCourseFragment.class);
                        intent2.putExtra(AppValues.INTENT_NAME_SELECT_COURSE, AppValues.REQ_CODE_TUTORIAL);
                        startActivityForResult(intent2, AppValues.REQ_CODE_TUTORIAL);
                        return true;

                    case R.id.addCourseMenu:
                        Intent intent3 = new Intent(getApplicationContext(), AddCourseFragment.class);
                        startActivityForResult(intent3, AppValues.REQ_CODE_ADD_COURSE);
                        return true;

                    case R.id.addTopicMenu:
                        Intent intent4 = new Intent(getApplicationContext(), AddTopicFragment.class);
                        startActivityForResult(intent4, AppValues.REQ_CODE_ADD_TOPIC);
                        return true;
                }

                return false;
            }
        });

    }
     */
}
