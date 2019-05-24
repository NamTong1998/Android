package com.example.weatherapp;

public class Weather {
    public String date;
    public String status;
    public String icon;
    public String maxTemp;
    public String minTemp;

    public Weather(String date, String status, String icon, String maxTemp, String minTemp)
    {
        this.date = date;
        this.status = status;
        this.icon = icon;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }
}
