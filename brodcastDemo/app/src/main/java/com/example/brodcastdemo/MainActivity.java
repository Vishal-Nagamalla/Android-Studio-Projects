package com.example.brodcastdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BroadcastReceiver myReciever;
        myReciever = new BatterMonitor();

        IntentFilter batteryFilter = new IntentFilter();
        batteryFilter.addAction(Intent.ACTION_BATTERY_CHANGED);

        registerReceiver(myReciever, batteryFilter);


    }

    public class BatterMonitor extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Battery Changed", Toast.LENGTH_SHORT).show();
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            if(status == 5)
            {
                Log.d("TAG", "Full charge");
            }
            else if(status == 2)
            {
                Log.d("TAG", "Charging");
            }
            else if(status == -1)
            {
                Log.d("TAG", "Error");
            }

        }
    }
}