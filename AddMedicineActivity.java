package com.example.dawaatracker;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddMedicineActivity extends AppCompatActivity {

    private EditText etName, etDose, etTimes, etNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitle("Add Medicine");

        etName = findViewById(R.id.etName);
        etDose = findViewById(R.id.etDose);
        etTimes = findViewById(R.id.etTimes);
        etNotes = findViewById(R.id.etNotes);
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMedicine();
            }
        });
    }

    private void saveMedicine() {
        String name = etName.getText().toString().trim();
        String dose = etDose.getText().toString().trim();
        String timesText = etTimes.getText().toString().trim();
        String notes = etNotes.getText().toString().trim();

        if (name.isEmpty()) {
            etName.setError("Please enter the medicine name");
            return;
        }
        if (timesText.isEmpty()) {
            etTimes.setError("Please enter how many times per day");
            return;
        }

        int times = Integer.parseInt(timesText);
        if (times < 1) {
            etTimes.setError("Must be at least 1");
            return;
        }

        DatabaseHelper db = new DatabaseHelper(this);
        long result = db.insertMedicine(new Medicine(0, name, dose, times, notes));

        if (result == -1) {
            Toast.makeText(this, "Something went wrong, medicine not saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Medicine saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
