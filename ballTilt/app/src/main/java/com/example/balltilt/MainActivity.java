package com.example.balltilt;

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
import android.os.Bundle;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //Code from this program has been used from Beginning Android Games
    //Review SurfaceView, Canvas, continue

    GameSurface gameSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameSurface = new GameSurface(this);
        setContentView(gameSurface);
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
        Bitmap ball;
        int ballX=0;
        int x=200;
        String sensorOutput="This is test";
        Paint paintProperty;

        int screenWidth;
        int screenHeight;

        public GameSurface(Context context) {
            super(context);
            holder=getHolder();
            ball= BitmapFactory.decodeResource(getResources(),R.drawable.ball);


            Display screenDisplay = getWindowManager().getDefaultDisplay();
            Point sizeOfScreen = new Point();
            screenDisplay.getSize(sizeOfScreen);
            screenWidth=sizeOfScreen.x;
            screenHeight=sizeOfScreen.y;

            SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this,accelerometerSensor,sensorManager.SENSOR_DELAY_NORMAL);

            paintProperty= new Paint();
            paintProperty.setTextSize(100);

        }

        @Override
        public void run() {
            while (running == true){
                if (holder.getSurface().isValid() == false)
                    continue;
                Canvas canvas= holder.lockCanvas();

                canvas.drawRGB(255,0,0);

                canvas.drawText(sensorOutput,x,200,paintProperty);

                canvas.drawBitmap( ball,(screenWidth/2) - ball.getWidth()/2 +ballX ,(screenHeight/2) - ball.getHeight(),null);

                holder.unlockCanvasAndPost(canvas);
            }
        }

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
        public void onSensorChanged(SensorEvent event) {
            float z = -3 * event.values[0];
            ballX += z;

            int leftmost = 0;
            int rightmost = screenWidth - ball.getWidth();

            // Calculate leftmost and rightmost positions of the ball
            int ballLeftmost = ballX;
            int ballRightmost = ballX + ball.getWidth();

            // Clamp ballX to the screen boundaries based on leftmost and rightmost tips of the ball
            if (ballLeftmost < leftmost) {
                ballX = leftmost;
            } else if (ballRightmost > rightmost) {
                ballX = rightmost;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }//GameSurface
}//Activity