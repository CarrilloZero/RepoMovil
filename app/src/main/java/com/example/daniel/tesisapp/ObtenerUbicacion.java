package com.example.daniel.tesisapp;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ObtenerUbicacion extends AppCompatActivity implements LocationListener{

    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    private LocationManager locationManager;
    private Location location;
    private double longitud;

    public double obtenerLongitud() {
        location = null;
        try{
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }catch(SecurityException se){
            se.printStackTrace();
        }
        try{
            if (location != null) {
                longitud = location.getLongitude();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return longitud;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try{
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MINIMUM_TIME_BETWEEN_UPDATES,
                    MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                    new ObtenerUbicacion()
            );
        }catch(SecurityException se){
            se.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
