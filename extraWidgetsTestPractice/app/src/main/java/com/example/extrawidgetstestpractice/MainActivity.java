package com.example.extrawidgetstestpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    Switch s1, s2, s3;
    TextView color, verified, database, size;
    EditText verify, check;
    Button verifyButton, checkButton;
    SeekBar seekBar;

    ArrayList<String> verifiedEmails = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        s1 = findViewById(R.id.switch1);
        s2 = findViewById(R.id.switch2);
        s3 = findViewById(R.id.switch3);
        color = findViewById(R.id.textColor);
        verified = findViewById(R.id.displayVerified);
        database = findViewById(R.id.textDatabase);
        size = findViewById(R.id.textSize);
        verify = findViewById(R.id.emailVerified);
        check = findViewById(R.id.checkEmail);
        verifyButton = findViewById(R.id.buttonVerify);
        checkButton = findViewById(R.id.buttonCheck);
        seekBar = findViewById(R.id.seekBar);


        s1.setOnCheckedChangeListener(this);
        s2.setOnCheckedChangeListener(this);
        s3.setOnCheckedChangeListener(this);

        verifiedEmails.add("shahvivaan7@gmail.com");
        verifiedEmails.add("monkey@gmail.com");
        verifiedEmails.add("monkeyTwo@gmail.com");
        verifiedEmails.add("10022633@sbstudents.com");


        verify.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                verified.setText("Not Verified");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = verify.getText().toString();

                if(email.contains("@") && email.contains(".com") && (email.indexOf("@") < email.indexOf(".com")))
                {
                    verified.setText("verified");
                }
            }
        });

        check.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                database.setText("Not in database");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(String s : verifiedEmails)
                {
                    if(check.getText().toString().equalsIgnoreCase(s))
                    {
                        database.setText("In database");
                        break;
                    }
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                size.setTextSize(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
        Switch switchWidget = (Switch)compoundButton;


        if(s1.isChecked() && s2.isChecked() && s3.isChecked())
        {
            color.setTextColor(Color.BLUE);
        }
        else if(s1.isChecked() && !s2.isChecked() && s3.isChecked())
        {
            color.setTextColor(Color.RED);
        }
        else if(s3.isChecked() && !s1.isChecked() && !s2.isChecked())
        {
            color.setTextColor(Color.GREEN);
        }



    }
}