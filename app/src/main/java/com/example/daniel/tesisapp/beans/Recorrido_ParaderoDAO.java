package com.example.daniel.tesisapp.beans;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.daniel.tesisapp.DbHelper;

import java.util.ArrayList;


/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class Recorrido_ParaderoDAO extends DbHelper {

    private String tabla = "recorrido_paradero";

    public Recorrido_ParaderoDAO(Context ctx){
        super(ctx);
    }

    public Recorrido_Paradero buscar(int id){
        Recorrido_Paradero dev = null;
        try{

            openDataBase();
            String[] campos = {"Recorrido_Id", "Paradero_Id","Id"};
            Cursor c = db.query(tabla, campos, "Id=" +id+";",null, null, null, null, null);
            if(c != null) {
                c.moveToFirst();
            }
            dev = new Recorrido_Paradero(c.getInt(0), c.getInt(1),id);
            c.close();
            close();

        }catch(Exception ex){
            ex.printStackTrace();
            close();
            return null;
        }


        return dev;
    }

    public ArrayList<Recorrido_Paradero> listado(){
        ArrayList<Recorrido_Paradero> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<Recorrido_Paradero>();
            String[] campos = {"Recorrido_Id", "Paradero_Id","Id"};
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int recorrido_id= c.getInt(0);
                    int usuario_id=c.getInt(1);
                    int id=c.getInt(2);
                    Recorrido_Paradero recorridoParadero = new Recorrido_Paradero(recorrido_id,usuario_id, id);
                    dev.add(recorridoParadero);
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

    public boolean modificar(int id,int usuario_id, int paradero_id){
        try{
            openDataBase();
            ContentValues valores = new ContentValues();
            valores.put("Id", id);
            valores.put("Usuario_Id", usuario_id);
            valores.put("Paradero_Id", paradero_id);
            db.update(tabla, valores, "Id=" + id, null);
            close();
        }
        catch(Exception ex){
            close();
            return false;
        }
        return true;
    }

    public boolean insertar(int usuario_id, int paradero_id){
        ContentValues valores = new ContentValues();
        valores.put("Usuario_Id", usuario_id);
        valores.put("Paradero_Id", paradero_id);
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
