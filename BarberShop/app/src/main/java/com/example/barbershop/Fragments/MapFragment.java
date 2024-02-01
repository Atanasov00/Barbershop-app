package com.example.barbershop.Fragments;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;

import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.barbershop.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.mapsforge.core.model.LatLong;
import org.mapsforge.map.layer.Layers;
import org.mapsforge.map.layer.overlay.Marker;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.Arrays;

import Utils.Utilities;

public class MapFragment extends Fragment {

    private boolean setCenterSet = false;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    private LocationRequest locationRequest;

    private ActivityResultLauncher<String> requestPermissionLauncher;
    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private MapView map = null;

    private GeoPoint startPoint = null;
    private GeoPoint endPoint = null;
    private IMapController mapController;
    private MyLocationNewOverlay mLocationOverlay;
    private boolean requestingLocationUpdates = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() != null) {
            Context ctx = getActivity().getApplicationContext();
            Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map, container, false);
        view.findViewById(R.id.btnCompute).setEnabled(false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentActivity activity = getActivity();

        if (activity != null) {
            Utilities.setUpToolbar((AppCompatActivity) activity, getString(R.string.position_item));

            map = (MapView) view.findViewById(R.id.map);
            map.setTileSource(TileSourceFactory.MAPNIK);
            mapController = map.getController();
            mapController.setZoom(15.5);




            String [] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};

            //requestPermissionsIfNecessary(permissions);

            requestPermissionLauncher = registerForActivityResult(
                    new ActivityResultContracts.RequestPermission(),
                    new ActivityResultCallback<Boolean>() {
                        @Override
                        public void onActivityResult(Boolean result) {
                            if (result) {
                                startLocationUpdates(activity);
                                Log.d("LAB-ADDFRAGMENT", "PERMISSION GRANTED");
                            } else {
                                Log.d("LAB-ADDFRAGMENT", "PERMISSION NOT GRANTED");
                                showDialog(activity);
                            }
                        }
                    });
            System.out.println("esecuzione");
            initializeLocation(activity);
            //requestingLocationUpdates = true;
            //registerNetworkCallback(activity);
            startLocationUpdates(activity);
            this.mLocationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(activity.getApplicationContext()),map);
            this.mLocationOverlay.enableMyLocation();
            map.getOverlays().add(this.mLocationOverlay);


            view.findViewById(R.id.btnCompute).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(startPoint != null && startPoint.getLatitude() != 0 && startPoint.getLongitude() != 0 &&
                            endPoint != null && endPoint.getLatitude() != 0 && endPoint.getLongitude() != 0){
                        /*System.out.println("Latitude1" + startPoint.getLatitude() + " Longitude1" + startPoint.getLongitude() +
                                " Latitude2" + endPoint.getLatitude() + " Longitude2" + endPoint.getLongitude());*/
                        int distance = (int)calculateDistance(new LatLong(startPoint.getLatitude(), startPoint.getLongitude()), new LatLong(endPoint.getLatitude(), endPoint.getLongitude()));

                        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

                        // Imposta il titolo e il messaggio del dialog
                        builder.setTitle("Calcolo distanza");
                        builder.setMessage("La distanza tra la posizione attuale e il barber shop indicato con il marker rosso è di circa: "+ distance +" metri.");

                        // Aggiunge pulsanti al dialog
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Azione da eseguire quando l'utente fa clic su "OK"
                                dialog.dismiss(); // Chiudi il dialog
                            }
                        });

                        // Mostra il dialog
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });

        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
        if (requestingLocationUpdates && getActivity() != null){
            startLocationUpdates(getActivity());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
        stopLocationUpdates();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        FragmentActivity activity = getActivity();
        if(activity != null) {
            for (int i = 0; i < grantResults.length; i++) {
                permissionsToRequest.add(permissions[i]);
            }
            if (permissionsToRequest.size() > 0) {
                ActivityCompat.requestPermissions(
                        activity,
                        permissionsToRequest.toArray(new String[0]),
                        REQUEST_PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    private void requestPermissionsIfNecessary(String[] permissions) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        FragmentActivity activity = getActivity();
        if(activity != null) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(activity.getApplicationContext(), permission)
                        != PackageManager.PERMISSION_GRANTED) {
                    // Permission is not granted
                    permissionsToRequest.add(permission);
                }
            }
            if (permissionsToRequest.size() > 0) {
                ActivityCompat.requestPermissions(
                        activity,
                        permissionsToRequest.toArray(new String[0]),
                        REQUEST_PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    private void initializeLocation(Activity activity) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        locationRequest = LocationRequest.create();
        //Set the desired interval for active location updates
        locationRequest.setInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        System.out.println("Test");
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);

                //Update UI with the location data
                Location location = locationResult.getLastLocation();

                System.out.println(locationResult.getLastLocation().getLatitude() +"  " +locationResult.getLastLocation().getLongitude());
                startPoint = new GeoPoint(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
                LatLong endPosition = new LatLong(44.05057, 12.17742);
                endPoint = new GeoPoint(endPosition.getLatitude(), endPosition.getLongitude());
                onVariablesUpdated(startPoint, endPoint);
                addMarkers();
                if(!setCenterSet){
                    mapController.setCenter(startPoint);
                    setCenterSet = true;
                }

                //mapController.setCenter(endPoint);

                /*String text = location.getLatitude() + ", " + location.getLongitude();
                placeTIET.setText(text);*/

                /*if (isNetworkConnected) {
                    sendVolleyRequest(String.valueOf(location.getLatitude()),
                            String.valueOf(location.getLongitude()));

                    requestingLocationUpdates = false;
                    stopLocationUpdates();
                } else {
                    snackbar.show();
                }*/
            }
        };
    }

    private void startLocationUpdates(Activity activity) {
        final String PERMISSION_REQUESTED = Manifest.permission.ACCESS_FINE_LOCATION;
        //permission granted
        if (ActivityCompat.checkSelfPermission(activity, PERMISSION_REQUESTED)
                == PackageManager.PERMISSION_GRANTED) {
            checkStatusGPS(activity);
            fusedLocationProviderClient.requestLocationUpdates(locationRequest,
                    locationCallback,
                    Looper.getMainLooper());
        } else if (ActivityCompat
                .checkSelfPermission(activity, PERMISSION_REQUESTED) == PackageManager.PERMISSION_DENIED) {
            //permission denied before
            System.out.println("test 1");
            //showDialog(activity);
            requestPermissionLauncher.launch(PERMISSION_REQUESTED);
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, PERMISSION_REQUESTED)) {
            //ask for the permission
            System.out.println("test 2");
            showDialog(activity);
        }

    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void showDialog(Activity activity) {
        new AlertDialog.Builder(activity)
                .setMessage("Permesso negato, ma necessario per la funzionalità del gps.")
                .setCancelable(false)
                .setPositiveButton("OK", ((dialogInterface, i) ->
                        activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))))
                .setNegativeButton("Cancella", ((dialogInterface, i) -> dialogInterface.cancel()))
                .create()
                .show();
    }

    private void checkStatusGPS(Activity activity) {
        final LocationManager locationManager =
                (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
        //if gps is off, show the alert message
        if (locationManager != null && !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            new AlertDialog.Builder(activity)
                    .setMessage("Il gps è disattivato, vuoi abilitarlo?")
                    .setCancelable(false)
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            activity.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                            initializeLocation(activity);
                        }
                    })
                    .setNegativeButton("No", (dialogInterface, i) -> dialogInterface.cancel())
                    .create()
                    .show();
        }
    }

    public static double calculateDistance(LatLong point1, LatLong point2) {
        // Raggio medio della Terra in metri
        double earthRadius = 6371000;

        // Converti le coordinate in radianti
        double lat1 = Math.toRadians(point1.latitude);
        double lon1 = Math.toRadians(point1.longitude);
        double lat2 = Math.toRadians(point2.latitude);
        double lon2 = Math.toRadians(point2.longitude);

        // Calcola la differenza tra le longitudini e le latitudini
        double dLon = lon2 - lon1;
        double dLat = lat2 - lat1;

        // Calcola la distanza utilizzando la formula di Vincenty
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earthRadius * c;
    }

    public void onVariablesUpdated(GeoPoint start, GeoPoint end){
        if(start != null && end != null){
            getView().findViewById(R.id.btnCompute).setEnabled(true);
            getView().findViewById(R.id.btnCompute).setBackgroundColor(getResources().getColor(R.color.brown_900));
        }
    }

    private void addMarkers() {
        // Crea una lista di OverlayItem per i marker
        ArrayList<OverlayItem> items = new ArrayList<>();
        items.add(new OverlayItem("Marker 1", "Descrizione del Marker 1", new GeoPoint(44.05057, 12.17742)));
        //items.add(new OverlayItem("Marker 2", "Descrizione del Marker 2", new GeoPoint(40.7120, -74.0070)));
        //items.add(new OverlayItem("Marker 3", "Descrizione del Marker 3", new GeoPoint(40.7130, -74.0050)));

        // Crea un overlay con i marker
        ItemizedIconOverlay<OverlayItem> overlay = new ItemizedIconOverlay<>(items,
                getResources().getDrawable(R.drawable.baseline_location_on_24), // Puoi utilizzare un'icona personalizzata
                new ItemizedIconOverlay.OnItemGestureListener<OverlayItem>() {
                    @Override
                    public boolean onItemSingleTapUp(int index, OverlayItem item) {
                        // Gestisci l'evento di tap su un marker
                        // Puoi, ad esempio, aprire una finestra di informazioni
                        return true;
                    }

                    @Override
                    public boolean onItemLongPress(int index, OverlayItem item) {
                        // Gestisci l'evento di long press su un marker
                        return false;
                    }
                }, getContext());

        // Aggiungi l'overlay alla mappa
        map.getOverlays().add(overlay);

        // Aggiorna la mappa
        map.invalidate();
    }




}
