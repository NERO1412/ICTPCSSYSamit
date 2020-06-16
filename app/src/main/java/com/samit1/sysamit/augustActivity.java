package com.samit1.sysamit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class augustActivity extends AppCompatActivity {

    //Spinner monthSpinner;
    EditText hardware, software;
    Button btn_insert;

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_august);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("AUGUST COMPLAINT");

        hardware = (EditText) findViewById(R.id.x_Value);
        software = (EditText) findViewById(R.id.y_Value);
        btn_insert = (Button) findViewById(R.id.btn_insert);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("august");

        //monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        // ArrayAdapter <String> monthAdapter = new ArrayAdapter<>(januaryActivity.this,
        //        android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Month));
        // monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //monthSpinner.setAdapter(monthAdapter);

        setListeners();

    }

    private void setListeners() {
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = reference.push().getKey();
                //String month = monthSpinner.getSelectedItem().toString().trim();
                //id = month;

                int x = Integer.parseInt(hardware.getText().toString());
                int y = Integer.parseInt(software.getText().toString());

                //PointValue pointValue = new PointValue(x,y);

                reference.child("hardware").child(id).setValue(x);
                reference.child("software").child(id).setValue(y);

            }
        });

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

