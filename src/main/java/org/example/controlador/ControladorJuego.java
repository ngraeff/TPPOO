package org.example.controlador;

import org.example.enums.*;
import org.example.modelo.*;
import org.example.vista.VentanaPrincipal;
import org.example.vista.PanelJuego;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class ControladorJuego {
    private VentanaPrincipal vista;
    private Partida partida;
    private Ranking ranking;
    private Timer timer;
    private PanelJuego panelJuego;


    // flags de teclas
    private boolean moviendoseIzquierda = false;
    private boolean moviendoseDerecha = false;
    private boolean disparoPresionado = false;

    public ControladorJuego() {
        this.ranking = new Ranking();
        this.partida = new Partida(Dificultad.SIN_INFORMAR,EstadoDeJuego.MENU_PRINCIPAL,0,50,0,0,"Valen");
        this.vista = null;
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

        //valida condiciones necesarias para crear el juego
        if (this.partida.getDificultad() == Dificultad.SIN_INFORMAR){
            vista.mostrarMensaje("Seleccione una dificultad antes de comenzar el juego.", "Error de configuración");
            return false;
        }
        if (this.partida.getCreditosJugador()< this.partida.getDificultad().getCreditosParaIniciar()){
            vista.mostrarMensaje("Por favor, cargue mas creditos para jugar con la dificultad seleccionada.\nUsted tiene " + partida.getCreditosJugador() + " creditos. Necesita " + this.partida.getDificultad().getCreditosParaIniciar() + " creditos para iniciar.", "Creditos Insuficientes");
            return false;
        }
        //Una vez ya validado, crea al jugador y a la primera oleada.
        this.partida.setEstadoDeJuego(EstadoDeJuego.EN_CURSO);
        partida.inicializarJugador();
        partida.crearOleadaInicial();
        iniciarTimer();
        return true;

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

    /***
     * Se encarga de terminar la partida cuando se haya quedado sin vidas el jugador.
     */
    public void finalizarPartida(){
        agregarPartidaAlRanking(partida.getNombreJugador(), partida.getPuntosJugador());
    };

    //========================================================================
    // FUNCIONES DE JUEGO
    //========================================================================

    /***
     * Avanza el nivel del juego cuando haya terminado la oleada.
     */
    public void avanzarNivel(){
        System.out.println("avanzando nivel");
    }

    //========================================================================
    // TIMER DEL JUEGO
    //========================================================================

    private void iniciarTimer() {
        if (timer != null && timer.isRunning()) timer.stop();

        timer = new Timer(16, e -> {
            if (panelJuego != null) {
                actualizarJuego(panelJuego.getWidth());
                panelJuego.repaintCanvas();
            }
        });
        timer.start();
    }

    public void pausarJuego() {
        if (timer != null) timer.stop();
    }

    public void reanudarJuego() {
        if (timer != null) timer.start();
    }

    public void terminarJuego() {
        if (timer != null) timer.stop();
        finalizarPartida();
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
<<<<<<< Updated upstream
        partida.actualizarEstado(anchoPanel, moviendoseIzquierda, moviendoseDerecha, disparoPresionado);
=======
        actualizarMovimiento(anchoPanel);
        jugador.actualizarProyectiles(getNavesVivas());


        if (disparoPresionado) {
            jugador.intentarDisparar();
        }

        if (oleada != null) {
            oleada.actualizar(anchoPanel);
            for(NaveInvasora n : getNavesVivas()) {
                n.actualizarProyectiles(jugador);
            }

        }
    }

    /***
     * Actualiza el jugador en el juego segun que tecla se este presionando (derecha o izquierda).
     * @param anchoPanel Ancho del panel del juego.
     */
    private void actualizarMovimiento(int anchoPanel) {
        if (moviendoseIzquierda) jugador.moverIzquierda(0);
        if (moviendoseDerecha) jugador.moverDerecha(anchoPanel);
>>>>>>> Stashed changes
    }

    //========================================================================
    // GETTER PARA LA VISTA
    //========================================================================

    /***
     * Envia los datos necesarios para dibujar a la vista.
     * @return Datos del jugador.
     */
    public List<Integer> getDatosJugadorADibujar() {
        return partida.getDatosJugadorADibujar();
    }

    /***
     * Setea la Vista principal
     * @param vista Menu principal del juego.
     */
    public void setVista(VentanaPrincipal vista) {
        this.vista = vista;
    }

    public void setPanelJuego(PanelJuego panelJuego) {
        this.panelJuego = panelJuego;
    }
    /***
     * Envia los datos necesarios para dibujar a la vista.
     * @return Datos de los proyectiles.
     */
    public List<int[]> getDatosProyectiles() {
        return partida.getDatosProyectiles();
    }

    public List<int[]> getDatosProyectilesEnemigos() {
        List<int[]> datos = new ArrayList<>();
        if (oleada != null) {
            for (NaveInvasora nave : getNavesVivas()) {
                datos.addAll(nave.getDatosProyectiles()); // cada nave devuelve sus proyectiles
            }
        }
        return datos;
    }
    /***
     * Envia los datos necesarios para dibujar a la vista.
     * @return Datos de las naves.
     */
    public List<int[]> getDatosNavesInvasoras() {
        return partida.getDatosNavesInvasoras();
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

}
