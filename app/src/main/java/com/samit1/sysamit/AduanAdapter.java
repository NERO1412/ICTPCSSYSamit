package com.samit1.sysamit;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class AduanAdapter extends FirestoreRecyclerAdapter<Aduan, AduanAdapter.AduanHolder> {

    EditAduanMainActivity editAduanMainActivity;
    List<Aduan> aduanList;
    Context context;

    private  OnItemClickListerner listerner;

    public AduanAdapter(EditAduanMainActivity editAduanMainActivity, FirestoreRecyclerOptions<Aduan> options, List<Aduan> aduanList) {
        super(options);
        this.editAduanMainActivity = editAduanMainActivity;
        this.aduanList = aduanList;
    }

    public AduanAdapter(FirestoreRecyclerOptions<Aduan> options) {
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
    public AduanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item,parent,false);
        return new AduanHolder(v);
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
                public void onItemClickListerner(View view, final int position){

                    //this will be called when user click item
                    AlertDialog.Builder xx = new AlertDialog.Builder(editAduanMainActivity);
                    //option to display in dialog
                    final String[] options = {"Update", "Delete"};
                    xx.setItems(options, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which ){
                            if(which == 0){
                                //update is clicked
                                //get data
                                String dateComplaint = aduanList.get(position).getDateComplaint();
                                //String cusrName = firebasenameview.getText().toString();
                                //String statusComplaint = statusSpinner.getSelectedItem().toString().trim();
                                String title = aduanList.get(position).getTitle();
                                //String description = editTextDescription.getText().toString();
                                //String ItemType = naSpinner.getSelectedItem().toString().trim();
                                //int priority = numberPickerPriority.getValue();
                                //String itdescription = editTextIt.getText().toString();

                                //intent to start activity
                                Intent intent = new Intent(editAduanMainActivity, NewEditAduanActivity.class);
                                //put data in intent
                                intent.putExtra("dateCompaint", dateComplaint);
                                //intent.putExtra("cusrName",cusrName);
                                //intent.putExtra("statusComplaint",statusComplaint);
                                intent.putExtra("pTitle",title);
                                //intent.putExtra("description",description);
                                //intent.putExtra("itemType",ItemType);
                                //intent.putExtra("priority",priority);
                                //intent.putExtra("itdescription",itdescription);

                                //start activity
                                editAduanMainActivity.startActivity(intent);
                            }
                            if(which == 1){
                                //delete is clicked

                            }
                        }
                    }).create().show();

                }
            });
        }
    }

    public interface OnItemClickListerner {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);

    }


    public void setOnItemClickListener(OnItemClickListerner listener){
        this.listerner = listener;
    }

}
