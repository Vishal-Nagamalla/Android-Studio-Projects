package com.example.webscraper;

import android.graphics.Movie;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WebScraper {
    private static final String URL = "https://www.boxofficemojo.com/chart/ww_top_lifetime_gross/?area=XWW";
    private static final int NUM_MOVIES = 50;

    public List<Movie> scrapeMovies() {
        List<Movie> movies = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(URL).get();
            Elements rows = doc.select("#table tr:gt(0)");

            for (int i = 0; i < NUM_MOVIES; i++) {
                Element row = rows.get(i);
                String movieName = row.select("td:nth-child(2) a").text();
                String gross = row.select("td:nth-child(3)").text();
                movies.add(new Movie(movieName, gross));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
