package com.example.widgetquiz;

import androidx.appcompat.app.AppCompatActivity;

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

public class aneMainActivity extends AppCompatActivity {
    TextView a;
    EditText b;
    TextView c;
    EditText d;
    Button e;
    Switch f;
    Button g;
    String h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        a = findViewById(R.id.t1);
        b = findViewById(R.id.e1);
        c = findViewById(R.id.t2);
        d = findViewById(R.id.e2);
        e = findViewById(R.id.b1);
        f = findViewById(R.id.s1);
        g = findViewById(R.id.b2);

        f.setClickable(false);
        b.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                h = b.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        d.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(b.getText().toString().equals(d.getText().toString()))
                {
                    f.setText("MATCH");
                    f.setChecked(true);
                }
                else
                {
                    f.setText("NO MATCH");
                    f.setChecked(false);
                }
            }
        });
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.setText(h);
            }
        });



    }
}