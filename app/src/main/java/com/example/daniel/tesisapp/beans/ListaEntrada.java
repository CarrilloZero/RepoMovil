package com.example.daniel.tesisapp.beans;

/**
 * Created by daniel on 19/07/2016.
 */
public class ListaEntrada {

    private String textoCiudadOrigen;
    private String textoCiudadDestino;
    private String textoHorario;

    public ListaEntrada (String ciudadOrigen, String ciudadDestino, String horario) {
        this.textoCiudadOrigen = ciudadOrigen;
        this.textoCiudadDestino = ciudadDestino;
        this.textoHorario = horario;
    }

    public String getTextoCiudadOrigen() {
        return textoCiudadOrigen;
    }

    public ListaEntrada setTextoCiudadOrigen(String textoCiudadOrigen) {
        this.textoCiudadOrigen = textoCiudadOrigen;
        return this;
    }

    public String getTextoCiudadDestino() {
        return textoCiudadDestino;
    }

    public ListaEntrada setTextoCiudadDestino(String textoCiudadDestino) {
        this.textoCiudadDestino = textoCiudadDestino;
        return this;
    }

    public String getTextoHorario() {
        return textoHorario;
    }

    public ListaEntrada setTextoHorario(String textoHorario) {
        this.textoHorario = textoHorario;
        return this;
    }
}
