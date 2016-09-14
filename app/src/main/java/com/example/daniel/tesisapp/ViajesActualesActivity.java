package com.example.daniel.tesisapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

public class ViajesActualesActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viajes_actuales);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Ubicacion Obu = new Ubicacion(this);
        double longitud = Obu.obtenerLongitud();
        double latitud = Obu.obtenerLatitud();
        double latitudLaUnion = -40.293437;
        double longitudLaUnion = -73.0716605;
        // Instantiates a new Polyline object and adds points to define a rectangle


        /*PolylineOptions rutaOsornoUnion = new PolylineOptions()
                .add(new LatLng(-40.5731558,-73.1285305))
                .add(new LatLng(-40.5732074,-73.1275684))
                .add(new LatLng(-40.5736133,-73.1245077))
                .add(new LatLng(-40.574022,-73.1146582))
                .add(new LatLng(-40.5740816,-73.114278))
                .add(new LatLng(-40.5744079,-73.1138439))
                .add(new LatLng(-40.574995,-73.1112149))
                .add(new LatLng(-40.5761553,-73.1116263))
                .add(new LatLng(-40.5762844,-73.110863))
                .add(new LatLng(-40.5768692,-73.1086699))
                .add(new LatLng(-40.5775047,-73.1068722))
                .add(new LatLng(-40.5793907,-73.1025937))
                .add(new LatLng(-40.5811926,-73.0993579))
                .add(new LatLng(-40.5816077,-73.0973147))
                .add(new LatLng(-40.5818147,-73.096983))
                .add(new LatLng(-40.582091,-73.0969848))
                .add(new LatLng(-40.5822149,-73.097211))
                .add(new LatLng(-40.5820335,-73.0976382))
                .add(new LatLng(-40.559728, -73.097936))
                .add(new LatLng(-40.559728, -73.097936))
                .add(new LatLng(-40.558729, -73.097823));*/
        PolylineOptions rutaa = null;
        DbHelper helper = new DbHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        helper.openDataBase();

        Cursor cursorRutaOsornoUnion = db.rawQuery("SELECT * FROM coordenadas WHERE Hace_Id=1", null);
        if (cursorRutaOsornoUnion.moveToFirst()) {
            rutaa = new PolylineOptions().add(new LatLng(cursorRutaOsornoUnion.getDouble(1), cursorRutaOsornoUnion.getDouble(2)));
            do {
                rutaa.add(new LatLng(cursorRutaOsornoUnion.getDouble(1), cursorRutaOsornoUnion.getDouble(2)));
            } while (cursorRutaOsornoUnion.moveToNext());
            cursorRutaOsornoUnion.close();
        }

        Cursor cursorTerBusLU = db.rawQuery("SELECT Latitud,Longitud,Nombre FROM terminal WHERE Nombre='Terminal De Buses La Unión'", null);
        if (cursorTerBusLU.moveToFirst()) {
            do {
                LatLng TerPurr = new LatLng(cursorTerBusLU.getDouble(0), cursorTerBusLU.getDouble(1));
                mMap.addMarker(new MarkerOptions()
                        .position(TerPurr)
                        .title(cursorTerBusLU.getString(2)));
            } while (cursorTerBusLU.moveToNext());
            cursorTerBusLU.close();
        }

        Cursor cursorTerMerOso = db.rawQuery("SELECT Latitud,Longitud,Nombre FROM terminal WHERE Nombre='Terminal Mercado Osorno'", null);
        if (cursorTerMerOso.moveToFirst()) {
            do {
                LatLng MerOso = new LatLng(cursorTerMerOso.getDouble(0), cursorTerMerOso.getDouble(1));
                mMap.addMarker(new MarkerOptions()
                        .position(MerOso)
                        .title(cursorTerMerOso.getString(2)));
            } while (cursorTerMerOso.moveToNext());
            cursorTerMerOso.close();
        }

        Cursor cursorTerPurr = db.rawQuery("SELECT Latitud,Longitud,Nombre FROM terminal WHERE Nombre='Terminal De Buses Purranque'", null);
        if (cursorTerPurr.moveToFirst()) {
            do {
                LatLng TerPurr = new LatLng(cursorTerPurr.getDouble(0), cursorTerPurr.getDouble(1));
                mMap.addMarker(new MarkerOptions()
                        .position(TerPurr)
                        .title(cursorTerPurr.getString(2)));
            } while (cursorTerPurr.moveToNext());
            cursorTerPurr.close();
        }


        // Get back the mutable Polyline
        Polyline asd = mMap.addPolyline(rutaa);

        // Add a marker in Sydney and move the camera
        LatLng me = new LatLng(latitud, longitud);
        LatLng asdasd = new LatLng(latitudLaUnion, longitudLaUnion);

        mMap.addMarker(new MarkerOptions()
                .position(me)
                .title("holi")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.asd)));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(me));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
    }

}
