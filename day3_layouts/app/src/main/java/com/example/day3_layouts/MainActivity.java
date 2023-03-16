package com.example.day3_layouts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        Notes:
            Layouts - you can put layouts inside the parent layout to create child layouts to have different button orientations

            Wrap_content - takes up as much space as it needs to (the set size for button), match_parent - takes up the amount of space that the parent is using
                android:layout_width="wrap_content" / android:layout_height="wrap_content"

            Gravity - moves objects in the app
                Layout Gravity makes a object move inside its parent
                    android:layout_gravity="bottom"/"left"/"right"/"top"
                Gravity makes the text inside the object move to the right (does not effect actual button)
                    android:gravity="right"
                android's gravity inside of the object class moves the object, if it is inside the layout class it moves every object inside the class

             Button Sizing - Changes the sizing of a object proportionally
                android:layout_weight="1" - makes all objects the same size
                Ex.
                    button 1 - android:layout_weight="1"
                    button 2 - android:layout_weight="2" - biggest button out of the group
                    button 3 - android:layout_weight="1"

             Padding and Margins - changes the sizing of the layout itself
                android:padding="10dp" - The distance between the margin of the layout to the object
                android:layout_marginRight/marginLeft/marginBottom/marginTop="50dp" - margin size
        */
    }
}