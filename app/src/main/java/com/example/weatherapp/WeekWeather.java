package com.example.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class WeekWeather extends AppCompatActivity {
    ImageView btnBack;
    TextView txtName;
    ListView listView;

    CustomAdapter customAdapter;
    ArrayList<WeatherCustom> arrayListWeather;

    public void mapping()
    {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        txtName = (TextView) findViewById(R.id.txtName3);
        listView = (ListView) findViewById(R.id.listView);

        arrayListWeather = new ArrayList<WeatherCustom>();
        customAdapter = new CustomAdapter(WeekWeather.this, arrayListWeather);
        listView.setAdapter(customAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_week);

        mapping();

        Intent intent = getIntent();
        String city = intent.getStringExtra("city");

        if(city.equals(""))
        {
            city = "Hanoi";
        }

        get7DaysData(city);
    }

    public void get7DaysData(String data)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(WeekWeather.this);
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + data + "&units=metric&cnt=7&appid=e6c67e6d24ed10099b1136d1b903a5f8";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        
        requestQueue.add(stringRequest);
    }
}
