package com.example.imageradiorps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public clasMainActivity extends AppCompatActivity {
    Button play;
    TextView winorlose;
    TextView score;
    ImageView imageView;
    RadioGroup volume;
    RadioGroup rps;
    public int choice;
    public int playerscore;
    public int cpuscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play = findViewById(R.id.play);
        winorlose = findViewById(R.id.winorlose);
        score = findViewById(R.id.score);
        imageView = findViewById(R.id.imageView);
        volume = findViewById(R.id.volume);
        rps = findViewById(R.id.rps);

        volume.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.volume100){
                    Toast myToast = Toast.makeText(MainActivity.this, "WARNING! volume can damage hearing", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        });

        rps.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rock){
                    choice = 0;
                }
                if(checkedId == R.id.paper){
                    choice = 1;
                }
                if(checkedId == R.id.scissor){
                    choice = 2;
                }
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rnd = (int)(Math.random()*3);
                switch (rnd){
                    case 0:
                        iimmageView.setImageResource(R.drawable.icon_rock);
                        break;

                    case 1:
                        imageView.setImageResource(R.drawable.icon_paper);
                        break;

                    case 2:
                        imageView.setImageResource(R.drawable.icon_scissor);
                        break;
                }

                if(choice == 0){
                    if(rnd == 0){
                        winorlose.setText("TIE");
                    }
                    else if(rnd == 1){
                        winorlose.setText("YOU LOSE");
                        cpuscore++;
                    }
                    else if(rnd == 2){
                        winorlose.setText("YOU WIN");
                        playerscore++;
                    }
                }
                else if(choice == 1){
                    if(rnd == 0) {
                        winorlose.setText("YOU WIN");
                        playerscore++;
                    }
                    else if(rnd == 1){
                        winorlose.setText("TIE");
                    }
                    else if(rnd == 2){
                        winorlose.setText("YOU LOSE");
                        cpuscore++;
                    }
                }
                else if(choice == 2){
                    if(rnd == 0) {
                        winorlose.setText("YOU LOSE");
                        cpuscore++;
                    }
                    else if(rnd == 1){
                        winorlose.setText("YOU WIN");
                        playerscore++;
                    }
                    else if(rnd == 2){
                        winorlose.setText("TIE");
                    }
                }

                score.setText("Player Score: " + playerscore + " CPU Score: " + cpuscore);
            }
        });
    }
}