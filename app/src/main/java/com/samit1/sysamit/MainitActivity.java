package com.samit1.sysamit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainitActivity extends AppCompatActivity {

    //Views
    EditText mTitleIt, mDescriptionIt;
    Button mSaveBtn, mListBtn;

    //progress dialog
    ProgressDialog pd;

    //Firestore
    FirebaseFirestore db;

    String pId, pTitle, pDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainit);

        //actionbar & its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Data");

        //initialize view with xml
        mTitleIt = findViewById(R.id.titleIt);
        mDescriptionIt = findViewById(R.id.descriptionIt);
        mSaveBtn = findViewById(R.id.saveBtn);
        mListBtn = findViewById(R.id.listBtn);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            //Update data
            actionBar.setTitle("Update Data");
            mSaveBtn.setText("Update");
            //get data
            pId = bundle.getString("pId");
            pTitle = bundle.getString("pTitle");
            pDescription = bundle.getString("pDescription");
            //set Data
            mTitleIt.setText(pTitle);
            mDescriptionIt.setText(pDescription);
        }
        else {
            //new Data
            actionBar.setTitle("Add Data");
            mSaveBtn.setText("Save");
        }

        //progress dialog
        pd = new ProgressDialog(this);

        //Firestore
        db = FirebaseFirestore.getInstance();

        //click button to upload data
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null){
                    //updating
                    //input data
                    String id = pId;
                    String title = mTitleIt.getText().toString().trim();
                    String description = mDescriptionIt.getText().toString().trim();
                    //function to upload data
                    updateData(id, title, description);

                }
                else {
                    //adding new
                    //input data
                    String title = mTitleIt.getText().toString().trim();
                    String description = mDescriptionIt.getText().toString().trim();
                    //function to upload data
                    uploadData(title, description);
                }

            }
        });

        //click btn to start ListitActrivity
        mListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainitActivity.this, ListitActivity.class));
                finish();
            }
        });

    }

    private void updateData(String id, String title, String description) {
        //set title od progress bar
        pd.setTitle("Updating Data...");
        //show progress Bar
        pd.show();

        db.collection("Records").document(id)
                .update("title", title,
                        "search", title.toLowerCase(),
                        "description", description)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //called when updated succesfully
                        pd.dismiss();
                        Toast.makeText(MainitActivity.this, "Updated...", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when updated succesfully
                        pd.dismiss();
                        Toast.makeText(MainitActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void uploadData(String title, String description) {
        //set title od progress bar
        pd.setTitle("Adding Data to Firestore");
        //show progress Bar
        pd.show();
        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);
        doc.put("title", title);
        doc.put("search", title.toLowerCase());
        doc.put("description", description);

        db.collection("Records").document(id).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //this will be called when data is added successfully

                        pd.dismiss();
                        Toast.makeText(MainitActivity.this, "Uploaded...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //error uploading
                        pd.dismiss();
                        Toast.makeText(MainitActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



}
