package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button bluebutton, redbutton, changesize;
    private int unit;
    private float size;
    int temp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bluebutton = findViewById(R.id.bluebutton);
        redbutton = findViewById(R.id.redbutton);
        changesize = findViewById(R.id.changesize);
    }

    public void redcolor(View v) {
        redbutton.setTextColor(Color.RED);
    }

    public void bluecolor(View v) {
        bluebutton.setTextColor(Color.BLUE);
        redbutton.setText(bluebutton.getText());
    }

    public void setTextSize(View v){
        int rnd = (int)(Math.random()*100);
        if(rnd>temp) {
            changesize.setTextSize(TypedValue.COMPLEX_UNIT_DIP,rnd);
            temp = rnd;
        }
    }
}