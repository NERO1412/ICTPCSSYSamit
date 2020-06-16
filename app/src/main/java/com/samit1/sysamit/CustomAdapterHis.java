package com.samit1.sysamit;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class CustomAdapterHis extends RecyclerView.Adapter<ViewHolder> {

    //ListitActivity listitActivity;
    HistoryAduanActivity historyAduanActivity;
    List<ModelInv> modelInvList;
    //List<Model> modelList;
    Context context;

    // public CustomAdapter(ListitActivity listitActivity, List<Model> modelList, Context context) {
    // this.listitActivity = listitActivity;
    // this.modelList = modelList;
    // this.context = context;
    // }

    public CustomAdapterHis(HistoryAduanActivity historyAduanActivity, List<ModelInv> modelInvList) {
        this.historyAduanActivity = historyAduanActivity;
        this.modelInvList = modelInvList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //imflate layout
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model_layout, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(itemView);
        //handle item clicks here
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //this will be called when user click item

                //show data in toast on clicking
                String aduanTitle= modelInvList.get(position).getAduanTitle();
                String aduanType= modelInvList.get(position).getAduanType();
                String aduanStatus= modelInvList.get(position).getAduanStatus();
                String aduanBy= modelInvList.get(position).getAduanBy();


                Toast.makeText(historyAduanActivity, "ADUAN TITLE : " + aduanTitle + "\n" + "ADUAN TYPE : " + aduanType + "\n" + "ADUAN STATUS : " + aduanStatus + "\n" + "ADUAN BY : " + aduanBy, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemlongClick(View view, final int position) {
                //this will be called when user long click item
                //Disable for staff
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //bind views / set data
        viewHolder.mItemIdTv.setText(modelInvList.get(i).getAduanTitle());
        viewHolder.mItemModelTv.setText(modelInvList.get(i).getAduanDesc());
        viewHolder.mItemTypeTv.setText(modelInvList.get(i).getAduanStatus());


    }

    @Override
    public int getItemCount() {
        return modelInvList.size();
    }
}
