package com.example.barbershop.Fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.barbershop.R;
import com.example.barbershop.Tables.Recension;
import com.example.barbershop.ViewModel.ListViewModel;

import java.io.IOException;
import java.io.InputStream;

import Utils.Utilities;

public class DetailsFragment extends Fragment {

    private TextView placeTextName;
    private TextView placeTextDate;
    private TextView placeTextDescription;
    private RatingBar placeRating;
    private ImageView placeImage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentActivity activity = getActivity();

        if(activity != null) {
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.details));

            placeTextName = view.findViewById(R.id.place_name);
            placeTextDate = view.findViewById(R.id.travel_date);
            placeTextDescription = view.findViewById(R.id.place_description);
            placeRating = view.findViewById(R.id.ratingBar);
            placeImage = view.findViewById(R.id.place_image);

            ListViewModel listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);
            listViewModel.getItemSelected().observe(getViewLifecycleOwner(), new Observer<Recension>() {
                @Override
                public void onChanged(Recension recension) {
                    placeTextName.setText(recension.getName());
                    placeTextDate.setText(recension.getDate());
                    placeTextDescription.setText(recension.getDescription());
                    placeRating.setRating(recension.getRating());
                    placeRating.setIsIndicator(true);
                    String image_path = recension.getImage();
                    System.out.println(image_path);

                    Bitmap imageBitmap = getBitmapFromUri(activity, Uri.parse(image_path));
                    if(imageBitmap != null) {
                        placeImage.setImageBitmap(imageBitmap);
                    }


                    /*int resourceId = getResources().getIdentifier(image_path, "drawable", activity.getPackageName());

                    /*if(resourceId != 0) {
                        Drawable drawable = getResources().getDrawable(resourceId);
                        placeImage.setImageDrawable(drawable);
                    }*/

                    /*if (image_path.contains("ic_")){
                        Drawable drawable = ResourcesCompat.getDrawable(activity.getResources(),
                                R.drawable.ic_haircut1, activity.getTheme());
                        placeImage.setImageDrawable(drawable);
                    } else {
                        Bitmap bitmap = Utilities.getImageBitmap(activity, Uri.parse(image_path));
                        if (bitmap != null){
                            placeImageView.setImageBitmap(bitmap);
                            placeImageView.setBackgroundColor(Color.WHITE);
                        }
                    }*/
                }
            });

        }
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
        ContentResolver contentResolver = context.getContentResolver();
        InputStream inputStream = null;
        try {
            inputStream = contentResolver.openInputStream(uri);
            if (inputStream != null) {
                return BitmapFactory.decodeStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
