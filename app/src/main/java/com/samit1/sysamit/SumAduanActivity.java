package com.samit1.sysamit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.net.DatagramPacket;

public class SumAduanActivity extends AppCompatActivity {

    Spinner monthSpinner;
    EditText hardware, software;
    Button btn_insert;

    FirebaseDatabase database;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_aduan);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("MONTHLY COMPLAINT");

        hardware = (EditText) findViewById(R.id.x_Value);
        software = (EditText) findViewById(R.id.y_Value);
        btn_insert = (Button) findViewById(R.id.btn_insert);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Monthly Complaint");

        monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        ArrayAdapter <String> monthAdapter = new ArrayAdapter<>(SumAduanActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Month));
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        setListeners();

    }

    private void setListeners() {
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = reference.push().getKey();
                String month = monthSpinner.getSelectedItem().toString().trim();
                id = month;

                int x = Integer.parseInt(hardware.getText().toString());
                int y = Integer.parseInt(software.getText().toString());

                PointValue pointValue = new PointValue(x,y);

                reference.child(id).setValue(pointValue);

            }
        });

    }
}
