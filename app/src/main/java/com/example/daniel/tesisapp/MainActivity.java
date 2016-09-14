package com.example.daniel.tesisapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.daniel.tesisapp.beans.Notificacion;
import com.example.daniel.tesisapp.beans.NotificacionDAO;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnBuscarEmpresa,btnMapa,btnBuscarViaje,btnViajesActuales;
    private ImageButton btnComentario;
    private int PERMISSION_CODE_1= 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 23)
        {
            if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                requestpermisions();
            }
        }

        btnBuscarViaje = (Button) findViewById(R.id.btnBuscarViaje);
        btnBuscarEmpresa = (Button) findViewById(R.id.btnBuscarEmpresa);
        btnComentario = (ImageButton) findViewById(R.id.ibtnComentario);
        btnMapa = (Button) findViewById(R.id.btnMapa);
        btnViajesActuales = (Button) findViewById(R.id.btnViajesActuales);


        try {
            ListView listView = new ListView(this);
            listView = (ListView)findViewById(R.id.listCom);
            NotificacionDAO noti = new NotificacionDAO(this);
            ArrayList<Notificacion> notis = noti.listado();
            int count= notis.size();
            int c = 0;
            String alerta="";
            Notificacion notificacion;
            ArrayList<String> comentariosHora = new ArrayList<String>();
            while(c<count) {
                notificacion = notis.get(c);
                alerta = notificacion.getHora()+" - "+notificacion.getComentario();
                comentariosHora.add(alerta);
                c++;
            }
            ArrayAdapter<String> adapterAlerts= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, comentariosHora);
            listView.setAdapter(adapterAlerts);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        btnBuscarViaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BuscarViajeActivity.class);
                startActivity(intent);
            }
        });


        btnBuscarEmpresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BuscarEmpresaActivity.class);
                startActivity(intent);
            }
        });

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViajesActualesActivity.class);
                startActivity(intent);
            }
        });

        btnComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Ubicacion.class);
                startActivity(intent);
            }
        });

        btnViajesActuales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListaViajesActualesActivity.class);
                startActivity(intent);
            }
        });
    }

    public void requestpermisions() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE_1);

    }

    @Override
    public void onClick(View v) {

    }
}
