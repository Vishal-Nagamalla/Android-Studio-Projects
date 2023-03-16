package com.example.gpsapp;

import static java.util.Locale.US;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements LocationListener {
    TextView text1;
    TextView text2;
    TextView text3;
    TextView text4;
    TextView text5;
    double a;
    double c;
    double dist;
    int reqCode;
    int count;
    long time1;
    long time2;
    long timeR;
    long timeM;
    long timeAdd;
    long loopT;
    String addyMost = "";
    ArrayList<Addy> saveArr = new ArrayList<>();
    ArrayList<Location> arr = new ArrayList<>();
    ArrayList<Address> add = new ArrayList<>();

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LocationManager e = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (requestCode == reqCode) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                e.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, this);

            } else {
                text1 = findViewById(R.id.t3);
                text2 = findViewById(R.id.t4);
                text3 = findViewById(R.id.t5);

                text1.setVisibility(View.INVISIBLE);
                text3.setVisibility(View.INVISIBLE);
                text2.setText("SORRY! APP CANNOT BE USED WITHOUT LOCATION PERMISSIONS! PLEASE ALLOW LOCATION!");
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loopT = 0;
        timeM = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationManager e = (LocationManager) getSystemService(LOCATION_SERVICE);

        reqCode = 123;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, reqCode);
            return;
        }
        else {
            e.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 5, this);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(@NonNull String provider) {}

    @Override
    public void onProviderDisabled(@NonNull String provider) {}

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            time1 = SystemClock.elapsedRealtime();
            Geocoder geoCoder3 = new Geocoder(this, US);

            text1 = findViewById(R.id.t3);
            text2 = findViewById(R.id.t4);
            text3 = findViewById(R.id.t5);
            text4 = findViewById(R.id.t6);
            text5 = findViewById(R.id.textView);
            a = location.getLatitude();
            c = location.getLongitude();
            double aChange = a*100.0;
            int aC = (int)aChange;
            double aD = (double)aC/100;

            double cChange = c*100.0;
            int Cc = (int)cChange;
            double cTotal = (double)Cc/100.0;

            String lat1 = ("Latitude: "+aD);
            String long1 =("Longitude: "+cTotal);
            text1.setText(lat1);
            text2.setText(long1);
            List<Address> first = (geoCoder3.getFromLocation(a, c, 1));
            int used = 0;
            for(int i = 0; i<add.size(); i++) {
                if(add.get(i)==first.get(0))
                    used++;
            }

            if(used==0) {
                add.add(first.get(0));
                saveArr.add(new Addy(add.get(add.size() - 1).getAddressLine(0), timeR));
            }

            if(count==0) {arr.add(location);}

            if (add.size() > 0) {
                text3.setText(add.get(add.size() - 1).getAddressLine(0));

                text4.setText(dist + " Miles");

                if (arr.size() > 0) {
                    dist = location.distanceTo(arr.get(0));
                    double distmiles = dist/1609;
                    text4.setText(distmiles + " Miles");
                }
                time2 = SystemClock.elapsedRealtime();
                timeR = (time2-time1);
                int time13 = Math.toIntExact(timeR/1000);
                String addy1 = add.get(add.size() - 1).getAddressLine(0);


                for(int i = 0; i<add.size(); i++)
                {
                    if(addy1.equals(saveArr.get(i).getAdd1()))
                    {
                        saveArr.get(i).setTime1(saveArr.get(i).getTime1()+timeR);
                    }
                }
                for(int i =0; i<add.size(); i++)
                {
                    if(saveArr.get(i).getTime1()>timeM) {
                        timeM = saveArr.get(i).getTime1();
                        addyMost = saveArr.get(i).getAdd1();
                    }
                }
                text5.setText("Favorite Location: "+addyMost+"\nTime Spent Here: "+timeM+" seconds");
                }

        } catch(IndexOutOfBoundsException e) {
            text3.setText("GPS CANNOT WORK");
            text4.setText("");
            text3.setText("");
            text4.setText("INVALID LOCATION");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        count++;
    }
}