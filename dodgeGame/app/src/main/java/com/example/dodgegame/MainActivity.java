package com.example.tilt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView textViewX;
    TextView textViewY;
    TextView textViewZ;
    SensorManager sensorManager;
    Sensor accelerometerSensor;
    Player player;
    Enemy enemy;
    boolean isGameRunning = false;
    int score = 0;
    int lives = 3;
    CountDownTimer gameTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewX = findViewById(R.id.textViewX);
        textViewY = findViewById(R.id.textViewY);
        textViewZ = findViewById(R.id.textViewZ);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,accelerometerSensor,SensorManager.SENSOR_DELAY_NORMAL);

        player = new Player();
        enemy = new Enemy();

        gameTimer = new CountDownTimer(30000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Update timer display
                //textViewTimer.setText("Time Remaining: " + millisUntilFinished / 1000 + " seconds");
            }

            public void onFinish() {
                // End game
                isGameRunning = false;
                showGameOverDialog();
            }
        };
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float tiltX = event.values[0];
        float tiltY = event.values[1];
        // Update player's position based on tilt
        player.updatePosition(tiltX, tiltY);
        textViewX.setText("Tilt X: " + tiltX);
        textViewY.setText("Tilt Y: " + tiltY);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometerSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private void showGameOverDialog() {
        // Display game over dialog with score
        Toast.makeText(this, "Game Over! Score: " + score, Toast.LENGTH_SHORT).show();
        // Reset game state
        score = 0;
        lives = 3;
    }

    private class Player {
        private float x;
        private float y;
        private float speed;

        private Bitmap image;

        public Player() {
            x = 0;
            y = 0;
            speed = 1; // initial speed
            image = BitmapFactory.decodeResource(getResources(), R.drawable.player); // load player image
        }

        public void updatePosition(float tiltX, float tiltY) {
            // Update player's position based on tilt
            x += tiltX * speed;
            y += tiltY * speed;

            // Keep player within screen bounds
            if (x < 0) {
                x = 0;
            } else if (x > getWindowManager().getDefaultDisplay().getWidth() - image.getWidth()) {
                x = getWindowManager().getDefaultDisplay().getWidth() - image.getWidth();
            }

            if (y < 0) {
                y = 0;
            } else if (y > getWindowManager().getDefaultDisplay().getHeight() - image.getHeight()) {
                y = getWindowManager().getDefaultDisplay().getHeight() - image.getHeight();
            }
        }

        public void draw(Canvas canvas, Paint paint) {
            // Draw player on canvas
            canvas.drawBitmap(image, x, y, paint);
        }
    }

    private class Enemy {
        private float x;
        private float y;
        private float speed;

        private Bitmap image;

        public Enemy() {
            x = 0;
            y = 0;
            speed = 2; // initial speed
            image = BitmapFactory.decodeResource(getResources(), R.drawable.enemy); // load enemy image
        }

        public void updatePosition() {
            // Update enemy's position based on speed
            x += speed;

            // Reset enemy's position when it goes off the screen
            if (x > getWindowManager().getDefaultDisplay().getWidth()) {
                x = -image.getWidth();
                y = (float) (Math.random() * getWindowManager().getDefaultDisplay().getHeight());
                // Increase score when enemy passes by without collision
                score++;
            }
        }

        public void draw(Canvas canvas, Paint paint) {
            // Draw enemy on canvas
            canvas.drawBitmap(image, x, y, paint);
        }

        public boolean checkCollision(Player player) {
            // Check for collision between enemy and player
            if (x < player.x + player.image.getWidth() &&
                    x + image.getWidth() > player.x &&
                    y < player.y + player.image.getHeight() &&
                    y + image.getHeight() > player.y) {
                return true; // collision detected
            }
            return false; // no collision
        }
    }

    private class GameView extends View {
        private Paint paint;
        private Canvas canvas;

        public GameView(Context context) {
            super(context);
            paint = new Paint();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            this.canvas = canvas;

            // Draw player and enemy on canvas
            player.draw(canvas, paint);
            enemy.draw(canvas, paint);

            // Update enemy position and check for collision
            enemy.updatePosition();
            if (enemy.checkCollision(player)) {
                // Reduce lives when collision occurs
                lives--;
                if (lives <= 0) {
                    // End game when no lives left
                    isGameRunning = false;
                    showGameOverDialog();
                }
            }

            // Update score on canvas
            paint.setTextSize(48);
            canvas.drawText("Score: " + score, 20, 60, paint);

            // Update lives on canvas
            paint.setTextSize(36);
            canvas.drawText("Lives: " + lives, 20, 100, paint);

            if (isGameRunning) {
                // Redraw canvas if game is still running
                invalidate();
            }
        }
    }
}
