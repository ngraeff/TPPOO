package org.example.controlador;

import org.example.enums.*;
import org.example.modelo.*;
import org.example.vista.VentanaPrincipal;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class ControladorJuego {
    private VentanaPrincipal vista;
    private Partida partida;
    private Ranking ranking;
    private Jugador jugador;
    private Oleada oleada;
    //dispario y movimiento
    private List<Proyectil> proyectiles = new ArrayList<>();
    private boolean moviendoseIzquierda = false;
    private boolean moviendoseDerecha = false;
    private long ultimoDisparo = 0;
    private boolean disparoPresionado = false;

    public ControladorJuego() {

        this.jugador = null;
        this.ranking = null;
        this.partida = new Partida(Dificultad.FACIL,EstadoDeJuego.MENU_PRINCIPAL,0,1000,0,0,"Valen");
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
     */
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
        //Una vez ya validado, crea al jugador y a la primera oleada.
        this.partida.setEstadoDeJuego(EstadoDeJuego.EN_CURSO);
        crearJugador(Dificultad.FACIL);
        crearOleadaInvasores();
        //empieza el juego
        //this.partida.comenzarJuego()
    }

    /***
     * Crea al jugador con la dificultad seleccionada por el usuario.
     * @param dificultad Dificultad seleccionada por el usuario
     */
    private void crearJugador(Dificultad dificultad) {
        this.jugador = new Jugador(dificultad.getVidaInicialJugador(),dificultad.getPosicionJugadorX(),dificultad.getPosicionJugadorY(),dificultad.getCooldownJugador(),dificultad.getAltoJugador(), dificultad.getAnchoJugador(),dificultad.getVelocidadDelJugador());
    }

    /***
     * Crea las oleadas de naves invasoras
     */
    private void crearOleadaInvasores() {
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
        System.out.println("finalizando partida");
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
        actualizarMovimiento(anchoPanel);
        actualizarProyectiles();

        // control de disparo continuo con cooldown
        if (disparoPresionado) {
            long ahora = System.currentTimeMillis();
            if (ahora - ultimoDisparo >= jugador.getCooldownDisparo()) {
                disparar();
                ultimoDisparo = ahora;
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
    }

    /***
     * Actualiza el proyectil cuando es disparado.
     */
    private void actualizarProyectiles() {
        List<Proyectil> activos = new ArrayList<>();
        for (Proyectil p : proyectiles) {
            if (p.isEstaActivo()) {
                p.mover();
                // si sale de la pantalla, se destruye
                if (p.getPosicionY() < 0) {
                    p.destruir();
                } else {
                    activos.add(p);
                }
            }
        }
        proyectiles = activos;
    }

    /***
     * Crea el proyectil cuando el usuario presiona espacio.
     */
    public void disparar() {
        if (jugador != null) {
            int xCentro = jugador.getPosicionX() + (jugador.getAncho() / 2);
            int yInicio = jugador.getPosicionY();

            Proyectil p = new Proyectil(
                    xCentro,
                    yInicio,
                    Proyectil.TipoProyectil.ALIADO,
                    true,
                    8
            );
            proyectiles.add(p);
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

        List<Integer> datosJugador = new ArrayList<>();
        datosJugador.add(this.jugador.getPosicionX());
        datosJugador.add(this.jugador.getPosicionY());
        datosJugador.add(this.jugador.getAncho());
        datosJugador.add(this.jugador.getAlto());
        return datosJugador;
    }

    /***
     * Envia los datos necesarios para dibujar a la vista.
     * @return Datos de los proyectiles.
     */
    public List<int[]> getDatosProyectiles() {
        List<int[]> datos = new ArrayList<>();
        for (Proyectil p : proyectiles) {
            if (p.isEstaActivo()) {
                datos.add(new int[] { p.getPosicionX(), p.getPosicionY(), 3, 8 });
            }
        }
        return datos;
    }

    /***
     * Envia los datos necesarios para dibujar a la vista.
     * @return Datos de las naves.
     */
    public List<int[]> getDatosNavesInvasoras() {
        List<int[]> datos = new ArrayList<>();

        for (NaveInvasora nave : oleada.getNavesVivas()) {
            datos.add(new int[]{nave.getPosicionX(), nave.getPosicionY()});
        }

        return datos;
    }

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



}
