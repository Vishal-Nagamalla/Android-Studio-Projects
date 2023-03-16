package com.example.buttontest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button color;
    Button rnd;
    Button swap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        color = findViewById(R.id.color);
        rnd = findViewById(R.id.rnd);
        swap = findViewById(R.id.swap);
    }

    public void color(View v){
        int random = (int)(Math.random()*3);

        switch(random) {
            case 0:
                color.setTextColor(Color.RED);
                break;
            case 1:
                color.setTextColor(Color.GREEN);
                break;
            case 2:
                color.setTextColor(Color.BLUE);
                break;
        }
    }

    public void rnd(View V){
        int rndsize = (int)(Math.random()*50) + 1;
        Button rnd = (Button)findViewById(R.id.rnd);
    }

    public void swap(View V){
        Button color = (Button)findViewById(R.id.color);
        color.setText("Rnd Size");

        Button rnd = (Button)findViewById(R.id.rnd);
        rnd.setText("Change Color");
    }
}