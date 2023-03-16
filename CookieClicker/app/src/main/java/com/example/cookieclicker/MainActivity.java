package com.example.cookieclicker;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import android.view.View;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {
    TextView titleText, totalCookies, numGrandma, numFarm, gprice, fprice, cookieText, cpstext, cookieT, storeTitle, g, f;
    ImageView cookie, grandma, farm;
    ConstraintLayout layout, farmlayout, storeLayout, layoutbig;
    int gnum, fnum, totc = 0;
    int grandmabuy = 100;
    int farmbuy = 1000;
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        grandma = findViewById(R.id.grandmaImg); farm = findViewById(R.id.farmImg); cookie = findViewById(R.id.cookieImg);
        titleText = findViewById(R.id.gameTitle); totalCookies = findViewById(R.id.totcookie); numGrandma = findViewById(R.id.grandmanum);
        numFarm = findViewById(R.id.farmnum); layout = findViewById(R.id.layout); farmlayout = findViewById(R.id.farmLayout);
        layoutbig = findViewById(R.id.layoutbig); gprice = findViewById(R.id.grandmaPrice); fprice = findViewById(R.id.farmPrice);
        cookieText = findViewById(R.id.cookieText); cpstext = findViewById(R.id.cps); mVideoView = (VideoView) findViewById(R.id.videoView);
        cookieT = findViewById(R.id.cookieText); storeTitle = findViewById(R.id.storeTitle); g = findViewById(R.id.g);
        f = findViewById(R.id.f); storeLayout = findViewById(R.id.storeLayout);

        grandma.bringToFront(); farm.bringToFront(); cookie.bringToFront(); titleText.bringToFront(); totalCookies.bringToFront();
        numGrandma.bringToFront(); numFarm.bringToFront(); layout.bringToFront(); farmlayout.bringToFront(); layoutbig.bringToFront();
        gprice.bringToFront(); fprice.bringToFront(); cookieText.bringToFront(); cpstext.bringToFront(); cookieT.bringToFront();
        storeTitle.bringToFront(); g.bringToFront(); f.bringToFront(); storeLayout.bringToFront();

        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.cookiefalling);
        mVideoView.setVideoURI(uri);
        mVideoView.start();

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        final ScaleAnimation scaleAnimation1 = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation1.setDuration(150);
        final ScaleAnimation scaleAnimation2 = new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation2.setDuration(250);

        cookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cookie.startAnimation(scaleAnimation1);
                cookie.startAnimation(scaleAnimation2);
                totc++;
                totalCookies.setText("Cookies: " + totc);

                TextView cookieText = new TextView(MainActivity.this);
                cookieText.setText("+1");
                cookieText.setTextSize(24);
                int[] location = new int[2];
                cookie.getLocationOnScreen(location);
                int x = location[0] + new Random().nextInt(cookie.getWidth());
                cookieText.setX(x);
                cookieText.setY(cookie.getY() - 50);
                layoutbig.addView(cookieText);

                ObjectAnimator animY = ObjectAnimator.ofFloat(cookieText, "translationY", -500f);
                ObjectAnimator animX = ObjectAnimator.ofFloat(cookieText, "translationX", x, x + new Random().nextInt(500) - 250);
                AnimatorSet animSetXY = new AnimatorSet();
                animSetXY.playTogether(animX, animY);
                animSetXY.setDuration(2000);
                animSetXY.start();

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                layoutbig.removeView(cookieText);
                            }
                        }, 2000);
            }
        });

        Thread cookieThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    int cookiesFromGrandmas = gnum * 3; int cookiesFromFarms = fnum * 10;
                    int totalcps = cookiesFromGrandmas + cookiesFromFarms;
                    totc += totalcps;

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            totalCookies.setText("Cookies: " + totc);
                            cpstext.setText("CPS: " + totalcps);
                        }
                    });
                    try {Thread.sleep(1000);}
                        catch (InterruptedException e) {e.printStackTrace();}
                }
            }
        });
        cookieThread.start();

        final ConstraintLayout layout = findViewById(R.id.layout);
        int margin = 50;
        int maxPerRow = 8;
        int startY = -100;

        grandma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totc >= grandmabuy) {
                    ObjectAnimator rotate = ObjectAnimator.ofFloat(grandma, "rotation", 0f, 360f);
                    rotate.setDuration(1000);
                    rotate.start();

                    gnum++;
                    grandma.startAnimation(scaleAnimation1); grandma.startAnimation(scaleAnimation2);
                    numGrandma.setText("#: " + gnum);
                    totc = totc - grandmabuy;
                    grandmabuy += 50;
                    gprice.setText("Price: " + grandmabuy);

                    ImageView spawnedImage = new ImageView(MainActivity.this);
                    spawnedImage.setScaleX(0.25f);
                    spawnedImage.setScaleY(0.25f);
                    spawnedImage.setImageResource(R.drawable.minigrandma);
                    spawnedImage.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    spawnedImage.setId(View.generateViewId());
                    spawnedImage.setVisibility(View.INVISIBLE);
                    layout.addView(spawnedImage);

                    ConstraintSet set = new ConstraintSet();
                    set.clone(layout);

                    int numInRow = (gnum - 1) % maxPerRow;
                    int numRows = (gnum - 1) / maxPerRow;
                    int x = numInRow * (spawnedImage.getWidth() + margin);
                    int y = startY + numRows * (spawnedImage.getHeight() + margin);

                    set.connect(spawnedImage.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, x);
                    set.connect(spawnedImage.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, y);

                    set.applyTo(layout);
                    spawnedImage.setVisibility(View.VISIBLE);
                }
            }
        });

        final ConstraintLayout farmLayout = findViewById(R.id.farmLayout);
        farm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(totc >= farmbuy) {
                    ObjectAnimator rotate = ObjectAnimator.ofFloat(farm, "rotation", 0f, 360f);
                    rotate.setDuration(1000);
                    rotate.start();

                    fnum++;
                    farm.startAnimation(scaleAnimation1); farm.startAnimation(scaleAnimation2);
                    numFarm.setText("#: " + fnum);
                    totc = totc - farmbuy;
                    farmbuy += 50;
                    fprice.setText("Price: " + farmbuy);

                    ImageView spawnedImage = new ImageView(MainActivity.this);
                    spawnedImage.setScaleX(0.25f);
                    spawnedImage.setScaleY(0.25f);
                    spawnedImage.setImageResource(R.drawable.farm);
                    spawnedImage.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    spawnedImage.setId(View.generateViewId());
                    spawnedImage.setVisibility(View.INVISIBLE);
                    farmLayout.addView(spawnedImage);

                    ConstraintSet set = new ConstraintSet();
                    set.clone(farmLayout);

                    int numInRow = (fnum - 1) % maxPerRow;
                    int numRows = (fnum - 1) / maxPerRow;
                    int x = numInRow * (spawnedImage.getWidth() + margin);
                    int y = startY + numRows * (spawnedImage.getHeight() + margin);

                    set.connect(spawnedImage.getId(), ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, x);
                    set.connect(spawnedImage.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, y);

                    set.applyTo(farmLayout);
                    spawnedImage.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}