package com.example.barbershop.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.barbershop.R;
import com.example.barbershop.Tables.ProfileInformation;
import com.example.barbershop.ViewModel.AddViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

import Utils.Utilities;

public class RegistrationFragment extends Fragment {

    private TextInputEditText name;

    private TextInputEditText surname;

    private TextInputEditText email;

    private TextInputEditText psw;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.registration, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();

        if(activity != null){
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.app_name));

            AddViewModel addViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(AddViewModel.class);

            name = view.findViewById(R.id.placeR_name);
            surname = view.findViewById(R.id.placeR_surname);
            email = view.findViewById(R.id.placeR_email);
            psw = view.findViewById(R.id.placeR_password);

            view.findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if(name.getText() != null && surname.getText() != null && email.getText() != null && psw.getText() != null &&
                        patternMatches(email.getText()) && psw.getText().length() >= 8) {
                            addViewModel.addProfile(new ProfileInformation(email.getText().toString(), psw.getText().toString(),
                                    name.getText().toString(), surname.getText().toString()));

                            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                            builder.setMessage("Account creato! Vuoi loggarti?").setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Utilities.insertMainActivityFragment((AppCompatActivity) activity, new LoginFragment(), LoginFragment.class.getSimpleName());
                                    Utilities.setEmail(email.getText().toString());
                                    Utilities.setEmail(email.getText().toString());
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

                            ((AppCompatActivity) activity).getSupportFragmentManager().popBackStack();
                        } else {
                            Toast.makeText(activity, "Compila i campi con i valori corretti!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    private boolean patternMatches(Editable text) {
        return text.length() > 0 && Pattern.compile("^(.+)@(\\S+)$")
                .matcher(text)
                .matches();
    }

}
