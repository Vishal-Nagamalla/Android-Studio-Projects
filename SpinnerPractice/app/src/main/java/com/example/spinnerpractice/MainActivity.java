package com.example.spinnerpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView fullname;
    EditText name;
    Button addname;
    Spinner prefix;
    Spinner spinner2;
    ArrayList<String> prefixname;
    ArrayList<String> completename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fullname=findViewById(R.id.fullname);
        name=findViewById(R.id.name);
        addname=findViewById(R.id.addname);
        prefix=findViewById(R.id.spinner_prefix);
        spinner2=findViewById(R.id.spinner2);

        prefixname = new ArrayList<>();
        prefixname.add("Mr. ");
        prefixname.add("Ms. ");
        prefixname.add("Mrs. ");
        prefixname.add("Dr. ");
        completename = new ArrayList<>();

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, prefixname);
        prefix.setAdapter(spinnerAdapter);

        prefix.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                addname.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fullname.setText(prefixname.get(position) + name.getText());
                        completename.add((String)fullname.getText());
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, completename);
        spinner2.setAdapter(spinnerAdapter2);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}