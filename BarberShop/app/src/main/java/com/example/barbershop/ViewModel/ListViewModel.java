package com.example.barbershop.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.barbershop.Database.ProfileInformationRepository;
import com.example.barbershop.Tables.ProfileInformation;

import java.text.ParseException;
import java.util.List;

public class ListViewModel extends AndroidViewModel {

    public LiveData<List<ProfileInformation>> profilesInfo;

    public ListViewModel(@NonNull Application application) throws ParseException {
        super(application);
        ProfileInformationRepository repository = new ProfileInformationRepository(application);
        profilesInfo = repository.getProfileInformationList();
    }

    public LiveData<List<ProfileInformation>> getProfilesInfo() {
        return profilesInfo;
    }
}
