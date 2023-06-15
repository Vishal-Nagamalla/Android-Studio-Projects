package com.example.dodge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    GameSurface gameSurface;
    MediaPlayer crash, song;
    int score = 0;
    boolean clicked = false;
    int speedMultiplier = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameSurface = new GameSurface(this);
        gameSurface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!clicked)
                {
                    speedMultiplier *= 4;
                    clicked = true;
                }
                else {
                    speedMultiplier /= 4;
                }
            }
        });
        setContentView(gameSurface);
        //crash = MediaPlayer.create(this, R.raw.crash);
        song = MediaPlayer.create(this, R.raw.backgroundmusic);
    }

    @Override
    protected void onPause(){
        super.onPause();
        gameSurface.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        gameSurface.resume();
    }

    //----------------------------GameSurface Below This Line--------------------------
    public class GameSurface extends SurfaceView implements Runnable,SensorEventListener{

        Thread gameThread;
        SurfaceHolder holder;
        volatile boolean running = false;
        Bitmap player, enemy;
        int playerX, enemyX, playerY, enemyY;
        int x = 10;

        boolean crashedOnce = true;
        boolean addScore = true;
        String sensorOutput1, sensorOutput2;
        Paint paintProperty;
        int screenWidth, screenHeight;

        public GameSurface(Context context) {
            super(context);
            holder=getHolder();
            player = BitmapFactory.decodeResource(getResources(),R.drawable.player);
            enemy = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);

            Display screenDisplay = getWindowManager().getDefaultDisplay();
            Point sizeOfScreen = new Point();
            screenDisplay.getSize(sizeOfScreen);
            screenWidth=sizeOfScreen.x;
            screenHeight=sizeOfScreen.y;

            SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this,accelerometerSensor,sensorManager.SENSOR_DELAY_GAME);

            paintProperty= new Paint();
            paintProperty.setTextSize(100);

            playerX = 0; playerY = 0; enemyX = 0; enemyY = 0;
        }
        Canvas canvas;

        public void resume(){
            running=true;
            gameThread=new Thread(this);
            gameThread.start();
        }

        public void pause() {
            running = false;
            while (true) {
                try {
                    gameThread.join();
                } catch (InterruptedException e) {
                }
            }
        }

        @Override
        public void run() {
            Random random = new Random();
            enemyX = random.nextInt(screenWidth / 2);
            enemyX *= random.nextBoolean() ? -1 : 1;
            enemyY += 5 * speedMultiplier;

            song.start();
            long startTime = System.currentTimeMillis();

            while (running) {
                if (!holder.getSurface().isValid()) {
                    continue;
                }
                canvas = holder.lockCanvas();

                canvas.drawRGB(102, 100, 111);

                long timeLeft = 45 - Math.round((System.currentTimeMillis() - startTime) / 1000.0);

                sensorOutput1 = "Score: " + score;
                sensorOutput2 = "Time Left: " + timeLeft;

                if (timeLeft == 0) {
                    running = false;
                    canvas.drawText("GAME OVER", 250, 700, paintProperty);
                }

                canvas.drawText(sensorOutput1, x, 200, paintProperty);
                canvas.drawText(sensorOutput2, 450, 200, paintProperty);

                if ((-enemy.getHeight() / 2 + enemyY) > screenHeight) {
                    enemyX = random.nextInt(screenWidth / 2 - enemy.getWidth() / 2);
                    enemyX *= random.nextBoolean() ? -1 : 1;
                    enemyY = 0;

                    if (addScore) {
                        score += 1;
                    } else {
                        addScore = true;
                    }
                }
                enemyY += 7 * speedMultiplier;

                int halfScreenHeight = screenHeight / 2;

                int enemyYDiff = Math.abs(enemyY - halfScreenHeight - 400);
                int playerXDiff = Math.abs(playerX - enemyX);

                if (enemyYDiff < 100 && playerXDiff < 100 && crashedOnce) {
                    player = BitmapFactory.decodeResource(getResources(), R.drawable.player_crash);
                    crashedOnce = false;
                    speedMultiplier /= 2;

                    score -= 1;
                    addScore = false;

                    crash.start();
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            speedMultiplier *= 2;
                            crashedOnce = true;
                            player = BitmapFactory.decodeResource(getResources(), R.drawable.player);
                        }
                    }, 2000);
                }

                canvas.drawBitmap(player, (screenWidth / 2) - player.getWidth() / 2 + playerX, screenHeight / 2 - player.getHeight() + 400, null);
                canvas.drawBitmap(enemy, (screenWidth / 2) - enemy.getWidth() / 2 + enemyX, -enemy.getHeight() / 2 + enemyY, null);
                holder.unlockCanvasAndPost(canvas);
            }
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            float xcoord = event.values[0];

            int sign = xcoord < 0 ? 1 : -1;

            int absXcoord = (int) Math.abs(xcoord);
            int increment = 0;

            if (absXcoord >= 7) {increment = 18;}
            else if (absXcoord >= 3) {increment = 9;}
            else if (absXcoord >= 0.02) {increment = 3;}

            playerX += increment * sign * speedMultiplier;

            int playerHalfWidth = player.getWidth() / 2;
            int minPlayerX = -screenWidth / 2 + playerHalfWidth;
            int maxPlayerX = screenWidth / 2 - playerHalfWidth;

            playerX = Math.max(minPlayerX, Math.min(maxPlayerX, playerX));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    }//GameSurface
}//Activity