package com.samit1.sysamit;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.barchart.ondemand.BarchartOnDemandClient;

import java.util.ArrayList;
import java.util.List;

public class reportActivity extends AppCompatActivity {

    AnyChartView anyChartView;

    String[] months = {"Jan", "Feb", "Mar"};
    int[] reports = {500, 800, 2000};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        anyChartView = findViewById(R.id.any_chart_view);

        setupChart();

    }

    private void setupChart() {

        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        for (int i=0; i<months.length; i++ ){
            dataEntries.add(new ValueDataEntry(months[i], reports[i]));
        }

        pie.data(dataEntries);
        anyChartView.setChart(pie);


    }
}
