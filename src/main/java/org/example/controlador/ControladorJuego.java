package org.example.controlador;

import org.example.enums.Dificultad;
import org.example.enums.EstadoDeJuego;
import org.example.modelo.Jugador;
import org.example.modelo.Partida;
import org.example.modelo.Ranking;
import org.example.modelo.Oleada;
import org.example.modelo.NaveInvasora;
import org.example.vista.VentanaPrincipal;
import java.awt.event.KeyEvent;
import java.util.List;

public class ControladorJuego {
    private VentanaPrincipal vista;
    private Partida partida;
    private Ranking ranking;
    private Jugador jugador;
    private Oleada oleada;

    public ControladorJuego() {

        this.jugador = null;
        this.ranking = null;
        this.partida = new Partida(Dificultad.FACIL,EstadoDeJuego.MENU_PRINCIPAL,0,1000,0,0,"Valen");
        this.vista = null;
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
            //vista.errorDificultadSinInformar();
            return;
        }
        if (this.partida.getCreditosJugador()< this.partida.getDificultad().getCreditosParaIniciar()){
            System.out.println("Creditos insuficientes, por favor cargar mas");
            //vista.errorCreditosInsuficientes();
            return;
        }
        this.partida.setEstadoDeJuego(EstadoDeJuego.EN_CURSO);
        //empieza el juego
        //this.partida.comenzarJuego()
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

    // METODOS MOVIMIENTO
public void manejarTecla(int codigoTecla) {
    switch(codigoTecla) {
        case KeyEvent.VK_LEFT:
            jugador.moverIzquierda(0);
            break;
        case KeyEvent.VK_RIGHT:
            jugador.moverDerecha(800); // Ancho de la ventana
            break;
        case KeyEvent.VK_SPACE:
            // Lógica para disparar
            break;
    }
}


// GETTERS para la vista
public Jugador getJugador() {
    return this.jugador;
}

public List<NaveInvasora> getNavesVivas() {
    if(oleada != null) {
        return oleada.getNavesVivas();
    }
    return null;
}

public void actualizarJuego() {
    // Lógica del juego (movimiento de invasores, colisiones, etc.)
    if(oleada != null) {
        oleada.moverTodas();
    }
}

public void crearOleadaInvasores() {
    java.util.List<NaveInvasora> naves = new java.util.ArrayList<>();
    
    // Crear 5 filas x 8 columnas de invasores
    for(int fila = 0; fila < 5; fila++) {
        for(int col = 0; col < 8; col++) {
            int x = 50 + col * 60;
            int y = 50 + fila * 40;
            NaveInvasora nave = new NaveInvasora(x, y, 1, true);
            naves.add(nave);
        }
    }
    
    this.oleada = new Oleada(naves, 1);
}
}
