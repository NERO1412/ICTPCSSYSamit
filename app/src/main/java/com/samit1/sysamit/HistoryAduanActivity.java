package com.samit1.sysamit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
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

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoryAduanActivity extends AppCompatActivity {

    //List<Model> modelList = new ArrayList<>();
    List<ModelInv> modelInvList = new ArrayList<>();
    RecyclerView mRecyclerView;
    //layout manager for recyclerview
    RecyclerView.LayoutManager layoutManager;

    FloatingActionButton mAddBtn;

    //firestore instance
    FirebaseFirestore db;

    CustomAdapterHis adapter;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_aduan);

        //actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("COMPLAINT LOG");

        //init firestore
        db = FirebaseFirestore.getInstance();

        //initialize views
        mRecyclerView = findViewById(R.id.recycler_view);

        //set recycler view properties
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        pd = new ProgressDialog(this);

        //show data in recyler view
        showData();

    }

    private void showData() {
        //set title of progress dialog
        pd.setTitle("Loading Data...");
        //show progress dialog
        pd.show();

        db.collection("AduanRecords")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        modelInvList.clear();
                        //called when data is retrieved
                        pd.dismiss();
                        //show data
                        for (DocumentSnapshot doc: task.getResult()){
                            ModelInv model = new ModelInv(
                                    doc.getString("Aduan Date"),
                                    doc.getString("Aduan Title"),
                                    doc.getString("Aduan Type"),
                                    doc.getString("Aduan Status"),
                                    doc.getString("Aduan Description"),
                                    doc.getString("Aduan Priority"),
                                    doc.getString("Aduan By"),
                                    doc.getString("Aduan Remark"));
                            modelInvList.add(model);
                        }
                        //adapter
                        adapter = new CustomAdapterHis(HistoryAduanActivity.this, modelInvList);
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

        db.collection("AduanRecords").document(modelInvList.get(index).getAduanTitle())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //called when deleted successfully
                        Toast.makeText(HistoryAduanActivity.this, "Deleted...", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(HistoryAduanActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void searchData(String s){
        //set title od progress bar
        pd.setTitle("Searching...");
        //show progress Bar
        pd.show();

        db.collection("AduanRecords").whereEqualTo("search", s.toLowerCase())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //called when searching is succeedded
                        modelInvList.clear();
                        pd.dismiss();
                        //show data
                        for (DocumentSnapshot doc: Objects.requireNonNull(task.getResult())){
                            ModelInv model = new ModelInv(
                                    doc.getString("Aduan Date"),
                                    doc.getString("Aduan Title"),
                                    doc.getString("Aduan Type"),
                                    doc.getString("Aduan Status"),
                                    doc.getString("Aduan Description"),
                                    doc.getString("Aduan Priority"),
                                    doc.getString("Aduan By"),
                                    doc.getString("Aduan Remark"));
                            modelInvList.add(model);
                        }
                        //adapter
                        adapter = new CustomAdapterHis(HistoryAduanActivity.this, modelInvList);
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
                        Toast.makeText(HistoryAduanActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
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
