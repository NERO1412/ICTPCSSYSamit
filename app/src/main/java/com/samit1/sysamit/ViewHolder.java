package com.samit1.sysamit;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    //TextView mTitleTv, mDescriptionTv;
    TextView mItemIdTv, mItemTypeTv, mItemModelTv;
    View mView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        mView = itemView;

        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemClick(v, getAdapterPosition());
            }
        });
        //item long click listener
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemlongClick(v, getAdapterPosition());
                return true;
            }
        });

        //initialize views with mode_layout.xml
        mItemIdTv = itemView.findViewById(R.id.rItemIdTv);
        mItemTypeTv = itemView.findViewById(R.id.rItemTypeTv);
        mItemModelTv = itemView.findViewById(R.id.rItemModelTv);
    }

    private ViewHolder.ClickListener mClickListener;
    //interface for click listener
    public  interface ClickListener{
        void onItemClick(View view, int position);
        void onItemlongClick(View view, int position);
    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
}
