package com.example.orientationandlistviewapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Game> gameInfo;
    ListView listViewM;
    TextView relDM;
    TextView pM;
    ListView listViewR;
    TextView relDMR;
    TextView pR;
    TextView bigR;
    public ArrayList<Game> arr1;
    int counter;
    public Game g9;
    final static String COUNTER_KEY = "abc123";
    public ArrayList<String> sA= new ArrayList<String>();

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(COUNTER_KEY, sA);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arr1 = new ArrayList<>();

        String TAG = "Test";
        Game g0 = new Game("PlayStation", "Release Date: 1994", "Price: $299", "The original PlayStation came out on December 3, 1994, and was known as the PSX. It was discontinued, on March 23, 2006, and was $299. They sold 102.49 million units with their best-selling game being Gran Turismo.", R.drawable.icon_pso);
        Game g1 = new Game("PlayStation 1", "Release Date: 2000","Price: $299", "The PlayStation one came out on July 7, 2000, and was known as the PS1. It was discontinued, on March 26, 2006, and was $299. They sold 28.15 million units.", R.drawable.icon_psone);
        Game g2 = new Game("PlayStation 2", "Release Date: 2000", "Price: $299", "The PlayStation two came out on March 4, 2000, and was known as the PS2. It was discontinued, on January 4, 2013, and was $299. They sold 155 million units with their best-selling game being Grand Theft Auto: San Andreas.", R.drawable.icon_pstwo);
        Game g3 = new Game("PlayStation Portable", "Release Date: 2004", "Price: $249.99", "The PlayStation Portable came out on December 12, 2004, and was known as the PSP. It was discontinued, on January 2014, and was $249.99. They sold 82 million units with their best-selling game being Grand Theft Auto: Liberty City Stories.", R.drawable.icon_psp);
        Game g4 = new Game("PlayStation 3", "Release Date: 2006", "Price: $499", "The PlayStation Three came out on November 11, 2006, and was known as the PS3. It was discontinued, on October 2016, and was $499. They sold 87.4 million units with their best-selling game being Grand Theft Auto V.", R.drawable.icon_psthree);
        Game g5 = new Game("PlayStation Vita", "Release Date: 2011","Price: $249.99", "The PlayStation Vita came out on December 17, 2011, and was known as the PS Vita. It was discontinued, on March 1, 2019, and was $249.99. They sold 16 million units with their best-selling game being Uncharted: Golden Abyss.", R.drawable.icon_psvita);
        Game g6 = new Game("PlayStation 4", "Release Date: 2013", "Price: $399", "The PlayStation Four came out on November 15, 2013, and was known as the PS4. It was discontinued and was $399. They sold 102.8 million units with their best-selling game being Uncharted 4: A Thief’s End.", R.drawable.icon_psfour);
        Game g7 = new Game("PlayStation Classic","Release Date: 2018", "Price: $9.99", "The PlayStation Classic came out on December 3, 2018, and was known as the PS4. It is $99.99.", R.drawable.icon_psclassic);
        Game g8 = new Game("PlayStation 5", "Release Date: 2020", "Price: $499", "The PlayStation Five came out on 12 November 2020 and was known as the PS5. It is $499. They sold 10 million as of July 18, 2021.", R.drawable.icon_psfive);
        arr1.add(g0); arr1.add(g1); arr1.add(g2); arr1.add(g3); arr1.add(g4); arr1.add(g5); arr1.add(g6); arr1.add(g7); arr1.add(g8);

        AdapterCustom adapter = new AdapterCustom(this, R.layout.adapter_custom, arr1);

        if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE) {
            listViewR = findViewById(R.id._l2);
            relDMR = findViewById(R.id._t3);
            pR = findViewById(R.id._t4);
            bigR = findViewById(R.id._t5);
            listViewR.setAdapter(adapter);
            if(savedInstanceState!=null&&savedInstanceState.getStringArrayList(COUNTER_KEY).size()>0)
            {
                relDMR.setText(savedInstanceState.getStringArrayList(COUNTER_KEY).get(0));
                pR.setText(savedInstanceState.getStringArrayList(COUNTER_KEY).get(1));
                AdapterCustom adapter1 = new AdapterCustom(this, R.layout.adapter_custom, arr1);
                for(int i = 0; i<arr1.size(); i++)
                {
                    if(savedInstanceState.getStringArrayList(COUNTER_KEY).get(1).equals(arr1.get(i).getCost()))
                    {
                        bigR.setText(arr1.get(i).getDescription());
                    }
                }
            }
            listViewR.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i==0) {
                        relDMR.setText(g0.getYear());
                        pR.setText(g0.getCost());
                        bigR.setText(g0.getDescription());
                        if(sA.size()>0) {
                            sA.set(0, g0.getYear());
                            sA.set(1, g0.getCost());
                            sA.set(2, g0.getDescription());
                            g9 = g0;
                        }
                        else if(sA.size()<1)
                        {
                            sA.add(g0.getYear());
                            sA.add(g0.getCost());
                            sA.add(g0.getDescription());
                            g9 = g0;
                        }
                    }
                    if(i==1) {
                        relDMR.setText(g1.getYear());
                        pR.setText(g1.getCost());
                        bigR.setText(g1.getDescription());
                        if(sA.size()>0) {
                            sA.set(0, g1.getYear());
                            sA.set(1, g1.getCost());
                            sA.set(2, g1.getDescription());
                            g9 = g1;
                        }
                        else if(sA.size()<1)
                        {
                            sA.add(g1.getYear());
                            sA.add(g1.getCost());
                            sA.add(g1.getDescription());
                            g9 = g1;
                        }
                    }
                    if(i==2) {
                        relDMR.setText(g2.getYear());
                        pR.setText(g2.getCost());
                        bigR.setText(g2.getDescription());
                        if(sA.size()>0) {
                            sA.set(0, g2.getYear());
                            sA.set(1, g2.getCost());
                            sA.set(2, g2.getDescription());
                            g9 = g2;
                        }
                        else if(sA.size()<1)
                        {
                            sA.add(g2.getYear());
                            sA.add(g2.getCost());
                            sA.add(g2.getDescription());
                            g9 = g2;
                        }
                    }
                    if(i==3) {
                        relDMR.setText(g3.getYear());
                        pR.setText(g3.getCost());
                        bigR.setText(g3.getDescription());
                        if(sA.size()>0) {
                            sA.set(0, g3.getYear());
                            sA.set(1, g3.getCost());
                            sA.set(2, g3.getDescription());
                            g9 = g3;
                        }
                        else if(sA.size()<1)
                        {
                            sA.add(g3.getYear());
                            sA.add(g3.getCost());
                            sA.add(g3.getDescription());
                            g9 = g3;
                        }
                    }
                    if(i==4) {
                        relDMR.setText(g4.getYear());
                        pR.setText(g4.getCost());
                        bigR.setText(g4.getDescription());
                        if(sA.size()>0) {
                            sA.set(0, g4.getYear());
                            sA.set(1, g4.getCost());
                            sA.set(2, g4.getDescription());
                            g9 = g4;
                        }
                        else if(sA.size()<1)
                        {
                            sA.add(g4.getYear());
                            sA.add(g4.getCost());
                            sA.add(g4.getDescription());
                            g9 = g4;
                        }
                    }
                    if(i==5) {
                        relDMR.setText(g5.getYear());
                        pR.setText(g5.getCost());
                        bigR.setText(g5.getDescription());
                        if(sA.size()>0) {
                            sA.set(0, g5.getYear());
                            sA.set(1, g5.getCost());
                            sA.set(2, g5.getDescription());
                            g9 = g5;
                        }
                        else if(sA.size()<1)
                        {
                            sA.add(g5.getYear());
                            sA.add(g5.getCost());
                            sA.add(g5.getDescription());
                            g9 = g5;
                        }
                    }
                    if(i==6) {
                        relDMR.setText(g6.getYear());
                        pR.setText(g6.getCost());
                        bigR.setText(g6.getDescription());
                        if(sA.size()>0) {
                            sA.set(0, g6.getYear());
                            sA.set(1, g6.getCost());
                            sA.set(2, g6.getDescription());
                            g9 = g6;
                        }
                        else if(sA.size()<1)
                        {
                            sA.add(g6.getYear());
                            sA.add(g6.getCost());
                            sA.add(g6.getDescription());
                            g9 = g6;
                        }
                    }
                    if(i==7) {
                        relDMR.setText(g7.getYear());
                        pR.setText(g7.getCost());
                        bigR.setText(g7.getDescription());
                        if(sA.size()>0) {
                            sA.set(0, g7.getYear());
                            sA.set(1, g7.getCost());
                            sA.set(2, g7.getDescription());
                            g9 = g7;
                        }
                        else if(sA.size()<1)
                        {
                            sA.add(g7.getYear());
                            sA.add(g7.getCost());
                            sA.add(g7.getDescription());
                            g9 = g7;
                        }
                    }
                    if(i==8) {
                        relDMR.setText(g8.getYear());
                        pR.setText(g8.getCost());
                        bigR.setText(g8.getDescription());
                        if(sA.size()>0) {
                            sA.set(0, g8.getYear());
                            sA.set(1, g8.getCost());
                            sA.set(2, g8.getDescription());
                            g9 = g8;
                        }
                        else if(sA.size()<1)
                        {
                            sA.add(g8.getYear());
                            sA.add(g8.getCost());
                            sA.add(g8.getDescription());
                            g9 = g8;
                        }
                    }

                }
            });
            if(!relDMR.getText().equals("Price")) {
                sA.add(relDMR.getText().toString());
                sA.add(pR.getText().toString());
                sA.add(bigR.getText().toString());
            }
        }
        else if(getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT) {
            listViewM = findViewById(R.id._l1);
            relDM = findViewById(R.id._t1);
            pM = findViewById(R.id._t2);
            listViewM.setAdapter(adapter);
            if(savedInstanceState!=null&&savedInstanceState.getStringArrayList(COUNTER_KEY).size()>0)
            {
                relDM.setText(savedInstanceState.getStringArrayList(COUNTER_KEY).get(0));
                pM.setText(savedInstanceState.getStringArrayList(COUNTER_KEY).get(1));
            }
            listViewM.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(i==0) {
                        relDM.setText(g0.getYear());
                        pM.setText(g0.getCost());
                        if(sA.size()>0) {
                            sA.set(0, g0.getYear());
                            sA.set(1, g0.getCost());
                            g9 = g0;
                        }
                        else if(sA.size()<1)
                        {
                            sA.add(g0.getYear());
                            sA.add(g0.getCost());
                            g9 = g0;
                        }
                    }
                    if(i==1) {
                        relDM.setText(g1.getYear());
                        pM.setText(g1.getCost());
                        if(sA.size()>0) {
                            sA.set(0, g1.getYear());
                            sA.set(1, g1.getCost());
                            g9 = g1;
                        }
                        else if(sA.size()<1)
                        {
                            sA.add(g1.getYear());
                            sA.add(g1.getCost());
                            g9 = g1;
                        }
                    }
                    if(i==2) {
                        relDM.setText(g2.getYear());
                        pM.setText(g2.getCost());
                        if(sA.size()>0) {
                            sA.set(0, g2.getYear());
                            sA.set(1, g2.getCost());
                            g9 = g2;
                        }
                        else if(sA.size()<1)
                        {
                            sA.add(g2.getYear());
                            sA.add(g2.getCost());
                            g9 = g2;
                        }
                    }
                    if(i==3) {
                        relDM.setText(g3.getYear());
                        pM.setText(g3.getCost());
                        if(sA.size()>0) {
                            sA.set(0, g3.getYear());
                            sA.set(1, g3.getCost());
                            g9 = g3;
                        }
                        else if(sA.size()<1)
                        {
                            sA.add(g3.getYear());
                            sA.add(g3.getCost());
                            g9 = g3;
                        }
                    }
                    if(i==4) {
                        relDM.setText(g4.getYear());
                        pM.setText(g4.getCost());
                        if(sA.size()>0) {
                            sA.set(0, g4.getYear());
                            sA.set(1, g4.getCost());
                            g9 = g4;
                        }
                        else if(sA.size()<1)
                        {
                            sA.add(g4.getYear());
                            sA.add(g4.getCost());
                            g9 = g4;
                        }
                    }
                    if(i==5) {
                        relDM.setText(g5.getYear());
                        pM.setText(g5.getCost());
                        if(sA.size()>0) {
                            sA.set(0, g5.getYear());
                            sA.set(1, g5.getCost());
                            g9 = g5;
                        }
                        else if(sA.size()<1)
                        {
                            sA.add(g5.getYear());
                            sA.add(g5.getCost());
                            g9 = g5;
                        }
                    }
                    if(i==6) {
                        relDM.setText(g6.getYear());
                        pM.setText(g6.getCost());
                        if(sA.size()>0) {
                            sA.set(0, g6.getYear());
                            sA.set(1, g6.getCost());
                            g9 = g6;

                        }
                        else if(sA.size()<1)
                        {
                            sA.add(g6.getYear());
                            sA.add(g6.getCost());
                            g9 = g6;
                        }
                    }
                    if(i==7) {
                        relDM.setText(g7.getYear());
                        pM.setText(g7.getCost());
                        if(sA.size()>0) {
                            sA.set(0, g7.getYear());
                            sA.set(1, g7.getCost());
                            g9 = g7;
                        }
                        else if(sA.size()<1)
                        {
                            sA.add(g7.getYear());
                            sA.add(g7.getCost());
                            g9 = g7;
                        }
                    }
                    if(i==8) {
                        relDM.setText(g8.getYear());
                        pM.setText(g8.getCost());
                        if(sA.size()>0) {
                            sA.set(0, g8.getYear());
                            sA.set(1, g8.getCost());
                            g9 = g8;
                        }
                        else if(sA.size()<1)
                        {
                            sA.add(g8.getYear());
                            sA.add(g8.getCost());
                            g9 = g8;
                        }
                    }
                }
            });
            sA.add(relDM.getText().toString());
            sA.add(pM.getText().toString());
            if(g9!=null)
                sA.add(g9.getDescription());
        }

    }
}