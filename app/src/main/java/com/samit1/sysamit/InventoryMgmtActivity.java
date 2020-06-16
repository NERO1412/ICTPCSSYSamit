package com.samit1.sysamit;
//importantnote* ItemID cannot be changed once it had been registered

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class InventoryMgmtActivity extends AppCompatActivity{

    //Views
    EditText mIdItemEt, mSnEt, mUsedByEt ;
    Button mGenerate, mSaveBtn, mListBtn;

    //progress dialog;
    ProgressDialog pd;

    //Firebase instance
    FirebaseFirestore db;

    String pID, pType, pModel, pSN, pDepartment, pUsedBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_int_mgmt);

        //spinner item model
        final Spinner mySpinner = (Spinner) findViewById(R.id.spinnermodel);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(InventoryMgmtActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Model));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        //spinner item type
        final Spinner itypeSpinner = (Spinner) findViewById(R.id.spinnertype);
        ArrayAdapter<String> itypeAdapter = new ArrayAdapter<>(InventoryMgmtActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ItemType));
        itypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itypeSpinner.setAdapter(itypeAdapter);

        //spinner item department
        final Spinner idpmtSpinner = (Spinner) findViewById(R.id.spinnerdpmt);
        ArrayAdapter<String> idpmtAdapter = new ArrayAdapter<>(InventoryMgmtActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Department));
        idpmtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idpmtSpinner.setAdapter(idpmtAdapter);

        //actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("INVENTORY MANAGEMENT");

        //initiliaze views with xml
        mIdItemEt = findViewById(R.id.idItemEt);
        mSnEt = findViewById(R.id.snItemEt);
        mUsedByEt = findViewById(R.id.usedByEt);
        mSaveBtn = findViewById(R.id.saveBtn);
        mListBtn = findViewById(R.id.listBtn);
        mGenerate = findViewById(R.id.btn_scan);

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            //Update data
            actionBar.setTitle("Update INVENTORY Data");
            mSaveBtn.setText("Update");
            //get data
            pID = bundle.getString("pID");
            pType = bundle.getString("pType");
            pModel = bundle.getString("pModel");
            pSN = bundle.getString("pSN");
            pDepartment = bundle.getString("pDepartment");
            pUsedBy = bundle.getString("pUsedBy");

            //set data
            mIdItemEt.setText(pID);
            //spinner Type SHOW
            itypeSpinner.setAdapter(itypeAdapter);
            if (mIdItemEt != null)
            {
                int spinnerPosition = itypeAdapter.getPosition(pType);
                itypeSpinner.setSelection(spinnerPosition);
            }

            //Spinner Model SHOW
            mySpinner.setAdapter(myAdapter);
            if (mIdItemEt != null)
            {
                int spinnerPosition = myAdapter.getPosition(pModel);
                mySpinner.setSelection(spinnerPosition);
            }

            mSnEt.setText(pSN);

            //spinner Department
            idpmtSpinner.setAdapter(idpmtAdapter);
            if (mIdItemEt != null)
            {
                int spinnerPosition = idpmtAdapter.getPosition(pDepartment);
                idpmtSpinner.setSelection(spinnerPosition);
            }

            mUsedByEt.setText(pUsedBy);

        }
        else {
            //New Data
            actionBar.setTitle("Add INVENTORY Data");
            mSaveBtn.setText("Save");
        }

        //progress bar dialog
        pd = new ProgressDialog(this);

        //firestore
        db = FirebaseFirestore.getInstance();

        mGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),QRActivity.class));
            }
        });

        //click button to upload data
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null){
                    //updating
                    //input data
                    String ItemID = mIdItemEt.getText().toString().trim();
                    String ItemType = itypeSpinner.getSelectedItem().toString().trim();
                    String ItemModel = mySpinner.getSelectedItem().toString().trim();
                    String ItemSN = mSnEt.getText().toString().trim();
                    String ItemDepartment = idpmtSpinner.getSelectedItem().toString().trim();
                    String ItemUsedBy = mUsedByEt.getText().toString().trim();
                    //funtion call to update data
                    updateData(ItemID, ItemType, ItemModel, ItemSN, ItemDepartment, ItemUsedBy);

                }
                else {
                    //adding new
                    //input data
                    String ItemID = mIdItemEt.getText().toString().trim();
                    String ItemType = itypeSpinner.getSelectedItem().toString().trim();
                    String ItemModel = mySpinner.getSelectedItem().toString().trim();
                    String ItemSN = mSnEt.getText().toString().trim();
                    String ItemDepartment = idpmtSpinner.getSelectedItem().toString().trim();
                    String ItemUsedBy = mUsedByEt.getText().toString().trim();

                    //function call to upload data
                    uploadData(ItemID, ItemType, ItemModel, ItemSN, ItemDepartment, ItemUsedBy);

                }

            }
        });

        //click btn to start ItemListActivity
        mListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InventoryMgmtActivity.this, ListitActivity.class));
                finish();
            }
        });
    }

    private void updateData(String itemID,  String itemType, String itemModel, String itemSN,
                            String itemDepartment, String itemUsedBy) {
        //set title of progress bar
        pd.setTitle("Updating Data...");
        //show progress bar when user click save button
        pd.show();

        db.collection("ICTrecords").document(itemID)
                .update("Item ID", itemID, "Item Type", itemType, "Item Model", itemModel,
                "Item SN", itemSN, "Item Department", itemDepartment ,"Item Used By", itemUsedBy,"search", itemID.toLowerCase())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //callled when update successfully
                        pd.dismiss();
                        Toast.makeText(InventoryMgmtActivity.this, "Updated...", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //called when there is any error
                pd.dismiss();
                //get and show error messsage
                Toast.makeText(InventoryMgmtActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void uploadData(String ItemID, String ItemType, String ItemModel, String ItemSN,
                            String ItemDepartment, String ItemUsedBy) {
        //set title of progress bar
        pd.setTitle("Adding Data to Firestore");
        //show progress bar when user click save button
        pd.show();;
        //random id for each data to be stored
        //String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        //doc.put("id", id); //id of data
        doc.put("Item ID", ItemID);
        doc.put("Item Type", ItemType);
        doc.put("Item Model", ItemModel);
        doc.put("Item SN", ItemSN);
        doc.put("Item Department", ItemDepartment);
        doc.put("Item Used By", ItemUsedBy);
        doc.put("search", ItemID.toLowerCase());



        //add this data
        db.collection("ICTrecords").document(ItemID).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //this will be called when data is added succesfully

                        pd.dismiss();
                        Toast.makeText(InventoryMgmtActivity.this, "Uploaded...", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //this will be called if there is any error while uploading

                        pd.dismiss();
                        //get and show error message
                        Toast.makeText(InventoryMgmtActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    //menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating menu_main.xml
        getMenuInflater().inflate(R.menu.menu_return, menu);
        //Search View
        MenuItem item = menu.findItem(R.id.action_log);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //handler other menu item clicks here
        if (item.getItemId() == R.id.action_return){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
