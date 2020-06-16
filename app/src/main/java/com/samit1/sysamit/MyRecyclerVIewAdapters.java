package com.samit1.sysamit;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class MyRecyclerVIewAdapters extends RecyclerView.Adapter<MyRecyclerViewHolders> {

    ViewItechActivity viewItechActivity;
    ArrayList<User> userArrayList;


    public MyRecyclerVIewAdapters(ViewItechActivity viewItechActivity, ArrayList<User> userArrayList) {
        this.viewItechActivity = viewItechActivity;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public MyRecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewItechActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.single_row, parent, false);

        return new MyRecyclerViewHolders(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewHolders holder, final int position) {

        holder.mUserName.setText(userArrayList.get(position).getUserName());
        holder.mUserStatus.setText(userArrayList.get(position).getUserStatus());
        holder.mDeleteRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedRow(position);
            }
        });

    }

    private void deleteSelectedRow(int position) {
        viewItechActivity.db.collection("ITechusers")
                .document(userArrayList.get(position).getUserId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(viewItechActivity, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        viewItechActivity.loadDataFromFirebase();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(viewItechActivity, "Unable to Delete ~~3~~", Toast.LENGTH_SHORT).show();
                        Log.v("~~3~~", e.getMessage());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }
}
