package com.example.dawaatracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Medicine medicine);
        void onDeleteClick(Medicine medicine);
    }

    private List<Medicine> medicines;
    private final OnItemClickListener listener;

    public MedicineAdapter(List<Medicine> medicines, OnItemClickListener listener) {
        this.medicines = medicines;
        this.listener = listener;
    }

    public void setData(List<Medicine> newList) {
        this.medicines = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medicine, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        final Medicine m = medicines.get(position);
        holder.tvName.setText(m.getName());
        holder.tvDose.setText("Dose: " + m.getDose());
        holder.tvTimes.setText(m.getTimesPerDay() + " time(s) per day");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(m);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteClick(m);
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    static class MedicineViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDose, tvTimes;
        Button btnDelete;

        MedicineViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvItemName);
            tvDose = itemView.findViewById(R.id.tvItemDose);
            tvTimes = itemView.findViewById(R.id.tvItemTimes);
            btnDelete = itemView.findViewById(R.id.btnItemDelete);
        }
    }
}
