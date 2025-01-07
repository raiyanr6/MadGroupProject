package com.example.appfin;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CityWeather extends AppCompatActivity {

    private TextView cityNameText, temperatureText, humidityText, WindSpeed, descriptionText;
    private ImageView WeatherIcon;
    private Button refreshCity;
    private EditText cityNameInput;
    private static final String API_KEY = "62919279b8c53c812fc2a6c3537b9cdd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);

        cityNameInput = findViewById(R.id.TextCity);
        cityNameText = findViewById(R.id.TVWeather);
        temperatureText = findViewById(R.id.TVTemperature);
        humidityText = findViewById(R.id.HumidityNum);
        WindSpeed = findViewById(R.id.WindSpeed);
        WeatherIcon = findViewById(R.id.WeatherIcon);
        descriptionText = findViewById(R.id.TVDescription);
        refreshCity = findViewById(R.id.BtnChange);

        refreshCity.setOnClickListener(v -> {
            String cityName = cityNameInput.getText().toString();
            if (!cityName.isEmpty()) {
                fetchWeatherData(cityName);
            } else {
                cityNameInput.setError("Please Enter City Name");
            }
        });

        fetchWeatherData("Mumbai");
    }
    private void fetchWeatherData(String cityName) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" +
                cityName + "&appid=" + API_KEY + "&units=metric";
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                if (response.body() != null) {
                    String result = response.body().string();
                    runOnUiThread(() -> updateUI(result));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    private void updateUI(String result) {
        if (result != null) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONObject main = jsonObject.getJSONObject("main");
                double temperature = main.getDouble("temp");
                double humidity = main.getDouble("humidity");
                double windSpeed = jsonObject.getJSONObject("wind").getDouble("speed");

                String description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
                String iconCode = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
                String resourceName = "ic_" + iconCode;
                int resID = getResources().getIdentifier(resourceName, "drawable", getPackageName());
                WeatherIcon.setImageResource(resID);

                cityNameText.setText(jsonObject.getString("name"));
                temperatureText.setText(String.format("%.0f°C", temperature));
                humidityText.setText(String.format("%.0f%%", humidity));
                WindSpeed.setText(String.format("%.0f km/h", windSpeed));
                descriptionText.setText(description);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}