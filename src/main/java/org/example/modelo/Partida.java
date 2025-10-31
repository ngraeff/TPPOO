package org.example.modelo;

import org.example.enums.Dificultad;
import org.example.enums.EstadoDeJuego;

import java.util.ArrayList;
import java.util.List;

public class Partida {
    private int vidaJugador;
    private int puntosJugador;
    private String nombreJugador;
    private int creditosJugador;
    private int estadoJugador;
    private EstadoDeJuego estadoDeJuego;
    private Dificultad dificultad;
    private Jugador jugador;
    private Oleada oleada;

    public Partida(Dificultad dificultad, EstadoDeJuego estadoDeJuego, int estadoJugador, int creditosJugador, int puntosJugador, int vidaJugador, String nombreJugador) {
        this.dificultad = dificultad;
        this.estadoDeJuego = estadoDeJuego;
        this.estadoJugador = estadoJugador;
        this.creditosJugador = creditosJugador;
        this.puntosJugador = puntosJugador;
        this.vidaJugador = vidaJugador;
        this.nombreJugador = nombreJugador;
    }

    public void setVidaJugador(int vidaJugador) {
        this.vidaJugador = vidaJugador;
    }

    public void setPuntosJugador(int puntosJugador) {
        this.puntosJugador = puntosJugador;
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public void setCreditosJugador(int creditosJugador) {
        this.creditosJugador += creditosJugador;
    }

    public void setEstadoJugador(int estadoJugador) {
        this.estadoJugador = estadoJugador;
    }

    public void setDificultad(Dificultad dificultad) {
        this.dificultad = dificultad;
    }

    public void setEstadoDeJuego(EstadoDeJuego estadoDeJuego) {
        this.estadoDeJuego = estadoDeJuego;
    }

    public int getVidaJugador() {
        return vidaJugador;
    }

    public int getPuntosJugador() {
        return puntosJugador;
    }

    public String getNombreJugador() {
        return nombreJugador;
    }

    public int getCreditosJugador() {
        return creditosJugador;
    }

    public int getEstadoJugador() {
        return estadoJugador;
    }

    public EstadoDeJuego getEstadoDeJuego() {
        return estadoDeJuego;
    }

    public Dificultad getDificultad() {
        return dificultad;
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
                dificultad.getAltoJugador(),
                dificultad.getAnchoJugador(),
                dificultad.getVelocidadDelJugador()
        );
    }

    /**
     * Crea la primera oleada de naves invasoras.
     */
    public void crearOleadaInicial() {
        if (dificultad == null || dificultad == Dificultad.SIN_INFORMAR) {
            return;
        }

        List<NaveInvasora> naves = new ArrayList<>();

        // Crear 5 filas x 8 columnas de invasores
        for(int fila = 0; fila < 5; fila++) {
            for(int col = 0; col < 8; col++) {
                int x = 50 + col * 60;
                int y = 50 + fila * 40;
                NaveInvasora nave = new NaveInvasora(x, y, dificultad.getVelocidadNavesEnemigas(), true);
                naves.add(nave);
            }
        }

        this.oleada = new Oleada(naves, dificultad.getVelocidadNavesEnemigas());
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
            jugador.actualizarProyectiles(oleada.getNavesVivas());
        } else {
            jugador.actualizarProyectiles(null);
        }

        // Actualizar oleada
        if (oleada != null) {
            oleada.actualizar(anchoPanel);
        }
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

    //========================================================================
    // GETTERS PARA DATOS DE LA VISTA
    //========================================================================

    /**
     * Obtiene los datos del jugador para dibujar en la vista.
     * @return Lista con [posicionX, posicionY, ancho, alto]
     */
    public List<Integer> getDatosJugadorADibujar() {
        List<Integer> datosJugador = new ArrayList<>();
        if (jugador != null) {
            datosJugador.add(jugador.getPosicionX());
            datosJugador.add(jugador.getPosicionY());
            datosJugador.add(jugador.getAncho());
            datosJugador.add(jugador.getAlto());
        }
        return datosJugador;
    }

    /**
     * Obtiene los datos de los proyectiles para dibujar en la vista.
     * @return Lista de arrays con [posicionX, posicionY, ancho, alto]
     */
    public List<int[]> getDatosProyectiles() {
        if (jugador != null) {
            return jugador.getDatosProyectiles();
        }
        return new ArrayList<>();
    }

    /**
     * Obtiene los datos de las naves invasoras para dibujar en la vista.
     * @return Lista de arrays con [posicionX, posicionY]
     */
    public List<int[]> getDatosNavesInvasoras() {
        List<int[]> datos = new ArrayList<>();
        if (oleada != null) {
            for (NaveInvasora nave : oleada.getNavesVivas()) {
                datos.add(new int[]{nave.getPosicionX(), nave.getPosicionY()});
            }
        }
        return datos;
    }
}
