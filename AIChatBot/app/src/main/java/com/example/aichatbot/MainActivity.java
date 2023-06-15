package com.example.aichatbot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private TextView displayText;
    private String currentState = "greeting";
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayText = findViewById(R.id.display_text);
        checkAndRequestPermissions();
    }

    private void checkAndRequestPermissions() {
        String[] permissions = {Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS};
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, permissions[1]) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CODE);
        } else {
            registerSmsReceiver();
        }
    }

    private void registerSmsReceiver() {
        BroadcastReceiver smsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Object[] pdus = (Object[]) intent.getExtras().get("pdus");
                SmsMessage[] smsMessages = new SmsMessage[pdus.length];
                StringBuilder receivedMessageBody = new StringBuilder();
                for (int i = 0; i < pdus.length; i++) {
                    smsMessages[i] = SmsMessage.createFromPdu((byte[]) pdus[i], intent.getStringExtra("format"));
                    receivedMessageBody.append(smsMessages[i].getMessageBody());
                }
                phoneNumber = smsMessages[0].getOriginatingAddress();
                String replyMessage = getReplyMessage(receivedMessageBody.toString());
                receivedMessageBody.setLength(0);
                displayText.setText(currentState);
                new Handler().postDelayed(() -> sendMessage(replyMessage), 2000);
            }
        };
        registerReceiver(smsReceiver, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
    }

    private void sendMessage(String message) {
        SmsManager.getDefault().sendTextMessage(phoneNumber, null, message, null, null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            registerSmsReceiver();
        }
    }

    private String getReplyMessage(String received) {
        String[] greetings = {"Hello!", "Hey, what\'s up?", "Hello, how are you?", "Hi!"};
        String[] goodbyes = {"See you soon!", "Bye bye!", "Talk to you later!", "Goodbye!"};
        String[] feelings = {"I'm feeling decent.", "I'm fine.", "I'm good!", "I'm a bit sad."};
        Random random = new Random();
        int randomIndex = random.nextInt(4);
        if (currentState.equals("greeting") && (received.equalsIgnoreCase("hi") || received.equalsIgnoreCase("hello"))) {
            currentState = "feeling";
            return greetings[randomIndex];
        } else if (currentState.equals("feeling") && (received.equalsIgnoreCase("how are you?") || received.equalsIgnoreCase("how do you feel?"))) {
            currentState = "goodbye";
            return feelings[randomIndex];
        } else if (currentState.equals("goodbye")&& (received.equalsIgnoreCase("bye") || received.equalsIgnoreCase("talk to you soon"))) {
            currentState = "greeting";
            return goodbyes[randomIndex];
        } else {
            return "Sorry, I don't understand.";
        }
    }
}