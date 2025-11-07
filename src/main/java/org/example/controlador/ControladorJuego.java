package org.example.controlador;

import org.example.enums.*;
import org.example.modelo.*;
import org.example.vista.VentanaPrincipal;
import org.example.vista.PanelJuego;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;

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
        this.partida = new Partida(Dificultad.SIN_INFORMAR,EstadoDeJuego.MENU_PRINCIPAL,0);
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
        if (!this.partida.chequearDificultad()){
            vista.mostrarMensaje("Seleccione una dificultad antes de comenzar el juego.", "Error de configuración");
            return false;
        }
        if (!this.partida.chequearCreditos()) {
            vista.mostrarMensaje("Por favor, cargue mas creditos para jugar con la dificultad seleccionada.\nUsted tiene " + partida.getCreditosJugador() + " creditos. Necesita " + this.partida.getDificultad().getCreditosParaIniciar() + " creditos para iniciar.", "Creditos Insuficientes");
            return false;
        }
        //Una vez ya validado, crea al jugador y a la primera oleada.
        this.partida.setEstadoDeJuego(EstadoDeJuego.EN_CURSO);
        this.partida.restarCreditos();
        partida.inicializarJugador();
        partida.crearOleada();
        partida.crearMuroDeEnergia();

        // Resetear el flag de game over cuando se inicia una nueva partida
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


    //========================================================================
    // FUNCIONES DE JUEGO
    //========================================================================

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

        if (vista != null) {

            switch (partida.getEstadoDeJuego()) {
                case GAME_OVER_VIDA -> vista.mostrarMensaje(
                        "¡Te has quedado sin vidas! \nEl juego ha terminado\nPuntaje: " + partida.getPuntosJugador(),
                        "GAME OVER"
                );
                case GAME_OVER_LIMITE -> vista.mostrarMensaje(
                        "¡Las naves invasoras han alcanzado tu posición!\n\n" +
                                "Puntuación final: " + partida.getPuntosJugador() + "\n" +
                                "Vidas restantes: " + partida.getVidaJugador(),
                        "GAME OVER"
                );
                default -> vista.mostrarMensaje(
                        "Finalizó la partida\n\n" +
                                "Puntuación final: " + partida.getPuntosJugador(),
                        "Partida Terminada"
                );
            }

            int contador = 1;
            String  nombre = JOptionPane.showInputDialog(
                    null,
                    "Ingrese su nombre para el ranking:",
                    "Guardar Puntaje",
                    JOptionPane.PLAIN_MESSAGE
            );

            while ((nombre == null || nombre.trim().isEmpty()) && contador < 3){
                contador++;
                nombre = JOptionPane.showInputDialog(
                        null,
                        "Por favor, ingrese su nombre para el ranking:",
                        "Guardar Puntaje",
                        JOptionPane.PLAIN_MESSAGE
                );}

            while (nombre == null || nombre.trim().isEmpty()){
                contador++;
                nombre = JOptionPane.showInputDialog(
                        null,
                        "Dale loko, ayudame. Ingresa tu nombre para el ranking :) :",
                        "Guardar Puntaje",
                        JOptionPane.PLAIN_MESSAGE
                );}
            partida.setNombreJugador(nombre.trim());
            agregarPartidaAlRanking(nombre.trim(), partida.getPuntosJugador());
            vista.mostrarMensaje("Puntaje guardado exitosamente.", "Ranking");


        }
        partida.setEstadoDeJuego(EstadoDeJuego.MENU_PRINCIPAL);
        reiniciarJuego();
        // Volver al menú principal si hay vista
        if (vista != null) {
            vista.mostrarMenuPrincipal();
        }

    }

    public void reiniciarJuego() {
        vista.mostrarMensaje("Se devolvieron " + getCreditosJugador() +" creditos.","Devolucion de Creditos");
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
        if (panelJuego != null) {
            panelJuego.actualizarInfo(partida.getPuntosJugador(),partida.getVidaJugador(), partida.getNivel());
        }
        //Verifica si partida esta en GAMEOVER
        if (partida.getEstadoDeJuego() == EstadoDeJuego.GAME_OVER_LIMITE || partida.getEstadoDeJuego() == EstadoDeJuego.GAME_OVER_VIDA) {
            terminarJuego();
        }
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

    public List<int[]> getDatosMuro() {
        return partida.getDatosMuro();
    }

    /***
     * Envia los datos necesarios para dibujar a la vista.
     * @return Datos de las naves.
     */
    public List<int[]> getDatosNavesInvasoras() {
        return partida.getDatosNavesInvasoras();
    }


    public List<int[]> getDatosProyectilesEnemigos() {
        List<int[]> datos = new ArrayList<>();

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

}
