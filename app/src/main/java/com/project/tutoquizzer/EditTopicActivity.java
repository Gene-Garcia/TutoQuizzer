package com.project.tutoquizzer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.tutoquizzer.entities.Topics;
import com.project.tutoquizzer.viewmodels.TopicViewModel;

public class EditTopicActivity extends AppCompatActivity {

    private TopicViewModel tvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_topic_activity);

        setTitle("TutoQuizzer - Edit Topic");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.return_arrow);

        this.tvm = ViewModelProviders.of(this).get(TopicViewModel.class);

        Intent intent = getIntent();

        setSelectedCriteria(intent);

        topics = new Topics( intent.getStringExtra(AppValues.INTENT_HOLDER_TOPIC), intent.getStringExtra(AppValues.INTENT_HOLDER_MEANING), selectedQuarterId, selectedCourseId, selectedSchoolYearId );
        topics.setTopicId(intent.getIntExtra(AppValues.INTENT_HOLDER_ID_TOPIC, -1) );

        init();

        displayData();

        buttonListeners();
    }

    private void displayData() {

        topic.setText( topics.getTopic() );
        topicDesc.setText( topics.getMeaning() );

    }

    private void setSelectedCriteria(Intent intent) {

        selectedCourse = intent.getStringExtra(AppValues.INTENT_HOLDER_COURSE);
        selectedSchoolYear = intent.getStringExtra(AppValues.INTENT_HOLDER_SCHOOLYEAR);
        selectedQuarter = intent.getStringExtra(AppValues.INTENT_HOLDER_QUARTER);

        selectedCourseId = intent.getIntExtra(AppValues.INTENT_HOLDER_ID_COURSE, -1);
        selectedSchoolYearId = intent.getIntExtra(AppValues.INTENT_HOLDER_ID_SCHOOLYEAR, -1);
        selectedQuarterId = intent.getIntExtra(AppValues.INTENT_HOLDER_ID_QUARTER, -1);

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

                Intent intent = new Intent(EditTopicActivity.this, TutorialActivity.class);

                intent.putExtra(AppValues.INTENT_HOLDER_COURSE,     selectedCourse      );
                intent.putExtra(AppValues.INTENT_HOLDER_SCHOOLYEAR, selectedSchoolYear  );
                intent.putExtra(AppValues.INTENT_HOLDER_QUARTER,    selectedQuarter     );

                intent.putExtra(AppValues.INTENT_HOLDER_ID_COURSE,      selectedCourseId    );
                intent.putExtra(AppValues.INTENT_HOLDER_ID_SCHOOLYEAR,  selectedSchoolYearId);
                intent.putExtra(AppValues.INTENT_HOLDER_ID_QUARTER,     selectedQuarterId   );

                Toast.makeText(EditTopicActivity.this, "Topic Successfully Edited.", Toast.LENGTH_SHORT).show();
                startActivityForResult(intent, AppValues.REQ_CODE_TUTORIAL);

            }
        });
    }

    // Compenents

    private EditText topic, topicDesc;

    private Button saveBtn;

    // End

    private void init(){
        topic = findViewById(R.id.topicEditTopicEditText);
        topicDesc = findViewById(R.id.descriptionEditTopicEditText);

        saveBtn = findViewById(R.id.saveEditBtnEditTopicAct);
    }

}
