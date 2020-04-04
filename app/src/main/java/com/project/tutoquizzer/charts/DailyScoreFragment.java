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
import com.project.tutoquizzer.viewmodels.DailyScoreStorage;
import com.project.tutoquizzer.viewmodels.ScoreboardViewModel;

import java.util.ArrayList;
import java.util.List;

public class DailyScoreFragment extends Fragment {

    private ScoreboardViewModel svm;
    private View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_daily_score, container, false);

        svm = ViewModelProviders.of(this).get(ScoreboardViewModel.class);

        init();
        populateChart();

        return this.rootView;
    }

    private void populateChart() {

        svm.getDailyScore().observe(getViewLifecycleOwner(), new Observer<List<DailyScoreStorage>>() {
            @Override
            public void onChanged(List<DailyScoreStorage> dailyScoreStorages) {

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

                cartesian.title("Daily Score");

                cartesian.yAxis(0).title("Scores");
                cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

                List<DataEntry> seriesData = new ArrayList<>();

                for (int i = 0; i < dailyScoreStorages.size(); i++){
                    seriesData.add(new CustomDataEntry(dailyScoreStorages.get(i).getDateAdded(), dailyScoreStorages.get(i).getScore(), dailyScoreStorages.get(i).getItems()));
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

    // Components

    private AnyChartView anyChartView;

    private void init(){
        anyChartView = rootView.findViewById(R.id.any_chart_view);
    }

}
