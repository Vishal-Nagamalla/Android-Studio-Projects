package com.example.webscraper;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import com.example.webscraper.Movie;

public class MainActivity extends AppCompatActivity {
    private ListView movieListView;
    private ArrayAdapter<Movie> movieAdapter;
    private List<Movie> movies;
    private Button sortGrossButton;
    private Button sortGrossReverseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieListView = findViewById(R.id.movie_list_view);
        sortGrossButton = findViewById(R.id.sort_gross_button);
        sortGrossReverseButton = findViewById(R.id.sort_gross_reverse_button);

        WebScraper webScraper = new WebScraper();
        movies = webScraper.scrapeMovies();

        movieAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, movies);
        movieListView.setAdapter(movieAdapter);

        sortGrossButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortMoviesByGross(false);
            }
        });

        sortGrossReverseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortMoviesByGross(true);
            }
        });
    }

    private void sortMoviesByGross(final boolean reverseOrder) {
        Collections.sort(movies, new Comparator<Movie>() {
            @Override
            public int compare(Movie m1, Movie m2) {
                if (reverseOrder) {
                    return m2.getGross().compareTo(m1.getGross());
                } else {
                    return m1.getGross().compareTo(m2.getGross());
                }
            }
        });
        movieAdapter.notifyDataSetChanged();
    }
}
