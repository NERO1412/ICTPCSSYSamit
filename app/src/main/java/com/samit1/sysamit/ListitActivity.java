package com.samit1.sysamit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.util.CustomClassMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListitActivity extends AppCompatActivity {

    List<Model> modelList = new ArrayList<>();
    RecyclerView mRecyclerView;
    //layout manager for recyclerview
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton mAddBtn;

    //firestore instance
    FirebaseFirestore db;

    CustomAdapter adapter;

    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listit);

        //actionbar & its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("INVENTORY ITEMS LIST");

        //init firestore
        db = FirebaseFirestore.getInstance();

        //initialize views
        mRecyclerView = findViewById(R.id.recycler_view);
        mAddBtn = findViewById(R.id.addBtn);

        //set recycler view properties
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        pd = new ProgressDialog(this);

        //show data in recyler view
        showData();

        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(ListitActivity.this, MainitActivity.class));
                startActivity(new Intent(ListitActivity.this, InventoryMgmtActivity.class));
                finish();
            }
        });
    }

    private void showData() {
        //set title of progress dialog
        pd.setTitle("Loading Data...");
        //show progress dialog
        pd.show();

        db.collection("ICTrecords")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        modelList.clear();
                        //called when data is retrieved
                        pd.dismiss();
                        //show data
                        for (DocumentSnapshot doc: task.getResult()){
                            Model model = new Model(
                                    doc.getString("Item ID"),
                                    doc.getString("Item Type"),
                                    doc.getString("Item Model"),
                                    doc.getString("Item SN"),
                                    doc.getString("Item Department"),
                                    doc.getString("Item Used By"));
                            modelList.add(model);
                        }
                        //adapter
                        adapter = new CustomAdapter(ListitActivity.this, modelList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(adapter);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

    public void deleteData(int index){
        //set title of progress dialog
        pd.setTitle("Deleting Data...");
        //show progress dialog
        pd.show();

        db.collection("ICTrecords").document(modelList.get(index).getItemID())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //called when deleted successfully
                        Toast.makeText(ListitActivity.this, "Deleted...", Toast.LENGTH_SHORT).show();
                        //update data
                        showData();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //called when there is any error
                        //get and show error message
                        pd.dismiss();
                        Toast.makeText(ListitActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void searchData(String s){
        //set title od progress bar
        pd.setTitle("Searching...");
        //show progress Bar
        pd.show();

        db.collection("ICTrecords").whereEqualTo("search", s.toLowerCase())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //called when searching is succeedded
                        modelList.clear();
                        pd.dismiss();
                        //show data
                        for (DocumentSnapshot doc: Objects.requireNonNull(task.getResult())){
                            Model model = new Model(
                                    doc.getString("Item ID"),
                                    doc.getString("Item Type"),
                                    doc.getString("Item Model"),
                                    doc.getString("Item SN"),
                                    doc.getString("Item Department"),
                                    doc.getString("Item Used By"));
                            modelList.add(model);
                        }
                        //adapter
                        adapter = new CustomAdapter(ListitActivity.this, modelList);
                        //set adapter to recyclerview
                        mRecyclerView.setAdapter(adapter);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //when there is any error
                        pd.dismiss();
                        //get and show error message
                        Toast.makeText(ListitActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating menu_main.xml
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //Search View
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //called when we press search button
                searchData(s); //function call with string entered in searchview as parameter
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //called as and we type even a single letter
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //handler other menu item clicks here
        if (item.getItemId() == R.id.action_settings){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
