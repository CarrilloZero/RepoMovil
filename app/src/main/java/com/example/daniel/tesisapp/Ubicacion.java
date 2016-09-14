package com.example.daniel.tesisapp;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Ubicacion extends AppCompatActivity implements LocationListener{

    private Context ctx;
    protected LocationManager locationManager;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 1000; // in Milliseconds
    private Location location; // location
    double latitud; // latitude
    double longitud; // longitude
    private String proveedor;
    private boolean networkOn;


    public Ubicacion(Context ctx) {
        try{
            this.ctx = ctx;
            locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
            proveedor = LocationManager.NETWORK_PROVIDER;
            networkOn = locationManager.isProviderEnabled(proveedor);
            locationManager.requestLocationUpdates(proveedor,1000,1,this);

        }catch (SecurityException ex){
            ex.printStackTrace();
        }
    }


    public double obtenerLongitud(){
        try{
            if(networkOn){
                location = locationManager.getLastKnownLocation(proveedor);
                if(location != null){
                    longitud = location.getLongitude();
                }
            }
        }catch (SecurityException ex){
            ex.printStackTrace();
        }
        return longitud;
    }

    public double obtenerLatitud(){
        try{
            if(networkOn){
                location = locationManager.getLastKnownLocation(proveedor);
                if(location != null){
                    latitud = location.getLatitude();
                }
            }
        }catch (SecurityException ex){
            ex.printStackTrace();
        }
        return latitud;
    }

    @Override
    public void onLocationChanged(Location location)
    {
        obtenerLongitud();
        obtenerLatitud();

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
