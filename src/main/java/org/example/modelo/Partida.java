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
}
