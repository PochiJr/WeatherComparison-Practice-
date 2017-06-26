package com.example.android.weathercomparisonpractice;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by jesus on 26/06/2017.
 */

public class DataView extends AppCompatActivity {

    public static final String LOG_TAG_2 = DataView.class.getName();

    //URL que nos proporciona los datos de las 5 ciudades que queremos en este caso.
    private static final String WEATHERMAP_REQUEST_URL =
            "http://api.openweathermap.org/data/2.5/group?id=6360360,4544379,2673730,1227603,2186224&appid=27b42a4827346748e8c232cac5ad95a7";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_view);
        Bundle extras = getIntent().getExtras();
        int datos;

        if (extras != null) {
            datos = extras.getInt("POSICION_CIUDAD");
        }
        dataAsyncTask task = new dataAsyncTask();
        task.execute();
    }
    // Actualiza la pantalla para que muestre la infromación correcta del objeto Data.
    private void updateUi (Data data){
        // Muestra el nombre de la ciudad en la Ui. (FALTA EXTRAERLO DEL SERVIDOR).
        TextView nameTextView = (TextView) findViewById(R.id.nombre_ciudad);
        nameTextView.setText(data.name);

        // Muestra la temperatura de la ciudad en la Ui. (FALTA EXTRAERLA DEL SERVIDOR).
        TextView temperatureTextView = (TextView) findViewById(R.id.temperatura);
        temperatureTextView.setText(data.temperature);

        // Muestra el tiempo atmosférico de la ciudad en la Ui. (FALTA EXTRAERLO DEL SERVIDOR).
        TextView weatherStatusTextView = (TextView) findViewById(R.id.estado_atmosferico);
        weatherStatusTextView.setText(data.weatherStatus);

        // Muestra la humedad de la ciudad en la Ui. (FALTA EXTRAERLA DEL SERVIDOR).
        TextView humidityTextView = (TextView) findViewById(R.id.humedad);
        humidityTextView.setText(data.humidity);

        // Muestra la velocidad del viento en la ciudad en la Ui. (FALTA EXTRAERLA DEL SERVIDOR).
        TextView windTextView = (TextView) findViewById(R.id.viento);
        windTextView.setText(data.speed);

        // Muestra la nubosidad de la ciudad en la Ui. (FALTA EXTRAERLA DEL SERVIDOR).
        TextView cloudsTextView = (TextView) findViewById(R.id.nubosidad);
        cloudsTextView.setText(data.all);
    }

    private class dataAsyncTask extends AsyncTask<URL, Void, Data>{
        @Override
        protected Data doInBackground(URL... urls) {
            // Crea el objeto URL.
            URL url = createUrl(WEATHERMAP_REQUEST_URL);

            // Hace la HTTP request a la url y recibe datos en JSON.
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e){

            }

            // Extrae los datos deseados del servidor y crea un nuevo Data object.
            Data data = extractFeatureFromJson(jsonResponse);
            // Devuelve el objeto Data.
            return data;
        }
        // Actualiza la Ui con el objeto Data previamente obtenido en el doInBakgroud.
        @Override
        protected void onPostExecute (Data data){
            if (data == null){
                return;
            }
            updateUi(data);
        }
    }

    // Creamos un objeto URL.
    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG_2, "Error creando la URL", e);
            return null;
        }
        return url;
    }

    // Creamos la HTTP request.
    private String makeHttpRequest (URL url) throws IOException {
        String jsonResponse = "";
        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(100000 /*Milisegundos*/);
            urlConnection.setConnectTimeout(100000 /*Milisegundos */);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG_2, "Error de consexión: " + urlConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(LOG_TAG_2, "Problema recuperando los datos en JSON del servidor", e);
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    // Convierte la inputStream en una String que contiene toda la respuesta en JSON del servidor.
    private String readFromStream (InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    // Extrae la información atmosférica en JSON del servidor.
    private Data extractFeatureFromJson (String dataJSON){
        try {
            JSONObject baseJsonObject = new JSONObject(dataJSON);
            JSONArray cityDataArray = baseJsonObject.getJSONArray("list");

            for (int i = 0; i < cityDataArray.length(); i++) {
                JSONObject currentCityData = cityDataArray.getJSONObject(i);

                // Extrae el valor de la key llamada "name".
                String name = currentCityData.getString("name");

                // Por cada dato de ciudad escoge la JSONArray asociado a la key "weather".
                JSONArray weather = currentCityData.getJSONArray("weather");

                // Extrae el valor del JSONObject que hay en la JSONArray weather.
                JSONObject weatherJsonObject = weather.getJSONObject(0);

                // Extrae el valor de la key llamada "main"
                String weatherStatus = weatherJsonObject.getString("main");

                // Por cada dato de ciudad escoge la JSONArray asociado a la key "main".
                JSONObject main = currentCityData.getJSONObject("main");

                // Extrae el valor de la key llamada "temp".
                int temperature = main.getInt("temp");
                // Extrae el valor de la key llamada "humidity".
                int humidity = main.getInt("humidity");

                // Por cada dato de ciudad escoge la JSONArray asociado a la key "wind".
                JSONObject wind = currentCityData.getJSONObject("wind");

                // Extrae el valor de la key llamada "speed".
                int speed = wind.getInt("speed");

                // Por cada dato de ciudad escoge la JSONArray asociado a la key "clouds".
                JSONObject clouds = currentCityData.getJSONObject("clouds");

                // Extrae el valor de la key llamada "all".
                int all = clouds.getInt("all");

                // Devuelve un nuevo objeto Data.
                return new Data(name, temperature, weatherStatus, humidity, speed, all);
            }

        } catch (JSONException e){
            Log.e(LOG_TAG_2, "Problema parsing la temperatura en JSON", e);
        }
        return  null;
    }
}
