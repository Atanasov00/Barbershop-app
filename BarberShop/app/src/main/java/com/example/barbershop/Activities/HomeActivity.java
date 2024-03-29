package com.example.barbershop.Activities;

import static Utils.Utilities.REQUEST_IMAGE_CAPTURE;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.barbershop.Fragments.AddReviewFragment;
import com.example.barbershop.Fragments.HomeFragment;
import com.example.barbershop.Fragments.MapFragment;
import com.example.barbershop.Fragments.ProfileFragment;
import com.example.barbershop.Fragments.RecensionFragment;
import com.example.barbershop.R;
import com.example.barbershop.Tables.Recension;
import com.example.barbershop.Tables.Service;
import com.example.barbershop.ViewModel.AddViewModel;
import com.example.barbershop.ViewModel.ListViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import Utils.Utilities;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    private AddViewModel addViewModel;
    private ListViewModel listViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //setSupportActionBar(findViewById(R.id.topAppBar));

        if(savedInstanceState == null) {
            Utilities.insertHomeActivityFragment(this, new HomeFragment(), HomeFragment.class.getSimpleName());
        }

        addViewModel = new ViewModelProvider(this).get(AddViewModel.class);
        listViewModel = new ViewModelProvider(this).get(ListViewModel.class);

        listViewModel.getServicesInfo().observe(this, services -> {
            try {
                if(services.size() == 0){
                    addViewModel.addServices(new Service("Taglio barba", 30, 10));
                    addViewModel.addServices(new Service("Taglio capelli", 60, 20));
                    addViewModel.addServices(new Service("Taglio barba e capelli", 90, 25));
                } else {
                    System.out.println("Services != 0");
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        });


        Utilities.setRecensionList(this);



        AppCompatActivity activity = this;
        navigationView = findViewById(R.id.bottomAppBar);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.homePage:
                        Utilities.insertHomeActivityFragment(activity, new HomeFragment(), HomeFragment.class.getSimpleName());
                        return true;
                    case R.id.recension:
                        Utilities.insertHomeActivityFragment(activity, new RecensionFragment(), RecensionFragment.class.getSimpleName());
                        return true;
                    case R.id.profile:
                        Utilities.insertHomeActivityFragment(activity, new ProfileFragment(), ProfileFragment.class.getSimpleName());
                        //Utilities.insertHomeActivityFragment(activity, new AddReviewFragment(), AddReviewFragment.class.getSimpleName());
                        return true;
                    case R.id.mapItem:
                        Utilities.insertHomeActivityFragment(activity, new MapFragment(), MapFragment.class.getSimpleName());
                        return true;
                }
                return false;
            }
        });

    }


    @Override
    /*public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar, menu);
        return true;
    }*/

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            addViewModel.setImageBitmap(imageBitmap);
        }
    }

}
