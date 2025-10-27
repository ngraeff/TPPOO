package org.example.modelo;

import org.example.enums.Dificultad;
import org.example.enums.EstadoDeJuego;

public class Partida {
    private int vidaJugador;
    private int puntosJugador;
    private String nombreJugador;
    private int creditosJugador;
    private int estadoJugador;
    private EstadoDeJuego estadoDeJuego;
    private Dificultad dificultad;

    public Partida(Dificultad dificultad, EstadoDeJuego estadoDeJuego, int estadoJugador, int creditosJugador, int puntosJugador, int vidaJugador, String nombreJugador) {
        this.dificultad = dificultad;
        this.estadoDeJuego = estadoDeJuego;
        this.estadoJugador = estadoJugador;
        this.creditosJugador = creditosJugador;
        this.puntosJugador = puntosJugador;
        this.vidaJugador = vidaJugador;
        this.nombreJugador = nombreJugador;
    }

    public void setVidaJugador(int vidaJugador) {
        this.vidaJugador = vidaJugador;
    }

    public void setPuntosJugador(int puntosJugador) {
        this.puntosJugador = puntosJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public void setCreditosJugador(int creditosJugador) {
        this.creditosJugador += creditosJugador;
    }

    public void setEstadoJugador(int estadoJugador) {
        this.estadoJugador = estadoJugador;
    }

    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    public void setEstadoDeJuego(EstadoDeJuego estadoDeJuego) {
        this.estadoDeJuego = estadoDeJuego;
    }

    public int getVidaJugador() {
        return vidaJugador;
    }

    public int getPuntosJugador() {
        return puntosJugador;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public int getCreditosJugador() {
        return creditosJugador;
    }

    public int getEstadoJugador() {
        return estadoJugador;
    }

    public EstadoDeJuego getEstadoDeJuego() {
        return estadoDeJuego;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }
}
