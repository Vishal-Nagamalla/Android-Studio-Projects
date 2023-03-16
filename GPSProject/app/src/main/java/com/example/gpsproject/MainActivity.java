package com.example.gpsproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationListenerCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListenerCompat {
    TextView one, two, three, four;
    double latInit, longInit;
    double latInito, longInito;
    double totalDist;
    boolean d = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        one = findViewById(R.id.textView);
        two = findViewById(R.id.textView2);
        three = findViewById(R.id.textView3);
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 100);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            LocationManager loc = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            loc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        Log.d("TEST", "" + location.getLatitude());
        float[] test = new float[1];
        if (d) {
            latInit = location.getLatitude();
            longInit = location.getLongitude();
            latInito = location.getLatitude();
            longInito = location.getLongitude();
            d = !d;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        one = (TextView) findViewById(R.id.textView);
        two = (TextView) findViewById(R.id.textView2);
        three = (TextView) findViewById(R.id.textView3);
        four = findViewById(R.id.textView5);
        one.setText("Latitude: " + location.getLatitude());
        two.setText("Longitude: " + location.getLongitude());
        Location l = new Location("");
        l.setLatitude(latInit);
        l.setLongitude(longInit);
        totalDist += location.distanceTo(l);
        //location.distanceBetween(latInit, longInit, location.getLatitude(), location.getLongitude(), test);
        Log.d("teste", location.getLatitude() + " " + location.getLongitude() + " " + latInit + " " + longInit + " " + test[0]);
        //totalDist += test[0];
        Log.d("teste", "" + totalDist);
        three.setText("Distance travelled: " + (totalDist * 0.00062137119));
        latInit = location.getLatitude();
        longInit = location.getLongitude();
        location.distanceBetween(latInito, longInito, location.getLatitude(), location.getLongitude(), test);
        four.setText("Displacement from origin: " + (test[0] * 0.00062137119));
        Async a = new Async();
        a.execute(location);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d("log_loc", permissions[0]);
        Log.d("log_loc", ""+requestCode);
        Log.d("log_loc", ""+ (permissions[0] == Manifest.permission.ACCESS_FINE_LOCATION));
        if (requestCode == 100 && grantResults[0] == PackageManager.PERMISSION_GRANTED && (permissions[0].equals(Manifest.permission.ACCESS_COARSE_LOCATION) || permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION))) {
            Toast.makeText(this, "Permission Granted! Move around to track location!", Toast.LENGTH_LONG).show();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationManager loc = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
                loc.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);
            } else {
                Log.d("log_loc", "test failed");
            }

        } else {
            Toast.makeText(this, "permission not granted bozo enjoy not using the app", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListenerCompat.super.onStatusChanged(provider, status, extras);
    }
    public class Async extends AsyncTask<Location, Void, String> {
        String saved = "";//add favorite location functionlaity
        int index = 0;
        long timeElapsed = SystemClock.elapsedRealtime();
        ArrayList<String> arr = new ArrayList<String>();
        ArrayList<Double> times = new ArrayList<Double>();
        @Override
        protected String doInBackground(Location... location) {
            Location loc = location[0];
            Geocoder geo = new Geocoder(getBaseContext(), Locale.US);
            String s = "";
            try {
                s = geo.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1).get(0).getAddressLine(0).toString();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView tv = findViewById(R.id.textView4);
            tv.setText(s);
        }
    }

}