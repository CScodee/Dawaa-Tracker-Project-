package com.example.dawaatracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MedicineListActivity extends AppCompatActivity
        implements ConfirmDeleteDialog.DeleteListener {

    private EditText etSearch;
    private TextView tvEmpty;
    private RecyclerView recyclerView;
    private MedicineAdapter adapter;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        setTitle("My Medicines");

        db = new DatabaseHelper(this);
        etSearch = findViewById(R.id.etSearch);
        tvEmpty = findViewById(R.id.tvEmpty);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MedicineAdapter(new ArrayList<Medicine>(), new MedicineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Medicine m) {
                // send the data of the clicked medicine to the details screen
                Intent intent = new Intent(MedicineListActivity.this, MedicineDetailsActivity.class);
                intent.putExtra("id", m.getId());
                intent.putExtra("name", m.getName());
                intent.putExtra("dose", m.getDose());
                intent.putExtra("times", m.getTimesPerDay());
                intent.putExtra("notes", m.getNotes());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Medicine m) {
                ConfirmDeleteDialog.newInstance(m.getId(), m.getName())
                        .show(getSupportFragmentManager(), "confirmDelete");
            }
        });
        recyclerView.setAdapter(adapter);

        // search while the user types
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadData();
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        String keyword = etSearch.getText().toString().trim();
        List<Medicine> list;
        if (keyword.isEmpty()) {
            list = db.getAllMedicines();
        } else {
            list = db.searchMedicines(keyword);
        }
        adapter.setData(list);

        if (list.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onConfirmDelete(int medicineId) {
        db.deleteMedicine(medicineId);
        Toast.makeText(this, "Medicine deleted", Toast.LENGTH_SHORT).show();
        loadData();
    }
}
