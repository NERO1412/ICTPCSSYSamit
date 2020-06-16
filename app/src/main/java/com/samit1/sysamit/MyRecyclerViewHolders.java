package com.samit1.sysamit;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyRecyclerViewHolders extends RecyclerView.ViewHolder{

    public TextView mUserName, mUserStatus;
    public Button mDeleteRow;

    public MyRecyclerViewHolders(View itemView) {
        super(itemView);

        mUserName = itemView.findViewById(R.id.mRowUSerName);
        mUserStatus = itemView.findViewById(R.id.mRowUserStatus);
        mDeleteRow = itemView.findViewById(R.id.mRowDeleteButton);
    }
}
