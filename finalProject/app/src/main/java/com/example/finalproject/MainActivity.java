package com.example.finalproject;

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

import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    EditText editText;
    Button button, add;
    TextView latLong, text1, text2, text3, text4;
    String url;
    String info;
    String lat, lon, name;
    JSONObject weather;
    String reader;
    int count = 0;
    ImageView cardImage, previous, next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_layout);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPager);
        Search search = new Search();
        DeckBuilder builder = new DeckBuilder();
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                switch(position){
                    case 0:
                        return search;
                    case 1:
                        return builder;
                }
                return null;
            }

            @Override
            public int getCount() {
                return tabLayout.getTabCount();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        button = findViewById(R.id.button);
        previous = findViewById(R.id.backwardsImage);
        add = findViewById(R.id.button3);
        next = findViewById(R.id.forwardsImage);
        editText = findViewById(R.id.editTextTextPersonName);
        cardImage = findViewById(R.id.imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = editText.getText().toString();
                name = name.replaceAll(" ", "%20");
                Log.d("Name", name);
                count = 0;
                url = "https://db.ygoprodeck.com/api/v7/cardinfo.php?fname="+name;
                yugioh asyncTask = new yugioh();
                asyncTask.execute(url);
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
                url = "https://db.ygoprodeck.com/api/v7/cardinfo.php?fname="+name;
                yugioh asyncTask = new yugioh();
                asyncTask.execute(url);
            }
        });
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
                Log.d("Count", data.getJSONObject(count) + "");
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
                Bitmap bimage = null;
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
                    bimage = BitmapFactory.decodeStream(inputStream1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                BufferedReader bufferedReader1 = new BufferedReader(new InputStreamReader(inputStream1));
                while (true) {
                    try {
                        if (!((line2 = bufferedReader1.readLine()) != null)) break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    cardDataImage.append(line2);
                }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            super.onPostExecute(image);

            cardImage.setImageBitmap(image);
            //String name = cardIndex.getString("name");
            //String desc = cardIndex.getString("desc");
        }
    }
}