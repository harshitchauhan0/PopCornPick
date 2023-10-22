package com.harshit.popcornpick.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.harshit.popcornpick.R;

public class LoginActivity extends AppCompatActivity {

    TextView registerBTN;
    AppCompatButton loginBTN;
    private EditText emailET,passwordET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        // LOGIN


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
}