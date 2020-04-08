package com.project.tutoquizzer.charts;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChartView;
import com.project.tutoquizzer.R;
import com.project.tutoquizzer.viewmodels.ScoreboardViewModel;

public class DailyUsageFragment extends Fragment {

    private ScoreboardViewModel svm;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_daily_usage, container, false);

        svm = ViewModelProviders.of(this).get(ScoreboardViewModel.class);
        init();
        populateChart();

        return rootView;
    }

    private void populateChart(){



    }

    //Component

    private AnyChartView anyChartView;

    private void init(){
        anyChartView = rootView.findViewById(R.id.any_chart_view);
    }

}
