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
    private ProfileInformation user;

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

    public void updatePassword(String newPassword, int id){
        BarberShopDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                profileInformationDAO.updatePassword(newPassword, id);
            }
        });
    }

    public ProfileInformation getUserByID(int id){

        BarberShopDatabase.executor.execute(new Runnable() {
            @Override
            public void run() {
                user = profileInformationDAO.getUserByID(id);
            }
        });
        return user;
    }

}
