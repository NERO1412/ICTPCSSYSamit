package com.samit1.sysamit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OctReport extends AppCompatActivity {

    AnyChartView anyChartView;
    Button printBtn;
    String itemType[] = {"HARDWARE", "SOFTWARE"};
    int totalCase[] = {0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oct_report);

        final DatabaseReference checkFBvalue = FirebaseDatabase.getInstance()
                .getReference().child("october");


        // DatabaseReference checkFBvalue = FirebaseDatabase.getInstance()
        // .getReference("/Monthly Complaint/JANUARY");

        checkFBvalue.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int xHardware = (int) dataSnapshot.child("hardware").getChildrenCount();
                int xSoftware = (int) dataSnapshot.child("software").getChildrenCount();

                //checkFBvalue.child("harware").setValue(xHardware);
                //checkFBvalue.child("software").setValue(xSoftware);

                int totalCase[] = {xHardware, xSoftware};

                anyChartView = findViewById(R.id.any_chart_january);

                Pie pie = AnyChart.pie();
                List<DataEntry> dataentries = new ArrayList<>();

                for (int i = 0; i<itemType.length; i++){
                    dataentries.add(new ValueDataEntry(itemType[i], totalCase[i]));

                }

                pie.data(dataentries);
                anyChartView.setChart(pie);
            }

            //@Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        printBtn = findViewById(R.id.preportBtn);

    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating menu_main.xml
        getMenuInflater().inflate(R.menu.menu_return, menu);
        //Search View
        MenuItem item = menu.findItem(R.id.action_log);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //handler other menu item clicks here
        if (item.getItemId() == R.id.action_return){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}


