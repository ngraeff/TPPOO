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

    public void crearPartida(Dificultad dificultad,int estadoJugador, int creditosJugador,String nombreJugador) {
        //Verifica que tenga creditos suficientes)?
        if (verificarCreditos(dificultad,creditosJugador)) {
            //deberia llamar un pop up
            System.out.println("sin creditos suficientes");
        } else
        {
            this.partida = new Partida(dificultad, EstadoDeJuego.EN_CURSO,estadoJugador,creditosJugador, dificultad.getPuntosInicialJugador(),dificultad.getVidaInicialJugador(),nombreJugador);        }
    }

    public void crearJugador(Dificultad dificultad) {
        this.jugador = new Jugador(dificultad.getVidaInicialJugador(),dificultad.getPosicionJugadorX(),dificultad.getPosicionJugadorY(),dificultad.getCooldownJugador(),dificultad.getAltoJugador(), dificultad.getAnchoJugador());
    }

    private boolean verificarCreditos(Dificultad dificultad, int creditosJugador){
        boolean puedeJugar = creditosJugador < dificultad.getCreditosParaIniciar() ?  false :  true;
        return puedeJugar;
    }

    public void finalizarPartida(){
        System.out.println("finalizando partida");
    };
    public void recargarCreditos(int cantidad){
       System.out.println("cargar creditos de jugador");
    }

    public void avanzarNivel(){
        System.out.println("avanzando nivel");
    }


}
