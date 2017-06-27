package com.example.android.weathercomparisonpractice;

/**
 * Created by jesus on 26/06/2017.
 */

public class Data {
    // Nombre de la ciudad.
    public final String name;
    // Temperatura de la ciudad.
    public final int temperature;
    // Tiempo atmosférico de la ciudad.
    public final String weatherStatus;
    // Humedad de la ciudad;
    public final int humidity;
    // Viento de la ciudad;
    public final double speed;
    // Nubosidad de la ciudad.
    public final int all;

    //Creamos el objeto Data con sus 6 parámetros (nombre, temperatura, tiempo, humedad, viento y nubosidad).
    public Data (String dataName, int dataTemperature, String dataWeatherStatus, int dataHumidity,
                 double dataSpeed, int dataAll){
        name = dataName;
        temperature = dataTemperature;
        weatherStatus = dataWeatherStatus;
        humidity = dataHumidity;
        speed = dataSpeed;
        all = dataAll;
    }
}
