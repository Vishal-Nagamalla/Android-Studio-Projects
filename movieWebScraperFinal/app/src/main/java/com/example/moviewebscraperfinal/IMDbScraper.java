package com.example.moviewebscraperfinal;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class IMDbScraper extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "MovieInfo";

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            // Fetch the IMDb Top 250 page
            String url = "http://www.imdb.com/chart/top";
            Document doc = Jsoup.connect(url).get();

            // Select the movie rows
            Elements rows = doc.select(".lister-list tr");

            // Iterate over the movie rows and extract the title and rating
            for (Element row : rows) {
                Element titleColumn = row.select(".titleColumn a").first();
                String title = titleColumn.text();

                Element ratingColumn = row.select(".imdbRating strong").first();
                String rating = ratingColumn.text();

                // Display the movie info using Log.d()
                Log.d(TAG, title + " -> Rating: " + rating);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}