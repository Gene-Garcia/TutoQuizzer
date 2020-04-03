package com.project.tutoquizzer.charts;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.project.tutoquizzer.R;
import com.project.tutoquizzer.entities.Courses;
import com.project.tutoquizzer.entities.Scoreboard;
import com.project.tutoquizzer.viewmodels.ScoreboardQueryStorage;
import com.project.tutoquizzer.viewmodels.ScoreboardViewModel;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class ScoreAccumulationFragment extends Fragment {

    private ScoreboardViewModel svm;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_score_accumulation, container, false);

        svm = ViewModelProviders.of(this).get(ScoreboardViewModel.class);

        init();
        populateSpinner();

        return rootView;
    }

    private void populateChart(final String courseCode){
        this.svm.getAvgByCourse(courseCode).observe(getViewLifecycleOwner(), new Observer<List<ScoreboardQueryStorage>>() {
            @Override
            public void onChanged(List<ScoreboardQueryStorage> scoreboardQueryStorages) {
                anyChartView = rootView.findViewById(R.id.any_chart_view);

                Cartesian cartesian = AnyChart.line();

                cartesian.animation(true);

                cartesian.padding(10d, 20d, 5d, 20d);

                cartesian.crosshair().enabled(true);
                cartesian.crosshair()
                        .yLabel(true)
                        // TODO ystroke
                        .yStroke((Stroke) null, null, null, (String) null, (String) null);

                cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

                cartesian.title("Score Accumulation in " + courseCode);

                cartesian.yAxis(0).title("Scores " + courseCode);
                cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

                List<DataEntry> seriesData = new ArrayList<>();

                for (int i = 0; i < scoreboardQueryStorages.size(); i++){
                    seriesData.add(new CustomDataEntry(scoreboardQueryStorages.get(i).getDateAdded(), scoreboardQueryStorages.get(i).getScore(), scoreboardQueryStorages.get(i).getItems()));
                }

                Set set = Set.instantiate();
                set.data(seriesData);
                Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
                Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");

                Line series1 = cartesian.line(series1Mapping);
                series1.name("Score");
                series1.hovered().markers().enabled(true);
                series1.hovered().markers()
                        .type(MarkerType.CIRCLE)
                        .size(4d);
                series1.tooltip()
                        .position("right")
                        .anchor(Anchor.LEFT_CENTER)
                        .offsetX(5d)
                        .offsetY(5d);

                Line series2 = cartesian.line(series2Mapping);
                series2.name("Number of Items");
                series2.hovered().markers().enabled(true);
                series2.hovered().markers()
                        .type(MarkerType.CIRCLE)
                        .size(4d);
                series2.tooltip()
                        .position("right")
                        .anchor(Anchor.LEFT_CENTER)
                        .offsetX(5d)
                        .offsetY(5d);

                cartesian.legend().enabled(true);
                cartesian.legend().fontSize(13d);
                cartesian.legend().padding(0d, 0d, 10d, 0d);

                anyChartView.setChart(cartesian);

            }
        });
    }

    // Data Entry Class

    private class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2) {
            super(x, value);
            setValue("value2", value2);
        }

    }

    private void populateSpinner(){

        spinnerListener();

        this.svm.getAvgByCourse("IS100").observe(getViewLifecycleOwner(), new Observer<List<ScoreboardQueryStorage>>() {
            @Override
            public void onChanged(List<ScoreboardQueryStorage> scoreboardQueryStorages) {

                for (int i = 0; i < scoreboardQueryStorages.size(); i++){
                    Toast.makeText(getContext(), "Score" + scoreboardQueryStorages.get(i).getScore() + " Items " + scoreboardQueryStorages.get(i).getItems() + " Date " + scoreboardQueryStorages.get(i).getDateAdded(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        final List<String> courses = new ArrayList<String>();
        courses.add("Select item");
        Observer<List<String>> observer = new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> coursesTemp) {
                for (int i = 0; i < coursesTemp.size(); i++){
                    courses.add( coursesTemp.get(i)); //course code
                }
            }
        };
        svm.getRecordedCourses().observe(getViewLifecycleOwner(), observer);

        ArrayAdapter<String> coursesAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_spinner_item, courses);
        coursesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        courseSpinner.setAdapter(coursesAdapter);

    }

    private void spinnerListener(){

        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String courseCode = parent.getItemAtPosition(position).toString();

                if (courseCode != "Select item"){
                    Toast.makeText(getContext(), courseCode, Toast.LENGTH_SHORT).show();

                    populateChart(courseCode);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    // Components

    private AnyChartView anyChartView;

    private Spinner courseSpinner;

    private void init(){

        courseSpinner = rootView.findViewById(R.id.courseSpnrChartFrg);
    }

}
