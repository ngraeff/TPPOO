package org.example.controlador;

import org.example.enums.Dificultad;
import org.example.enums.EstadoDeJuego;
import org.example.modelo.Jugador;
import org.example.modelo.Partida;
import org.example.modelo.Ranking;

public class ControladorJuego {
    private Partida partida;
    private Ranking ranking;
    private Jugador jugador;

    public ControladorJuego() {

    }
    //Dificultad dificultad, EstadoDeJuego estadoDeJuego, int estadoJugador, int creditosJugador, int puntosJugador, int vidaJugador
    public void iniciarPartida(Dificultad dificultad,int estadoJugador, int creditosJugador){
        this.partida = new Partida(dificultad, EstadoDeJuego.INICIANDO,estadoJugador,creditosJugador, dificultad.getPuntosInicialJugador(),dificultad.getVidaInicialJugador());
    }


}
