package com.example.android.weathercomparisonpractice;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by jesus on 18/06/2017.
 */

public class WeatherAdapter extends ArrayAdapter<CityData> {

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

        // Se encuentra la ImageView que muestra la imagen del escudo de la ciudad.
        ImageView escudoImageView = (ImageView) listItemView.findViewById(R.id.imagen_escudo);
        // Se asigna esta imagen a su objeto de la lista en particular según la posición de este.
        escudoImageView.setImageResource(currentCityData.getmImagenId());

        // Se encuentra la TextView que muestra el nombre de la ciudad.
        TextView nombreCiudadTextView = (TextView) listItemView.findViewById(R.id.nombre_ciudad);
        // Se asigna este texto a su objeto de la lista en particular según la posición de este.
        nombreCiudadTextView.setText(currentCityData.getmNombre());

        // Se encuentra la TextView que muestra la longitud.
        TextView longitudTextView = (TextView) listItemView.findViewById(R.id.longitud);
        // Se asigna este texto a su objeto de la lista en particular según la posición de este.
        longitudTextView.setText(currentCityData.getmLongitud());

        // Se encuentra la TextView que muestra la latitud.
        TextView latitudTextView = (TextView) listItemView.findViewById(R.id.latitud);
        // Se asigna este texto a su objeto de la lista en particular según la posición de este.
        latitudTextView.setText(currentCityData.getmLatitud());

        // Se devuelve el listItemView (que contiene una ImageView y 3 TextViews para que sea
        // mostrado en la ListView.
        return listItemView;
    }
}
