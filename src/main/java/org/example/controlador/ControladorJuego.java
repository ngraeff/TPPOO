package org.example.controlador;

import org.example.enums.Dificultad;
import org.example.enums.EstadoDeJuego;
import org.example.modelo.Jugador;
import org.example.modelo.Partida;
import org.example.modelo.Ranking;
import org.example.vista.VentanaPrincipal;

public class ControladorJuego {
    private VentanaPrincipal vista;
    private Partida partida;
    private Ranking ranking;
    private Jugador jugador;

    public ControladorJuego() {

        this.jugador = null;
        this.ranking = null;
        this.partida = new Partida(Dificultad.SIN_INFORMAR,EstadoDeJuego.MENU_PRINCIPAL,0,0,0,0,"");
    }

    public void cargarCreditos(int creditosACargar){
        this.partida.setCreditosJugador(creditosACargar);
    }

    public void seleccionarDificultad(Dificultad dificultad){
        this.partida.setDificultad(dificultad);
        this.jugador = new Jugador(
                dificultad.getVidaInicialJugador(),
                dificultad.getPosicionJugadorX(),
                dificultad.getPosicionJugadorY(),
                dificultad.getCooldownJugador(),
                dificultad.getAltoJugador(),
                dificultad.getAnchoJugador()
        );
        //this.jugador = new Jugador(dificultad.getVidaInicialJugador(),dificultad.getPosicionJugadorX(),dificultad.getPosicionJugadorY(),dificultad.getCooldownJugador(),dificultad.getAltoJugador(), dificultad.getAnchoJugador());
    }

    public void setearNombreDeJugador(String nombreJugador){
        this.partida.setNombreJugador(nombreJugador);
    }

    public void iniciarJuego(){

        //valida condiciones necesarias para crear el juego
        if (this.partida.getDificultad() == Dificultad.SIN_INFORMAR){
            System.out.println("Seleccione una dificultad");
            vista.errorDificultadSinInformar();
            return;
        }
        if (this.partida.getCreditosJugador()< this.partida.getDificultad().getCreditosParaIniciar()){
            System.out.println("Creditos insuficientes, por favor cargar mas");
            vista.errorCreditosInsuficientes();
            return;
        }
        this.partida.setEstadoDeJuego(EstadoDeJuego.EN_CURSO);
        //empieza el juego

    }


    //this.partida = new Partida(dificultad, EstadoDeJuego.EN_CURSO,estadoJugador,creditosJugador, dificultad.getPuntosInicialJugador(),dificultad.getVidaInicialJugador(),nombreJugador);        }


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
