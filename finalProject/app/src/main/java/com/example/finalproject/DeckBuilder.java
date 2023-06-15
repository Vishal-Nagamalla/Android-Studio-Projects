package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class DeckBuilder extends Fragment {
    public static ListView listView;
    public static ArrayList<YugiohCard> yugiohArrayList;
    public static CustomAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.deckbuilder_layout, null);
        listView = fragmentView.findViewById(R.id.listview);
        yugiohArrayList = new ArrayList<YugiohCard>();
//        try {
//            adapter = new CustomAdapter(getContext(), R.layout.adapter_layout, yugiohArrayList);
//            listView.setAdapter(adapter);
//        }catch(Exception e){
//            e.printStackTrace();
//        }

        return fragmentView;
    }
}
