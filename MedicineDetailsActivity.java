package com.example.dawaatracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MedicineDetailsActivity extends AppCompatActivity
        implements ConfirmDeleteDialog.DeleteListener {

    private int medicineId;
    private String medicineName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setTitle("Medicine Details");

        // read the data that came from the list screen
        Intent intent = getIntent();
        medicineId = intent.getIntExtra("id", -1);
        medicineName = intent.getStringExtra("name");
        String dose = intent.getStringExtra("dose");
        int times = intent.getIntExtra("times", 0);
        String notes = intent.getStringExtra("notes");

        TextView tvName = findViewById(R.id.tvDetailName);
        TextView tvDose = findViewById(R.id.tvDetailDose);
        TextView tvTimes = findViewById(R.id.tvDetailTimes);
        TextView tvNotes = findViewById(R.id.tvDetailNotes);
        Button btnDelete = findViewById(R.id.btnDetailDelete);

        tvName.setText(medicineName);
        tvDose.setText(dose == null || dose.isEmpty() ? "-" : dose);
        tvTimes.setText(String.valueOf(times));
        tvNotes.setText(notes == null || notes.isEmpty() ? "-" : notes);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDeleteDialog.newInstance(medicineId, medicineName)
                        .show(getSupportFragmentManager(), "confirmDelete");
            }
        });
    }

    @Override
    public void onConfirmDelete(int id) {
        DatabaseHelper db = new DatabaseHelper(this);
        db.deleteMedicine(id);
        Toast.makeText(this, "Medicine deleted", Toast.LENGTH_SHORT).show();
        finish();
    }
}
