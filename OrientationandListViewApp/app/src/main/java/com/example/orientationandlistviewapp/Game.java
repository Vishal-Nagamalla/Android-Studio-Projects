package com.example.orientationandlistviewapp;

public class Game {
    private String Productionyear;
    private String Gamecost;
    private String Gamename;
    private int GameImage;
    private String Gamedescription;

    public Game(String name, String year, String cost, String description, int image)
    {
        Gamename = name;
        Productionyear = year;
        Gamecost = cost;
        Gamedescription = description;
        GameImage = image;
    }

    public String getName() {return Gamename;}
    public String getYear() {
        return Productionyear;
    }
    public String getCost() {
        return Gamecost;
    }
    public String getDescription()
    {
        return Gamedescription;
    }
    public int getGameImage()
    {
        return GameImage;
    }

    @Override
    public String toString() {
        return "Game{" + "year='" + Productionyear + '\'' + ", cost='" + Gamecost + '\'' + ", name='" + Gamename + '\'' + ", gameImage=" + GameImage + '}';
    }
}
