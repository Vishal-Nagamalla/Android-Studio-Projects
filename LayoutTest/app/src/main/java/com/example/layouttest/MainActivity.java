package com.example.layouttest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button b1;
    Button b2;
    TextView t1;
    TextView t2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = findViewById(R.id.button1);
        b2 = findViewById(R.id.button2);
        t1 = findViewById(R.id.textView);
        t2 = findViewById(R.id.textView2);
    }

    public void b1(View v){
        int rnd = (int)(Math.random()*2);
        switch(rnd) {
            case 0:
                t1.setText("Clicked");
                break;
            case 1:
                t1.setText("Not Clicked");
                break;
        }
    }
    public void b2(View v){
        int rnd = (int)(Math.random()*2);
        switch(rnd) {
            case 0:
                t2.setText("Clicked");
                break;
            case 1:
                t2.setText("Not Clicked");
                break;
        }
    }
}