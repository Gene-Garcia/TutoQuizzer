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
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.project.tutoquizzer.R;
import com.project.tutoquizzer.viewmodels.DailyUsageStorage;
import com.project.tutoquizzer.viewmodels.ScoreboardViewModel;

import java.util.ArrayList;
import java.util.List;

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

        this.svm.getDailyUsage().observe(getViewLifecycleOwner(), new Observer<List<DailyUsageStorage>>() {
            @Override
            public void onChanged(List<DailyUsageStorage> dailyUsageStorages) {

                Cartesian cartesian = AnyChart.column();

                List<DataEntry> data = new ArrayList<>();

                for (int i = 0; i < dailyUsageStorages.size(); i++){
                    data.add(new ValueDataEntry(dailyUsageStorages.get(i).getDateAdded(), dailyUsageStorages.get(i).getNumber()));
                }

                Column column = cartesian.column(data);

                column.tooltip()
                        .titleFormat("{%X}")
                        .position(Position.CENTER_BOTTOM)
                        .anchor(Anchor.CENTER_BOTTOM)
                        .offsetX(0d)
                        .offsetY(5d)
                        .format("Usage Count - {%Value}{groupsSeparator: }");

                cartesian.animation(true);
                cartesian.title("Daily Quiz Usage");

                cartesian.yScale().minimum(0d);

                cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

                cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
                cartesian.interactivity().hoverMode(HoverMode.BY_X);

                cartesian.xAxis(0).title("Date");
                cartesian.yAxis(0).title("Counter");

                anyChartView.setChart(cartesian);

            }
        });

    }

    //Component

    private AnyChartView anyChartView;

    private void init(){
        anyChartView = rootView.findViewById(R.id.any_chart_view);
    }

}
