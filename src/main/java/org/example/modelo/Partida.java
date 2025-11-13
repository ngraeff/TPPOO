package org.example.modelo;

import org.example.enums.Dificultad;
import org.example.enums.EstadoDeJuego;

import java.util.ArrayList;
import java.util.List;

public class Partida {
    private String nombreJugador;
    private int creditosJugador;
    private EstadoDeJuego estadoDeJuego;
    private Dificultad dificultad;
    private Jugador jugador;
    private Oleada oleada;
    private int nivel;
    private List<MuroDeEnergia> muros;

    public Partida(Dificultad dificultad, EstadoDeJuego estadoDeJuego) {
        this.dificultad = dificultad;
        this.estadoDeJuego = estadoDeJuego;
        this.creditosJugador = 0;
        this.nombreJugador = null;
        this.nivel = 1;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public void setCreditosJugador(int creditosJugador) {
        this.creditosJugador += creditosJugador;
    }


    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    public void setEstadoDeJuego(EstadoDeJuego estadoDeJuego) {
        this.estadoDeJuego = estadoDeJuego;
    }

    public boolean chequearDificultad(){
        return !(this.dificultad == Dificultad.SIN_INFORMAR);
    };

    public boolean chequearCreditos(){
        return !(this.creditosJugador < dificultad.getCreditosParaIniciar());
    }

    public void restarCreditos(){
        this.creditosJugador -= dificultad.getCreditosParaIniciar();
    }

    public int getPuntosJugador() {
        return jugador.getPuntos();
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public int getCreditosJugador() {
        return creditosJugador;
    }

    public EstadoDeJuego getEstadoDeJuego() {
        return estadoDeJuego;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public int getVidaJugador(){
        return jugador.getVida();
    }

    public boolean jugadorPerdioPorVida(){
        return this.jugador.getVida() == 0;
    }

    //========================================================================
    // MÉTODOS DE INICIALIZACIÓN DE JUEGO
    //========================================================================

    /**
     * Inicializa el jugador con la dificultad actual de la partida.
     */
    public void inicializarJugador() {
        if (dificultad == null || dificultad == Dificultad.SIN_INFORMAR) {
            return;
        }
        this.jugador = new Jugador(
                dificultad.getVidaInicialJugador(),
                dificultad.getPosicionJugadorX(),
                dificultad.getPosicionJugadorY(),
                dificultad.getCooldownJugador(),
                dificultad.getVelocidadDelJugador(),
                dificultad.getPuntosInicialJugador()
        );
    }

    /**
     * Crea la primera oleada de naves invasoras.
     */
    public void crearOleada() {
        if (dificultad == null || dificultad == Dificultad.SIN_INFORMAR) {
            return;
        }
        this.oleada = Oleada.crearOleadaBasica(dificultad, nivel);
        
    }

    //========================================================================
    // MÉTODOS DE ACTUALIZACIÓN DEL JUEGO
    //========================================================================

    /**
     * Actualiza el estado completo del juego.
     * @param anchoPanel Ancho del panel de juego.
     * @param moviendoIzquierda Flag de movimiento izquierda.
     * @param moviendoDerecha Flag de movimiento derecha.
     * @param disparando Flag de disparo.
     */
    public void actualizarEstado(int anchoPanel, boolean moviendoIzquierda, boolean moviendoDerecha, boolean disparando) {
        if (jugador == null) {
            return;
        }

        // Actualizar movimiento del jugador
        if (moviendoIzquierda) {
            jugador.moverIzquierda(0);
        }
        if (moviendoDerecha) {
            jugador.moverDerecha(anchoPanel);
        }

        // Actualizar disparo del jugador
        if (disparando) {
            jugador.intentarDisparar();
        }

        // Actualizar proyectiles (colisiones con naves)
        if (oleada != null) {
            jugador.actualizarProyectiles(oleada.getNavesVivas(),muros);
            for (NaveInvasora n : oleada.getNavesVivas()) {
                n.actualizarProyectiles(jugador,muros);
            }
        }
        // Actualizar muro
        if (muros != null) {
            muros.removeIf(m -> !m.getEstaVivo());
        }

        // Actualizar oleada
        if (oleada != null) {
            oleada.actualizar(anchoPanel);
            // Verificar si las naves invasoras alcanzaron el límite crítico (perdiste)
            // GRASP: Information Expert - Partida es experta en información del juego
            float limiteCriticoY = jugador.getPosicionY() - 20f;
            for (NaveInvasora nave : oleada.getNavesVivas()) {
                // Verificar si la nave alcanzó o pasó la altura del jugador
                if (nave.getPosicionY() + nave.getAlto() >= limiteCriticoY) {
                    estadoDeJuego = EstadoDeJuego.GAME_OVER_LIMITE;
                    oleada.detenerMovimiento();
                    return; // Solo necesitamos encontrar una nave que haya alcanzado el límite
                }
            }
        }
        // Ver si perdio por vida
        if (jugadorPerdioPorVida()) {
            this.estadoDeJuego = EstadoDeJuego.GAME_OVER_VIDA;
        }

        if ((oleada != null) && !oleada.hayNavesVivas() ){
            this.avanzarNivel();
        }
    }

    public void reiniciar() {
        this.jugador = null;
        this.oleada = null;
        this.nombreJugador = null;
        this.estadoDeJuego = EstadoDeJuego.MENU_PRINCIPAL;
        this.dificultad = Dificultad.SIN_INFORMAR;
        this.creditosJugador = 0;
        this.nivel= 1;
    }

    public void avanzarNivel() {
        this.nivel++;
        this.oleada = Oleada.crearOleadaBasica(dificultad, nivel);
        crearMuroDeEnergia();
    }
    //========================================================================
    // GETTERS PARA ACCESO A OBJETOS DEL JUEGO
    //========================================================================

    public Jugador getJugador() {
        return jugador;
    }

    public Oleada getOleada() {
        return oleada;
    }

    public List<NaveInvasora> getNavesVivas() {
        if (oleada != null) {
            return oleada.getNavesVivas();
        }
        return new ArrayList<>();
    }

    public int getNivel() {
        return nivel;
    }

    //========================================================================
    // GETTERS PARA DATOS DE LA VISTA
    //========================================================================

    /**
     * Obtiene los datos del jugador para dibujar en la vista.
     * @return Lista con [posicionX, posicionY, ancho, alto]
     */
    public float[] getDatosJugadorADibujar() {
        if (jugador != null) {
            return new float[]{
                    jugador.getPosicionX(),
                    jugador.getPosicionY(),
                    jugador.getAncho(),
                    jugador.getAlto()
            };
        }
        return null;
    }

    /**
     * Obtiene los datos de los proyectiles para dibujar en la vista.
     * @return Lista de arrays con [posicionX, posicionY, ancho, alto]
     */
    public List<float[]> getDatosProyectiles() {
        if (jugador != null) {
            return jugador.getDatosProyectiles();
        }
        return new ArrayList<>();
    }

    /**
     * Obtiene los datos de las naves invasoras para dibujar en la vista.
     * @return Lista de arrays con [posicionX, posicionY]
     */
    public List<float[]> getDatosNavesInvasoras() {
        List<float[]> datos = new ArrayList<>();
        if (oleada != null) {
            for (NaveInvasora nave : oleada.getNavesVivas()) {
                datos.add(new float[]{nave.getPosicionX(), nave.getPosicionY(), nave.getAncho(), nave.getAlto()});
            }
        }
        return datos;
    }

    public List<float[]> getDatosMuro() {
        List<float[]> datos = new ArrayList<>();
        if (muros != null) {
            for (MuroDeEnergia muro : muros) {
                if(muro.getEstaVivo()){
                    datos.add(new float[]{
                            muro.getPosicionX(),
                            muro.getPosicionY(),
                            muro.getAncho(),
                            muro.getAlto(),
                            muro.getVida()
                    });
                }
            }
        }
        return datos;
    }

    public void crearMuroDeEnergia() {
        List<MuroDeEnergia> muros = new ArrayList<>();
        // Crear 5 filas x 8 columnas de invasores
        for (int fila = 1; fila < 2; fila++) {
            for (int col = 0; col < 10; col++) {
                int x = 5 + col * 100;
                int y = 300 + fila * 40;
                MuroDeEnergia muro = new MuroDeEnergia(2, x, y);
                muros.add(muro);
            }
        }
        this.muros = muros;
    }
}
