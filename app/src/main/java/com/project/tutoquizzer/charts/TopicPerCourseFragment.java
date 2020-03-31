package com.project.tutoquizzer.charts;


import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.util.ArrayList;
import java.util.List;

public class TopicPerCourseFragment extends Fragment {


    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_topic_course, container, false);

        init();
        populateChart();

        return this.rootView;
    }

    private void populateChart(){
        String[] courses = {"IS100", "IT101-1", "CS100", "MGT101"};
        int[] count = {20, 15, 10, 30, 50};

        Pie pie = AnyChart.pie();
        List<DataEntry> data = new ArrayList<>();

        for(int i = 0; i < courses.length; i++){

            data.add( new ValueDataEntry(courses[i], count[i]));

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

    private AnyChartView anyChartView;

    private void init(){
        anyChartView = rootView.findViewById(R.id.any_chart_view);
    }

}
