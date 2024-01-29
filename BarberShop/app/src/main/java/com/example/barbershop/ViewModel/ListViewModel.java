package com.example.barbershop.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.barbershop.Database.AppointmentsRepository;
import com.example.barbershop.Database.ProfileInformationRepository;
import com.example.barbershop.Database.ServiceRepository;
import com.example.barbershop.Tables.Appointments;
import com.example.barbershop.Tables.ProfileInformation;
import com.example.barbershop.Tables.Service;

import java.text.ParseException;
import java.util.List;

public class ListViewModel extends AndroidViewModel {

    public LiveData<List<ProfileInformation>> profilesInfo;
    public LiveData<List<Service>> servicesInfo;

    public LiveData<List<Appointments>> appointmentsInfo;

    ServiceRepository serviceRepository;

    public ListViewModel(@NonNull Application application) throws ParseException {
        super(application);
        ProfileInformationRepository repository = new ProfileInformationRepository(application);
        this.serviceRepository = new ServiceRepository(application);
        AppointmentsRepository appointmentsRepository = new AppointmentsRepository(application);
        profilesInfo = repository.getProfileInformationList();
        servicesInfo = serviceRepository.getServiceList();
        appointmentsInfo = appointmentsRepository.getAppointmentsList();
    }

    public LiveData<List<ProfileInformation>> getProfilesInfo() {
        return profilesInfo;
    }

    public LiveData<List<Service>> getServicesInfo() {
        return servicesInfo;
    }

    public LiveData<List<Appointments>> getAppointmentsInfo() {
        return appointmentsInfo;
    }

    public int getIDfromName(String name){
        return serviceRepository.getIDfromName(name);
    }

}
