package com.project.tutoquizzer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.tutoquizzer.entities.Topics;
import com.project.tutoquizzer.view.adapters.TutorialAdapter;
import com.project.tutoquizzer.viewmodels.TopicViewModel;

import java.util.ArrayList;
import java.util.List;

public class TutorialActivity extends AppCompatActivity {

    private TopicViewModel tvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial_activity);

        setTitle("TutoQuizzer - Tutorial");

        this.tvm = ViewModelProviders.of(this).get(TopicViewModel.class);

        init();
        navigationListener();

        Intent intent = getIntent();
        setSelectionDisplay(intent);

        selectedCourseId        = intent.getIntExtra(AppValues.INTENT_HOLDER_ID_COURSE, -1);
        selectedSchoolYearId    = intent.getIntExtra(AppValues.INTENT_HOLDER_ID_SCHOOLYEAR, -1);
        selectedQuarterId       = intent.getIntExtra(AppValues.INTENT_HOLDER_ID_QUARTER, -1);

        recyclerView.setLayoutManager( new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TutorialAdapter tutorialAdapter = new TutorialAdapter();
        recyclerView.setAdapter(tutorialAdapter);

        this.tvm.getSelectedTopics(selectedCourseId, selectedSchoolYearId, selectedQuarterId).observe(this, new Observer<List<Topics>>() {
            @Override
            public void onChanged(List<Topics> topics) {
                if (topics.size() < 1){
                    List<Topics> emptyTopics = new ArrayList<>();
                    emptyTopics.add( new Topics("N/A", "N/A", 0, 0,0) );
                    tutorialAdapter.setTopics(  emptyTopics );
                } else{
                    tutorialAdapter.setTopics(topics);
                }
            }
        });

        adapterListener(tutorialAdapter);

    }

    private void adapterListener(TutorialAdapter adapter){
        adapter.setOnItemClickListener(new TutorialAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Topics topic) {
                Intent intent  = new Intent(TutorialActivity.this, EditTopicActivity.class);
                intent.putExtra(AppValues.INTENT_HOLDER_ID_TOPIC, topic.getTopicId());
                intent.putExtra(AppValues.INTENT_HOLDER_TOPIC, topic.getTopic());
                intent.putExtra(AppValues.INTENT_HOLDER_MEANING, topic.getMeaning());

                intent.putExtra(AppValues.INTENT_HOLDER_COURSE, selectedCourse);
                intent.putExtra(AppValues.INTENT_HOLDER_SCHOOLYEAR, selectedSchoolYear);
                intent.putExtra(AppValues.INTENT_HOLDER_QUARTER, selectedQuarter);

                intent.putExtra(AppValues.INTENT_HOLDER_ID_COURSE, selectedCourseId);
                intent.putExtra(AppValues.INTENT_HOLDER_ID_SCHOOLYEAR, selectedSchoolYearId);
                intent.putExtra(AppValues.INTENT_HOLDER_ID_QUARTER, selectedQuarterId);

                startActivityForResult(intent, AppValues.REQ_CODE_EDIT_TOPIC);
            }
        });
    }

    private String selectedCourse;
    private String selectedSchoolYear;
    private String selectedQuarter;

    private int selectedCourseId;
    private int selectedSchoolYearId;
    private int selectedQuarterId;

    private void setSelectionDisplay(Intent temp){
        selectedCourse       = temp.getStringExtra(AppValues.INTENT_HOLDER_COURSE);
        selectedSchoolYear   = temp.getStringExtra(AppValues.INTENT_HOLDER_SCHOOLYEAR);
        selectedQuarter      = temp.getStringExtra(AppValues.INTENT_HOLDER_QUARTER);

        selectionTV.setText( selectedSchoolYear + " > " + selectedCourse.toUpperCase() + " > " + selectedQuarter.toUpperCase() );
    }

    // Components

    private TextView selectionTV;

    private RecyclerView recyclerView;

    // For Navigation
    private BottomNavigationView bottomNavigationView;

    // End

    private void init(){
        recyclerView = findViewById(R.id.recycler_view_tutorial_act);

        selectionTV = findViewById(R.id.displaySelectionTextViewTutorialAct);

        // For Navigation
        bottomNavigationView = findViewById(R.id.menuAct);
    }

    // For Navigation
    private void navigationListener(){

        bottomNavigationView.setSelectedItemId(R.id.tutorialMenu);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()){

                    case R.id.homeMenu:
                        Intent intent = new Intent(getApplicationContext(), MainFragment.class);
                        startActivityForResult( intent, AppValues.REQ_CODE_HOME);
                        return true;

                    case R.id.quizMenu:
                        Intent intent1 = new Intent(getApplicationContext(), SelectCourseActivity.class);
                        intent1.putExtra(AppValues.INTENT_NAME_SELECT_COURSE, AppValues.REQ_CODE_QUIZ);
                        startActivityForResult(intent1, AppValues.REQ_CODE_QUIZ);
                        return true;

                    case R.id.tutorialMenu:
                        Intent intent2 = new Intent(getApplicationContext(), SelectCourseActivity.class);
                        intent2.putExtra(AppValues.INTENT_NAME_SELECT_COURSE, AppValues.REQ_CODE_TUTORIAL);
                        startActivityForResult(intent2, AppValues.REQ_CODE_TUTORIAL);

                    case R.id.addCourseMenu:
                        Intent intent3 = new Intent(getApplicationContext(), AddCourseActivity.class);
                        startActivityForResult(intent3, AppValues.REQ_CODE_ADD_COURSE);
                        return true;

                    case R.id.addTopicMenu:
                        Intent intent4 = new Intent(getApplicationContext(), AddTopicActivity.class);
                        startActivityForResult(intent4, AppValues.REQ_CODE_ADD_TOPIC);
                        return true;
                }

                return false;
            }
        });

        //Intent intent = new Intent(MainFragment.this, AddCourseActivity.class);
        //startActivityForResult(intent, AppValues.REQ_CODE_ADD_COURSE);

        //Intent intent1 = new Intent(MainFragment.this, AddTopicActivity.class);
        //startActivityForResult(intent1, AppValues.REQ_CODE_ADD_TOPIC);

    }
}
