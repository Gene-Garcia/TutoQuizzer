package com.project.tutoquizzer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.tutoquizzer.entities.Topics;
import com.project.tutoquizzer.viewmodels.TopicViewModel;

public class EditTopicFragment extends Fragment {

    private TopicViewModel tvm;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_edit_topic_, container, false);

        this.tvm = ViewModelProviders.of(this).get(TopicViewModel.class);

        Bundle bundle = getArguments();

        setSelectedCriteria(bundle);

        topics = new Topics(
                bundle.getString(RouteValues.TOPIC_NAME_KEY),
                bundle.getString(RouteValues.DESCRIPTION_NAME_KEY),
                selectedQuarterId,
                selectedCourseId,
                selectedSchoolYearId );

        topics.setTopicId(bundle.getInt(RouteValues.TOPIC_ID_KEY) );

        init();

        displayData();

        buttonListeners();

        return this.rootView;
    }

    private void displayData() {

        topic.setText( topics.getTopic() );
        topicDesc.setText( topics.getMeaning() );

    }

    private void setSelectedCriteria(Bundle bundle) {

        selectedCourse       = bundle.getString(RouteValues.COURSE_NAME_KEY);
        selectedSchoolYear   = bundle.getString(RouteValues.SCHOOL_YEAR_NAME_KEY);
        selectedQuarter      = bundle.getString(RouteValues.QUARTER_NAME_KEY);

        selectedCourseId        = bundle.getInt(RouteValues.COURSE_ID_KEY);
        selectedSchoolYearId    = bundle.getInt(RouteValues.SCHOOL_YEAR_ID_KEY);
        selectedQuarterId       = bundle.getInt(RouteValues.QUARTER_ID_KEY);

    }

    private String selectedCourse;
    private String selectedSchoolYear;
    private String selectedQuarter;

    private int selectedCourseId;
    private int selectedSchoolYearId;
    private int selectedQuarterId;

    private Topics topics;

    private void buttonListeners(){
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                topics.setTopic( topic.getText().toString().trim() );
                topics.setMeaning( topicDesc.getText().toString().trim() );

                tvm.update(topics);

                Toast.makeText(getContext(), "Topic Successfully Edited.", Toast.LENGTH_SHORT).show();

                returnToSelect();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext()).setTitle("Confirm Delete").setMessage("Are you sure you want to delete topic?")
                    .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            tvm.delete(topics);

                            Toast.makeText(getContext(), "Topic Deleted.", Toast.LENGTH_SHORT).show();

                            returnToSelect();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert).show();
            }
        });
    }

    private void returnToSelect(){
        Bundle bundle = new Bundle();

        bundle.putString(RouteValues.SELECT_COURSE_RE_ROUTE, RouteValues.TUTORIAL_SELECTED);

        bundle.putString    (RouteValues.COURSE_NAME_KEY, selectedCourse);
        bundle.putString    (RouteValues.QUARTER_NAME_KEY, selectedQuarter);
        bundle.putString    (RouteValues.SCHOOL_YEAR_NAME_KEY, selectedSchoolYear);

        bundle.putInt       (RouteValues.COURSE_ID_KEY, selectedCourseId);
        bundle.putInt       (RouteValues.QUARTER_ID_KEY, selectedQuarterId);
        bundle.putInt       (RouteValues.SCHOOL_YEAR_ID_KEY, selectedSchoolYearId);

        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        TutorialFragment tutorialFragment = new TutorialFragment();
        tutorialFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.fragment_container, tutorialFragment);
        fragmentTransaction.commit();
    }

    // Compenents

    private EditText topic, topicDesc;

    private Button saveBtn, deleteBtn;

    // End

    private void init(){
        topic       = rootView.findViewById(R.id.topicEditTopicEditText);
        topicDesc   = rootView.findViewById(R.id.descriptionEditTopicEditText);

        saveBtn     = rootView.findViewById(R.id.saveEditBtnEditTopicAct);
        deleteBtn   = rootView.findViewById(R.id.deleteTopicBtnEditTopicAct);
    }

}
