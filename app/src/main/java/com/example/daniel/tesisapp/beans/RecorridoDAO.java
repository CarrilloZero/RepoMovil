package com.example.daniel.tesisapp.beans;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.daniel.tesisapp.DbHelper;

import java.util.ArrayList;

/**
 * Created by daniel on 11/07/2016.
 */
public class RecorridoDAO extends DbHelper{

    private String tabla = "recorrido";

    public RecorridoDAO(Context ctx){
        super(ctx);
    }



    public ArrayList<Recorrido> listado(){
        ArrayList<Recorrido> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<Recorrido>();
            String[] campos = {"Id","HorarioOrigen","HorarioDestino","Ciudad_Origen_Id","Ciudad_Destino_Id"};
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(0);
                    String horaOri = c.getString(1);
                    String horaDes = c.getString(2);
                    int ciuOri = c.getInt(3);
                    int ciuDes = c.getInt(4);

                    Recorrido recorrido = new Recorrido(id,horaOri,horaDes,ciuOri,ciuDes);
                    dev.add(recorrido);

                } while(c.moveToNext());
            }
            c.close();
            close();

        }catch(Exception ex){
            close();
            return null;
        }

        return dev;
    }

    public ArrayList<String> listadoNombres(String ciudadOrigen,String ciudadDestino, String horaOrigen,String horaDestino){
        ArrayList<Recorrido> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<Recorrido>();
            String[] campos = {"Id","HorarioOrigen","HorarioDestino","Ciudad_Origen_Id","Ciudad_Destino_Id"};
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id = c.getInt(0);
                    String horaOri = c.getString(1);
                    String horaDes = c.getString(2);
                    int ciuOri = c.getInt(3);
                    int ciuDes = c.getInt(4);

                    Recorrido recorrido = new Recorrido(id,horaOri,horaDes,ciuOri,ciuDes);
                    dev.add(recorrido);

                } while(c.moveToNext());
            }
            c.close();
            close();

        }catch(Exception ex){
            close();
            return null;
        }

        return null;
    }

    public boolean eliminar(int id) {
        try{
            openDataBase();
            db.delete(tabla, "Id="+id, null);
            close();
        }catch(Exception ex){
            close();
            return false;
        }
        return true;
    }

    public boolean modificar(int id,String nombre){
        try{
            openDataBase();
            ContentValues valores = new ContentValues();
            valores.put("Id", id);
            valores.put("Nombre", nombre);
            db.update(tabla, valores, "Id=" + id, null);
            close();
        }
        catch(Exception ex){
            close();
            return false;
        }
        return true;
    }

    public boolean insertar(String nombre){
        ContentValues valores = new ContentValues();
        valores.put("Nombre", nombre);
        try{
            openDataBase();
            db.insert(tabla, null, valores);
            close();
            return true;
        }
        catch(Exception ex){
            close();
            return false;
        }
    }
}
