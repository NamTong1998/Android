package com.example.weatherapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import com.squareup.picasso.Picasso;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Weather> arrayList;

    public CustomAdapter(Context context, ArrayList<Weather> arrayList) {
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
        convertView = inflater.inflate(R.layout.line, null);

        Weather weather = arrayList.get(position);

        TextView txtDate2 = (TextView) convertView.findViewById(R.id.txtDate2);
        TextView txtStatus2 = (TextView) convertView.findViewById(R.id.txtStatus2);
        TextView txtMax = (TextView) convertView.findViewById(R.id.txtMax);
        TextView txtMin = (TextView) convertView.findViewById(R.id.txtMin);
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);

        txtDate2.setText(weather.date);
        txtStatus2.setText(weather.status);
        txtMax.setText(weather.maxTemp);
        txtMin.setText(weather.minTemp);

        int max = Integer.valueOf(weather.maxTemp);
        int min = Integer.valueOf(weather.minTemp);

        if(max >= 28)
            txtMax.setTextColor(Color.RED);
        if((max < 28) && (max >= 18))
            txtMax.setTextColor(Color.GREEN);
        if(max < 18)
            txtMax.setTextColor(Color.BLUE);

        if(min >= 28)
            txtMin.setTextColor(Color.RED);
        if((min < 28) && (max >= 18))
            txtMin.setTextColor(Color.GREEN);
        if(min < 18)
            txtMin.setTextColor(Color.BLUE);

        Picasso.with(context).load("http://openweathermap.org/img/w/" + weather.icon + ".png").into(icon);

        return convertView;
    }
}
