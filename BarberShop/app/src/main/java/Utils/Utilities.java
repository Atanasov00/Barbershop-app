package Utils;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.barbershop.Fragments.LoginFragment;
import com.example.barbershop.R;
import com.google.android.material.appbar.MaterialToolbar;

public class Utilities {

    public static void insertMainActivityFragment(AppCompatActivity activity, Fragment fragment, String tag){
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_container_view, fragment, tag);

        if(!(fragment instanceof LoginFragment)){
            transaction.addToBackStack(tag);
        }

        transaction.commit();
    }

    public static void insertHomeActivityFragment(AppCompatActivity activity, Fragment fragment, String tag){
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.home_container_view, fragment, tag);

        if(!(fragment instanceof LoginFragment)){
            transaction.addToBackStack(tag);
        }

        transaction.commit();
    }

    public static void setUpToolbar(AppCompatActivity activity, String title){
        ActionBar actionBar = activity.getSupportActionBar();
        if(actionBar == null) {
            Toolbar toolbar = new Toolbar(activity);
            activity.setSupportActionBar(toolbar);
        } else {
            activity.getSupportActionBar().setTitle(title);
            activity.getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#BF7E19")));
        }
    }


}
