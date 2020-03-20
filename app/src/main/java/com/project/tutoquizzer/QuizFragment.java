package com.project.tutoquizzer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.tutoquizzer.entities.Topics;
import com.project.tutoquizzer.viewmodels.TopicViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuizFragment extends Fragment {

    private TopicViewModel tvm;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_quiz, container, false);

        this.tvm = ViewModelProviders.of(this).get(TopicViewModel.class);

        init();
        configureCall();

        /*Intent intent = getIntent();
        setIds(intent);
        setSelectionDisplay(intent);
        selectedNumberOfQuestions = intent.getIntExtra(AppValues.INTENT_HOLDER_NUMITEMS, 5);*/

        //createQuestion();
        //createPoolOfAnswers();
        //setRadioListeners();
        //buttonListeners();

        return rootView;
    }

    private void configureCall(){
        Bundle bundle = getArguments();

        if (bundle.get(RouteValues.QUIZSELECTEDVALUEACCESS).equals(RouteValues.QUIZSELECTED)){
            // Quiz button selected
            Toast.makeText(getContext(), "Quiz", Toast.LENGTH_SHORT).show();

        }
    }

    private void buttonListeners(){
        btnSaveAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btnSaveAnswer.getText().toString().equals("Start")){
                    btnSaveAnswer.setText("Save Answer");
                }

                if (questionCounter > 0 ) {
                    if (userAnswer.equals(correctAnswers.get(questionCounter-1))){
                        score++;
                    }else {
                        error++;
                    }
                }

                if (questionCounter < selectedNumberOfQuestions){

                    clearTexts();
                    setQuestionAndAnswerView();
                    questionCounter++;

                    btnSaveAnswer.setEnabled(false);
                }else {
                    clearTexts();
                    btnSaveAnswer.setText("Submit");
                    showMessage("TutoQuizzer", "You hae accumulated a score of " + score + " over " + (score+error));

                    // insert to statistics

                    //Intent intent = new Intent(QuizFragment.this, MainFragment.class);
                    //startActivityForResult(intent, AppValues.REQ_CODE_HOME);
                }


            }
        });
    }

    public void showMessage (String title, String msg){
        AlertDialog.Builder bld = new AlertDialog.Builder(rootView.getContext());
        bld.setCancelable(true);
        bld.setTitle(title);
        bld.setMessage(msg);
        bld.show();
    }

    private void setRadioListeners(){
        radBtnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaveAnswer.setEnabled(true);
                userAnswer = radBtnA.getText().toString().trim();
            }
        });

        radBtnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaveAnswer.setEnabled(true);
                userAnswer = radBtnB.getText().toString().trim();
            }
        });

        radBtnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaveAnswer.setEnabled(true);
                userAnswer = radBtnC.getText().toString().trim();
            }
        });

        radBtnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSaveAnswer.setEnabled(true);
                userAnswer = radBtnD.getText().toString().trim();
            }
        });
    }

    private void clearTexts(){
        questionTV.setText("");

        radBtnA.setText("");
        radBtnB.setText("");
        radBtnC.setText("");
        radBtnD.setText("");

        radBtnA.setSelected(false);
        radBtnB.setSelected(false);
        radBtnC.setSelected(false);
        radBtnD.setSelected(false);
    }

    private void setQuestionAndAnswerView(){
        questionTV.setText(
                questions.get(questionCounter)
        );

        List<String> tempChoices = new ArrayList<>();

        Random rand = new Random();

        tempChoices.add(correctAnswers.get(questionCounter));
        for (int i = 1; i < 4; i++){
            tempChoices.add( answersPool.get( rand.nextInt( answersPool.size() ) ) );
        }

        Collections.shuffle(tempChoices);

        radBtnA.setText(tempChoices.get(0));
        radBtnB.setText(tempChoices.get(1));
        radBtnC.setText(tempChoices.get(2));
        radBtnD.setText(tempChoices.get(3));

    }

    private int score = 0;
    private int error = 0;

    private List<String> questions      = new ArrayList<>();
    private List<String> correctAnswers = new ArrayList<>();
    private List<String> answersPool    = new ArrayList<>();

    private String userAnswer = "";

    private int questionCounter             = 0;
    private int selectedNumberOfQuestions   = 0;


    private int selectedCourseId;
    private int selectedSchoolYearId;
    private int selectedQuarterId;

    private String selectedCourse;
    private String selectedSchoolYear;
    private String selectedQuarter;

    private void setIds(Intent temp){
        selectedCourseId        = temp.getIntExtra(AppValues.INTENT_HOLDER_ID_COURSE, -1);
        selectedSchoolYearId    = temp.getIntExtra(AppValues.INTENT_HOLDER_ID_SCHOOLYEAR, -1);
        selectedQuarterId       = temp.getIntExtra(AppValues.INTENT_HOLDER_ID_QUARTER, -1);
    }

    private void setSelectionDisplay(Intent temp){
        String course       = temp.getStringExtra(AppValues.INTENT_HOLDER_COURSE);
        String schoolYear   = temp.getStringExtra(AppValues.INTENT_HOLDER_SCHOOLYEAR);
        String quarter      = temp.getStringExtra(AppValues.INTENT_HOLDER_QUARTER);

        selectionTV.setText( schoolYear + " > " + course.toUpperCase() + " > " + quarter.toUpperCase() );
    }

    // Components

    private TextView questionTV, selectionTV;

    private RadioButton radBtnA, radBtnB, radBtnC, radBtnD;

    private Button btnSaveAnswer;

    private RadioGroup radGroup;

    // For Navigation
    private BottomNavigationView bottomNavigationView;

    // End Components

    private void init(){

        questionTV  = rootView.findViewById(R.id.questionTextViewQuizAct);
        selectionTV = rootView.findViewById(R.id.displaySelectionTextViewQuizAct);

        radGroup = rootView.findViewById(R.id.radGroupQuizAct);

        radBtnA = rootView.findViewById(R.id.rBtnAQuizAct);
        radBtnB = rootView.findViewById(R.id.rBtnBQuizAct);
        radBtnC = rootView.findViewById(R.id.rBtnCQuizAct);
        radBtnD = rootView.findViewById(R.id.rBtnDQuizAct);

        btnSaveAnswer = rootView.findViewById(R.id.saveAnswerBtnQuizAct);

        bottomNavigationView = rootView.findViewById(R.id.menuAct);

    }

    private void createQuestion(){
        Observer<List<Topics>> observer = new Observer<List<Topics>>() {
            @Override
            public void onChanged(@Nullable List<Topics> topics) {
                if (selectedNumberOfQuestions >= topics.size()){
                    selectedNumberOfQuestions = topics.size();
                }
                Toast.makeText(getContext(), String.valueOf(selectedNumberOfQuestions), Toast.LENGTH_SHORT).show();
                Collections.shuffle(topics);

                for (int i = 0; i < selectedNumberOfQuestions; i++){
                    questions.add(topics.get(i).getMeaning());

                    correctAnswers.add(topics.get(i).getTopic());
                }
            }
        };
        tvm.getSelectedTopics(selectedCourseId, selectedSchoolYearId, selectedQuarterId).observe(getViewLifecycleOwner(), observer);

    }

    private void createPoolOfAnswers(){

        final Random random = new Random();
        Observer<List<Topics>> observer = new Observer<List<Topics>>() {
            @Override
            public void onChanged(@Nullable List<Topics> topics) {

                Collections.shuffle(topics);

                for (int i = 0; i < topics.size(); i++){
                    answersPool.add(topics.get(i).getTopic());
                }
            }
        };
        tvm.getAllTopics().observe(getViewLifecycleOwner(), observer);

    }
}
