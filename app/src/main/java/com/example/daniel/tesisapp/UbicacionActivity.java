package com.example.daniel.tesisapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * Created by daniel on 18/07/2016.
 */
public class UbicacionActivity extends AppCompatActivity implements LocationListener{

    protected LocationManager locationManager;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    private Context ctx;
    private Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    public void onLocationChanged(Location location) {
        String message = String.format(
                "New Location \n Longitude: %1$s \n Latitude: %2$s",
                location.getLongitude(), location.getLatitude()
        );
        Toast.makeText(UbicacionActivity.this, message, Toast.LENGTH_LONG).show();
    }

    public void onStatusChanged(String s, int i, Bundle b) {
        Toast.makeText(UbicacionActivity.this, "Provider status changed",Toast.LENGTH_LONG).show();
    }

    public void onProviderDisabled(String s) {
        Toast.makeText(UbicacionActivity.this,
                "Provider disabled by the user. GPS turned off",
                Toast.LENGTH_LONG).show();
    }

    public void onProviderEnabled(String s) {
        Toast.makeText(UbicacionActivity.this,
                "Provider enabled by the user. GPS turned on",
                Toast.LENGTH_LONG).show();
    }



    protected void showCurrentLocation(View view) {
        Location location = null;
        try{
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }catch(SecurityException se){
        }

        if (location != null) {
            //new Sender().execute(String.valueOf(location.getLongitude()),String.valueOf(location.getLatitude()),"CORREO");
            //new Sender().execute("jolaasd");
            StringBuilder builder = null;
            builder = builder.append("Longitud: ").append(location.getLongitude())
                    .append("Latitud: ").append(location.getLatitude());
            Toast.makeText(UbicacionActivity.this, builder, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_viaje_actual);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try{
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MINIMUM_TIME_BETWEEN_UPDATES,
                    MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                    new UbicacionActivity()
            );
        }catch(SecurityException se){
            se.printStackTrace();
        }
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) this
                    .getSystemService(LOCATION_SERVICE);

            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {

                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.

                    }
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MINIMUM_TIME_BETWEEN_UPDATES,
                            MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MINIMUM_TIME_BETWEEN_UPDATES,
                                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }

    public double getLatitude() {
        if (location != null) {
            latitude = location.getLatitude();
        }

        // return latitude
        return latitude;
    }

    /**
     * Function to get longitude
     */
    public double getLongitude() {
        if (location != null) {
            longitude = location.getLongitude();
        }

        // return longitude
        return longitude;
    }

}
