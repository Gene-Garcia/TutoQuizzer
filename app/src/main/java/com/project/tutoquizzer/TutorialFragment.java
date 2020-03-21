package com.project.tutoquizzer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.tutoquizzer.entities.Topics;
import com.project.tutoquizzer.view.adapters.TutorialAdapter;
import com.project.tutoquizzer.viewmodels.TopicViewModel;

import java.util.ArrayList;
import java.util.List;

public class TutorialFragment extends Fragment {

    private TopicViewModel tvm;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_tutorial, container, false);

        this.tvm = ViewModelProviders.of(this).get(TopicViewModel.class);

        init();

        /*Intent intent = getIntent();
        setSelectionDisplay(intent);

        selectedCourseId        = intent.getIntExtra(AppValues.INTENT_HOLDER_ID_COURSE, -1);
        selectedSchoolYearId    = intent.getIntExtra(AppValues.INTENT_HOLDER_ID_SCHOOLYEAR, -1);
        selectedQuarterId       = intent.getIntExtra(AppValues.INTENT_HOLDER_ID_QUARTER, -1);*/

        recyclerView.setLayoutManager( new LinearLayoutManager(rootView.getContext()));
        recyclerView.setHasFixedSize(true);

        final TutorialAdapter tutorialAdapter = new TutorialAdapter();
        recyclerView.setAdapter(tutorialAdapter);

        this.tvm.getSelectedTopics(selectedCourseId, selectedSchoolYearId, selectedQuarterId).observe(getViewLifecycleOwner(), new Observer<List<Topics>>() {
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

        return this.rootView;
    }

    private void adapterListener(TutorialAdapter adapter){
        adapter.setOnItemClickListener(new TutorialAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Topics topic) {
                /*Intent intent  = new Intent(TutorialFragment.this, EditTopicActivity.class);
                intent.putExtra(AppValues.INTENT_HOLDER_ID_TOPIC, topic.getTopicId());
                intent.putExtra(AppValues.INTENT_HOLDER_TOPIC, topic.getTopic());
                intent.putExtra(AppValues.INTENT_HOLDER_MEANING, topic.getMeaning());

                intent.putExtra(AppValues.INTENT_HOLDER_COURSE, selectedCourse);
                intent.putExtra(AppValues.INTENT_HOLDER_SCHOOLYEAR, selectedSchoolYear);
                intent.putExtra(AppValues.INTENT_HOLDER_QUARTER, selectedQuarter);

                intent.putExtra(AppValues.INTENT_HOLDER_ID_COURSE, selectedCourseId);
                intent.putExtra(AppValues.INTENT_HOLDER_ID_SCHOOLYEAR, selectedSchoolYearId);
                intent.putExtra(AppValues.INTENT_HOLDER_ID_QUARTER, selectedQuarterId);

                startActivityForResult(intent, AppValues.REQ_CODE_EDIT_TOPIC);*/
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
        recyclerView = rootView.findViewById(R.id.recycler_view_tutorial_act);

        selectionTV = rootView.findViewById(R.id.displaySelectionTextViewTutorialAct);

        // For Navigation
        bottomNavigationView = rootView.findViewById(R.id.menuAct);
    }
}
