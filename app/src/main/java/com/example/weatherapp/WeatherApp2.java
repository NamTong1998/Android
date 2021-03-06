package com.example.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class WeatherApp2 extends AppCompatActivity {

    ImageView btnBack;
    TextView txtName3;
    ListView listView;

    CustomAdapter customAdapter;
    ArrayList<Weather> arrayList;

    public void mapping2()
    {
        btnBack = (ImageView) findViewById(R.id.btnBack);
        txtName3 = (TextView) findViewById(R.id.txtName3);
        listView = (ListView) findViewById(R.id.listView);

        arrayList = new ArrayList<Weather>();
        customAdapter = new CustomAdapter(WeatherApp2.this, arrayList);
        listView.setAdapter(customAdapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);

        mapping2();

        Intent intent = getIntent();
        String city = intent.getStringExtra("city");

        getDaysWeather(city);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void getDaysWeather(String data)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(WeatherApp2.this);
        String url = "http://api.openweathermap.org/data/2.5/forecast?q=" + data + "&units=metric&cnt=16&appid=e6c67e6d24ed10099b1136d1b903a5f8";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);

                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String city = jsonObjectCity.getString("name");
                            txtName3.setText("City Name: " + city);

                            JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                            for (int i = 0; i < jsonArrayList.length(); i++)
                            {
                                JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);

                                String dt = jsonObjectList.getString("dt");
                                long l = Long.valueOf(dt);
                                Date date1 = new Date(l * 1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                                String date = simpleDateFormat.format(date1);

                                JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("main");
                                String max = jsonObjectTemp.getString("temp_max");
                                String min = jsonObjectTemp.getString("temp_min");
                                Double max1 = Double.valueOf(max);
                                Double min1 = Double.valueOf(min);
                                String maxTemp = String.valueOf(max1.intValue());
                                String minTemp = String.valueOf(min1.intValue());

                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                                String status = jsonObjectWeather.getString("description");
                                String icon = jsonObjectWeather.getString("icon");

                                arrayList.add(new Weather(date, status, icon, maxTemp, minTemp));
                            }

                            customAdapter.notifyDataSetChanged();

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

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
