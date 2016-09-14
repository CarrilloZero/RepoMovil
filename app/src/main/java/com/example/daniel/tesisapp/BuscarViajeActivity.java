package com.example.daniel.tesisapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.daniel.tesisapp.beans.Ciudad;
import com.example.daniel.tesisapp.beans.CiudadDAO;

import java.sql.Time;
import java.util.ArrayList;

public class BuscarViajeActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner spnOrigen,spnDestino,spnDia;
    private ArrayAdapter adapterCiudades,adapterViajes;
    private Button btnBuscar;
    private EditText etHoraOrigen,etHoraDestino;
    private ListView lvViajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_viaje);

        spnOrigen = (Spinner) findViewById(R.id.spnCiudadOrigen);
        spnDestino = (Spinner) findViewById(R.id.spnCiudadDestino);
        spnDia = (Spinner) findViewById(R.id.spnDia);
        btnBuscar = (Button) findViewById(R.id.btnBuscarViaje);
        etHoraOrigen = (EditText) findViewById(R.id.etDesde);
        etHoraDestino = (EditText) findViewById(R.id.etHasta);
        lvViajes = (ListView) findViewById(R.id.lvViajes);

        btnBuscar.setOnClickListener(this);

        CiudadDAO ciuDAO = new CiudadDAO(this);
        ArrayList<String> emp = ciuDAO.listadoNombres();
        adapterCiudades = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, emp);
        adapterCiudades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnOrigen.setAdapter(adapterCiudades);
        spnDestino.setAdapter(adapterCiudades);

        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.dias , android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDia.setAdapter(spinner_adapter);
    }

    @Override
    public void onClick(View v) {
        String getCiudadOrigen = spnOrigen.getSelectedItem().toString();
        String getCiudadDestino = spnDestino.getSelectedItem().toString();
        String getHoraDesde = etHoraOrigen.getText().toString();
        String getHoraHasta = etHoraDestino.getText().toString();
        String dia = spnDia.getSelectedItem().toString();
        Time.valueOf(getHoraHasta+getHoraDesde);


        int ciudadOrigen = 0,ciudadDestino = 0;
        String ciudadOrigenNombre = null,ciudadDestinoNombre = null;

        DbHelper helper = new DbHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        try
        {
            helper.openDataBase();
            CiudadDAO ciuDao = new CiudadDAO(this);
            Ciudad origen = ciuDao.buscarNombre(getCiudadOrigen);
            Ciudad destino = ciuDao.buscarNombre(getCiudadDestino);

            Cursor cursorOrigen = db.rawQuery("SELECT * FROM ciudad WHERE Nombre='"+origen.getNombre()+"';",null);
            if(cursorOrigen.moveToFirst()){
                do{
                    ciudadOrigen = cursorOrigen.getInt(0);
                    ciudadOrigenNombre = cursorOrigen.getString(1);
                }while(cursorOrigen.moveToNext());
                cursorOrigen.close();
            }

            Cursor cursorDestino = db.rawQuery("SELECT * FROM ciudad WHERE Nombre='"+destino.getNombre()+"';",null);
            if(cursorDestino.moveToFirst()){
                do{
                    ciudadDestino = cursorDestino.getInt(0);
                    ciudadDestinoNombre = cursorDestino.getString(1);
                }while(cursorDestino.moveToNext());
                cursorDestino.close();
            }

            Cursor cc = db.rawQuery("SELECT HorarioOrigen,HorarioDestino,Ciudad_Origen_Id,Ciudad_Destino_Id,Día " +
                    "FROM recorrido WHERE (HorarioOrigen>='"+getHoraDesde+"' AND HorarioOrigen<='"+getHoraHasta+"'" +
                    " AND Ciudad_Origen_Id="+ciudadOrigen+" AND Ciudad_Destino_Id="+ciudadDestino+
                    " AND Día='"+dia+"');",null);

            ArrayList<String> dev = new ArrayList<>();
            String horaorigen,horadestino;
            if(cc.moveToFirst()){
                do{
                    horaorigen = cc.getString(0);
                    horadestino = cc.getString(1);
                    dev.add(ciudadOrigenNombre+"-"+ciudadDestinoNombre+"-"+horaorigen+"-"+horadestino);

                }while(cc.moveToNext());
                adapterViajes = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dev);
                adapterViajes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                lvViajes.setAdapter(adapterViajes);
                helper.close();
                cc.close();
            }
        }catch (Exception ex){
            ex.printStackTrace();
            helper.close();
        }

    }
}
