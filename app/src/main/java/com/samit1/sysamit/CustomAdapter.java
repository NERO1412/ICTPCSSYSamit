package com.samit1.sysamit;

//Custom Adapter for ListitActivity

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

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    ListitActivity listitActivity;
    List<Model> modelList;
    Context context;

   // public CustomAdapter(ListitActivity listitActivity, List<Model> modelList, Context context) {
       // this.listitActivity = listitActivity;
       // this.modelList = modelList;
       // this.context = context;
   // }

    public CustomAdapter(ListitActivity listitActivity, List<Model> modelList) {
        this.listitActivity = listitActivity;
        this.modelList = modelList;
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
                String itemID= modelList.get(position).getItemID();
                String itemType= modelList.get(position).getItemType();
                String itemModel= modelList.get(position).getItemModel();
                //String itemSn= modelList.get(position).getItemSN();
                //String itemDpmt= modelList.get(position).getItemDepartment();
                String itemUsed= modelList.get(position).getItemUsedby();


                Toast.makeText(listitActivity, "ITEM ID : " + itemID + "\n" + "ITEM TYPE : " + itemType + "\n" + "ITEM MODEL : " + itemModel + "\n" + "USED BY : " + itemUsed, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemlongClick(View view, final int position) {
                //this will be called when user long click item

                //Creating AlertDialog
                AlertDialog.Builder builder = new AlertDialog.Builder(listitActivity);
                //option to display in dialog
                String[] options = {"Update", "Delete"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0){
                            //update is clicked
                            //get data
                            String itemID= modelList.get(position).getItemID();
                            String itemType= modelList.get(position).getItemType();
                            String itemModel= modelList.get(position).getItemModel();
                            String itemSn= modelList.get(position).getItemSN();
                            String itemDpmt= modelList.get(position).getItemDepartment();
                            String itemUsed= modelList.get(position).getItemUsedby();

                            //intent to start activity
                            Intent intent = new Intent(listitActivity, InventoryMgmtActivity.class);
                            //put data in intent
                            intent.putExtra("pID", itemID);
                            intent.putExtra("pType", itemType);
                            intent.putExtra("pModel", itemModel);
                            intent.putExtra("pSN", itemSn);
                            intent.putExtra("pDepartment", itemDpmt);
                            intent.putExtra("pUsedBy", itemUsed);

                            //start activity
                            listitActivity.startActivity(intent);
                        }
                        if (which == 1){
                            //delete is clicked
                            listitActivity.deleteData(position);
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
        viewHolder.mItemIdTv.setText(modelList.get(i).getItemID());
        viewHolder.mItemModelTv.setText(modelList.get(i).getItemModel());
        viewHolder.mItemTypeTv.setText(modelList.get(i).getItemType());


    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }
}
