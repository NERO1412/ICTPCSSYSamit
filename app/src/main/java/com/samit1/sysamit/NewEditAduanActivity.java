package com.samit1.sysamit;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;

public class NewEditAduanActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    TextView firebasenameview;

    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerPriority;
    private Spinner naSpinner;
    private Spinner statusSpinner;
    private EditText editTextIt;
    private TextView textViewDate;

    String pTitle, pDesc, pNum, pNa, pStatus, pIt, pDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_edit_aduan);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Edit Aduan");

        firebasenameview = findViewById(R.id.firebasename);

        // this is for username to appear after login

        firebaseAuth = FirebaseAuth.getInstance();

        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser=users.getEmail();
        String result = finaluser.substring(0, finaluser.indexOf("@"));
        String resultemail = result.replace(".","");
        firebasenameview.setText(" "+resultemail);

        //textTime =findViewById(R.id.time);
        Calendar calendar = Calendar.getInstance();
        String curentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        textViewDate = findViewById(R.id.time);
        textViewDate.setText(curentDate);
        // SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        //String time = " " + format.format(calendar.getTime());
        //textTime.setText(time);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(5);

        statusSpinner = (Spinner) findViewById(R.id.statusSpinner);
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<>(NewEditAduanActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.StatusComplaint));
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        statusSpinner.setAdapter(statusAdapter);

        naSpinner = (Spinner) findViewById(R.id.catAspinner);
        ArrayAdapter <String> itypeAdapter = new ArrayAdapter<>(NewEditAduanActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.ItemType));
        itypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        naSpinner.setAdapter(itypeAdapter);

        editTextIt = findViewById(R.id.edit_it_description);

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            //get data
            pTitle = bundle.getString("pTitle");
            pDesc = bundle.getString("pDesc");
            pNum = bundle.getString("pNum");
            pNa = bundle.getString("pNa");
            pStatus = bundle.getString("pStatus");
            pIt = bundle.getString("pIt");
            pDate = bundle.getString("pDate");

            //set data
            editTextTitle.setText(pTitle);
            editTextDescription.setText(pDesc);
            //numberPickerPriority
            //spinner cats
            naSpinner.setAdapter(itypeAdapter);
            if(editTextTitle != null)
            {
                int spinnerPosition = itypeAdapter.getPosition(pNa);
                naSpinner.setSelection(spinnerPosition);
            }
            //spinner status
            statusSpinner.setAdapter(statusAdapter);
            if (editTextTitle != null)
            {
                int spinnerPosition = statusAdapter.getPosition(pStatus);
                statusSpinner.setSelection(spinnerPosition);
            }
            editTextIt.setText(pIt);
            textViewDate.setText(pDate);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_aduan_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveAduan();
                notificationDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void saveAduan(){
        String dateComplaint = textViewDate.getText().toString();
        String cusrName = firebasenameview.getText().toString();
        String statusComplaint = statusSpinner.getSelectedItem().toString().trim();
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String ItemType = naSpinner.getSelectedItem().toString().trim();
        int priority = numberPickerPriority.getValue();
        String itdescription = editTextIt.getText().toString();

        if (title.trim().isEmpty() || ItemType.trim().isEmpty() || description.trim().isEmpty() ){
            Toast.makeText(this, "Please insert a title, description and Item Type", Toast.LENGTH_SHORT).show();
            return;
        }

        CollectionReference aduanRef = FirebaseFirestore.getInstance()
                .collection("AduanLog");
        aduanRef.add(new Aduan(dateComplaint, cusrName, statusComplaint, title, description, ItemType, priority, itdescription));
        Toast.makeText(this, "Aduan Edited", Toast.LENGTH_SHORT).show();
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void notificationDialog() {
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
                .setContentTitle("Edit Complaint Notification")
                .setContentText(name + ", Your have editted the staff's complaint. Thanks You.")
                .setContentInfo("Information");
        notificationManager.notify(1, notificationBuilder.build());
    }
}

