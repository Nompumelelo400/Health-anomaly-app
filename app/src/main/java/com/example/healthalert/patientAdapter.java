package com.example.healthalert;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class patientAdapter extends RecyclerView.Adapter<patientAdapter.ViewHolder> {

    private final List<patient> patients;

    public patientAdapter(List<patient> patients) {
        this.patients = patients;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_patient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        patient p = patients.get(position);
        holder.tvName.setText(p.getName());
        holder.tvHeart.setText("Heart Rate: " + p.getHeartRate() + " bpm");
        holder.tvOxygen.setText("Oxygen: " + p.getOxygen() + "%");
        holder.tvTemp.setText("Temp: " + p.getTemperature() + "°C");
        holder.tvBloodPressure.setText("Blood pressure: " + p.getbloodPressure()+ "mmHg");
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvHeart, tvOxygen, tvTemp, tvBloodPressure;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvHeart = itemView.findViewById(R.id.tvHeartRate);
            tvOxygen = itemView.findViewById(R.id.tvOxygen);
            tvTemp = itemView.findViewById(R.id.tvTemperature);
            tvBloodPressure = itemView.findViewById(R.id.tvBloodPressure);
        }
    }
}

