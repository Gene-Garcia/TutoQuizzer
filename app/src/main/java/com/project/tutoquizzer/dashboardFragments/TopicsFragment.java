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
import android.widget.TextView;
import android.widget.Toast;

import com.project.tutoquizzer.EditTopicFragment;
import com.project.tutoquizzer.Personal.GSONHelper;
import com.project.tutoquizzer.Personal.User;
import com.project.tutoquizzer.R;
import com.project.tutoquizzer.RouteValues;
import com.project.tutoquizzer.entities.Topics;
import com.project.tutoquizzer.view.adapters.TutorialAdapter;
import com.project.tutoquizzer.viewmodels.TopicViewModel;

import java.util.List;

public class TopicsFragment extends Fragment {

    private TopicViewModel tvm;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_topics, container, false);

        tvm = ViewModelProviders.of(this).get(TopicViewModel.class);

        init();

        displayPersonalDetails(GSONHelper.loadData(getContext()));

        initRecyclerView();

        return this.rootView;
    }

    private void displayPersonalDetails(User user){
        nameTV.setText(user.getFirstName() + " " + user.getLastName());
        schoolTV.setText(user.getSchool());
    }

    private void initRecyclerView(){
        recyclerView.setLayoutManager( new LinearLayoutManager(rootView.getContext()));
        recyclerView.setHasFixedSize(true);

        final TutorialAdapter tutorialAdapter = new TutorialAdapter();
        recyclerView.setAdapter(tutorialAdapter);

        this.tvm.getAllTopics().observe(getViewLifecycleOwner(), new Observer<List<Topics>>() {
            @Override
            public void onChanged(List<Topics> topics) {
                // Update RecyclerView
                tutorialAdapter.setTopics(topics);
                topicCount.setText( Integer.toString(topics.size()) );
            }
        });

        adapterListener(tutorialAdapter);
        swipeListener(tutorialAdapter);
    }

    private void swipeListener(final TutorialAdapter adapter){
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {

                new AlertDialog.Builder(getContext()).setTitle("Confirm Delete").setMessage("Are you sure you want to delete topic?")
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            tvm.delete( adapter.getTopicAtPosition( viewHolder.getAdapterPosition() ) );
                            Toast.makeText(getContext(), "Topic Deleted.", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

    private void adapterListener(TutorialAdapter adapter){
        adapter.setOnItemClickListener(new TutorialAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Topics topic) {

                Bundle bundle = new Bundle();

                bundle.putInt       (RouteValues.TOPIC_ID_KEY, topic.getTopicId());
                bundle.putString    (RouteValues.TOPIC_NAME_KEY, topic.getTopic());
                bundle.putString    (RouteValues.DESCRIPTION_NAME_KEY, topic.getMeaning());

                bundle.putInt       (RouteValues.COURSE_ID_KEY, topic.getCourseId());
                bundle.putInt       (RouteValues.SCHOOL_YEAR_ID_KEY, topic.getYearId());
                bundle.putInt       (RouteValues.QUARTER_ID_KEY, topic.getQuarterId());

                bundle.putBoolean   (RouteValues.IS_FOR_DASHBOARD, true);

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                EditTopicFragment editTopicFragment = new EditTopicFragment();
                editTopicFragment.setArguments(bundle);

                fragmentTransaction.replace(R.id.fragment_container, editTopicFragment);
                fragmentTransaction.commit();
            }
        });
    }

    // Components

    private TextView topicCount, nameTV, schoolTV;

    private RecyclerView recyclerView;

    private void init(){
        topicCount = rootView.findViewById(R.id.topicCountTV);

        recyclerView = rootView.findViewById(R.id.recycler_view_topics_fragment);

        nameTV = rootView.findViewById(R.id.tvNameD);
        schoolTV = rootView.findViewById(R.id.tvSchoolD);
    }
}
