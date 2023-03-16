package com.example.calculator;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

import java.util.Objects;
public class MainActivity extends AppCompatActivity {
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, bAdd, bSub, bMulti, bDivide, bEquals, bClear;
    TextView numString;
    String firstNum;
    ArrayList<String> numSequence = new ArrayList<String>();
    double calc, x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = findViewById(R.id.b1);
        b2 = findViewById(R.id.b2);
        b3 = findViewById(R.id.b3);
        b4 = findViewById(R.id.b4);
        b5 = findViewById(R.id.b5);
        b6 = findViewById(R.id.b6);
        b7 = findViewById(R.id.b7);
        b8 = findViewById(R.id.b8);
        b9 = findViewById(R.id.b9);
        b0 = findViewById(R.id.b0);
        bAdd = findViewById(R.id.bAdd); bSub = findViewById(R.id.bSub);
        bMulti = findViewById(R.id.bMulti); bDivide = findViewById(R.id.bDivide);
        bEquals= findViewById(R.id.bEquals); bClear = findViewById(R.id.bClear);
        numString = findViewById(R.id.numString);
    }

    public void onClick(View v){
        Button button = (Button)v;
        firstNum = numString.getText().toString();
        if(firstNum.equals("0")){numString.setText("");}
        if(v.getId() == R.id.bClear){numString.setText(""); numSequence.clear();}
        else if(v.getId() == R.id.bEquals) {calculations();}
        else{numString.setText(numString.getText() + (String)button.getText());}
    }

    public void calculations(){
        try{
            String nums = numString.getText().toString();
            StringTokenizer numtoken = new StringTokenizer(nums, "+-*/=", true);
            while(numtoken.hasMoreTokens()){numSequence.add(numtoken.nextToken());}

            for(int i = 0; i < numSequence.size(); i++){
                if (numSequence.get(i).equals("*") || numSequence.get(i).equals("/")) {
                    if (numSequence.get(i).equals("*")) {
                        x = Double.parseDouble(numSequence.get(i - 1));
                        y = Double.parseDouble(numSequence.get(i + 1));
                        calc = x * y;
                    }
                    if(numSequence.get(i).equals("/")){
                        if (numSequence.get(i).equals("/") && numSequence.get(i+1).equals("0")) throw new ArithmeticException();
                        x = Double.parseDouble(numSequence.get(i - 1));
                        y = Double.parseDouble(numSequence.get(i + 1));
                        calc = x / y;
                    }
                    numSequence.remove(i + 1);
                    numSequence.set(i, String.valueOf(calc));
                    numSequence.remove(i - 1);
                    i = 0;
                }
                numString.setText(String.valueOf(calc));
                firstNum = String.valueOf(calc);
            }
            for(int i = 0; i < numSequence.size(); i++){
                if(numSequence.get(i).equals("+")|| numSequence.get(i).equals("-")){
                    if(numSequence.get(i).equals("+")) {
                        x = Double.parseDouble(numSequence.get(i - 1));
                        y = Double.parseDouble(numSequence.get(i + 1));
                        calc = x + y;
                    }
                    if(numSequence.get(i).equals("-")){
                        x = Double.parseDouble(numSequence.get(i - 1));
                        y = Double.parseDouble(numSequence.get(i + 1));
                        calc = x - y;
                    }
                    numSequence.remove(i + 1);
                    numSequence.set(i, String.valueOf(calc));
                    numSequence.remove(i - 1);
                    i = 0;
                }
                numString.setText(String.valueOf(calc));
                firstNum = String.valueOf(calc);
            }
        } catch (Exception e){numString.setText("Error");}
    }
}