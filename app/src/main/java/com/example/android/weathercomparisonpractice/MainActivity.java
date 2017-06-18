package com.example.android.weathercomparisonpractice;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);

        ArrayList<CityData> cityDatas = new ArrayList<>();
        cityDatas.add(new CityData(R.mipmap.escudo_doninos, "Doñinos de Salamanca", "-5.75449", "40.96006" ));
        cityDatas.add(new CityData(R.mipmap.escudo_oklahoma, "Oklahoma", "-97.503281", "35.492088"));
        cityDatas.add(new CityData(R.mipmap.escudo_estocolmo, "Stockholm", "18.064899", "59.332581"));
        cityDatas.add(new CityData(R.mipmap.escudo_sri_lanka, "Democratic Socialist Republic of Sri Lanka", "81", "7"));
        cityDatas.add(new CityData(R.mipmap.escudo_nueva_zelanda, "New Zealand", "174", "-42"));

        // Crea un ArrayAdapter de cityDatas
        final WeatherAdapter weatherAdapter = new WeatherAdapter(this, cityDatas);
        // Encuentra la ListView que creamos en el weather_activity.xml.
        ListView weatherListView = (ListView) findViewById(R.id.list);
        // Se coloca el Adapter en la ListView.
        weatherListView.setAdapter(weatherAdapter);
    }





}
