package com.example.barbershop.Fragments;

import static Utils.Utilities.REQUEST_IMAGE_CAPTURE;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.barbershop.R;
import com.example.barbershop.Tables.Recension;
import com.example.barbershop.ViewModel.AddViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

import Utils.Utilities;

public class AddReviewFragment extends Fragment {

    TextView placeNameText;

    RatingBar placeRatingBar;

    TextInputEditText placeDescriptionText;

    private int id;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.add_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentActivity activity = getActivity();

        if(activity != null) {
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.add_review_title));

            placeNameText = view.findViewById(R.id.nameText_addReview);
            placeRatingBar = view.findViewById(R.id.ratingBar_addReview);
            placeDescriptionText = view.findViewById(R.id.description_addReviewPlace);

            SharedPreferences sharedPreferences = activity.getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
            String pref_name = sharedPreferences.getString("name", "default");
            String pref_surname = sharedPreferences.getString("surname", "default");
            int pref_id = sharedPreferences.getInt("id", 0);

            String name = "", surname = "";
            id = -1;

            if(!pref_name.equals("default") && !pref_surname.equals("default") && pref_id != 0){
                name = pref_name;
                surname = pref_surname;
                id = pref_id;
            }

            placeNameText.setText(name + " " + surname);

            view.findViewById(R.id.btnPicture).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    if (takePicture.resolveActivity(activity.getPackageManager()) != null) {
                        activity.startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE);
                    }
                }
            });

            ImageView imageView = view.findViewById(R.id.picture_displayed_imageview);
            AddViewModel addViewModel = new ViewModelProvider((ViewModelStoreOwner) activity)
                    .get(AddViewModel.class);

            addViewModel.getImageBitmap().observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
                @Override
                public void onChanged(Bitmap bitmap) {
                    imageView.setImageBitmap(bitmap);
                }
            });

            view.findViewById(R.id.btnInsertReview).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bitmap bitmap = addViewModel.getImageBitmap().getValue();

                    String imageUriString;
                    try {
                        if(bitmap != null) {
                            imageUriString = String.valueOf(saveImage(bitmap, activity));
                            System.out.println("uri dell'immagine:"+imageUriString);
                        } else {
                            imageUriString = "";
                        }
                        if(placeRatingBar.getRating() >= 0 && placeDescriptionText.getText() != null) {

                            String formattedDate = " ";
                            LocalDate currentDate = null;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                currentDate = LocalDate.now();
                                int year = currentDate.getYear();
                                int month = currentDate.getMonthValue();
                                int day = currentDate.getDayOfMonth();
                                formattedDate = day +"/" + month + "/"+ year;
                            }

                            addViewModel.addRecension(new Recension(id, formattedDate, (int)placeRatingBar.getRating(), placeDescriptionText.getText().toString(),
                                    imageUriString, placeNameText.getText().toString()));
                            addViewModel.setImageBitmap(null);

                            Toast.makeText(activity, "Recensione inserita con successo!", Toast.LENGTH_SHORT).show();
                            activity.getSupportFragmentManager().popBackStack();
                            //Utilities.insertHomeActivityFragment((AppCompatActivity) activity, new RecensionFragment(), RecensionFragment.class.getSimpleName());

                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            });



        }
    }

    private Uri saveImage(Bitmap bitmap, Activity activity) throws FileNotFoundException {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.ITALY)
                .format(new Date());
        String name = "JPEG_" + timestamp + ".jpg";

        ContentResolver contentResolver = activity.getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpg");

        Uri imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        Log.d("AddFragment", String.valueOf(imageUri));

        OutputStream outputStream = contentResolver.openOutputStream(imageUri);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageUri;
    }

}
