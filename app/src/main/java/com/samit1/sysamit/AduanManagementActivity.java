package com.samit1.sysamit;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AduanManagementActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    TextView firebasenameview;

    //Views
    EditText mAduanTitle, mAduanDesc, mAduanUsrName, mAduanRemark ;
    Button mSaveBtn;
    TextView textViewDate;

    //progress dialog;
    ProgressDialog pd;

    //Firebase instance
    FirebaseFirestore db;

    String pAdate, pAtitle, pAtype, pAstatus, pAdesc, pAprior, pAusrname, pAremark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aduan_management);

        firebasenameview = findViewById(R.id.firebasename2);

        // this is for username to appear after login

        firebaseAuth = FirebaseAuth.getInstance();

        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser=users.getEmail();
        String result = finaluser.substring(0, finaluser.indexOf("@"));
        String resultemail = result.replace(".","");
        firebasenameview.setText(" "+resultemail);

        //Get Current Date
        Calendar calendar = Calendar.getInstance();
        String curentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        textViewDate = findViewById(R.id.time);
        textViewDate.setText(curentDate);

        //spinner item type
        final Spinner itypeSpinner = (Spinner) findViewById(R.id.spinnerType);
        ArrayAdapter<String> itypeAdapter = new ArrayAdapter<>(AduanManagementActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ItemType));
        itypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        itypeSpinner.setAdapter(itypeAdapter);

        //spinner aduan status
        final Spinner statusSpinner = (Spinner) findViewById(R.id.spinnerStatus);
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(AduanManagementActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.StatusComplaint));
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);


        //spinner aduan priority
        final Spinner priorSpinner = (Spinner) findViewById(R.id.spinnerPrior);
        ArrayAdapter<String> priorAdapter = new ArrayAdapter<>(AduanManagementActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Problems));
        priorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priorSpinner.setAdapter(priorAdapter);

        //actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ADUAN LIST");

        //initiliaze views with xml
        mAduanTitle = findViewById(R.id.aduanTitle);
        mAduanDesc = findViewById(R.id.aduanDesc);
        mAduanUsrName = findViewById(R.id.aduanUsrName);
        mAduanRemark = findViewById(R.id.remarkIT);
        mSaveBtn = findViewById(R.id.saveBtn);

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            //Update data
            actionBar.setTitle("Update ADUAN Data");
            mSaveBtn.setText("Update");
            //get data
            pAdate = bundle.getString("pAdate");
            pAtitle = bundle.getString("pAtitle");
            pAtype = bundle.getString("pAtype");
            pAstatus = bundle.getString("pAstatus");
            pAdesc = bundle.getString("pAdesc");
            pAprior = bundle.getString("pAprior");
            pAusrname = bundle.getString("pAusrname");
            pAremark = bundle.getString("pAremark");

            //set data
            mAduanTitle.setText(pAtitle);
            //spinner Type SHOW
            itypeSpinner.setAdapter(itypeAdapter);
            if (mAduanTitle != null)
            {
                int spinnerPosition = itypeAdapter.getPosition(pAtype);
                itypeSpinner.setSelection(spinnerPosition);
            }

            //Spinner Status Aduan
            statusSpinner.setAdapter(statusAdapter);
            if (mAduanTitle != null)
            {
                int spinnerPosition = statusAdapter.getPosition(pAstatus);
                statusSpinner.setSelection(spinnerPosition);
            }

            mAduanDesc.setText(pAdesc);

            //spinner Aduan prior
            priorSpinner.setAdapter(priorAdapter);
            if (mAduanTitle != null)
            {
                int spinnerPosition = priorAdapter.getPosition(pAprior);
                priorSpinner.setSelection(spinnerPosition);
            }

            mAduanUsrName.setText(pAusrname);
            mAduanRemark.setText(pAremark);
            textViewDate.setText(pAdate);

        }
        else {
            //New Data
            actionBar.setTitle("Add ADUAN Data");
            mSaveBtn.setText("Save");
        }

        //progress bar dialog
        pd = new ProgressDialog(this);

        //firestore
        db = FirebaseFirestore.getInstance();

        //click button to upload data
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null){
                    //updating
                    //input data
                    String AduanTitle = mAduanTitle.getText().toString().trim();
                    String AduanType = itypeSpinner.getSelectedItem().toString().trim();
                    String AduanStatus = statusSpinner.getSelectedItem().toString().trim();
                    String AduanDesc = mAduanDesc.getText().toString().trim();
                    String AduanPrior = priorSpinner.getSelectedItem().toString().trim();
                    String AduanBy = mAduanUsrName.getText().toString().trim();
                    String AduanRemark = mAduanRemark.getText().toString().trim();
                    String AduanDate = textViewDate.getText().toString().trim();
                    //funtion call to update data
                    updateData(AduanDate, AduanTitle, AduanType, AduanStatus, AduanDesc, AduanPrior, AduanBy, AduanRemark);

                }
                else {
                    //adding new
                    //input data
                    String AduanTitle = mAduanTitle.getText().toString().trim();
                    String AduanType = itypeSpinner.getSelectedItem().toString().trim();
                    String  AduanStatus = statusSpinner.getSelectedItem().toString().trim();
                    String AduanDesc = mAduanDesc.getText().toString().trim();
                    String AduanPrior = priorSpinner.getSelectedItem().toString().trim();
                    String AduanBy = mAduanUsrName.getText().toString().trim();
                    String AduanRemark = mAduanRemark.getText().toString().trim();
                    String AduanDate = textViewDate.getText().toString().trim();

                    //function call to upload data
                    uploadData(AduanDate, AduanTitle, AduanType, AduanStatus, AduanDesc, AduanPrior, AduanBy, AduanRemark);

                }

            }
        });

    }

    private void updateData(String aduanDate, String aduanTitle, String aduanType, String aduanStatus, String aduanDesc,
                            String aduanPrior, String aduanBy, String aduanRemark) {
        //set title of progress bar
        pd.setTitle("Updating Data...");
        //show progress bar when user click save button
        pd.show();

        db.collection("AduanRecords").document(aduanTitle)
                .update("Aduan Date", aduanDate,"Aduan Title", aduanTitle, "Aduan Type", aduanType,
                        "Aduan Status", aduanStatus, "Aduan Description", aduanDesc, "Aduan Priority", aduanPrior,
                        "Aduan By", aduanBy, "Aduan Remark", aduanRemark, "search", aduanTitle.toLowerCase())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //callled when update successfully
                        pd.dismiss();
                        notificationDialogUpdate();
                        Toast.makeText(AduanManagementActivity.this, "Updated...", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //called when there is any error
                pd.dismiss();
                //get and show error messsage
                Toast.makeText(AduanManagementActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void uploadData(String AduanDate, String AduanTitle, String AduanType, String AduanStatus,
                            String AduanDesc, String AduanPrior, String AduanBy, String AduanRemark) {
        //set title of progress bar
        pd.setTitle("Adding Data to Firestore");
        //show progress bar when user click save button
        pd.show();;
        //random id for each data to be stored
        //String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        //doc.put("id", id); //id of data
        doc.put("Aduan Date", AduanDate);
        doc.put("Aduan Title", AduanTitle);
        doc.put("Aduan Type", AduanType);
        doc.put("Aduan Status", AduanStatus);
        doc.put("Aduan Description", AduanDesc);
        doc.put("Aduan Priority", AduanPrior);
        doc.put("Aduan By", AduanBy);
        doc.put("Aduan Remark", AduanRemark);
        doc.put("search", AduanTitle.toLowerCase());

        //add this data
        db.collection("AduanRecords").document(AduanTitle).set(doc)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //this will be called when data is added succesfully

                        pd.dismiss();
                        notificationDialog();
                        Toast.makeText(AduanManagementActivity.this, "Uploaded...",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //this will be called if there is any error while uploading

                        pd.dismiss();
                        //get and show error message
                        Toast.makeText(AduanManagementActivity.this, e.getMessage(),
                                Toast.LENGTH_SHORT).show();
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void notificationDialog() {
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        String name = firebasenameview.getText().toString();
        String NOTIFICATION_CHANNEL_ID = "notification 01";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel =
                    new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications",
                            NotificationManager.IMPORTANCE_MAX);
            // Configure the notification channel.
            notificationChannel.setDescription("Sample Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new
                NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Notification")
                //.setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("New Complaint Notification")
                .setContentText(name + ", Your complaint have been submitted. Thanks You.")
                .setContentInfo("Information");
        notificationManager.notify(1, notificationBuilder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void notificationDialogUpdate() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String name = firebasenameview.getText().toString();
        String NOTIFICATION_CHANNEL_ID = "notification 01";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_MAX);
            // Configure the notification channel.
            notificationChannel.setDescription("Sample Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("Notification")
                //.setPriority(Notification.PRIORITY_MAX)
                .setContentTitle("New Update Complaint Notification")
                .setContentText(name + ", The complaint have been updated. Thanks You.")
                .setContentInfo("Information");
        notificationManager.notify(1, notificationBuilder.build());
    }

}

