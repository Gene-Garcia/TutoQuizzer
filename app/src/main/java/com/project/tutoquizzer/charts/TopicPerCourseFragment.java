package com.project.tutoquizzer.charts;


import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.anychart.enums.LegendLayout;
import com.project.tutoquizzer.R;
import com.project.tutoquizzer.viewmodels.ScoreboardQueryStorageV2;
import com.project.tutoquizzer.viewmodels.ScoreboardViewModel;
import com.project.tutoquizzer.viewmodels.TopicQueryStorage;
import com.project.tutoquizzer.viewmodels.TopicViewModel;

import java.util.ArrayList;
import java.util.List;

public class TopicPerCourseFragment extends Fragment {

    private TopicViewModel tvm;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_topic_course, container, false);

        tvm = ViewModelProviders.of(this).get(TopicViewModel.class);

        init();
        populateChart();

        return this.rootView;
    }

    private void populateChart(){

        this.tvm.getCountPerTopic().observe(getViewLifecycleOwner(), new Observer<List<TopicQueryStorage>>() {
            @Override
            public void onChanged(List<TopicQueryStorage> topicQueryStorages) {

                Pie pie = AnyChart.pie();
                List<DataEntry> data = new ArrayList<>();

                for (int i = 0; i < topicQueryStorages.size(); i++){
                    data.add( new ValueDataEntry(topicQueryStorages.get(i).getCourseId(), topicQueryStorages.get(i).getTCount()));
                }

                pie.data(data);

                pie.title("Topics per Courses");

                pie.labels().position("outside");

                pie.legend().title().enabled(true);
                pie.legend().title()
                        .text("Course Codes")
                        .padding(5d, 5d, 10d, 5d);

                pie.legend()
                        .position("center-bottom")
                        .itemsLayout(LegendLayout.HORIZONTAL)
                        .align(String.valueOf(Paint.Align.CENTER));

                anyChartView.setChart(pie);

            }
        });

    }

    private AnyChartView anyChartView;

    private void init(){
        anyChartView = rootView.findViewById(R.id.any_chart_view);
    }

}
