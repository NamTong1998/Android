package com.example.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherApp extends AppCompatActivity {

    EditText etSearch;
    Button btnSearch, btnNext;
    TextView txtName, txtName2, txtTemp, txtStatus, txtHumid, txtCloud, txtWind, txtDate;
    ImageView iconBig;

    public void mapping()
    {
        etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnNext = (Button) findViewById(R.id.btnNext);

        txtName = (TextView) findViewById(R.id.txtName);
        txtName2 = (TextView) findViewById(R.id.txtName2);
        txtTemp = (TextView) findViewById(R.id.txtTemp);
        txtStatus = (TextView) findViewById(R.id.txtStatus);
        txtHumid = (TextView) findViewById(R.id.txtHumid);
        txtCloud = (TextView) findViewById(R.id.txtCloud);
        txtWind = (TextView) findViewById(R.id.txtWind);
        txtDate = (TextView) findViewById(R.id.txtDate);

        iconBig = (ImageView) findViewById(R.id.iconBig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mapping();

        etSearch.setText("Hanoi");
        getCurrentWeatherData("Hanoi");

        btnSearch.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                InputMethodManager imm = (InputMethodManager)getSystemService(WeatherApp.this.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etSearch.getWindowToken(), 0);

                String city = etSearch.getText().toString();

                if (city.equals(""))
                {
                    city = "Hanoi";
                }

                getCurrentWeatherData(city);
                //etSearch.setText("");
            }
        });

        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
            }
        });

        etSearch.setOnKeyListener(new EditText.OnKeyListener()
        {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if(keyCode == KeyEvent.KEYCODE_ENTER) {
                    String city = etSearch.getText().toString();

                    getCurrentWeatherData(city);
                    //etSearch.setText("");
                }

                return false;
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = etSearch.getText().toString();
                if (etSearch.getText().toString().equals(""))
                    city = "Hanoi";
                Intent intent = new Intent(WeatherApp.this, WeatherApp2.class);
                intent.putExtra("city", city);
                startActivity(intent);
            }
        });
    }

    public void getCurrentWeatherData(String data)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(WeatherApp.this);
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + data + "&units=metric&appid=e6c67e6d24ed10099b1136d1b903a5f8";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);

                            String date = jsonObject.getString("dt");
                            String name = jsonObject.getString("name");
                            txtName.setText("City Name: " + name);
                            Long l = Long.valueOf(date);
                            Date d = new Date(l * 1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                            String day = simpleDateFormat.format(d);
                            txtDate.setText("Update: " + day);

                            JSONArray jsonArrayWeather = jsonObject.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("main");
                            String icon = jsonObjectWeather.getString("icon");

                            Picasso.with(WeatherApp.this).load("http://openweathermap.org/img/w/" + icon + ".png").into(iconBig);
                            txtStatus.setText(status);

                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String temp = jsonObjectMain.getString("temp");
                            String humidity = jsonObjectMain.getString("humidity");
                            Double t = Double.valueOf(temp);
                            String temperature = String.valueOf(t.intValue());
                            txtTemp.setText(temperature + "°C");
                            txtHumid.setText(humidity + "%");

                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String speed = jsonObjectWind.getString("speed");
                            txtWind.setText(speed + "m/s");

                            JSONObject jsonObjectClouds = jsonObject.getJSONObject("clouds");
                            String cloud = jsonObjectClouds.getString("all");
                            txtCloud.setText(cloud + "%");

                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            String country = jsonObjectSys.getString("country");
                            txtName2.setText("Country Name: " + country);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                });

        requestQueue.add(stringRequest);
    }
}
