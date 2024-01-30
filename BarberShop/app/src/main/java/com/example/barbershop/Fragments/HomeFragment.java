package com.example.barbershop.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.barbershop.R;
import com.example.barbershop.Tables.Appointments;
import com.example.barbershop.Tables.Service;
import com.example.barbershop.ViewModel.AddViewModel;
import com.example.barbershop.ViewModel.ListViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import Utils.Utilities;

public class HomeFragment extends Fragment {

    private EditText selected_date;

    private List<String> times = new ArrayList<>(Arrays.asList("09:00", "09:30","10:00", "10:30","11:00", "11:30","14:00", "14:30",
            "15:00", "15:30","16:00", "16:30", "17:00", "17:30"));

    private String selectedService;
    private String selectedTime;
    private AutoCompleteTextView autoCompleteTextViewService;
    private AutoCompleteTextView autoCompleteTextViewTime;

    //private TextInputLayout textInputLayoutTime;

    private ArrayAdapter<String> adapterItems;
    FragmentActivity activity;

    private int userID;
    private String pickedDate;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //textInputLayoutTime = inflater.inflate(R.layout.home, container, false).findViewById(R.id.input_time);
        //autoCompleteTextViewTime = inflater.inflate(R.layout.home, container, false).findViewById(R.id.auto_complete_time);
        //textInputLayoutTime.setEnabled(false);
        return inflater.inflate(R.layout.home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = getActivity();



        if(activity != null){
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.home));

            SharedPreferences sharedPreferences = activity.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
            int pref_id = sharedPreferences.getInt("id", 0);
            System.out.println("pref_id:" + pref_id);
            if(pref_id != 0){
                userID = pref_id;
            }
            System.out.println("userID:" + userID);


            List<String> servicesVaulues = new ArrayList<>();

            AddViewModel addViewModel = new ViewModelProvider(activity).get(AddViewModel.class);

            ListViewModel listViewModel = new ViewModelProvider(activity).get(ListViewModel.class);
            listViewModel.servicesInfo.observe(activity, services -> {
                try {
                    services.toString();
                    for(Service service: services){
                        servicesVaulues.add(service.getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });




            List<String> freeTimes = new ArrayList<>();

            selected_date = view.findViewById(R.id.selected_date);
            selected_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    freeTimes.clear();
                    freeTimes.addAll(times);
                    MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                            .setTitleText("Seleziona la data")
                            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                            .build();
                    materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                        @Override
                        public void onPositiveButtonClick(Long selection) {
                            pickedDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(selection));
                            //textView.setText(MessageFormat.format("Selected Date: {0}", date));
                            //button.setText(MessageFormat.format("Selected Date: {0}", date));
                            selected_date.setText(MessageFormat.format("{0}", pickedDate));
                            System.out.println("Formattazione:" + pickedDate);
                        }
                    });
                    materialDatePicker.show(activity.getSupportFragmentManager(), "tag");

                }
            });

            ArrayAdapter<String> adapterService = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, servicesVaulues);
            autoCompleteTextViewService = view.findViewById(R.id.auto_complete_service);
            autoCompleteTextViewService.setDropDownVerticalOffset(-autoCompleteTextViewService.getHeight());
            autoCompleteTextViewService.setTextColor(activity.getResources().getColor(R.color.white));
            autoCompleteTextViewService.setAdapter(adapterService);
            autoCompleteTextViewService.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    pickedDate = selected_date.getText().toString();
                    listViewModel.getAppointmentsInfo().observe(activity, appointments -> {
                        try {
                            System.out.println("Data:"+ pickedDate);
                            List<Appointments> dateAppointments = new ArrayList<>();
                            for(Appointments appointment: appointments){
                                if(appointment.getDate().equals(pickedDate)){
                                    dateAppointments.add(appointment);
                                }
                            }
                            System.out.println("Data:"+ pickedDate);
                            for(Appointments appointment: dateAppointments){
                                if(appointment.getService_id() == 3){ // barba
                                    //TO DO -> ELIMINARE GLI ORATI DAL MENU
                                    for(String t: freeTimes){
                                        if(t.equals(appointment.getTime())){
                                            System.out.println("Orario trovato");
                                            freeTimes.remove(appointment.getTime());
                                        }
                                    }
                                } else if(appointment.getService_id() == 2){
                                    String sub = appointment.getTime().substring(3,5);
                                    String next;
                                    if(sub.compareTo("00") == 0){
                                        System.out.println(appointment.getTime().substring(0,2));
                                        next = appointment.getTime().substring(0,3) + "30";
                                    } else {
                                        int val = Integer.parseInt(appointment.getTime().substring(0,2));
                                        val++;
                                        next = Integer.toString(val) + ":00";
                                    }
                                    for(String t: freeTimes){
                                        if(t.equals(appointment.getTime())){
                                            System.out.println("Orario trovato");
                                            freeTimes.remove(appointment.getTime());
                                        }
                                    }
                                    System.out.println("Next: " + next);
                                    freeTimes.remove(next);
                                } else if(appointment.getService_id() == 1){
                                    String sub = appointment.getTime().substring(3,5);
                                    String next, next2;
                                    System.out.println(appointment.getTime().substring(0, 3));
                                    System.out.println(sub);
                                    if(sub.compareTo("00") == 0){
                                        System.out.println(appointment.getTime().substring(0, 3));
                                        next = appointment.getTime().substring(0, 3) + "30";
                                        int val = Integer.parseInt(appointment.getTime().substring(0, 2));
                                        val++;
                                        next2 = Integer.toString(val) + appointment.getTime().substring(2,5);
                                    } else {
                                        int val = Integer.parseInt(appointment.getTime().substring(0, 2));
                                        val++;
                                        next = Integer.toString(val) + ":00";
                                        next2 = Integer.toString(val) + appointment.getTime().substring(2,5);
                                    }
                                    for(String t: freeTimes){
                                        if(t.equals(appointment.getTime())){
                                            System.out.println("Orario trovato");
                                            freeTimes.remove(appointment.getTime());
                                        }
                                    }
                                    System.out.println("Next=" + next + "|"+"Next2="+next2);
                                    freeTimes.remove(next);
                                    freeTimes.remove(next2);
                                }
                            }

                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    });

                    selectedService = (String) adapterView.getItemAtPosition(i);
                    System.out.println(selectedService);
                    //System.out.println("Prova: " + listViewModel.getIDfromName(selectedService));
                }
            });




            ArrayAdapter<String> adapterTime = new ArrayAdapter<String>(activity, android.R.layout.simple_dropdown_item_1line, freeTimes);
            autoCompleteTextViewTime = view.findViewById(R.id.auto_complete_time);
            autoCompleteTextViewTime.setTextColor(activity.getResources().getColor(R.color.white));
            autoCompleteTextViewTime.setAdapter(adapterTime);
            autoCompleteTextViewTime.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    if(selected_date.getText().toString().equals("Data")){
                        Toast.makeText(activity, "Devi selezionare la data prima.", Toast.LENGTH_SHORT).show();
                    } else {
                        selectedTime = (String) adapterView.getItemAtPosition(i);
                    }
                }
            });

            view.findViewById(R.id.btnBook).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try{
                        if(pickedDate != null && selectedTime != null && selectedService != null && checkDate(pickedDate)){
                            int id = 0;
                            if(selectedService.equals("Taglio barba")){
                                id = 3;
                            } else if(selectedService.equals("Taglio capelli")){
                                id = 2;
                            } else if(selectedService.equals("Taglio barba e capelli")){
                                id = 1;
                            }
                            addViewModel.addAppointment(new Appointments(pickedDate, selectedTime, id, userID, 0));
                            Toast.makeText(activity, "La prenotazione è avvenuta con successo! E' possibile visualizzarla nella sezione profilo.", Toast.LENGTH_SHORT).show();
                            Utilities.insertHomeActivityFragment((AppCompatActivity) activity, new HomeFragment(), HomeFragment.class.getSimpleName());
                        } else {
                            if (!checkDate(pickedDate)){
                                Toast.makeText(activity, "Non è possibile selezionare una data antecedente a quella attuale.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(activity, "I dati non sono stati presi correttamente. Inserisci tutti i campi", Toast.LENGTH_LONG).show();
                            }
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }


    }

    public boolean checkDate(String stringDate){
        LocalDate currentDate = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
            int year = currentDate.getYear();
            int month = currentDate.getMonthValue();
            int day = currentDate.getDayOfMonth();
        }


        LocalDate date = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            date = LocalDate.parse(stringDate, formatter);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            if(date.compareTo(currentDate) >= 0) {
                return true;
            }
        }
        return false;
    }

}
