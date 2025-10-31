package org.example.modelo;

import org.example.enums.TipoProyectil;

import java.util.ArrayList;
import java.util.List;

public class NaveInvasora {
    private int posicionX;
    private int posicionY;
    private int velocidadDeMovimiento;
    private boolean estaViva;
    public final int ancho = 30;
    public final int alto = 20;
    private List<Proyectil> proyectiles;


    public NaveInvasora(int posicionX, int posicionY, int velocidadDeMovimiento, boolean estaViva) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.velocidadDeMovimiento = velocidadDeMovimiento;
        this.estaViva = estaViva;
        this.proyectiles = new ArrayList<>();
    }

    // METODOS MOVIMIENTO
    public void mover() {
        this.posicionX += this.velocidadDeMovimiento;
    }

    public void cambiarDireccion() {
        this.velocidadDeMovimiento *= -1;
        this.posicionY += 20; // Bajar una fila
    }

    public void destruir() {
        if (estaViva) {
            this.estaViva = false;
        }
    }

    public void disparar() {
        int xCentro = this.posicionX + (this.ancho / 2);
        int yInicio = this.posicionY;
        Proyectil nuevo = new Proyectil(
                xCentro,
                yInicio,
                TipoProyectil.ENEMIGO,
                true,
                8
        );
        proyectiles.add(nuevo);
    }

    public void actualizarProyectiles(Jugador jugador) {
        List<Proyectil> activos = new ArrayList<>();
        for (Proyectil p : proyectiles) {
            if (!p.isEstaActivo()) continue;

            p.mover();

            if (p.getPosicionY() < 0) {
                p.destruir();
                continue;
            }


            // Colisiones con las naves enemigas
            if (jugador != null){

                if (p.detectaColision(jugador)){
                    p.destruir();
                    jugador.restarVida();
                    break;
                }

            }

            if (p.isEstaActivo()){
                activos.add(p);
            }
        }
        proyectiles = activos;
    }

    public List<int[]> getDatosProyectiles() {
        List<int[]> datos = new ArrayList<>();
        for (Proyectil p : proyectiles) {
            if (p.isEstaActivo()) {
                datos.add(new int[]{p.getPosicionX(), p.getPosicionY(), p.getAncho(), p.getAlto()});
            }
        }
        return datos;
    }

    // Getters para la vista
    public int getPosicionX() { return posicionX; }
    public int getPosicionY() { return posicionY; }
    public boolean isEstaViva() { return estaViva; }
    public void setEstaViva(boolean viva) { this.estaViva = viva; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public void setPosicionX(int posicionX) { this.posicionX = posicionX; }
}
