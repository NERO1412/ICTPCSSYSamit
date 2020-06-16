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

public class CustomAdapterStaf extends RecyclerView.Adapter<ViewHolder> {

    StafViewAduanActivity stafViewAduanActivity;
    List<ModelInv> modelInvList;
    Context context;

    public CustomAdapterStaf(StafViewAduanActivity stafViewAduanActivity, List<ModelInv> modelInvList){
        this.stafViewAduanActivity = stafViewAduanActivity;
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


                Toast.makeText(stafViewAduanActivity, "ADUAN TITLE : " + aduanTitle + "\n" +
                        "ADUAN TYPE : " + aduanType + "\n" + "ADUAN STATUS : " + aduanStatus + "\n" +
                        "ADUAN BY : " + aduanBy, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemlongClick(View view, final int position) {
                //this will be called when user long click item

                //Creating AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(stafViewAduanActivity);
                //option to display in dialog
                String[] options = {"Update", "Return"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            //update is clicked
                            //get data
                            String aduanTitle= modelInvList.get(position).getAduanTitle();
                            String aduanType= modelInvList.get(position).getAduanType();
                            String aduanStatus= modelInvList.get(position).getAduanStatus();
                            String aduanDesc = modelInvList.get(position).getAduanDesc();
                            String aduanPrior = modelInvList.get(position).getAduanPrior();
                            String aduanBy= modelInvList.get(position).getAduanBy();
                            String aduanRemark = modelInvList.get(position).getAduanRemark();
                            String aduanDate = modelInvList.get(position).getAduanDate();

                            //intent to start activity
                            Intent intent = new Intent(stafViewAduanActivity, AduanManagementActivity.class);
                            //put data in intent
                            intent.putExtra("pAdate", aduanDate);
                            intent.putExtra("pAtitle", aduanTitle);
                            intent.putExtra("pAtype", aduanType);
                            intent.putExtra("pAstatus", aduanStatus);
                            intent.putExtra("pAdesc", aduanDesc);
                            intent.putExtra("pAprior", aduanPrior);
                            intent.putExtra("pAusrname", aduanBy);
                            intent.putExtra("pAremark", aduanRemark);


                            //start activity
                            stafViewAduanActivity.startActivity(intent);
                        }
                        if (which == 1){
                            //Return is clicked
                            return;
                        }
                    }
                }).create().show();
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
