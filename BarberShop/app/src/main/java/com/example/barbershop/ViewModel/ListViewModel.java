package com.example.barbershop.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.barbershop.Database.AppointmentsRepository;
import com.example.barbershop.Database.ProfileInformationRepository;
import com.example.barbershop.Database.RecensionRepository;
import com.example.barbershop.Database.ServiceRepository;
import com.example.barbershop.Tables.Appointments;
import com.example.barbershop.Tables.ProfileInformation;
import com.example.barbershop.Tables.Recension;
import com.example.barbershop.Tables.Service;

import java.text.ParseException;
import java.util.List;

public class ListViewModel extends AndroidViewModel {

    private final MutableLiveData<Recension> recensionSelected = new MutableLiveData<>();
    ProfileInformationRepository repository;
    AppointmentsRepository appointmentsRepository;
    ServiceRepository serviceRepository;
    RecensionRepository recensionRepository;
    public LiveData<List<ProfileInformation>> profilesInfo;
    public LiveData<List<Service>> servicesInfo;

    public LiveData<List<Recension>> recensionInfo;
    public LiveData<List<Appointments>> appointmentsInfo;



    public ListViewModel(@NonNull Application application) throws ParseException {
        super(application);
        this.repository = new ProfileInformationRepository(application);
        this.serviceRepository = new ServiceRepository(application);
        this.appointmentsRepository = new AppointmentsRepository(application);
        this.recensionRepository = new RecensionRepository(application);

        profilesInfo = repository.getProfileInformationList();
        servicesInfo = serviceRepository.getServiceList();
        appointmentsInfo = appointmentsRepository.getAppointmentsList();
        recensionInfo = recensionRepository.getRecensionList();
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

    public LiveData<List<Recension>> getRecensionInfo() { return recensionInfo; }
    public LiveData<List<Appointments>> getUserAppointments(int id) {
        return appointmentsRepository.getUserAppointments(id);
    }

    public int getIDfromName(String name){
        return serviceRepository.getIDfromName(name);
    }

    public ProfileInformation getUserByID(int id) {return repository.getUserByID(id);}

    public void setItemSelected(Recension recension) { recensionSelected.setValue(recension);}

    public MutableLiveData<Recension> getItemSelected() {return recensionSelected;}

}
