package com.samit1.sysamit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class sysMgmntActivity extends AppCompatActivity {

    Button mAdUsr;
    Button mStC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_mgmnt);

        mAdUsr = findViewById(R.id.auBtn);
        mAdUsr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),addUserActivity.class));
            }
        });

        //final Button button = (Button) findViewById(R.id.stBtn);
        //button.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), AduanMainActivity.class));
            //}
        //});

        //mStC.findViewById(R.id.stBtn);
        //mStC.setOnClickListener(new View.OnClickListener() {
           //@Override
            //public void onClick(View v) {
                //startActivity(new Intent(getApplicationContext(), AduanMainActivity.class));
            //}
       // });

    }
}
