package org.example.modelo;

import org.example.enums.TipoProyectil;

import java.util.ArrayList;
import java.util.List;

public class NaveInvasora {
    private float posicionX;
    private float posicionY;
    private float velocidadDeMovimiento;
    private boolean estaViva;
    public final int ancho = 30;
    public final int alto = 20;
    private List<Proyectil> proyectiles;


    public NaveInvasora(float posicionX, float posicionY, float velocidadDeMovimiento, boolean estaViva) {
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
        float xCentro = this.posicionX + (this.ancho / 2.0f);
        float yInicio = this.posicionY;
        Proyectil nuevo = new Proyectil(
                xCentro,
                yInicio,
                TipoProyectil.ENEMIGO,
                true,
                8f
        );
        proyectiles.add(nuevo);
    }

    public void actualizarProyectiles(Jugador jugador,List<MuroDeEnergia> muros) {
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
            if (muros != null) {
                for (MuroDeEnergia muro : muros) {
                    if (p.detectaColision(muro)){
                        p.destruir();
                        muro.reducirVida();
                        break;
                    }
                }
            }

            if (p.isEstaActivo()){
                activos.add(p);
            }
        }
        proyectiles = activos;
    }

    public List<float[]> getDatosProyectiles() {
        List<float[]> datos = new ArrayList<>();
        for (Proyectil p : proyectiles) {
            if (p.isEstaActivo()) {
                datos.add(new float[]{p.getPosicionX(), p.getPosicionY(), p.getAncho(), p.getAlto()});
            }
        }
        return datos;
    }

    // Getters para la vista
    public float getPosicionX() { return posicionX; }
    public float getPosicionY() { return posicionY; }
    public boolean isEstaViva() { return estaViva; }
    public void setEstaViva(boolean viva) { this.estaViva = viva; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public void setPosicionX(float posicionX) { this.posicionX = posicionX; }
    public void setVelocidadDeMovimiento(float velocidadDeMovimiento) { this.velocidadDeMovimiento = velocidadDeMovimiento; }
}
