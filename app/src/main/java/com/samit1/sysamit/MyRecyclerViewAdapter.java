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

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {

    ViewUserActivity viewUserActivity;
    ArrayList<User> userArrayList;

    public MyRecyclerViewAdapter(ViewUserActivity viewUserActivity, ArrayList<User> userArrayList) {
        this.viewUserActivity = viewUserActivity;
        this.userArrayList = userArrayList;
    }


    @NonNull
    @Override
    public MyRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewUserActivity.getBaseContext());
        View view = layoutInflater.inflate(R.layout.single_row, parent, false);

        return new MyRecyclerViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewHolder holder, final int position) {

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
        viewUserActivity.db.collection("users")
                .document(userArrayList.get(position).getUserId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(viewUserActivity, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        viewUserActivity.loadDataFromFirebase();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(viewUserActivity, "Unable to Delete ~~3~~", Toast.LENGTH_SHORT).show();
                        Log.v("~~3~~", e.getMessage());
                    }
                });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }
}
