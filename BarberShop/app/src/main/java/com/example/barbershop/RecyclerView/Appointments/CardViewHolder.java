package com.example.barbershop.RecyclerView.Appointments;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.R;

public class CardViewHolder extends RecyclerView.ViewHolder {

    TextView date;
    TextView service;
    TextView review;

    public CardViewHolder(@NonNull View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.placeDateAppointment);
        service = itemView.findViewById(R.id.placeServiceAppointment);
        review = itemView.findViewById(R.id.placeReviewAppointment);
    }

}
