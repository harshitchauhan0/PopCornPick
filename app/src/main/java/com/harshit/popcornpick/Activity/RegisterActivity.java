package com.harshit.popcornpick.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.harshit.popcornpick.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameET,emailET,passwordET;
    private AppCompatButton signupBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        //REGISTER ACCOUNT
    }

    private void initView() {

        signupBTN = findViewById(R.id.signupbtn);
        signupBTN.setOnClickListener(v->{
            Intent i = new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(i);
        });
        usernameET = findViewById(R.id.registeretusername);
        emailET = findViewById(R.id.registeretemail);
        passwordET = findViewById(R.id.registeretpsd);
    }
}