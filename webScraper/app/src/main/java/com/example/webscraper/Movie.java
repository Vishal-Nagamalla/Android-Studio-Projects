package com.example.webscraper;

public class Movie {
    private String name;
    private String gross;

    public Movie(String name, String gross) {
        this.name = name;
        this.gross = gross;
    }

    public String getName() {
        return name;
    }

    public String getGross() {
        return gross;
    }

    @Override
    public String toString() {
        return name + " - " + gross;
    }
}