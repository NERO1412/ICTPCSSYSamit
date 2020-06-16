package com.samit1.sysamit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuMainActivity extends AppCompatActivity {

    Button mRiBtn;
    Button mA2iBtn;
    Button mCmBtn;
    Button mAdBtn;
    Button mNtfBtn;
    Button mSmBtn;
    Button mGrBtn;
    Button mPfBtn;

    Button mItBtn;
    Button mAdmBtn;
    Button mStBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);

        mRiBtn = findViewById(R.id.riBtn); //Rekod Inventori button
        mA2iBtn = findViewById(R.id.a2iBtn);
        mCmBtn = findViewById(R.id.cmBtn);  //Complaint Management button
        mAdBtn = findViewById(R.id.mcBtn); //Make Complaint button
        mNtfBtn = findViewById(R.id.nBtn);  //Notification button
        mSmBtn = findViewById(R.id.smBtn);  //System Management button
        mGrBtn = findViewById(R.id.grBtn);  //Generate Report button
        mPfBtn = findViewById(R.id.mlBtn); //Back to Profile button

        mItBtn = findViewById(R.id.ittBtn);
        mAdmBtn = findViewById(R.id.adBtn);
        mStBtn = findViewById(R.id.stBtn);

        mItBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), dashboardActivity.class));
            }
        });

        mAdmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), dashboardaActivity.class));
            }
        });

        mStBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), dashboardsActivity.class));
            }
        });




        mRiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListitActivity.class));
            }
        });

        mA2iBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InventoryMgmtActivity.class));
            }
        });

        mCmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AduanMainActivity.class));
            }
        });

        mAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AduanMainActivity.class));
            }
        });

        mNtfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
            }
        });

        mSmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), sysMgmntActivity.class));
            }
        });

        mGrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), reportActivity.class));
            }
        });

        mPfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

}
