package com.harshit.popcornpick.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.harshit.popcornpick.R;

public class LoginActivity extends AppCompatActivity {

    TextView registerBTN;
    AppCompatButton loginBTN;
    private EditText emailET,passwordET;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        auth = FirebaseAuth.getInstance();
        // LOGIN
        loginBTN.setOnClickListener(v->{
            String email = emailET.getText().toString();
            String password = passwordET.getText().toString();
            if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                Toast.makeText(getApplicationContext(),"You have filled information incorrectly",Toast.LENGTH_LONG).show();
            }
            else if(password.length()<6){
                Toast.makeText(getApplicationContext(),"Password is too short",Toast.LENGTH_LONG).show();
            }
            else{
                loginUser(email,password);
            }
        });

    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(getApplicationContext(),"You have successfully logged in",Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });


    }

    private void initView() {
        registerBTN =  findViewById(R.id.Register);
        registerBTN.setOnClickListener(v->{
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);
        });
        loginBTN = findViewById(R.id.loginBTN);
        emailET = findViewById(R.id.editTextUserEmailLogIN);
        passwordET = findViewById(R.id.editTextPassWord);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser !=null){
            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
    }
}