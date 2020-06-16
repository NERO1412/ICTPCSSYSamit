package com.samit1.sysamit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class RiMainActivity extends AppCompatActivity {

    private static final String TAG = "RiMainActivity";

    public static  final String KEY_COMPTYPE = "brgType";
    public static  final String KEY_COMPCAT = "brgCat";
    public static  final String KEY_COMPMODEL = "brgModel";
    public static  final String KEY_COMPSN = "brgSn";
    public static  final String KEY_LOCATED = "brgLocated";
    public static  final String KEY_DESCRIPTION = "brgDesc";

    private EditText editTextComponentType;
    private EditText editTextComponentCategory;
    private EditText editTextComponentModel;
    private EditText editTextDescription;
    private EditText editTextComponentSerialNumber;
    private EditText editTextLocated;
    private TextView textViewData;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference inventoriRef = db.collection("INVENTORY");
    private DocumentReference noteRef = db.document("INVENTORY/COMPONENT");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainri);


        editTextComponentType = findViewById(R.id.edit_text_comptype);
        editTextComponentCategory = findViewById(R.id.edit_text_category);
        editTextComponentModel = findViewById(R.id.edit_text_model);
        editTextDescription = findViewById(R.id.edit_text_description);
        editTextComponentSerialNumber = findViewById(R.id.edit_text_compsn);
        editTextLocated = findViewById(R.id.edit_text_located);
        textViewData = findViewById(R.id.text_view_data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        inventoriRef.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (e != null){
                    return;
                }
                String data = "";

                for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                    Inventori ri = documentSnapshot.toObject(Inventori.class);
                    ri.setBrgId(documentSnapshot.getId());

                    String brgId = ri.getBrgId();
                    String btype = ri.getBrgType();
                    String bcategory= ri.getBrgCat();
                    String bmodel = ri.getBrgModel();
                    String bsn = ri.getBrgSn();
                    String bdesc = ri.getBrgDesc();
                    String bLocated = ri.getBrgLocated();

                    data += "ID: " +  brgId
                            + "\nType: " + btype + "\nCategory: " + bcategory + "\nModel: " + bmodel + "\nS/N: " + bsn + "\nLocated: " + bLocated + "\nDescription: " + bdesc + "\n\n";
                }
                textViewData.setText(data);
            }
        });
    }

    public void addNote(View v){

        String brgType = editTextComponentType.getText().toString();
        String brgCat = editTextComponentCategory.getText().toString();
        String brgModel = editTextComponentModel.getText().toString();
        String brgDesc = editTextDescription.getText().toString();
        String brgSn = editTextComponentSerialNumber.getText().toString();
        String brgLocated = editTextLocated.getText().toString();

        Inventori ri = new Inventori(brgType, brgCat, brgModel, brgSn, brgLocated, brgDesc);


        inventoriRef.add(ri);
    }

    public  void updateDesc(View v) {
        String brgType = editTextComponentType.getText().toString();
        String brgCat = editTextComponentCategory.getText().toString();
        String brgModel = editTextComponentModel.getText().toString();
        String brgDesc = editTextDescription.getText().toString();
        String brgSn = editTextComponentSerialNumber.getText().toString();
        String brgLocated = editTextLocated.getText().toString();

        //Map<String, Object> note = new HashMap<>();
        //note.put(KEY_DESCRIPTION, description);

        //noteRef.set(note, SetOptions.merge());
        noteRef.update(KEY_COMPTYPE, brgType);
        noteRef.update(KEY_COMPCAT, brgCat);
        noteRef.update(KEY_COMPMODEL, brgModel);
        noteRef.update(KEY_DESCRIPTION, brgDesc);
        noteRef.update(KEY_COMPSN, brgSn);
        noteRef.update(KEY_LOCATED, brgLocated);
    }

    public void delNote(View v){
        noteRef.delete();
    }

    public void loadNote(View v){
        inventoriRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){
                            Inventori ri = documentSnapshot.toObject(Inventori.class);
                            ri.setBrgId(documentSnapshot.getId());

                            String brgId = ri.getBrgId();
                            String btype = ri.getBrgType();
                            String bcategory= ri.getBrgCat();
                            String bmodel = ri.getBrgModel();
                            String bsn = ri.getBrgSn();
                            String bdesc = ri.getBrgDesc();
                            String bLocated = ri.getBrgLocated();

                            data += "ID: " +  brgId
                                    + "Type: " + btype + "\nCategory: " + bcategory + "\nModel: " + bmodel + "\nS/N: " + bsn + "\nLocated: " + bLocated + "\nDescription: " + bdesc + "\n\n";
                        }
                        textViewData.setText(data);
                    }
                });
    }
}
