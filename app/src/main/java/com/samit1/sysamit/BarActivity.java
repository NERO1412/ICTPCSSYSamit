package com.samit1.sysamit;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.google.firebase.firestore.CollectionReference;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

public class BarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);

        GraphView graph = (GraphView) findViewById(R.id.graph);

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(getDataPoint());
        graph.addSeries(series);

        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);

        series.setSpacing(10);

        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint dataPoint) {

                if(dataPoint.getY() > 6)
                    return Color.GREEN;
                else if(dataPoint.getY() > 4)
                    return Color.BLUE;
                else
                    return (Color.rgb(149,46,25));
            }
        });

    }

    private DataPoint[] getDataPoint() {
        DataPoint[] dp = new DataPoint[]
                {
                        new DataPoint(0,1),
                        new DataPoint(1,4),
                        new DataPoint(2,5),
                        new DataPoint(3,1),
                        new DataPoint(4,0),
                        new DataPoint(5,3),
                        new DataPoint(6,1),
                        new DataPoint(7,7),
                        new DataPoint(8,4),
                };
        return dp;
    }
}
