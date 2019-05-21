package com.example.weatherapp;

public class WeatherCustom {
    public String date;
    public String status;
    public String icon;
    public String maxTemp;
    public String minTemp;

    public WeatherCustom(String date, String status, String icon, String maxTemp, String minTemp) {
        this.date = date;
        this.status = status;
        this.icon = icon;
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }
}
