package com.example.barbershop.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.barbershop.Activities.HomeActivity;
import com.example.barbershop.Activities.MainActivity;
import com.example.barbershop.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import Utils.Utilities;

public class HomeFragment extends Fragment {

    private EditText selected_date;

    private String[] item = {"Taglio", "Barba", "Taglio + Barba"};

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapterItems;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentActivity activity = getActivity();

        if(activity != null){
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.home));


            //MaterialButton button = view.findViewById(R.id.btnDatePicker);

            //TextView textView = view.findViewById(R.id.textDate);


            /*autoCompleteTextView = getView().findViewById(R.id.auto_complete);
            adapterItems = new ArrayAdapter<String>(getContext(), R.layout.list_item);

            autoCompleteTextView.setAdapter(adapterItems);


            autoCompleteTextView.setOnClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String item = adapterView.getItemAtPosition(i).toString();
                    Toast.makeText(activity, "Item: " + item, Toast.LENGTH_LONG).show();
                }
            });*/


            selected_date = view.findViewById(R.id.selected_date);
            selected_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Seleziona la data")
                            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                            .build();
                    materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                        @Override
                        public void onPositiveButtonClick(Long selection) {
                            String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(selection));
                            //textView.setText(MessageFormat.format("Selected Date: {0}", date));
                            //button.setText(MessageFormat.format("Selected Date: {0}", date));
                            selected_date.setText(MessageFormat.format("{0}", date));
                        }
                    });
                    materialDatePicker.show(activity.getSupportFragmentManager(), "tag");
                }
            });






        }


    }
}
