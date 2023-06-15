package com.example.fragmentdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b = findViewById(R.id.id_button);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        TopFragment topFragment = new TopFragment();
        fragmentTransaction.add(R.id.id_top_frame, topFragment);

        fragmentTransaction.commit();

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                BotFragment botFragment = new BotFragment();
                fragmentTransaction1.add(R.id.id_bot_frame, botFragment);

                fragmentTransaction1.commit();
                }
        });

    }
}