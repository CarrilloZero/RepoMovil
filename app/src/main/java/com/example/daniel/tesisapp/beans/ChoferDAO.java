package com.example.daniel.tesisapp.beans;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.daniel.tesisapp.DbHelper;

import java.util.ArrayList;


/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class ChoferDAO extends DbHelper {

    private String tabla = "chofer";

    public ChoferDAO(Context ctx){
        super(ctx);
    }

    public Chofer buscar(int usuario_id){
        Chofer dev = null;
        try{

            openDataBase();
            String[] campos = {"Id","Usuario_Id", "Bus_Id", "Empresa_Id"};
            Cursor c = db.query(tabla, campos, "Usuario_Id=" +usuario_id+";",null, null, null, null, null);
            if(c != null) {
                c.moveToFirst();
            }
            dev = new Chofer(c.getInt(0),usuario_id, c.getInt(2), c.getInt(3));
            c.close();
            close();

        }catch(Exception ex){
            ex.printStackTrace();
            close();
            return null;
        }


        return dev;
    }

    public ArrayList<Chofer> listado(){
        ArrayList<Chofer> dev = null;
        try{
            openDataBase();
            dev = new ArrayList<Chofer>();
            String[] campos = {"Id","Usuario_Id", "Bus_Id", "Empresa_Id"};
            Cursor c = db.query(tabla, campos, null,null, null, null, null, null);
            if (c.moveToFirst()) {
                do {
                    int id= c.getInt(0);
                    int usuario_id=c.getInt(1);
                    int bus_id=c.getInt(2);
                    int empresa_id=c.getInt(3);
                    Chofer chofer = new Chofer(id,usuario_id, bus_id, empresa_id);
                    dev.add(chofer);
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

    public boolean modificar(int id,int usuario_id, int bus_id, int empresa_id){
        try{
            openDataBase();
            ContentValues valores = new ContentValues();
            valores.put("Id", id);
            valores.put("Usuario_Id", usuario_id);
            valores.put("Bus_Id", bus_id);
            valores.put("Empresa_Id", empresa_id);
            db.update(tabla, valores, "Id=" + id, null);
            close();
        }
        catch(Exception ex){
            close();
            return false;
        }
        return true;
    }

    public boolean insertar(int usuario_id, int bus_id, int empresa_id){
        ContentValues valores = new ContentValues();
        valores.put("Usuario_Id", usuario_id);
        valores.put("Bus_Id", bus_id);
        valores.put("Empresa_Id", empresa_id);
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
