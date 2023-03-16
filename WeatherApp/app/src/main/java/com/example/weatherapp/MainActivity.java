package com.example.weatherapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView l1;
    TextView long1;
    TextView town1;
    TextView quote;

    TextView time1;TextView time2;TextView time3;TextView time4;TextView time5;

    TextView temp1;TextView temp2;TextView temp3;TextView temp4;TextView temp5;

    TextView des1;TextView des2;TextView des3;TextView des4;TextView des5;

    ImageView i1;ImageView i2;ImageView i3;ImageView i4;ImageView i5;

    Button b1;
    EditText e1;
    String zipcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        l1 = findViewById(R.id.l1);
        long1 = findViewById(R.id.long1);
        quote = findViewById(R.id.quote);

        time1 = findViewById(R.id.time1);time2 = findViewById(R.id.time2);time3 = findViewById(R.id.time3);time4 = findViewById(R.id.time4);time5 = findViewById(R.id.time5);

        temp1 = findViewById(R.id.temp1);temp2 =  findViewById(R.id.temp2);temp3 = findViewById(R.id.temp3);temp4 = findViewById(R.id.temp4);temp5 = findViewById(R.id.temp5);

        des1 = findViewById(R.id.des1);des2 = findViewById(R.id.des2);des3 = findViewById(R.id.des3);des4 = findViewById(R.id.des4);des5 = findViewById(R.id.des5);

        i1 = findViewById(R.id.imageView);i2 = findViewById(R.id.imageView2);i3 = findViewById(R.id.imageView3);i4 = findViewById(R.id.imageView4);i5 = findViewById(R.id.imageView5);

        town1 = findViewById(R.id.town1);

        b1 = findViewById(R.id.button);
        e1 = findViewById(R.id.editTextNumber);

        i1.setImageResource(R.drawable.nothing);i2.setImageResource(R.drawable.nothing);i3.setImageResource(R.drawable.nothing);i4.setImageResource(R.drawable.nothing);i5.setImageResource(R.drawable.nothing);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                l1.setVisibility(View.VISIBLE);
                town1.setVisibility(View.VISIBLE);
                Thread t1 = new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        zipcode = e1.getText().toString();
                        Log.d("TAG", "Zip: "+zipcode);
                        runThread runT = new runThread(zipcode);
                    }
                });
                t1.start();
            }
        });
    }

    public class runThread extends Thread{
        @RequiresApi(api = Build.VERSION_CODES.O)
        public runThread(String zips) {
            try{

                Log.d("TAG", "STARTED...");
                String zip = "";
                if(zips.length()>0)
                    zip = zips;
                String whole = "";

                URL url1 = new URL("http://api.openweathermap.org/geo/1.0/zip?zip="+zip+"&appid=a8b3adaa8d7e46130bae0d4f7190ac3f");
                HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();
                InputStream input = connection1.getInputStream();
                BufferedReader read1 = new BufferedReader(new InputStreamReader(input));
                String lang = read1.readLine();
                while( lang!= null)
                {
                    whole=whole+lang;
                    lang = read1.readLine();
                }
                JSONObject longLat = new JSONObject(whole);
                String latitude= longLat.getString("lat");
                String longitude= longLat.getString("lon");
                String town = longLat.getString("name");

                Log.d("TAG", "STARTED 2...");
                String whole2 = "";
                URL url2 = new URL("https://api.openweathermap.org/data/2.5/onecall?lat="+latitude+"&lon="+longitude+"&exclude=minutely,daily&appid=a8b3adaa8d7e46130bae0d4f7190ac3f");
                HttpURLConnection connection2 = (HttpURLConnection) url2.openConnection();
                InputStream input2 = connection2.getInputStream();
                BufferedReader read2 = new BufferedReader(new InputStreamReader(input2));
                String lang2 = read2.readLine();
                while( lang2!= null)
                {
                    whole2=whole2+lang2;
                    lang2 = read2.readLine();
                }

                JSONObject longLat2 = new JSONObject(whole2);
                String tempe = longLat2.getJSONArray("hourly").getJSONObject(1).getString("temp");
                double tempeR = toNorm(Double.parseDouble(tempe));

                String time = longLat2.getJSONArray("hourly").getJSONObject(1).getString("dt");
                String timeR1 = toEst(Integer.parseInt(time));

                String tempe2 = longLat2.getJSONArray("hourly").getJSONObject(0).getString("temp");
                double tempeR1 = toNorm(Double.parseDouble(tempe2));
                String t2 = longLat2.getJSONArray("hourly").getJSONObject(0).getString("dt");
                String timeR2 = toEst(Integer.parseInt(t2));

                String tempe3 = longLat2.getJSONArray("hourly").getJSONObject(2).getString("temp");
                double tempeR2 = toNorm(Double.parseDouble(tempe3));
                String t3 = longLat2.getJSONArray("hourly").getJSONObject(2).getString("dt");
                String timeR3 = toEst(Integer.parseInt(t3));

                String tempe4 = longLat2.getJSONArray("hourly").getJSONObject(3).getString("temp");
                double tempeR3 = toNorm(Double.parseDouble(tempe4));
                String t4 = longLat2.getJSONArray("hourly").getJSONObject(3).getString("dt");
                String timeR4 = toEst(Integer.parseInt(t4));

                String tempe5 = longLat2.getJSONArray("hourly").getJSONObject(4).getString("temp");
                double tempeR4 = toNorm(Double.parseDouble(tempe5));
                String t5 = longLat2.getJSONArray("hourly").getJSONObject(4).getString("dt");
                String timeR5 = toEst(Integer.parseInt(t5));


                JSONArray hourlyArr = longLat2.getJSONArray("hourly");
                ArrayList<String> strArr = new ArrayList<String>();
                for(int i = 0; i < hourlyArr.length(); i++) {
                    JSONObject jOBJ = hourlyArr.getJSONObject(i);
                    JSONArray jArr = jOBJ.getJSONArray("weather");
                    for(int j = 0; j < jArr.length(); j++) {
                        JSONObject jOBJ2 = jArr.getJSONObject(j);
                        strArr.add(jOBJ2.getString("description"));
                        if(i==0)
                        {
                            des1.setText(strArr.get(i));
                            if(strArr.get(i).equals("heavy intensity rain")||strArr.get(i).equals("moderate rain")||strArr.get(i).equals("light rain"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(strArr.get(0).equals("heavy intensity rain")){
                                            quote.setText("High intensity rain may come and go, but true strength and resilience stays put.");
                                        }
                                        else if(strArr.get(0).equals("moderate rain")){
                                            quote.setText("Moderate rain brings growth, nourishment, and a reminder that sometimes, a little goes a long way.");
                                        }
                                        else if(strArr.get(0).equals("light rain")){
                                            quote.setText("Light rain is a gentle reminder that even the smallest droplets can make a big impact.");
                                        }
                                        i1.setImageResource(R.drawable.rain);
                                    }
                                });
                            }
                            else if(strArr.get(i).equals("clear sky"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        i1.setImageResource(R.drawable.clear);
                                        quote.setText("A clear sky is a reminder to strive for clarity in our own lives and to appreciate the beauty of simplicity.");
                                    }
                                });
                            }
                            else if(strArr.get(i).equals("scattered clouds")||strArr.get(i).equals("broken clouds")||strArr.get(i).equals("overcast clouds")||strArr.get(i).equals("few clouds"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(strArr.get(0).equals("scattered clouds")){
                                            quote.setText("Scattered clouds remind us that life is unpredictable, but it's the journey and not the destination that counts.");
                                        }
                                        else if(strArr.get(0).equals("broken clouds")){
                                            quote.setText("Broken clouds remind us that even on the darkest days, there are rays of hope shining through.");
                                        }
                                        else if(strArr.get(0).equals("overcast clouds")){
                                            quote.setText("Overcast clouds remind us that every day has its own mood, and it's important to find the silver linings even in the gloomiest of weather.");
                                        }
                                        else if(strArr.get(0).equals("few clouds")){
                                            quote.setText("Few clouds remind us that even in the midst of uncertainty, there are moments of calm and beauty to be found.");
                                        }
                                        i1.setImageResource(R.drawable.cloudy);
                                    }
                                });
                            }
                            else if(strArr.get(i).equals("light snow")||strArr.get(i).equals("snow")||strArr.get(i).equals("heavy intensity snow"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(strArr.get(0).equals("light snow")){
                                            quote.setText("Light snow is a reminder that even the smallest things can create a big change, and that something as simple as a snowflake can make the world more beautiful.");
                                        }
                                        else if(strArr.get(0).equals("snow")){
                                            quote.setText("Snow is a reminder that even the darkest and coldest of days can bring new beginnings, and that change can be beautiful.");
                                        }
                                        else if(strArr.get(0).equals("heavy intensity snow")){
                                            quote.setText("Heavy intensity snow reminds us that sometimes in life we face challenges and obstacles, but it's how we weather them that defines us.");
                                        }
                                        i1.setImageResource(R.drawable.goooodd);
                                    }
                                });
                            }

                        }
                        if(i==1)
                        {
                            des2.setText(strArr.get(i));
                            if(strArr.get(i).equals("heavy intensity rain")||strArr.get(i).equals("moderate rain")||strArr.get(i).equals("light rain"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        i2.setImageResource(R.drawable.rain);
                                    }
                                });
                            }
                            else if(strArr.get(i).equals("clear sky"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        i2.setImageResource(R.drawable.clear);
                                    }
                                });
                            }
                            else if(strArr.get(i).equals("scattered clouds")||strArr.get(i).equals("broken clouds")||strArr.get(i).equals("overcast clouds")||strArr.get(i).equals("few clouds"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        i2.setImageResource(R.drawable.cloudy);
                                    }
                                });
                            }
                            else if(strArr.get(i).equals("light snow")||strArr.get(i).equals("snow")||strArr.get(i).equals("heavy intensity snow"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        i2.setImageResource(R.drawable.goooodd);
                                    }
                                });
                            }
                        }
                        if(i==2)
                        {
                            des3.setText(strArr.get(i));
                            if(strArr.get(i).equals("heavy intensity rain")||strArr.get(i).equals("moderate rain")||strArr.get(i).equals("light rain"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        i3.setImageResource(R.drawable.rain);
                                    }
                                });
                            }
                            else if(strArr.get(i).equals("clear sky"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        i3.setImageResource(R.drawable.clear);
                                    }
                                });
                            }
                            else if(strArr.get(i).equals("scattered clouds")||strArr.get(i).equals("broken clouds")||strArr.get(i).equals("overcast clouds")||strArr.get(i).equals("few clouds"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        i3.setImageResource(R.drawable.cloudy);
                                    }
                                });
                            }
                            else if(strArr.get(i).equals("light snow")||strArr.get(i).equals("snow")||strArr.get(i).equals("heavy intensity snow"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        i3.setImageResource(R.drawable.goooodd);
                                    }
                                });
                            }

                        }
                        if(i==3)
                        {
                            des4.setText(strArr.get(i));
                            if(strArr.get(i).equals("heavy intensity rain")||strArr.get(i).equals("moderate rain")||strArr.get(i).equals("light rain"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        i4.setImageResource(R.drawable.rain);
                                    }
                                });
                            }
                            else if(strArr.get(i).equals("clear sky"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        i4.setImageResource(R.drawable.clear);
                                    }
                                });
                            }
                            else if(strArr.get(i).equals("scattered clouds")||strArr.get(i).equals("broken clouds")||strArr.get(i).equals("overcast clouds")||strArr.get(i).equals("few clouds"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        i4.setImageResource(R.drawable.cloudy);
                                    }
                                });
                            }
                            if(strArr.get(i).equals("light snow")||strArr.get(i).equals("snow")||strArr.get(i).equals("heavy intensity snow"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        i4.setImageResource(R.drawable.goooodd);
                                    }
                                });
                            }

                        }


                        if(i==4)
                        {
                            des5.setText(strArr.get(i));
                            if(strArr.get(i).equals("heavy intensity rain")||strArr.get(i).equals("moderate rain")||strArr.get(i).equals("light rain"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        i5.setImageResource(R.drawable.rain);
                                    }
                                });
                            }
                            else if(strArr.get(i).equals("clear sky"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        i5.setImageResource(R.drawable.clear);
                                    }
                                });
                            }
                            else if(strArr.get(i).equals("scattered clouds")||strArr.get(i).equals("broken clouds")||strArr.get(i).equals("overcast clouds")||strArr.get(i).equals("few clouds"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        i5.setImageResource(R.drawable.cloudy);
                                    }
                                });
                            }
                            if(strArr.get(i).equals("light snow")||strArr.get(i).equals("snow")||strArr.get(i).equals("heavy intensity snow"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        i5.setImageResource(R.drawable.goooodd);
                                    }
                                });
                            }
                        }
                    }
                }

                time1.setText("Time: \n"+ timeR2);
                temp1.setText("Temperature: \n"+tempeR1+" Fahrenheit");
                time2.setText("Time: \n"+ timeR1);
                temp2.setText("Temperature: \n"+tempeR+" Fahrenheit");
                time3.setText("Time: \n"+ timeR3);
                temp3.setText("Temperature: \n"+tempeR2+" Fahrenheit");
                time4.setText("Time: \n"+ timeR4);
                temp4.setText("Temperature: \n"+tempeR3+" Fahrenheit");
                time5.setText("Time: \n"+ timeR5);
                temp5.setText("Temperature: \n"+tempeR4+" Fahrenheit");

            } catch (MalformedURLException e) {
                Log.d("TAG", "ERROR");
                e.printStackTrace();
            }catch(FileNotFoundException e)
            {
            }
            catch (IOException e) {
                Log.d("TAG", "ERROR");
                e.printStackTrace();
            } catch (JSONException e) {
                Log.d("TAG", "ERROR");
                e.printStackTrace();
            }
        }
        public double toNorm(double a)
        {
            double c = ((a-273.15)*1.8)+32;
            double b = c*100;
            int d = (int)(b);
            double e = (double)(d)/100;
            return e;
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        public String toEst(int conversion)
        {
            Instant instant = Instant.ofEpochSecond(conversion);
            ZonedDateTime ESTTime = instant.atZone(ZoneId.of("EST"));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd h:mm");
            Log.d("TAG", "TIME: "+ESTTime);
            return dtf.format(ESTTime);
        }
    }
}