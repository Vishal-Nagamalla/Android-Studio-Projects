package com.example.ptest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivitytwo extends AppCompatActivity {
    TextView select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintwo);

        select = findViewById(R.id.select);
        Intent i = getIntent();
        select.setText(i.getExtras().getString("choice"));
    }
}
