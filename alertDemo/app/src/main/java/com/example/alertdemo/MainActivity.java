package com.example.alertdemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbuilder = new AlertDialog.Builder(MainActivity.this);

                alertbuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Confirm Pushed", Toast.LENGTH_SHORT).show();
                    }
                });

                alertbuilder.setNegativeButton("Push", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(MainActivity.this, "Deny Pushed", Toast.LENGTH_SHORT).show();
                    }
                });

                //set title
                alertbuilder.setTitle("This is the title");

                //set msg
                alertbuilder.setMessage("This is the message for the user!");

                AlertDialog myAlert = alertbuilder.create();
                myAlert.show();
            }
        });
    }
}