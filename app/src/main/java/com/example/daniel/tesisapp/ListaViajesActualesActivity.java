package com.example.daniel.tesisapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListaViajesActualesActivity extends AppCompatActivity implements View.OnClickListener{

    private ListView lvViajesActuales;
    private ArrayAdapter adapterViajesActuales;
    private String origenNombre,destinoNombre;
    private String horaInicio,horaTermino;
    private int idBus,idRecorrido;
    private String horarioSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_viajes_actuales);

        DbHelper helper = new DbHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        lvViajesActuales = (ListView) findViewById(R.id.lvViajesActuales);

        Cursor c = db.rawQuery("SELECT * FROM hace",null);
        ArrayList<String> dev = new ArrayList<>();
        origenNombre = null;
        destinoNombre = null;
        try{
            if(c.moveToFirst()){
                do{
                    horaInicio = c.getString(1);
                    horaTermino = c.getString(2);
                    idBus = c.getInt(3);
                    idRecorrido = c.getInt(4);

                    Cursor cursorRecorrido = db.rawQuery("SELECT Ciudad_Origen_Id,Ciudad_Destino_Id FROM recorrido" +
                            " WHERE Id="+idRecorrido,null);

                    if(cursorRecorrido.moveToFirst()){
                        do{
                            int idOrigen = cursorRecorrido.getInt(0);
                            int idDestino = cursorRecorrido.getInt(1);

                            Cursor cursorCiudadOrigen = db.rawQuery("SELECT Nombre FROM ciudad " +
                                    "WHERE Id="+idOrigen,null);
                            if (cursorCiudadOrigen.moveToFirst()){
                                do{
                                    origenNombre = cursorCiudadOrigen.getString(0);
                                }while (cursorCiudadOrigen.moveToNext());
                                cursorCiudadOrigen.close();
                            }

                            Cursor cursorCiudadDestino = db.rawQuery("SELECT Nombre FROM ciudad " +
                                    "WHERE Id="+idDestino,null);
                            if (cursorCiudadDestino.moveToFirst()){
                                do{
                                    destinoNombre = cursorCiudadDestino.getString(0);
                                }while (cursorCiudadDestino.moveToNext());
                                cursorCiudadDestino.close();
                            }
                        }while (cursorRecorrido.moveToNext());
                    }
                    dev.add(origenNombre+"-"+destinoNombre+"-"+horaInicio+"-"+horaTermino);
                }while (c.moveToNext());

                adapterViajesActuales = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dev);
                adapterViajesActuales.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                lvViajesActuales.setAdapter(adapterViajesActuales);
                lvViajesActuales.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ListaViajesActualesActivity.this, InfoViajeActualActivity.class);
                        intent.putExtra("origenNombre",origenNombre);
                        intent.putExtra("destinoNombre",destinoNombre);
                        intent.putExtra("idBus",idBus);
                        intent.putExtra("idRecorrido",idRecorrido);
                        intent.putExtra("horaInicio",horaInicio);
                        intent.putExtra("horaTermino",horaTermino);
                        startActivity(intent);
                    }
                });
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
