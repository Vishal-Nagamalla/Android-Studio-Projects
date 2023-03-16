package com.example.guessing;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    RadioGroup radioguess;
    RadioButton one;
    RadioButton two;
    Button play;
    ImageView imageView;
    TextView total;
    TextView result;
    boolean clicked;
    int pChoice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clicked = false;
        radioguess = findViewById(R.id.radioguess);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        play = findViewById(R.id.play);
        imageView = findViewById(R.id.imageView);
        total = findViewById(R.id.total);
        result = findViewById(R.id.result);

        imageView.setImageResource(R.drawable.icon_cpu);

        radioguess.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                clicked = true;
                total.setText("Total");
                result.setText("Result");
                imageView.setImageResource(R.drawable.icon_cpu);
                if(i==R.id.one) {
                    pChoice = 1;
                }
                else if(i==R.id.two)
                {
                    pChoice = 2;
                }
            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clicked)
                {
                    RunGame(pChoice, total, result);
                }
                else if(!clicked)
                {
                    Toast.makeText(MainActivity.this, "Please Select A Choice!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void RunGame(int choice, TextView tot1, TextView res1)
    {
        int rand = (int)(Math.random()*2+1);
        int total;
        if(rand==1)
        {
            imageView.setImageResource(R.drawable.icon_one);
            total = rand+choice;
            if(total%2==0)
            {
                res1.setText("You Win");
                Toast.makeText(MainActivity.this, "You Win!", Toast.LENGTH_SHORT).show();

            }
            else if(total%2==1)
            {
                res1.setText("Computer Wins");
                Toast.makeText(MainActivity.this, "The Computer Wins!", Toast.LENGTH_SHORT).show();
            }
            tot1.setText("Total is "+total);
        }
        else if(rand==2)
        {
            imageView.setImageResource(R.drawable.icon_two);
            total = rand+choice;
            if(total%2==0)
            {
                res1.setText("You Win");
                Toast.makeText(MainActivity.this, "You Win!", Toast.LENGTH_SHORT).show();
            }
            else if(total%2==1)
            {
                res1.setText("Computer Wins");
                Toast.makeText(MainActivity.this, "The Computer Wins!", Toast.LENGTH_SHORT).show();
            }
            tot1.setText("Total is "+total);
        }
    }
}