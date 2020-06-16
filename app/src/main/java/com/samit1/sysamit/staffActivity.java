package com.samit1.sysamit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class staffActivity extends AppCompatActivity {

    Button mAdBtn;
    Button mNtfBtn;
    Button mMmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        mAdBtn = findViewById(R.id.mcBtn); //Make Complaint button
        mAdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AduanMainActivity.class));
            }
        });

        mNtfBtn = findViewById(R.id.nBtn); //Notification button
        mNtfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
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
