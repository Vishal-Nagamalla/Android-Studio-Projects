package com.example.imageradiopractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        imageView.setImageResource(R.drawable.harry_potter_logo);

        radioGroup = findViewById(R.id.radiogroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.Harry){
                    imageView.setImageResource(R.drawable.icon_harry);
                    Toast myToast = Toast.makeText(MainActivity.this, "You Selected Harry!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                if(checkedId == R.id.Hermione){
                    imageView.setImageResource(R.drawable.icon_hermione);
                    Toast myToast = Toast.makeText(MainActivity.this, "You Selected Hermione!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                if(checkedId == R.id.Ron){
                    imageView.setImageResource(R.drawable.icon_ron);
                    Toast myToast = Toast.makeText(MainActivity.this, "You Selected Ron!", Toast.LENGTH_SHORT);
                    myToast.show();
                }
            }
        });
    }
}