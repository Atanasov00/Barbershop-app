package Utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.example.barbershop.Fragments.LoginFragment;
import com.example.barbershop.R;
import com.example.barbershop.Tables.Recension;
import com.example.barbershop.ViewModel.ListViewModel;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;
import java.util.List;

public class Utilities {

    public static String email = null;
    public static String password = null;

    public static void setEmail(String email) {
        Utilities.email = email;
    }

    public static void setPassword(String password) {
        Utilities.password = password;
    }

    public static String getEmail() {
        return email;
    }

    public static String getPassword() {
        return password;
    }

    public static List<Recension> listRecension = new ArrayList<>();
    public static final int REQUEST_IMAGE_CAPTURE = 1;

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
    public static Bitmap drawableToBitmap(Drawable drawable){
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static void setRecensionList(Activity activity){
        ListViewModel listViewModel = new ViewModelProvider((ViewModelStoreOwner) activity).get(ListViewModel.class);
        listViewModel.getRecensionInfo().observe((LifecycleOwner) activity, new Observer<List<Recension>>() {
            @Override
            public void onChanged(List<Recension> recensions) {
                listRecension.addAll(recensions);
            }
        });
    }

    public static List<Recension> getRecensionList(){
        return listRecension;
    }

}
