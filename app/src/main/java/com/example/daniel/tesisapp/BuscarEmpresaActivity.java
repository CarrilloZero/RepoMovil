package com.example.daniel.tesisapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.daniel.tesisapp.beans.EmpresaDAO;

import java.util.ArrayList;

public class BuscarEmpresaActivity extends AppCompatActivity implements View.OnClickListener {

    // Elementos de la vista
    private Spinner spn;
    private Button btnMostrarEmpresa;
    private ArrayList arrli;
    private TextView nombre,correo,direccion,telefono;

    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_empresa);

        // Obtenemos los elementos de la vista
        btnMostrarEmpresa = (Button) findViewById(R.id.btnMostrarEmpresa);
        spn = (Spinner) findViewById(R.id.spnEmpresas);
        nombre = (TextView) findViewById(R.id.tvNombreEmp2);
        correo = (TextView) findViewById(R.id.tvCorreoEmp2);
        direccion = (TextView) findViewById(R.id.tvDireccionEmp2);
        telefono = (TextView) findViewById(R.id.tvTelefonoEmp2);

        btnMostrarEmpresa.setOnClickListener(this);

        /*Spinner spinner_animales = (Spinner) findViewById(R.id.spinner_animales);
        ArrayAdapter spinner_adapter = ArrayAdapter.createFromResource( this, R.array.animales , android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_animales.setAdapter(spinner_adapter);*/

        //llenar el spinner
        EmpresaDAO empDAO = new EmpresaDAO(this);
        ArrayList<String> emp = empDAO.listadoNombres();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, emp);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn.setAdapter(adapter);


    }


    @Override
    public void onClick(View v) {
        String empseleccionada = spn.getSelectedItem().toString();
        DbHelper helper = new DbHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        try{
            helper.openDataBase();
            Cursor c = db.rawQuery("SELECT Nombre,Correo,Dirección,Telefono FROM empresa WHERE Nombre='"+empseleccionada+"';",null);
            if (c.moveToFirst()) {
                do {
                    String nom = c.getString(0);
                    String cor = c.getString(1);
                    String dir = c.getString(2);
                    String tel = c.getString(3);
                    nombre.setText(nom);
                    correo.setText(cor);
                    direccion.setText(dir);
                    telefono.setText(tel);

                } while(c.moveToNext());
            }
            c.close();
            helper.close();

        }catch(Exception ex){
            ex.printStackTrace();
            helper.close();
        }

    }
}
