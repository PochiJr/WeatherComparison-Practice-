package com.example.android.weathercomparisonpractice;

import android.text.TextUtils;
import android.util.Log;

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
import java.util.ArrayList;
import java.util.List;

import static com.example.android.weathercomparisonpractice.MainActivity.LOG_TAG;

/**
 * Created by jesus on 26/06/2017.
 */

public final class QueryUtils {

    private QueryUtils() {
    }

    // Crea un nuevo objeto URL a partir de la String stringUrl, si hay excepción se devuelve como
    // null.
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problema construyendo la URL", e);
        }
        return url;
    }

    // Crea la HTTP request para la URL obtenida y crea una String a partir de ella.
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        //Si la URL es null se devuelve inediatamente.
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(100000 /* Milisegundos.*/);
            urlConnection.setConnectTimeout(100000 /* Milisegundos.*/);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Si la request se realiza correctamente (código de respuesta 200) se lee el input
            // stream y se le hace parse a la respuesta.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error de conexión: " + urlConnection.getResponseCode());
            }
            // Aquí simplemente hacemos catch a la IOException.

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problema obteniendo los datos en JSON del servidor", e);
            // Independientemente de que se lance una exception o no en el bloque finally se realiza
            // una desconexión (o se "cierra" como en el caso del inputStream) para poder reusarlo.
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        // Se devuelve como resultado el JsonResponse que albergará la String inputStream.
        return jsonResponse;
    }

    // Convierte la inputStream en una String con toda la respuesta en JSON del servidor.
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Devuelve una lista de objetos CityData creada a partir de hacer parse a una respuesta en
     * JSON.
     */
    private static List<CityData> extractFeatureFromJson(String cityDataJSON) {
        // Si la String cityDataJSON es nula o está vacía, se devuelve el resultado.
        if (TextUtils.isEmpty(cityDataJSON)) {
            return null;
        }
        // Crea una ArrayList vacía a la que podemos añadir datos de ciudad.
        ArrayList<CityData> cityDatas = new ArrayList<>();

        // Intenta hacer parse a la JSON response.
        try {
            // Crea un JSONObject que es el root de la API.
            JSONObject baseJsonObject = new JSONObject(cityDataJSON);

            // Extrae la JSONArray asociada la key "list".
            JSONArray cityDataArray = baseJsonObject.getJSONArray("list");

            // Por cada ciudad en la cityData JSONArray crea un objeto CityData.
            for (int i = 0; i < cityDataArray.length(); i++) {
                // Escoge únicamente un elemento de la lista.
                JSONObject currentCityData = cityDataArray.getJSONObject(i);

                // Extrae el valor de la key llamada "name".
                String nombre = currentCityData.getString("name");

                // Por cada dato de ciudad escoge el JSONObject asociado a la key "coord".
                JSONObject coordinates = currentCityData.getJSONObject("coord");

                // Extrae el valor de la key llamada "lon".
                double longitud = coordinates.getDouble("lon");
                // Extrae el valor de la key llamada "lat":
                double latitud = coordinates.getDouble("lat");

                // Por cada dato de ciudad escoge la JSONArray asociado a la key "weather".
                JSONArray weather = currentCityData.getJSONArray("weather");

                // Extrae el valor del JSONObject que hay en la JSONArray weather.
                JSONObject weatherJsonObject = weather.getJSONObject(0);

                // Extrae el valor de la key llamada "main"
                String weatherStatus = weatherJsonObject.getString("main");

                // Por cada dato de ciudad escoge la JSONArray asociado a la key "main".
                JSONObject main = currentCityData.getJSONObject("main");

                // Extrae el valor de la key llamada "temp".
                float temperature = main.getLong("temp");
                // Extrae el valor de la key llamada "humidity".
                int humidity = main.getInt("humidity");

                // Por cada dato de ciudad escoge la JSONArray asociado a la key "wind".
                JSONObject wind = currentCityData.getJSONObject("wind");

                // Extrae el valor de la key llamada "speed".
                long speed = wind.getLong("speed");

                // Por cada dato de ciudad escoge la JSONArray asociado a la key "clouds".
                JSONObject clouds = currentCityData.getJSONObject("clouds");

                // Extrae el valor de la key llamada "all".
                int all = clouds.getInt("all");

                // Crea un nuevo objeto CityData con los valores requeridos.
                CityData cityData = new CityData(nombre, longitud, latitud);

                // Añade este nuevo objeto a la ArrayList.
                cityDatas.add(cityData);
            }
        } catch (JSONException e) {
            // Si ocurre algún error dentro del bloque try se capta aquí para evitar un cierre de la
            // App.
            Log.e("QueryUtils", "Problema parsing los resultados del terremoto en JSON", e);

        }
        return cityDatas;
    }
    // PARTE MÁS IMPORTANTE: Aquí todos los pasos de abajo se recogerán en un único public method
    // con el que interactuará la MainActivity.
    public static List<CityData> fetchCityDataData (String requestUrl){
        // Creamos el objeto URL.
        URL url = createUrl(requestUrl);

        // Realizamos la HTTP request y obtenemos la respuesta en JSON.
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e){
            Log.e(LOG_TAG, "Problema realizando la HTTP request.", e);
        }
        // Extrae los datos relevantes de la respuesta de la request en JSON.
        List<CityData> cityDatas = extractFeatureFromJson(jsonResponse);

        //Devuelve la lista de CityData.
        return cityDatas;
    }
}