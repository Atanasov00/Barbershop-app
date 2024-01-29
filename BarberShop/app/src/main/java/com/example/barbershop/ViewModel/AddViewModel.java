package com.example.barbershop.ViewModel;


import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.barbershop.Database.AppointmentsRepository;
import com.example.barbershop.Database.ProfileInformationRepository;
import com.example.barbershop.Database.ServiceRepository;
import com.example.barbershop.R;
import com.example.barbershop.Tables.Appointments;
import com.example.barbershop.Tables.ProfileInformation;
import com.example.barbershop.Tables.Service;

import java.text.ParseException;

import Utils.Utilities;

/**
 * ViewModel that handles the data flow between the MainActivity and its Fragment
 */
public class AddViewModel extends AndroidViewModel {

    private final ProfileInformationRepository repository;

    private final AppointmentsRepository appointmentsRepository;

    private final ServiceRepository serviceRepository;

    private final MutableLiveData<Bitmap> imageBitmap = new MutableLiveData<>();

    private final Application application;

    public AddViewModel(@NonNull Application application) throws ParseException {
        super(application);
        this.application = application;
        initializeBitmap();
        repository = new ProfileInformationRepository(application);
        appointmentsRepository = new AppointmentsRepository(application);
        serviceRepository = new ServiceRepository(application);
    }

    /**
     * Method that set the android icon as image in the ImageView when the Fragment is created
     */
    private void initializeBitmap() {
        Drawable drawable = ResourcesCompat.getDrawable(application.getResources(),
                R.drawable.ic_haircut4, application.getTheme());
        Bitmap bitmap = Utilities.drawableToBitmap(drawable);

        imageBitmap.setValue(bitmap);
    }

    /**
     * Method that set the image taken as a bitmap
     * @param bitmap the image taken by the user
     */
    public void setImageBitmap(Bitmap bitmap) {
        imageBitmap.setValue(bitmap);
    }

    public MutableLiveData<Bitmap> getImageBitmap() {
        return imageBitmap;
    }

    public void addProfile(ProfileInformation profile) {
        repository.addProfile(profile);
    }

    public void addServices(Service service) {
        serviceRepository.addService(service);
    }

    public void addAppointment(Appointments appointment) {
        appointmentsRepository.addAppointment(appointment);
    }

    public void updatePassword(String newPassword, int id) {
        repository.updatePassword(newPassword, id);
    }
}
