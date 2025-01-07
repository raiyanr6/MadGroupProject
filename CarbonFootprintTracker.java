package com.example.appfin;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CarbonFootprintTracker extends AppCompatActivity {

    private EditText flightUsageInput;
    private Spinner vehicleTypeSpinner;
    private EditText mileageInput;
    private Button calculateButton;
    private TextView resultTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carbon_footprint_tracker);

        flightUsageInput = findViewById(R.id.flightUsageInput);
        vehicleTypeSpinner = findViewById(R.id.vehicleTypeSpinner);
        mileageInput = findViewById(R.id.mileageInput);
        calculateButton = findViewById(R.id.calculateButton);
        resultTextView = findViewById(R.id.resultTextView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.vehicle_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicleTypeSpinner.setAdapter(adapter);

        // Set up button click listener
        calculateButton.setOnClickListener(v -> calculateCarbonFootprint());

    }
    private void calculateCarbonFootprint() {
        // Get user inputs
        String flightUsageStr = flightUsageInput.getText().toString();
        String vehicleType = vehicleTypeSpinner.getSelectedItem().toString();
        String mileageStr = mileageInput.getText().toString();

        if (flightUsageStr.isEmpty() || mileageStr.isEmpty()) {
            resultTextView.setText("Please fill in all fields.");
            return;
        }

        int flightUsage = Integer.parseInt(flightUsageStr);
        double mileage = Double.parseDouble(mileageStr);

        // Carbon emission factors (kg CO2)
        double flightEmissionFactor = 0.15; // Per km
        double carEmissionFactor = 2.31; // Per liter
        double electricCarEmissionFactor = 0.12; // Per kWh
        double bikeEmissionFactor = 0.02; // Approximation for production
        double electricScooterEmissionFactor = 0.05; // Per kWh
        double noneEmission = 0;

        double carbonFootprint = 0;
        carbonFootprint += flightUsage * flightEmissionFactor * 1000; // Assuming 1000 km per flight

        // Calculate emissions from vehicles
        switch (vehicleType) {
            case "Car":
                carbonFootprint += mileage * carEmissionFactor; // Mileage in liters
                break;
            case "Electric Car":
                carbonFootprint += mileage * electricCarEmissionFactor; // Mileage in kWh
                break;
            case "Bike":
                carbonFootprint += mileage * bikeEmissionFactor; // Approximation
                break;
            case "Electric Scooter":
                carbonFootprint += mileage * electricScooterEmissionFactor; // Mileage in kWh
                break;
            case "None":
                carbonFootprint +=0; // Mileage in kWh
                break;

        }
        // Display the result
        resultTextView.setText(String.format("Your approximate carbon footprint is %.2f kg CO2/year.", carbonFootprint));

    }
}