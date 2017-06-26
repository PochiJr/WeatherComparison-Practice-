package com.example.android.weathercomparisonpractice;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //URL que nos proporciona los datos de las 5 ciudades que queremos en este caso.
    private static final String WEATHERMAP_REQUEST_URL =
            "http://api.openweathermap.org/data/2.5/group?id=6360360,4544379,2673730,1227603,2186224&appid=27b42a4827346748e8c232cac5ad95a7";

    public static final String LOG_TAG = MainActivity.class.getName();
    private WeatherAdapter mAdapter;

    // Aquí realizaremos la petición en una tarea secundaria.
    private class WeatherAsyncTask extends AsyncTask<String, Void, List<CityData>>{
        @Override
        protected List<CityData> doInBackground (String... urls){
            // No realizaremos la request si no hay urls o si el valor de la primera es nulo.
            if (urls.length < 1 || urls[0]== null){
                return null;
            }
            List<CityData> result = QueryUtils.fetchCityDataData(urls[0]);
            return result;
        }
        @Override
        protected void onPostExecute (List<CityData> data){
            // Limpia el adapter de posibles datos anteriores.
            mAdapter.clear();
            // Si hay una lista de CityData válida los añade al adapter y se actualizará la
            // ListView.
            if (data != null && !data.isEmpty()){
                mAdapter.addAll(data);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_activity);

        // Encuentra la ListView que creamos en el weather_activity.xml.
        ListView weatherListView = (ListView) findViewById(R.id.list);

        // Crea un ArrayAdapter de cityDatas
        mAdapter = new WeatherAdapter(this, new ArrayList<CityData>());

        // Se coloca el Adapter en la ListView.
        weatherListView.setAdapter(mAdapter);

        WeatherAsyncTask task = new WeatherAsyncTask();
        task.execute(WEATHERMAP_REQUEST_URL);

        weatherListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int identificador;
                if (position == 0){
                    identificador = 0;
                } else if (position == 1){
                    identificador = 1;
                } else if (position == 2){
                    identificador = 2;
                } else if (position == 3){
                    identificador = 3;
                } else if (position == 4){
                    identificador = 4;
                }

                // Crea una intent que abrirá la data_view.
                Intent dataViewIntent = new Intent(MainActivity.this, DataView.class);
                dataViewIntent.putExtra("ID_CIUDAD", identificador);
                startActivity(dataViewIntent);
            }
        });

    }
}
