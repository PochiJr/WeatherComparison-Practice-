package com.example.android.weathercomparisonpractice;

/**
 * Created by jesus on 18/06/2017.
 */

public class CityData {
    // Nombre de la ciudad.
    private String mNombre;
    // Longitud en coordenadas.
    private double mLongitud;
    // Latitud en coordenadas.
    private double mLatitud;

    //Creamos el objeto CityData con sus 4 parámetros (ImagenId, Nombre, Longitud y Latitud).
    public CityData (String nombre, double longitud, double latitud){
        mNombre = nombre;
        mLongitud = longitud;
        mLatitud = latitud;
    }
    // Ahora obtenemos el valor de las variables mNombre, mLongitud y mLatitud;
    // respectivamente.
    public String getmNombre() {
        return mNombre;
    }

    public double getmLongitud() {
        return mLongitud;
    }

    public double getmLatitud() {
        return mLatitud;
    }
}
