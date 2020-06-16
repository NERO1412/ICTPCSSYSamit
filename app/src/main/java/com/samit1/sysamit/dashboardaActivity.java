package com.samit1.sysamit;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class dashboardaActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth firebaseAuth;
    TextView firebasenameview;
    Button toast;

    private CardView compReports, addUsers, statusComps, viewProfiles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboarda);
        firebasenameview = findViewById(R.id.firebasename);

        // this is for username to appear after login

        firebaseAuth = FirebaseAuth.getInstance();

        final FirebaseUser users = firebaseAuth.getCurrentUser();
        String finaluser=users.getEmail();
        String result = finaluser.substring(0, finaluser.indexOf("@"));
        String resultemail = result.replace(".","");
        firebasenameview.setText("Welcome, "+resultemail);
//        toast.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(dashboardActivity.this, users.getEmail(), Toast.LENGTH_SHORT).show();
//            }
//        });


        compReports = (CardView)findViewById(R.id.compReport);
        addUsers = (CardView) findViewById(R.id.addUser);
        statusComps = (CardView) findViewById(R.id.statusComp);
        viewProfiles = (CardView) findViewById(R.id.viewProfile);

        compReports.setOnClickListener(this);
        addUsers.setOnClickListener(this);
        statusComps.setOnClickListener(this);
        viewProfiles.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        Intent i;

        switch (view.getId()){
            case R.id.compReport : i = new Intent(this,MonthDashboardActivity.class); startActivity(i); break;
            case R.id.addUser : i = new Intent(this,addUserActivity.class);startActivity(i); break;
            case R.id.statusComp : i = new Intent(this,StatusAduanActivity.class);startActivity(i); break;
            case R.id.viewProfile : i = new Intent(this,ProfileAdmin.class);startActivity(i); break;
            default: break;
        }
    }





    // logout below
    private void Logout()
    {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(dashboardaActivity.this,frontPage.class));
        Toast.makeText(dashboardaActivity.this,"LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show();

    }

    //menu icon with app logo & logout icon
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflating menu_main.xml
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        //Search View
        MenuItem item = menu.findItem(R.id.action_log);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //handler other menu item clicks here
        if (item.getItemId() == R.id.action_return){
            Logout();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

