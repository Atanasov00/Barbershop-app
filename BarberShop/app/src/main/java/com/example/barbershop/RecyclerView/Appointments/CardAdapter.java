package com.example.barbershop.RecyclerView.Appointments;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.Fragments.AddReviewFragment;
import com.example.barbershop.R;
import com.example.barbershop.Tables.Appointments;
import com.example.barbershop.ViewModel.AddViewModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Utils.Utilities;

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

        if(appointment.getRecension() == 0){
            holder.review.setText("Inserisci");

            holder.review.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String formattedDate = " ";
                    LocalDate currentDate = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        currentDate = LocalDate.now();
                        int year = currentDate.getYear();
                        int month = currentDate.getMonthValue();
                        int day = currentDate.getDayOfMonth();
                        formattedDate = day +"/" + month + "/"+ year;
                    }

                    String serviceDate = appointment.getDate();
                    LocalDate date = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        date = LocalDate.parse(serviceDate, formatter);
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        Activity activity = (Activity) view.getContext();
                        if(currentDate.compareTo(date) >= 0) {
                            AddViewModel addViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(AddViewModel.class);
                            addViewModel.updateRecension(1, appointment.getId());
                            Utilities.insertHomeActivityFragment((AppCompatActivity) activity, new AddReviewFragment(), AddReviewFragment.class.getSimpleName());
                        } else {
                            Toast.makeText(activity, "Non è possibile inserire la recensione di questa prenotazione.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

        } else if (appointment.getRecension() == 1) {
            holder.review.setText("Già inserita");
        }


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
