package com.samit1.sysamit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class frontPage extends AppCompatActivity {

    Button mStaffBtn;
    Button mAdminBtn;
    Button mItTechBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        mStaffBtn = findViewById(R.id.stafBtn);

        mStaffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });

        mAdminBtn = findViewById(R.id.admBtn);

        mAdminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(frontPage.this, LoginAdmin.class);
                startActivity(intent);
            }
        });

        mItTechBtn = findViewById(R.id.itTechBtn);

        mItTechBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginItech.class));
            }
        });

    }
}
