package com.samit1.sysamit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class techActivity extends AppCompatActivity {

    Button mRib;
    Button mCmb;
    Button maib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tech);

        mRib.findViewById(R.id.riBtn1);
        mCmb.findViewById(R.id.cmBtn1);
        maib.findViewById(R.id.a2iBtn2);

        mRib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ListitActivity.class));
            }
        });

        mCmb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AduanMainActivity.class));
            }
        });

        maib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InventoryMgmtActivity.class));
            }
        });

    }
}
