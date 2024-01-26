package com.example.barbershop.Fragments;

import android.app.Activity;
import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.barbershop.Activities.HomeActivity;
import com.example.barbershop.R;

import Utils.Utilities;

public class LoginFragment extends Fragment {

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

            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Utilities.insertMainActivityFragment((AppCompatActivity) activity, new RegistrationFragment(), RegistrationFragment.class.getSimpleName());
                }
            });

            view.findViewById(R.id.btnLogin).setOnClickListener(view1 -> {
                Intent intent = new Intent(getContext(), HomeActivity.class);
                this.startActivity(intent);
                activity.finish();
            });

        }

    }
}
