package com.example.daniel.tesisapp.beans;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.daniel.tesisapp.DbHelper;

import java.util.ArrayList;


/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class HaceDAO extends DbHelper {

    private String tabla = "hace";

    public HaceDAO(Context ctx){
        super(ctx);
    }

    public Hace buscar(int id){
        Hace dev = null;
        try{

            openDataBase();
            String[] campos = {"Id","hora_inicio","hora_termino", "Bus_Id", "Recorrido_Id"};
            Cursor c = db.query(tabla, campos, "Id=" +id+";",null, null, null, null, null);
            if(c != null) {
                c.moveToFirst();
            }
            dev = new Hace(id,c.getInt(1),c.getInt(2), c.getInt(3), c.getInt(4));
            c.close();
            close();

        }catch(Exception ex){
            ex.printStackTrace();
            close();
            return null;
        }


        return dev;
    }

    public ArrayList<Hace> listado(){
        ArrayList<Hace> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<Hace>();
            String[] campos = {"Id","hora_inicio","hora_termino", "Bus_Id", "Recorrido_Id"};
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id= c.getInt(0);
                    int hora_inicio=c.getInt(1);
                    int hora_termino=c.getInt(2);
                    int bus_id=c.getInt(3);
                    int recorrido_id=c.getInt(4);
                    Hace hace = new Hace(id,hora_inicio,hora_termino, bus_id, recorrido_id);
                    dev.add(hace);
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

    public boolean modificar(int id, int hora_inicio,int hora_termino, int bus_id, int recorrido_id){
        try{
            openDataBase();
            ContentValues valores = new ContentValues();
            valores.put("Id", id);
            valores.put("hora_inicio", hora_inicio);
            valores.put("hora_termino", hora_termino);
            valores.put("Bus_Id", bus_id);
            valores.put("Recorrido_id", recorrido_id);
            db.update(tabla, valores, "Id=" + id, null);
            close();
        }
        catch(Exception ex){
            close();
            return false;
        }
        return true;
    }

    public boolean insertar(int hora_inicio,int hora_termino, int bus_id, int recorrido_id){
        ContentValues valores = new ContentValues();
        valores.put("hora_inicio", hora_inicio);
        valores.put("hora_termino", hora_termino);
        valores.put("Bus_Id", bus_id);
        valores.put("Recorrido_id", recorrido_id);
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
