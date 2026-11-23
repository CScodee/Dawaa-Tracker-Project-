package com.example.dawaatracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    private TextView tvCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCount = findViewById(R.id.tvCount);
        CardView cardList = findViewById(R.id.cardList);
        CardView cardAdd = findViewById(R.id.cardAdd);
        CardView cardBmi = findViewById(R.id.cardBmi);

        cardList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MedicineListActivity.class));
            }
        });

        cardAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddMedicineActivity.class));
            }
        });

        cardBmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BmiActivity.class));
            }
        });
    }

    // refresh the counter every time we come back to this screen
    @Override
    protected void onResume() {
        super.onResume();
        DatabaseHelper db = new DatabaseHelper(this);
        int count = db.getAllMedicines().size();
        tvCount.setText("Medicines saved: " + count);
    }
}
