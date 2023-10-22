package com.harshit.popcornpick.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;

import com.harshit.popcornpick.R;

public class IntroductionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        AppCompatButton button =findViewById(R.id.getIN);
        button.setOnClickListener(v->{
            Intent i = new Intent(IntroductionActivity.this,LoginActivity.class);
            startActivity(i);
        });
    }
}