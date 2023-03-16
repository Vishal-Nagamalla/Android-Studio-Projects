package com.example.day1buttondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //Declaration of button
    Button bluepill;
    Button redpill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluepill = findViewById(R.id.bluepill);
        bluepill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluepill.setText("Welcome to the world of the truth");
            }
        });
        redpill = findViewById(R.id.redpill);

    }

    /*public void Bluebuttonclicked(View x){
        bluepill.setText("Welcome to the world of the truth");
    }

    public void Redbuttonclicked(View y){
        redpill.setText("Welcome to the world of the ignorance");
    }*/
}