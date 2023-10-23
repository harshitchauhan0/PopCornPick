package com.harshit.popcornpick.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.harshit.popcornpick.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameET,emailET,passwordET;
    private AppCompatButton signupBTN;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        //REGISTER ACCOUNT
        auth = FirebaseAuth.getInstance();
        signupBTN.setOnClickListener(v->{
            String name = usernameET.getText().toString();
            String email = emailET.getText().toString();
            String password = passwordET.getText().toString();
            if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(getApplicationContext(),"You have filled Information Incorrectly",Toast.LENGTH_LONG).show();
            }
            else if(password.length()<6){
                Toast.makeText(getApplicationContext(),"Password Length is too short",Toast.LENGTH_LONG).show();
            }
            else{
                registerUser(name,email,password);
            }
        });
    }

    private void registerUser(String name, String email, String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    updateName(name);
                    Toast.makeText(getApplicationContext(),"Registering successful",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Registering failed",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void updateName(String name) {
        FirebaseUser user = auth.getCurrentUser();
        if(user!=null){
            user.sendEmailVerification();
            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
            try {
                user.updateProfile(userProfileChangeRequest).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"UPdated PROFILe",Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private void initView() {

        signupBTN = findViewById(R.id.signupbtn);
        usernameET = findViewById(R.id.registeretusername);
        emailET = findViewById(R.id.registeretemail);
        passwordET = findViewById(R.id.registeretpsd);
    }
}