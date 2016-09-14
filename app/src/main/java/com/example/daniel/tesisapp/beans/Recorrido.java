package com.example.daniel.tesisapp.beans;

/**
 * Created by daniel on 11/07/2016.
 */
public class Recorrido {

    private int Id;
    private String HorarioOrigen,HorarioDestino;
    private int Ciudad_Origen_id,Ciudad_Destino_id;

    public Recorrido(int id, String horarioOrigen, String horarioDestino, int ciudad_Origen_id, int ciudad_Destino_id) {
        Id = id;
        HorarioOrigen = horarioOrigen;
        HorarioDestino = horarioDestino;
        Ciudad_Origen_id = ciudad_Origen_id;
        Ciudad_Destino_id = ciudad_Destino_id;
    }

    public int getId() {
        return Id;
    }

    public Recorrido setId(int id) {
        Id = id;
        return this;
    }

    public int getCiudad_Destino_id() {
        return Ciudad_Destino_id;
    }

    public Recorrido setCiudad_Destino_id(int ciudad_Destino_id) {
        Ciudad_Destino_id = ciudad_Destino_id;
        return this;
    }

    public String getHorarioDestino() {
        return HorarioDestino;
    }

    public Recorrido setHorarioDestino(String horarioDestino) {
        HorarioDestino = horarioDestino;
        return this;
    }

    public int getCiudad_Origen_id() {
        return Ciudad_Origen_id;
    }

    public Recorrido setCiudad_Origen_id(int ciudad_Origen_id) {
        Ciudad_Origen_id = ciudad_Origen_id;
        return this;
    }

    public String getHorarioOrigen() {
        return HorarioOrigen;
    }

    public Recorrido setHorarioOrigen(String horarioOrigen) {
        HorarioOrigen = horarioOrigen;
        return this;
    }
}
