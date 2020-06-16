package com.samit1.sysamit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class RekodAduanAdapter extends FirestoreRecyclerAdapter<Aduan, RekodAduanAdapter.AduanHolder> {

    private OnItemClickListerner listerner;

    public RekodAduanAdapter(@NonNull FirestoreRecyclerOptions<Aduan> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull AduanHolder aduanHolder, int i, @NonNull Aduan aduan) {
        aduanHolder.textViewTitle.setText(aduan.getTitle());
        aduanHolder.textViewDescription.setText(aduan.getDescription());
        aduanHolder.textViewPriority.setText(String.valueOf(aduan.getPriority()));
    }

    @NonNull
    @Override
    public RekodAduanAdapter.AduanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item,parent,false);
        return new RekodAduanAdapter.AduanHolder(v);
    }

    public  void  deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class AduanHolder extends RecyclerView.ViewHolder{
        TextView textViewTitle;
        TextView textViewDescription;
        TextView textViewPriority;


        public  AduanHolder(View itemView){
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewDescription = itemView.findViewById(R.id.text_view_description);
            textViewPriority = itemView.findViewById(R.id.text_view_priority);


            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listerner != null) {
                        listerner.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListerner {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(RekodAduanAdapter.OnItemClickListerner listener){
        this.listerner = listener;
    }

}