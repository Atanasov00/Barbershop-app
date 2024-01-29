package com.example.barbershop.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.barbershop.Activities.HomeActivity;
import com.example.barbershop.R;
import com.example.barbershop.Tables.ProfileInformation;
import com.example.barbershop.ViewModel.ListViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import Utils.Utilities;

public class LoginFragment extends Fragment {

    private ListViewModel viewModel;

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
        return inflater.inflate(R.layout.login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();

        TextView textView = view.findViewById(R.id.idSignUp);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        if(activity != null){
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.app_name));
            TextView signUp = view.findViewById(R.id.idSignUp);



            email = view.findViewById(R.id.place_email);
            psw = view.findViewById(R.id.place_password);


            if(Utilities.getEmail() != null && Utilities.getPassword() != null){
                email.setText(Utilities.getEmail());
                psw.setText(Utilities.getPassword());
            }


            view.findViewById(R.id.btnLogin).setOnClickListener(view1 -> {
                viewModel = new ViewModelProvider(activity).get(ListViewModel.class);
                viewModel.getProfilesInfo().observe(activity, profileInformation -> {
                    try {
                        if(email.getText().length() != 0 && psw.getText().length() != 0){
                            boolean found = false;
                            for(ProfileInformation profile: profileInformation){
                                if(profile != null && profile.getEmail().equals(email.getText().toString()) &&
                                profile.getPassword().equals(psw.getText().toString())){
                                    Toast.makeText(activity, "Login eseguito con successo!", Toast.LENGTH_SHORT).show();
                                    SharedPreferences sharedPreferences = activity.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("email", profile.getEmail());
                                    editor.putInt("id", profile.getId());
                                    editor.putString("name", profile.getName());
                                    editor.putString("surname", profile.getSurname());
                                    editor.apply();
                                    found = true;
                                    Intent intent = new Intent(getContext(), HomeActivity.class);
                                    this.startActivity(intent);
                                    activity.finish();
                                    break;
                                }
                            }
                            if(!found){
                                Toast.makeText(activity, "Username o password incorretti!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(activity, "Inserisci la mail e la password", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            });

            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.insertMainActivityFragment((AppCompatActivity) activity, new RegistrationFragment(), RegistrationFragment.class.getSimpleName());
                }
            });

            /*view.findViewById(R.id.btnLogin).setOnClickListener(view1 -> {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                this.startActivity(intent);
                activity.finish();
            });*/

        }

    }
}
