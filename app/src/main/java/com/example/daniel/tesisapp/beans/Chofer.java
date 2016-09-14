package com.example.daniel.tesisapp.beans;

/**
 * Created by Juan JP Zamorano on 22-06-2016.
 */
public class Chofer {

    private int id, usuario_id, bus_id, empresa_id;

    public Chofer(int id, int usuario_id, int bus_id, int empresa_id) {
        this.id = id;
        this.usuario_id = usuario_id;
        this.bus_id = bus_id;
        this.empresa_id= empresa_id;



    }

    public int getBus_Id() {
        return bus_id;
    }

    public Chofer setBus_Id(int bus_id) {
        this.bus_id = bus_id;
        return this;
    }

    public int getEmpresa_Id() {
        return empresa_id;
    }

    public Chofer setEmpresa_Id(int empresa_id) {
        this.empresa_id = empresa_id;
        return this;
    }

    public int getUsuario_Id() {
        return usuario_id;
    }

    public Chofer setUsuario_Id(int usuario_id) {
        this.usuario_id = usuario_id;
        return this;
    }

    public int getId() {
        return id;
    }

    public Chofer setId(int id) {
        this.id = id;
        return this;
    }



}