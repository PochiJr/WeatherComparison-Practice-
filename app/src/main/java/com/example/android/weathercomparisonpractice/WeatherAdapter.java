package com.example.android.weathercomparisonpractice;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by jesus on 18/06/2017.
 */

public class WeatherAdapter extends ArrayAdapter<CityData> {
    // Crea el formateador de la longitud.
    private String formatLongitude(double longitude){
        DecimalFormat longitudeFormat = new DecimalFormat("0.000000");
        return longitudeFormat.format(longitude);
    }
    // Crea el formateador de la latitud.
    private String formatLatitude(double latitude){
        DecimalFormat latitudeFormat = new DecimalFormat("0.000000");
        return latitudeFormat.format(latitude);
    }

    public WeatherAdapter(Activity context, ArrayList<CityData> cityDatas){
        super(context, 0 ,cityDatas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Comprueba si la view está siendo reusada, si no, la infla.
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Se obtiene el objeto CityData según su determinada posición
        CityData currentCityData = getItem(position);

        // Se encuentra la TextView que muestra el nombre de la ciudad.
        TextView nombreCiudadTextView = (TextView) listItemView.findViewById(R.id.nombre_ciudad);
        // Se asigna este texto a su objeto de la lista en particular según la posición de este.
        nombreCiudadTextView.setText(currentCityData.getmNombre());

        // Se encuentra la TextView que muestra la longitud.
        TextView longitudTextView = (TextView) listItemView.findViewById(R.id.longitud);
        // Se formatea la longitud para mostrar 6 decimales.
        String formattedLongitude = formatLongitude(currentCityData.getmLongitud());
        // Se asigna la longitud del objeto correspondiente en el texto.
        longitudTextView.setText(formattedLongitude);

        // Se encuentra la TextView que muestra la latitud.
        TextView latitudTextView = (TextView) listItemView.findViewById(R.id.latitud);
        // Se formatea la latitud para que muestre 6 decimales.
        String formattedLatitude = formatLatitude(currentCityData.getmLatitud());
        // Se asigna la latitud del objeto correspondiente en el texto.
        latitudTextView.setText(formattedLatitude);

        // Se devuelve el listItemView (que contiene una ImageView y 3 TextViews para que sea
        // mostrado en la ListView.
        return listItemView;
    }
}
