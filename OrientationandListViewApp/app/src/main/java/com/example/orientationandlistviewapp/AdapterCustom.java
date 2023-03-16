package com.example.orientationandlistviewapp;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class AdapterCustom extends ArrayAdapter<Game> {
    public Context mainActivityContext;
    private int year;
    private double price;
    public ArrayList<Game> arr;
    public AdapterCustom(@NonNull Context context, int resource, ArrayList<Game> arr) {
        super(context, resource, arr);
        this.arr = arr;
        mainActivityContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Button button;
        TextView textView;
        ImageView imageView;

        LayoutInflater layoutInfalter = (LayoutInflater) mainActivityContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View v = layoutInfalter.inflate(R.layout.adapter_custom, null);
        button = v.findViewById(R.id.adapter_button);
        textView = v.findViewById(R.id.adapter_TextView);
        imageView = v.findViewById(R.id.adapter_imageView);

        textView.setText(arr.get(position).getName());
        button.setText("Remove");
            imageView.setImageResource(arr.get(position).getGameImage());


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arr.remove(position);
                notifyDataSetChanged();
            }
        });

        return v;

    }
}