package com.example.barbershop.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.barbershop.Tables.ProfileInformation;

import java.text.ParseException;
import java.util.List;

public class ProfileInformationRepository {

    private final ProfileInformationDAO profileInformationDAO;

    private final LiveData<List<ProfileInformation>> profileInformationList;

    private int profileID;

    public ProfileInformationRepository(Application application) throws ParseException {
        BarberShopDatabase db = BarberShopDatabase.getDatabase(application);
        profileInformationDAO = db.profileInformationDAO();
        profileInformationList = profileInformationDAO.getAllProfilesCredentials();
    }

    public LiveData<List<ProfileInformation>> getProfileInformationList() {
        return this.profileInformationList;
    }

    public void addProfile(ProfileInformation profile){
        BarberShopDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                profileInformationDAO.addProfile(profile);
            }
        });
    }
}
