package com.project.tutoquizzer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
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
        configureCall();

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

    private void configureCall(){
        Bundle bundle = getArguments();
        setSelectionDisplay(bundle);

        selectedCourseId        = bundle.getInt(RouteValues.COURSE_ID_KEY);
        selectedSchoolYearId    = bundle.getInt(RouteValues.SCHOOL_YEAR_ID_KEY);
        selectedQuarterId       = bundle.getInt(RouteValues.QUARTER_ID_KEY);
    }

    private void adapterListener(TutorialAdapter adapter){
        adapter.setOnItemClickListener(new TutorialAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Topics topic) {

                Bundle bundle = new Bundle();

                bundle.putInt       (RouteValues.TOPIC_ID_KEY, topic.getTopicId());
                bundle.putString    (RouteValues.TOPIC_NAME_KEY, topic.getTopic());
                bundle.putString    (RouteValues.DESCRIPTION_NAME_KEY, topic.getMeaning());

                bundle.putString    (RouteValues.COURSE_NAME_KEY, selectedCourse);
                bundle.putString    (RouteValues.SCHOOL_YEAR_NAME_KEY, selectedSchoolYear);
                bundle.putString    (RouteValues.QUARTER_NAME_KEY, selectedQuarter);

                bundle.putInt       (RouteValues.COURSE_ID_KEY, selectedCourseId);
                bundle.putInt       (RouteValues.SCHOOL_YEAR_ID_KEY, selectedSchoolYearId);
                bundle.putInt       (RouteValues.QUARTER_ID_KEY, selectedQuarterId);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                EditTopicFragment editTopicFragment = new EditTopicFragment();
                editTopicFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_container, editTopicFragment);
                fragmentTransaction.commit();
            }
        });
    }

    private String selectedCourse;
    private String selectedSchoolYear;
    private String selectedQuarter;

    private int selectedCourseId;
    private int selectedSchoolYearId;
    private int selectedQuarterId;

    private void setSelectionDisplay(Bundle bundle){
        selectedCourse       = bundle.getString(RouteValues.COURSE_NAME_KEY);
        selectedSchoolYear   = bundle.getString(RouteValues.SCHOOL_YEAR_NAME_KEY);
        selectedQuarter      = bundle.getString(RouteValues.QUARTER_NAME_KEY);

        selectionTV.setText( selectedSchoolYear + " > " + selectedCourse.toUpperCase() + " > " + selectedQuarter.toUpperCase() );
    }

    // Components

    private TextView selectionTV;

    private RecyclerView recyclerView;

    // End

    private void init(){
        recyclerView = rootView.findViewById(R.id.recycler_view_tutorial_act);

        selectionTV = rootView.findViewById(R.id.displaySelectionTextViewTutorialAct);
    }
}
