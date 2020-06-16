package com.samit1.sysamit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class addUserActivity extends AppCompatActivity {

    Button buttonAddStaff, buttonAddItech, buttonShowUser, buttonShowITech;
    public static final String TAG = "TAG";
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        buttonAddStaff = findViewById(R.id.addStaff);
        buttonAddItech = findViewById(R.id.addItech);
        buttonShowUser = findViewById(R.id.viewUserBtn);
        buttonShowITech = findViewById(R.id.viewUserBtn2);

        //buttonAddStaff.setOnClickListener(new View.OnClickListener() {
         //   @Override
         //   public void onClick(View v) {
         //       Intent intent = new Intent(addUserActivity.this, Register.class);
        //        startActivity(intent);
        //    }
       // });

        buttonAddItech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(addUserActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_register_tech,null);
                //final EditText mEmail = (EditText) mView.findViewById(R.id.email);
                //final EditText mPassword = (EditText) mView.findViewById(R.id.password);
                final EditText mEmail = (EditText) mView.findViewById(R.id.email);
                final EditText mPassword = (EditText) mView.findViewById(R.id.password);
                final EditText mFullName= (EditText) mView.findViewById(R.id.fullName);
                final EditText mPhone = (EditText) mView.findViewById(R.id.phone);
                final ProgressBar progressBar = (ProgressBar) mView.findViewById(R.id.progressBar);

                Button mLogin = (Button) mView.findViewById(R.id.loginBtn);

                fAuth   = FirebaseAuth.getInstance();
                fStore = FirebaseFirestore.getInstance();

                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //if(!mEmail.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty()){
                            //Toast.makeText(addUserActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        //}else{
                           // Toast.makeText(addUserActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        //}
                        final String email = mEmail.getText().toString().trim();
                        String password = mPassword.getText().toString().trim();
                        final String fullName = mFullName.getText().toString();
                        final String phone    = mPhone.getText().toString();


                        if(TextUtils.isEmpty(email)){
                            mEmail.setError("Email is Required");
                            return;
                        }

                        if(TextUtils.isEmpty(password)){
                            mPassword.setError("Password is Required");
                            return;
                        }

                        if(password.length() < 6){
                            mPassword.setError("Password Must be >= 6 Characters");
                            return;
                        }

                        progressBar.setVisibility(View.VISIBLE);

                        // register the user in Firebase

                        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(addUserActivity.this, "New IT Tech. User Created", Toast.LENGTH_SHORT).show();
                                    userID = fAuth.getCurrentUser().getUid();
                                    DocumentReference documentReference = fStore.collection("ITechusers").document(userID);
                                    Map<String,Object> user = new HashMap<>();
                                    user.put("fName",fullName);
                                    user.put("email",email);
                                    user.put("phone",phone);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                        }
                                    });
                                    startActivity(new Intent(getApplicationContext(),addUserActivity.class));
                                }else {
                                    Toast.makeText(addUserActivity.this, "Error !" + task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        finish();
                    } //close onclick registerItech
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            } //close onclick buttonAddItech
        });

        buttonAddStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(addUserActivity.this);
                View nView = getLayoutInflater().inflate(R.layout.activity_register_staf,null);
                //final EditText mEmail = (EditText) mView.findViewById(R.id.email);
                //final EditText mPassword = (EditText) mView.findViewById(R.id.password);
                final EditText mEmail = (EditText) nView.findViewById(R.id.email);
                final EditText mPassword = (EditText) nView.findViewById(R.id.password);
                final EditText mFullName= (EditText) nView.findViewById(R.id.fullName);
                final EditText mPhone = (EditText) nView.findViewById(R.id.phone);
                final ProgressBar progressBar = (ProgressBar) nView.findViewById(R.id.progressBar);

                Button mLogin = (Button) nView.findViewById(R.id.loginBtn);

                fAuth   = FirebaseAuth.getInstance();
                fStore = FirebaseFirestore.getInstance();

                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //if(!mEmail.getText().toString().isEmpty() && !mPassword.getText().toString().isEmpty()){
                        //Toast.makeText(addUserActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        //}else{
                        // Toast.makeText(addUserActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                        //}
                        final String email = mEmail.getText().toString().trim();
                        String password = mPassword.getText().toString().trim();
                        final String fullName = mFullName.getText().toString();
                        final String phone    = mPhone.getText().toString();


                        if(TextUtils.isEmpty(email)){
                            mEmail.setError("Email is Required");
                            return;
                        }

                        if(TextUtils.isEmpty(password)){
                            mPassword.setError("Password is Required");
                            return;
                        }

                        if(password.length() < 6){
                            mPassword.setError("Password Must be >= 6 Characters");
                            return;
                        }

                        progressBar.setVisibility(View.VISIBLE);

                        // register the user in Firebase

                        fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(addUserActivity.this, "New Staff User Created", Toast.LENGTH_SHORT).show();
                                    userID = fAuth.getUid();
                                    DocumentReference documentReference = fStore.collection("users").document(userID);
                                    Map<String,Object> user = new HashMap<>();
                                    user.put("fName",fullName);
                                    user.put("email",email);
                                    user.put("phone",phone);
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                        }
                                    });
                                    startActivity(new Intent(getApplicationContext(),addUserActivity.class));
                                }else {
                                    Toast.makeText(addUserActivity.this, "Error !" + task.getException().getMessage(),
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        finish();
                    } //close onclick registerItech
                });
                mBuilder.setView(nView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            } //close onclick buttonAddItech

        });

        //showUser
        buttonShowUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(addUserActivity.this, ViewUserActivity.class);
                startActivity(intent);
            }
        });

        //show IT Tech
        buttonShowITech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(addUserActivity.this, ViewItechActivity.class);
                startActivity(intent);
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
