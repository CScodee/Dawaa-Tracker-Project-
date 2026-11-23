package com.example.dawaatracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class BmiActivity extends AppCompatActivity {

    private EditText etWeight, etHeight;
    private TextView tvBmiValue, tvBmiCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        setTitle("BMI Calculator");

        etWeight = findViewById(R.id.etWeight);
        etHeight = findViewById(R.id.etHeight);
        tvBmiValue = findViewById(R.id.tvBmiValue);
        tvBmiCategory = findViewById(R.id.tvBmiCategory);
        Button btnCalc = findViewById(R.id.btnCalc);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBmi();
            }
        });
    }

    private void calculateBmi() {
        String w = etWeight.getText().toString().trim();
        String h = etHeight.getText().toString().trim();

        if (w.isEmpty() || h.isEmpty()) {
            Toast.makeText(this, "Please enter weight and height", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double weight = Double.parseDouble(w);
            double heightM = Double.parseDouble(h) / 100;

            if (weight <= 0 || heightM <= 0) {
                Toast.makeText(this, "Values must be bigger than zero", Toast.LENGTH_SHORT).show();
                return;
            }

            double bmi = weight / (heightM * heightM);

            String category;
            if (bmi < 18.5) {
                category = "Underweight";
            } else if (bmi < 25) {
                category = "Normal weight";
            } else if (bmi < 30) {
                category = "Overweight";
            } else {
                category = "Obese";
            }

            tvBmiValue.setText(String.format(Locale.US, "%.1f", bmi));
            tvBmiCategory.setText(category);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }
}
