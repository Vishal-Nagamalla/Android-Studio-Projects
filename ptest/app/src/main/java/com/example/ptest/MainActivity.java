package com.example.ptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ConstraintLayout background;
    RadioButton toast, color, upper;
    TextView name;
    Button run, launch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        toast = findViewById(R.id.toast);
        color =  findViewById(R.id.color);
        upper =  findViewById(R.id.upper);
        run = findViewById(R.id.run);
        launch = findViewById(R.id.launch);
        background = findViewById(R.id.layout);

        run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toast.isChecked())
                {
                    Toast.makeText(MainActivity.this, "Toast selected", Toast.LENGTH_SHORT).show();
                }
                else if(color.isChecked())
                {
                    int i = (int) (Math.random()*3);
                    if(i == 1) {background.setBackgroundColor(Color.GREEN);}
                    else if(i==2) {background.setBackgroundColor(Color.RED);}
                    else {background.setBackgroundColor(Color.BLUE);}
                }
                else if(upper.isChecked()) {name.setText(name.getText().toString().toUpperCase());}
            }
        });

        launch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MainActivitytwo.class);

                if(toast.isChecked())
                    i.putExtra("choice" , "You selected Toast");

                else if(color.isChecked())
                    i.putExtra("choice" , "You selected color");

                else if(upper.isChecked())
                    i.putExtra("choice" , "You selected upper");

                startActivity(i);
            }
        });
    }
}