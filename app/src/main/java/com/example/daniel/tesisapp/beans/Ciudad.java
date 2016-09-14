package com.example.daniel.tesisapp.beans;

/**
 * Created by daniel on 01/06/2016.
 */
public class Ciudad {
    private String nombre;
    private int id;

    public Ciudad(int id,String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public Ciudad setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public int getId() {
        return id;
    }

    public Ciudad setId(int id) {
        this.id = id;
        return this;
    }
}
