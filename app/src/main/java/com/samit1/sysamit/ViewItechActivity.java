package com.samit1.sysamit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ViewItechActivity extends AppCompatActivity {

    FirebaseFirestore db;
    RecyclerView mRecyclerViews;
    ArrayList<User> userArrayList;
    MyRecyclerVIewAdapters adapter;
    Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_itech);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("View IT Technician List");

        userArrayList = new ArrayList<>();
        setUpRecyclerView();
        setUpFireBase();
        //addTestDataToFirebase();
        loadDataFromFirebase();
        setUpUpdateButton();
    }

    private void setUpUpdateButton() {
        updateButton = findViewById(R.id.mUpdateBtn);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDataFromFirebase();
            }
        });

    }

    public void loadDataFromFirebase() {

        if (userArrayList.size() > 0)
            userArrayList.clear();

        db.collection("ITechusers")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot querySnashot: task.getResult())
                        {
                            User user = new User(
                                    querySnashot.getId(),
                                    querySnashot.getString("fName"),
                                    querySnashot.getString("email"));
                            userArrayList.add(user);
                        }
                        adapter = new MyRecyclerVIewAdapters(ViewItechActivity.this, userArrayList);
                        mRecyclerViews.setAdapter(adapter);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ViewItechActivity.this, "Problem ~~1~~", Toast.LENGTH_SHORT).show();
                        Log.w("~~~~~1~~~~~~~", e.getMessage());
                    }
                });

    }

    //private void addTestDataToFirebase() {
    //    Random random = new Random();

    //   for (int i = 0; i < 2; i++)
    //  {
    //     Map<String, String> dataMap = new HashMap<>();
    //     dataMap.put("name", "try name" + random.nextInt(50));
    //    dataMap.put("status", "try status" + random.nextInt(50));

    //    db.collection("Users")
    //           .add(dataMap)
    //           .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
    //              @Override
    //              public void onSuccess(DocumentReference documentReference) {
    //                 Toast.makeText(ViewUserActivity.this, "Added Test Data", Toast.LENGTH_SHORT).show();
    //            }
    //         });
    // }


    // }

    private void setUpFireBase() {
        db = FirebaseFirestore.getInstance();
    }

    private void setUpRecyclerView() {
        mRecyclerViews = findViewById(R.id.mRecyclerView);
        mRecyclerViews.setHasFixedSize(true);
        mRecyclerViews.setLayoutManager(new LinearLayoutManager(this));
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

