package com.samit1.sysamit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class AduanMainActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference aduanRef = db.collection("Aduan");

    private AduanAdapter adapter;
    public FloatingActionButton buttonAddAduan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aduanmain);

        //actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Click the LIST to see the status");

        buttonAddAduan = findViewById(R.id.button_add_note);

        buttonAddAduan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AduanMainActivity.this, NewAduanActivity.class);
                startActivity(intent);
            }
        });

        setUpRecyclerView();

    }

    private  void setUpRecyclerView(){
        Query query = aduanRef.orderBy("priority", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Aduan> options = new FirestoreRecyclerOptions.Builder<Aduan>()
                .setQuery(query, Aduan.class)
                .build();

        adapter = new AduanAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //adapter.deleteItem(viewHolder.getAdapterPosition());
                //staff cannot delete
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new AduanAdapter.OnItemClickListerner() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Aduan aduan = documentSnapshot.toObject(Aduan.class);
                String dateComplaint = documentSnapshot.getString("dateComplaint");
                String cuser = documentSnapshot.getString("cusrName");
                String title = documentSnapshot.getString("title");
                String status = documentSnapshot.getString("statusComplaint");
                Toast.makeText(AduanMainActivity.this,
                        "NAME : " + cuser + "\n" + "Complaint:" + title + "\n" + "Date Complaint " + dateComplaint +
                                "\n" + "Status : " + status, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
