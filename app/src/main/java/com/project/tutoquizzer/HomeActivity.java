package com.project.tutoquizzer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // use bundle for passing if for quiz or tutorial for drop down

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //I added this if statement to keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MainFragment()).commit();
        }

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.homeMenu:
                            selectedFragment = new MainFragment();
                            break;
                        case R.id.addCourseMenu:
                            selectedFragment = new AddCourseFragment();
                            break;
                        case R.id.addTopicMenu:
                            selectedFragment = new AddTopicFragment();
                            break;
                        case R.id.quizMenu:
                            Bundle bundle = new Bundle();
                            bundle.putString(RouteValues.HOME_TO_SELECT_COURSE_ACCESS, RouteValues.QUIZ_SELECTED);
                            selectedFragment = new SelectCourseFragment();
                            selectedFragment.setArguments(bundle);

                            break;
                        case R.id.tutorialMenu:
                            Bundle bundle1 = new Bundle();
                            bundle1.putString(RouteValues.HOME_TO_SELECT_COURSE_ACCESS, RouteValues.TUTORIAL_SELECTED);
                            selectedFragment = new SelectCourseFragment();
                            selectedFragment.setArguments(bundle1);

                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}