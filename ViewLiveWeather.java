package com.example.appfin;

import android.os.Bundle;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

public class ViewLiveWeather extends AppCompatActivity {

    private static final int LOCATION_REQUEST_CODE = 100;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private TextView temperatureTextView;

    private static final String API_KEY = "62919279b8c53c812fc2a6c3537b9cdd";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_live_weather);

            temperatureTextView = findViewById(R.id.temperatureTextView);
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            } else {
                getLocationAndWeather();
            }
        }
    private void getLocationAndWeather() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        fetchWeatherData(latitude, longitude);
                    } else {
                        Toast.makeText(ViewLiveWeather.this, "Unable to get location", Toast.LENGTH_SHORT).show();
                    }
                });


    }
    private void fetchWeatherData(double latitude, double longitude) {
        String url = BASE_URL + "?lat=" + latitude + "&lon=" + longitude + "&units=metric&appid=" + API_KEY;

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        JSONObject main = response.getJSONObject("main");
                        int temperature = main.getInt("temp");
                        temperatureTextView.setText("Temperature: " + temperature + "°C");
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(ViewLiveWeather.this, "Error parsing weather data", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(ViewLiveWeather.this, "Failed to get weather data", Toast.LENGTH_SHORT).show());

        queue.add(jsonObjectRequest);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocationAndWeather();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }





    }
}