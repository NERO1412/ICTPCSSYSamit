package com.samit1.sysamit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class itTechActivity extends AppCompatActivity {

    Button xmmBtn1;
    Button xriBtn1;
    Button xcmBtn1;
    Button ma2iBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_it_tech);

        xmmBtn1 = findViewById(R.id.mmBtn1);
        xmmBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(itTechActivity.this, MenuMainActivity.class);
                startActivity(intent);
            }
        });

        xriBtn1 = findViewById(R.id.riBtn1);
        xriBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(itTechActivity.this, dashboardActivity.class);
                startActivity(x);
            }
        });

        xcmBtn1 = findViewById(R.id.cmBtn1);
        xcmBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent y = new Intent(itTechActivity.this, MainActivity.class);
                startActivity(y);
            }
        });

        ma2iBtn2 = findViewById(R.id.a2iBtn2);
        ma2iBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z = new Intent(itTechActivity.this, InventoryMgmtActivity.class);
                startActivity(z);
            }
        });

    }
}
