package com.example.final3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText skuId;
    Button search;
    Button add;
    Button view;
    TextView compName;
    TextView compDate;
    ConstraintLayout layoutMain;

    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        skuId = findViewById(R.id.skuEnter);
        search = findViewById(R.id.search);
        compName = findViewById(R.id.compName);
        compDate = findViewById(R.id.compDate);
        layoutMain = findViewById(R.id.layoutMain);
        add = findViewById(R.id.add);
        view = findViewById(R.id.view);
        mDatabaseHelper = new DatabaseHelper(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = skuId.getText().toString();
                if (skuId.length() != 0) {
                    AddData(newEntry);
                    skuId.setText("");
                } else {
                    toastMessage("You must put something in the text field!");
                }
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListDataActivity.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncThread1 thread = new AsyncThread1();
                thread.execute();
            }
        });

        Intent intent = getIntent();
        String itemName = intent.getStringExtra("itemName");
        if (itemName != null) {
            EditText searchbar = findViewById(R.id.skuEnter);
            searchbar.setText(itemName);
        }
    }

    public void AddData(String newEntry) {
        boolean insertData = mDatabaseHelper.addData(newEntry);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public class AsyncThread1 extends AsyncTask<String, String, String> {
        public String runUrl(String s) {
            String data = "";
            try {
                URL url;
                url = new URL(s);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                String sAuthCode ="eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiNjk1ZjgwMWNjZjUxODM3ODZlNTg0NjhiMDI1YzE0Yjc0MmQ0MmI3OWZjM2E2OTQyN2IzODA4ODgxZmE1ZjEyMzM2ZmI2YTM0MWEzNTRlZmUiLCJpYXQiOjE2NTQ5OTI4MTQuMzE5Nzg3LCJuYmYiOjE2NTQ5OTI4MTQuMzE5Nzg5OSwiZXhwIjoyNjAxNzY3NjE0LjMxMzUwNjEsInN1YiI6IjEwMzEzMyIsInNjb3BlcyI6W119.igRnrlw6TQZF7ais_7a6u16TFpy4hvTM_QNEDp_TooNBuVtcfsl1xCjG9nzivtnGKv0KxCgapuX3Ml0O7-xQkAgGX50XbPxKbJnBlc-i3ixcZdweoW2CDxsZBHe2wm69vOzLPGDx76A4y_LydKl3_jcEJO-DrdLr7GaWZAPQvtamu-w2QfHiw33a49hr8tj-Q6ZPazWIF54d_SUciHsQYYWmpvazdS6LAWwpsCu_b8rufsiZXWW6KUE4lNiBPfKpCAEUckNyNo_xuJt0Al_m85BGupAi4fqdXXYAujsD9XWzEP30b1z4dGT1yrlOyShvyBfeACVl-jhf2ERQ-4MHRox9dMFV7391E1BxuMfYP9vEOjnwms5ibMN5skZDkmfrHMNeK4uY69vonMlLoBHm09TAXbYKowfMl_CNhI1A0PbSqRmJK2uvaRm9L3kp9FJOZXpYAleYg9iLdTqQ5QWWMdE8AsZKBiY3tDl1_kBh_VaSiE3qw54QmBB73vbIA4Xnje_n5dnNwqDMiaAvEE-sHqQ11oxtzHeXDdh2hhFXtBliJ6qo-OFe0-9gTmAp4zADj-RGJ6rLuuBfWpX1R_fZedM1RZEkHrrsrrNKYtGyCrFSc_xSnyXLGXgG-48DbnA4_yW2Apyq4UzoPgczokzJBAy3aqK4_q2CZNh-mox2D1M";

                connection.setRequestProperty("Authorization" , "Bearer " + sAuthCode );
                InputStream stream;
                BufferedReader bufferedReader;

                int responseCode = connection.getResponseCode();
                stream = connection.getInputStream();

                bufferedReader = new BufferedReader(new InputStreamReader(stream));

                String line = "";
                while(line != null) {
                    line = bufferedReader.readLine();
                    if ( line != null ) {
                        data = data + line;
                    }
                }

            }catch (MalformedURLException e) {
                data = "";
                e.printStackTrace();
            }
            catch (IOException e) {
                data = "";
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected String doInBackground(String... strings) {
            String data = "";
            String res1 = runUrl( "https://www.robotevents.com/api/v2/events?sku%5B%5D=" + skuId.getText() + "&myEvents=false" );

            if (res1 != null && res1 != "") {
                JSONObject jsonLoc;
                String loc = res1;
                int id = 0;
                try {
                    jsonLoc = new JSONObject(loc);
                    JSONArray datas = jsonLoc.getJSONArray("data");
                    JSONObject jsonObj = datas.getJSONObject(0);
                    id = jsonObj.getInt("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                data = res1;
            }

            return data;
        }

        protected void onPostExecute(String s) {
            if (s == null || s == "" )
                return;

            String info = s;

            JSONObject jsonLoc;
            String loc = info;
            int id = 0;
            String name = "";
            String date = "";

            try {
                jsonLoc = new JSONObject(loc);
                JSONArray datas = jsonLoc.getJSONArray("data");
                JSONObject jsonObj = datas.getJSONObject(0);
                id = jsonObj.getInt("id");
                name = jsonObj.getString("name");
                date = jsonObj.getString("start");

                String[] tempo = date.split("T");
                String date2 = tempo[0];
                compDate.setText("Date: " + date2);
                compName.setText("Name: " + name);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}