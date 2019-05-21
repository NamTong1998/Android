package com.example.weatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<WeatherCustom> arrayList;

    public CustomAdapter(Context context, ArrayList<WeatherCustom> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_line, null);

        WeatherCustom weatherCustom = arrayList.get(position);

        TextView txtDate = convertView.findViewById(R.id.txtDate2);
        TextView txtStatus = convertView.findViewById(R.id.txtStatus2);
        ImageView icon = convertView.findViewById(R.id.icon);
        TextView maxTemp = convertView.findViewById(R.id.maxTemp);
        TextView minTemp = convertView.findViewById(R.id.minTemp);

        txtDate.setText(weatherCustom.date);
        txtStatus.setText(weatherCustom.status);
        maxTemp.setText(weatherCustom.maxTemp + "°C");
        minTemp.setText(weatherCustom.minTemp + "°C");
        Picasso.with(context).load("http://openweathermap.org/img/w/" + weatherCustom.icon + ".png").into(icon);

        return convertView;
    }
}
