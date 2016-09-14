package com.example.daniel.tesisapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class InfoViajeActualActivity extends AppCompatActivity implements View.OnClickListener {

    private Button VerenelMapa;
    private TextView tvCiudades,tvHorario,tvEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_viaje_actual);

        String origenNombre = getIntent().getStringExtra("origenNombre");
        String destinoNombre = getIntent().getStringExtra("destinoNombre");
        int idBus = getIntent().getExtras().getInt("idBus");
        int idRecorrido = getIntent().getExtras().getInt("idRecorrido");
        String horaInicio = getIntent().getStringExtra("horaInicio");
        String horaTermino = getIntent().getStringExtra("horaTermino");
        int idEmpresa;
        String nombreEmpresa = null;

        DbHelper helper = new DbHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT Empresa_Id FROM bus WHERE Id="+idBus,null);
        if(c.moveToFirst()){
            do{
                idEmpresa = c.getInt(0);
            }while (c.moveToNext());

            Cursor cursorEmpresa = db.rawQuery("SELECT Nombre FROM empresa WHERE Id="+idEmpresa,null);
            if(cursorEmpresa.moveToFirst()) {
                do {
                    nombreEmpresa = cursorEmpresa.getString(0);
                } while (cursorEmpresa.moveToNext());
            }
        }

        tvCiudades = (TextView) findViewById(R.id.tvCiudades);
        tvHorario = (TextView) findViewById(R.id.tvHorario);
        tvEmpresa = (TextView) findViewById(R.id.tvEmpresa);

        tvCiudades.setText(origenNombre+"---"+destinoNombre);
        tvEmpresa.setText(nombreEmpresa);

        VerenelMapa = (Button) findViewById(R.id.btnVerEnElMapa);
        VerenelMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoViajeActualActivity.this,ViajesActualesActivity.class);
                //startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
