package com.example.appfin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button btnViewLiveWeather, btnCityWeather, btnNotePad, btnCarbonTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnViewLiveWeather = findViewById(R.id.btnViewLiveWeather);
        btnCityWeather = findViewById(R.id.btnCityWeather);
        btnNotePad = findViewById(R.id.btnNotePad);
        btnCarbonTracker = findViewById(R.id.btnCarbonTracker);

        btnViewLiveWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewLiveWeather.class);
                startActivity(intent);
                finish();
            }
        });

        btnCityWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CityWeather.class);
                startActivity(intent);
                finish();
            }
        });

        btnNotePad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotePad.class);
                startActivity(intent);
                finish();
            }
        });

        btnCarbonTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CarbonFootprintTracker.class);
                startActivity(intent);
                finish();
            }
        });


    }
}