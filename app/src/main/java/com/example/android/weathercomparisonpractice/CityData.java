package com.example.android.weathercomparisonpractice;

/**
 * Created by jesus on 18/06/2017.
 */

public class CityData {
    // Imagen que muestra el escudo de la ciudad.
    private int mImagenId;
    // Nombre de la ciudad.
    private String mNombre;
    // Longitud en coordenadas.
    private String mLongitud;
    // Latitud en coordenadas.
    private String mLatitud;

    //Creamos el objeto CityData con sus 4 parámetros (ImagenId, Nombre, Longitud y Latitud).
    public CityData (int imagenId, String nombre, String longitud, String latitud){
        mImagenId = imagenId;
        mNombre = nombre;
        mLongitud = longitud;
        mLatitud = latitud;
    }
    // Ahora obtenemos el valor de las variables mImagenId, mNombre, mLongitud y mLatitud;
    // respectivamente.
    public int getmImagenId(){
        return mImagenId;
    }

    public String getmNombre() {
        return mNombre;
    }

    public String getmLongitud() {
        return mLongitud;
    }

    public String getmLatitud() {
        return mLatitud;
    }
}
