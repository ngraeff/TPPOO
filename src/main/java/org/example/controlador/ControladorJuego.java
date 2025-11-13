package org.example.controlador;

import org.example.enums.*;
import org.example.modelo.*;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;

public class ControladorJuego {
    private Partida partida;
    private Ranking ranking;

    // flags de teclas
    private boolean moviendoseIzquierda = false;
    private boolean moviendoseDerecha = false;
    private boolean disparoPresionado = false;

    public ControladorJuego() {
        this.ranking = new Ranking();
        this.partida = new Partida(Dificultad.SIN_INFORMAR,EstadoDeJuego.MENU_PRINCIPAL);
    }

    //========================================================================
    // FUNCIONES DE INICIO JUEGO
    //========================================================================

    /***
     * Carga los creditos ingresados por el usuario.
     * @param creditosACargar cantidad de creditos a cargar.
     */
    public void cargarCreditos(int creditosACargar){
        this.partida.setCreditosJugador(creditosACargar);
    }

    /***
     * Selecciona la dificultad del juego y crea todas las variables.
     * @param dificultad dificultad seleccionada por el usuario.
     */
    public void seleccionarDificultad(Dificultad dificultad){
        this.partida.setDificultad(dificultad);
        //this.jugador = new Jugador(dificultad.getVidaInicialJugador(),dificultad.getPosicionJugadorX(),dificultad.getPosicionJugadorY(),dificultad.getCooldownJugador(),dificultad.getAltoJugador(), dificultad.getAnchoJugador());
    }

    /***
     * Inicia la partida del jugador.
     * @return Devuelve si es posible iniciar el juego.
     */
    public boolean iniciarJuego(){


        //Una vez ya validado, crea al jugador y a la primera oleada.
        this.partida.setEstadoDeJuego(EstadoDeJuego.EN_CURSO);
        this.partida.restarCreditos();
        partida.inicializarJugador();
        partida.crearOleada();
        partida.crearMuroDeEnergia();

        // Resetear el flag de game over cuando se inicia una nueva partida
        return true;

    }

    public boolean revisarDificultad(){
        //valida condiciones necesarias para crear el juego
        return this.partida.chequearDificultad();
    }

    public boolean revisarCreditos(){
        return this.partida.chequearCreditos();
    }



    //========================================================================
    // FUNCIONES DE FIN JUEGO
    //========================================================================

    /***
     * Setea el nombre ingresado del jugador
     * @param nombreJugador Nombre del usuario.
     */
    public void setearNombreDeJugador(String nombreJugador){
        this.partida.setNombreJugador(nombreJugador);
    }


    //========================================================================
    // FUNCIONES DE JUEGO
    //========================================================================

    //========================================================================
    // TIMER DEL JUEGO
    //========================================================================

    public EstadoDeJuego getEstadoDeJuego(){
        return partida.getEstadoDeJuego();
    }

    public int getPuntosJugador(){
        return partida.getPuntosJugador();
    }
    public int getVidasJugador(){
        return partida.getVidaJugador();
    }

    public void terminarJuego(String nombre) {

        int contador = 1;

        partida.setNombreJugador(nombre.trim());
        agregarPartidaAlRanking(nombre.trim(), partida.getPuntosJugador());
        partida.setEstadoDeJuego(EstadoDeJuego.MENU_PRINCIPAL);
        reiniciarJuego();
    }

    public void reiniciarJuego() {

        partida.reiniciar();
        moviendoseIzquierda = false;
        moviendoseDerecha = false;
        disparoPresionado = false;

    }

    //========================================================================
    // FUNCIONES DE TECLAS Y MOVIMIENTO EN PANTALLA
    //========================================================================

    /***
     * Setea flag de cual tecla se está presionando.
     * @param codigoTecla codigo de la tecla presionada.
     */
    public void teclaPresionada(int codigoTecla) {
        switch (codigoTecla) {
            case KeyEvent.VK_LEFT:
                moviendoseIzquierda = true;
                break;
            case KeyEvent.VK_RIGHT:
                moviendoseDerecha = true;
                break;
            case KeyEvent.VK_SPACE:
                disparoPresionado = true;
                break;
        }
    }

    /***
     * Apaga el flag segun que tecla este soltada.
     * @param codigoTecla tecla soltada.
     */
    public void teclaSoltada(int codigoTecla) {
        switch (codigoTecla) {
            case KeyEvent.VK_LEFT:
                moviendoseIzquierda = false;
                break;
            case KeyEvent.VK_RIGHT:
                moviendoseDerecha = false;
                break;
            case KeyEvent.VK_SPACE:
                disparoPresionado = false;
                break;
        }
    }

    /***
     * Actualiza la ventana de juego.
     * @param anchoPanel Ancho del panel de juego.
     */
    public void actualizarJuego(int anchoPanel) {
        // Solo actualizar si el juego no está en GAME_OVER
        if (partida.getEstadoDeJuego() != EstadoDeJuego.GAME_OVER_LIMITE && partida.getEstadoDeJuego() != EstadoDeJuego.GAME_OVER_VIDA) {
            partida.actualizarEstado(anchoPanel, moviendoseIzquierda, moviendoseDerecha, disparoPresionado);
        }

    }

    //========================================================================
    // GETTER PARA LA VISTA
    //========================================================================

    /***
     * Envia los datos necesarios para dibujar a la vista.
     * @return Datos del jugador.
     */
    public float[] getDatosJugadorADibujar() {
        return partida.getDatosJugadorADibujar();
    }




    /***
     * Envia los datos necesarios para dibujar a la vista.
     * @return Datos de los proyectiles.
     */
    public List<float[]> getDatosProyectiles() {
        return partida.getDatosProyectiles();
    }

    public List<float[]> getDatosMuro() {
        return partida.getDatosMuro();
    }

    /***
     * Envia los datos necesarios para dibujar a la vista.
     * @return Datos de las naves.
     */
    public List<float[]> getDatosNavesInvasoras() {
        return partida.getDatosNavesInvasoras();
    }


    public List<float[]> getDatosProyectilesEnemigos() {
        List<float[]> datos = new ArrayList<>();

        if (this.partida.getOleada() != null) {
            for (NaveInvasora nave : getNavesVivas()) {
                datos.addAll(nave.getDatosProyectiles()); // cada nave devuelve sus proyectiles
            }
        }
        return datos;
    }

    public int getNivelPartida() {
        return partida.getNivel();
    }

    /***
     * Devuelve el jugador.
     * @return Jugador.
     */
    public Jugador getJugador() {
        return partida.getJugador();
    }

    public List<NaveInvasora> getNavesVivas() {
        return partida.getNavesVivas();
    }

    

    public int  getVidaInicial() {

        return partida.getDificultad().getVidaInicialJugador();
    }

    public int getCreditosJugador(){
        return partida.getCreditosJugador();
    }

    /**
     * Obtiene el ranking para mostrar en el panel de ranking.
     * @return Lista de strings con el ranking ordenado.
     */
    public List<String> getRanking(){
        if (ranking == null) {
            ranking = new Ranking(); // Inicializar si es null
        }
        return ranking.getPartidas();
    }

    /**
     * Agrega una partida al ranking.
     * @param nombreJugador Nombre del jugador.
     * @param puntosJugador Puntos obtenidos.
     */
    public void agregarPartidaAlRanking(String nombreJugador, int puntosJugador){
        if (ranking == null) {
            ranking = new Ranking();
        }
        ranking.agregarPartida(nombreJugador, puntosJugador);
    }

    public int getPuntosPartida(){
        return this.partida.getPuntosJugador();
    }

    public int obtenerCreditosJugador(){
        return partida.getCreditosJugador();
    }

    public int obtenerCreditosNecesarios(){
        return this.partida.getDificultad().getCreditosParaIniciar();
    }

    public boolean tieneJugador(){
        return partida.getJugador() != null;
    }
}
