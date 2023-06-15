package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject.CustomAdapter;
import com.example.finalproject.DeckBuilder;
import com.example.finalproject.R;
import com.example.finalproject.YugiohCard;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;

public class Search extends Fragment {
    EditText editText;
    Button button, add, saveDeck, displayDeck;
    TextView latLong, text1, text2, text3, text4;
    String url;
    String info;
    String name;
    String nameCard;
    JSONObject weather;
    String reader;
    int count = 0;
    ImageView cardImage;
    ImageView previous, next;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.activity_main, null);

        button = fragmentView.findViewById(R.id.button);
        saveDeck = fragmentView.findViewById(R.id.button2);
        previous = fragmentView.findViewById(R.id.backwardsImage);
        add = fragmentView.findViewById(R.id.button3);
        displayDeck = fragmentView.findViewById(R.id.button4);
        next = fragmentView.findViewById(R.id.forwardsImage);
        editText = fragmentView.findViewById(R.id.editTextTextPersonName);
        cardImage = fragmentView.findViewById(R.id.imageView);

        firebaseDatabase = FirebaseDatabase.getInstance();

        displayDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference("YugiohCard");
                Log.d("Database Reference", databaseReference+"");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DeckBuilder.yugiohArrayList.clear();
                        for(DataSnapshot yugiohDataSnapshot: snapshot.getChildren()) {
                            String key = yugiohDataSnapshot.getKey();

                            for (DataSnapshot d : yugiohDataSnapshot.getChildren()) {
                                Log.d("yugiohDatabse", d.toString());
                                YugiohCard yugiohCard = d.getValue(YugiohCard.class);

                                Log.d(yugiohCard.toString(), "card");
                                DeckBuilder.yugiohArrayList.add(yugiohCard);
                            }


                            //YugiohCard yugiohCard = yugiohDataSnapshot.getValue(YugiohCard.class);
                            //ArrayList<String> cardName = new ArrayList<>();
                            //cardName.add(yugiohDataSnapshot.child("name").getValue(String.class));
                            //ArrayList<YugiohCard> yugiohCard = new ArrayList<>();
                            //yugiohCard.add(yugiohDataSnapshot.getValue(YugiohCard.class));
                            //YugiohCard yugiohCard = new YugiohCard(cardName);
                            //DeckBuilder.yugiohArrayList.add(yugiohCard);
                            //Log.d("YugiohCard", yugiohCard+"");
                        }

                        CustomAdapter adapter = new CustomAdapter(getActivity().getBaseContext(), R.layout.adapter_layout, DeckBuilder.yugiohArrayList);
                        DeckBuilder.listView.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        saveDeck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("YugiohCard");
                insertCardData();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editText.getText().toString();
                name = name.replaceAll(" ", "%20");
                Log.d("Name", name);
                count = 0;
                try {
                    url = "https://db.ygoprodeck.com/api/v7/cardinfo.php?fname=" + name;
                    yugioh asyncTask = new yugioh();
                    asyncTask.execute(url);
                }catch(Exception e){
                    Toast.makeText(getActivity().getBaseContext(),"Put in a valid name",Toast.LENGTH_LONG).show();
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count>0)
                    count--;
                name = editText.getText().toString();
                name = name.replaceAll(" ", "%20");
                url = "https://db.ygoprodeck.com/api/v7/cardinfo.php?fname="+name;
                yugioh asyncTask = new yugioh();
                asyncTask.execute(url);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                name = editText.getText().toString();
                name = name.replaceAll(" ", "%20");
                try {
                    url = "https://db.ygoprodeck.com/api/v7/cardinfo.php?fname=" + name;
                    yugioh asyncTask = new yugioh();
                    asyncTask.execute(url);
                }catch(Exception e){
                    Toast.makeText(getActivity().getBaseContext(),"You Scrolled Too Far",Toast.LENGTH_LONG).show();
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeckBuilder.yugiohArrayList.add(new YugiohCard(nameCard));
                Log.d("yugioharraylist", String.valueOf(DeckBuilder.yugiohArrayList));
                DeckBuilder.adapter = new CustomAdapter(getActivity().getBaseContext(), R.layout.adapter_layout, DeckBuilder.yugiohArrayList);
                DeckBuilder.listView.setAdapter(DeckBuilder.adapter);
            }
        });

        return fragmentView;
    }
    class yugioh extends AsyncTask<String, Void, Bitmap> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Bitmap doInBackground(String... strings) {

            try {
                StringBuilder cardData = new StringBuilder();
                String line1 = "";
                URL myUrl = new URL(strings[0]);
                URLConnection urlConnection = myUrl.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                while ((line1 = bufferedReader.readLine()) != null)
                    cardData.append(line1);
                JSONObject jsonObject = null;
                jsonObject = new JSONObject(String.valueOf(cardData));
                JSONArray data = jsonObject.getJSONArray("data");
                JSONObject cardIndex = data.getJSONObject(count);
                nameCard = cardIndex.getString("name");
                Log.d("Count", data.getJSONObject(count)+"");
                JSONArray cardImages = cardIndex.getJSONArray("card_images");
                String cardBigImage = cardImages.getJSONObject(0).getString("image_url");
                URL urlImage = null;
                try {
                    urlImage = new URL(cardBigImage);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                String line2 = "";
                StringBuilder cardDataImage = new StringBuilder();
                Bitmap bimage=null;
                URLConnection connection = null;
                try {
                    connection = urlImage.openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //urlConnection.connect();
                InputStream inputStream1 = null;
                try {
                    inputStream1 = connection.getInputStream();
                    bimage= BitmapFactory.decodeStream(inputStream1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));
                while(true) {
                    try {
                        if (!((line2=bufferedReader1.readLine())!=null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cardDataImage.append(line2);
                }*/
                return bimage;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        }
        @Override
        protected void onPostExecute(Bitmap image) {
            super.onPostExecute(image);

            cardImage.setImageBitmap(image);
            //String name = cardIndex.getString("name");
            //String desc = cardIndex.getString("desc");
        }
    }
    public void insertCardData(){
        ArrayList<YugiohCard> arrayList = new ArrayList<YugiohCard>();
        arrayList = DeckBuilder.yugiohArrayList;
        databaseReference.push().setValue(arrayList);
        Toast.makeText(getActivity().getBaseContext(),"Data Inserted",Toast.LENGTH_LONG).show();

    }
}
