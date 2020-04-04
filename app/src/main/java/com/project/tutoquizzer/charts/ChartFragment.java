package com.project.tutoquizzer.charts;


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

import com.project.tutoquizzer.R;

public class ChartFragment extends Fragment {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_chart, container, false);

        init();
        buttonListener();

        return this.rootView;
    }

    private void buttonListener(){
        scoreAccuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reRoute( new ScoreAccumulationFragment());
            }
        });

        scoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reRoute( new DailyScoreFragment());
            }
        });

        dailyUsageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void reRoute(Fragment fragment){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_chart_container, fragment);
        fragmentTransaction.commit();
    }

    private Button scoreAccuBtn, scoreBtn, dailyUsageBtn;

    private void init(){
        scoreAccuBtn    = rootView.findViewById(R.id.scoreAccumulationChartBtn);
        scoreBtn        = rootView.findViewById(R.id.dailyScoreChartBtn);
        dailyUsageBtn = rootView.findViewById(R.id.dailyUsageChartBtn);
    }

}
