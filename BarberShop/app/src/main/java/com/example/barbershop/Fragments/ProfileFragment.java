package com.example.barbershop.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.barbershop.Activities.MainActivity;
import com.example.barbershop.R;
import com.example.barbershop.RecyclerView.Appointments.CardAdapter;
import com.example.barbershop.Tables.Appointments;
import com.example.barbershop.ViewModel.AddViewModel;
import com.example.barbershop.ViewModel.ListViewModel;

import java.util.List;

import Utils.Utilities;

public class ProfileFragment extends Fragment {

    private CardAdapter adapter;
    private int id;
    private TextView placeName;
    private TextView placeSurname;
    private TextView placeEmail;

    private EditText txt;

    private AddViewModel addViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       //TextView textView1 = view.findViewById(R.id.textViewInsert1);
        //TextView textView2 = view.findViewById(R.id.textViewInsert2);
        TextView textView = view.findViewById(R.id.updatePassword);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        //textView1.setPaintFlags(textView1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        //textView2.setPaintFlags(textView2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        placeName = view.findViewById(R.id.placeTextNameProfile);
        placeSurname = view.findViewById(R.id.placeTextSurnameProfile);
        placeEmail = view.findViewById(R.id.placeTextEmailProfile);



        FragmentActivity activity = getActivity();

        if(activity != null) {
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.profile));
            setRecyclerView(activity);

            SharedPreferences sharedPreferences = activity.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
            String pref_name = sharedPreferences.getString("name", "default");
            String pref_surname = sharedPreferences.getString("surname", "default");
            String pref_email = sharedPreferences.getString("email", "default");
            int pref_id = sharedPreferences.getInt("id", 0);



            if(!pref_name.equals("default") && !pref_surname.equals("default") && !pref_email.equals("default") && pref_id != 0){
                placeName.setText(pref_name);
                placeSurname.setText(pref_surname);
                placeEmail.setText(pref_email);
                id = pref_id;
            }
            System.out.println("Il valore dell'id è:"+id);


            addViewModel = new ViewModelProvider((ViewModelStoreOwner)activity).get(AddViewModel.class);

            ListViewModel listViewModel = new ViewModelProvider(activity).get(ListViewModel.class);
            listViewModel.getUserAppointments(id).observe(activity, new Observer<List<Appointments>>() {
                @Override
                public void onChanged(List<Appointments> appointments) {
                    System.out.println(appointments.toString());
                    adapter.setData(appointments);

                }
            });

            view.findViewById(R.id.updatePassword).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    final EditText placePsw = new EditText(getContext());
                    placePsw.setHint("Deve essere almeno di 8 caratteri");
                    placePsw.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    builder.setTitle("Inserisci la nuova password");
                    builder.setView(placePsw);
                    LinearLayout layoutName = new LinearLayout(getContext());
                    layoutName.setOrientation(LinearLayout.VERTICAL);
                    layoutName.addView(placePsw);
                    builder.setView(layoutName);
                    builder.setPositiveButton("Aggiorna", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            txt = placePsw;
                            collectInputPsw();
                        }
                    });
                    builder.setNegativeButton("Cancella", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
            });


            view.findViewById(R.id.btnLogout).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("Sei sicuro di voler eseguire il log out?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SharedPreferences sharedPreferences = activity.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
                                    sharedPreferences.edit().clear().apply();
                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                    activity.finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .create()
                            .show();
                }
            });


            /*textView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.insertHomeActivityFragment((AppCompatActivity) activity, new AddReviewFragment(), AddReviewFragment.class.getSimpleName());
                }
            });*/

        }

    }

    private void collectInputPsw() {
        String getInput = txt.getText().toString();
        if (getInput.trim().equals("")){
            Toast.makeText(getContext(), "Devi inserire una stringa.", Toast.LENGTH_LONG).show();
        } else if(getInput.length() < 8){
            Toast.makeText(getContext(), "La password deve essere almeno di 8 caratteri.", Toast.LENGTH_LONG).show();
        } else {
            updatePassword(getInput);
        }
    }

    private void updatePassword(String psw) {
        addViewModel.updatePassword(psw, id);
        Toast.makeText(getContext(), "Password aggiornata!", Toast.LENGTH_SHORT).show();
    }

    private void setRecyclerView(Activity activity) {
        RecyclerView recyclerView = activity.findViewById(R.id.recycler_viewProfile);
        recyclerView.setHasFixedSize(false);
        adapter = new CardAdapter();
        recyclerView.setAdapter(adapter);
    }

}
