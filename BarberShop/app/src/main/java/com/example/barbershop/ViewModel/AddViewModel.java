package com.example.barbershop.ViewModel;


import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.barbershop.R;
import Utils.Utilities;

/**
 * ViewModel that handles the data flow between the MainActivity and its Fragment
 */
public class AddViewModel extends AndroidViewModel {

    private final MutableLiveData<Bitmap> imageBitmap = new MutableLiveData<>();

    private final Application application;

    public AddViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        initializeBitmap();
    }

    /**
     * Method that set the android icon as image in the ImageView when the Fragment is created
     */
    private void initializeBitmap() {
        Drawable drawable = ResourcesCompat.getDrawable(application.getResources(),
                R.drawable.ic_launcher_foreground, application.getTheme());
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
}
