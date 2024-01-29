package com.example.barbershop.RecyclerView.Appointments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.R;
import com.example.barbershop.Tables.Appointments;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardViewHolder> {

    private List<Appointments> appointmentsList = new ArrayList<>();

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointments_layout,
                parent, false);
        return new CardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Appointments appointment = appointmentsList.get(position);
        holder.date.setText(appointment.getDate());
        String serviceName = "null";
        if(appointment.getService_id() == 1) {
            serviceName = "Taglio barba e capelli";
        } else if(appointment.getService_id() == 2) {
            serviceName = "Taglio capelli";
        } if(appointment.getService_id() == 3) {
            serviceName = "Taglio barba";
        }
        holder.service.setText(serviceName);
        holder.review.setText("Inserisci");
    }

    @Override
    public int getItemCount() {
        return appointmentsList.size();
    }

    public void setData(List<Appointments> list){
        System.out.println("Pre diffresult Card adapter appointmentlist:"+this.appointmentsList.toString());
        System.out.println("Pre diffresult Card adapter list:"+list.toString());
        final CardItemDiffCallback diffCallback = new CardItemDiffCallback(this.appointmentsList, list);


        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        System.out.println("Card adapter appointmentlist:"+this.appointmentsList.toString());
        System.out.println("Card adapter list:"+list.toString());

        this.appointmentsList = new ArrayList<>(list);


        diffResult.dispatchUpdatesTo(this);

    }

}
