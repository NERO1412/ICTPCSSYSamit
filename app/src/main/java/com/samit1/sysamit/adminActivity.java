package com.samit1.sysamit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class adminActivity extends AppCompatActivity {

    Button mSmBtn;
    Button mGrBtn;
    Button mMmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        mSmBtn = findViewById(R.id.smBtn);  //System Management button
        mSmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), sysMgmntActivity.class));
            }
        });

        mGrBtn = findViewById(R.id.grBtn);  //Generate Report button
        mGrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), reportActivity.class));
            }
        });

        mMmBtn = findViewById(R.id.mmBtn); //Back to main menu
        mMmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MenuMainActivity.class));
            }
        });
    }
}
