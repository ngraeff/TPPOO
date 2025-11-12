package org.example.modelo;

import org.example.enums.TipoProyectil;

import java.util.ArrayList;
import java.util.List;

public class Jugador {


    private int vida;
    private int puntosJugador;
    private float posicionX;
    private float posicionY;
    private int cooldownDisparo;
    private int  ancho;
    private int alto;
    private float velocidadDelJugador;
    private List<Proyectil> proyectiles;
    private long ultimoDisparo;

    public Jugador(int vida, float posicionX, float posicionY, int cooldownDisparo, int altoJugador, int anchoJugador, float velocidadDelJugador) {
        this.vida = vida;
        this.puntosJugador = 1000;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.cooldownDisparo = cooldownDisparo;
        this.alto = altoJugador;
        this.ancho = anchoJugador;
        this.velocidadDelJugador = velocidadDelJugador;
        this.proyectiles = new ArrayList<>();
        this.ultimoDisparo = 0;
    }
    public int restarVida(){
        if (vida > 0){
            vida--;
        }
        return vida;
    }
    public boolean estaVivo(){
        if (vida > 0){
            return true;
        }
        else return false;
    }

    public int sumarVida (){
        if (vida > 0){
            vida++;}
        return vida;
    }

    // MOVIMIENTO 
    // Agregar estos métodos a la clase Jugador
    public void moverIzquierda(int limiteIzquierdo) {
        if(this.posicionX > limiteIzquierdo) {
            this.posicionX = Math.max((float) limiteIzquierdo, this.posicionX - this.velocidadDelJugador);
        }
    }

    public void moverDerecha(int limiteDerecho) {
        float limiteMaximo = limiteDerecho - this.ancho - 20;
        if(this.posicionX < limiteMaximo) {
            this.posicionX = Math.min(limiteMaximo, this.posicionX + this.velocidadDelJugador);
        } else {
            this.posicionX = limiteMaximo;
        }

    }

    public void intentarDisparar() {
        long ahora = System.currentTimeMillis();
        if (ahora - ultimoDisparo >= this.cooldownDisparo) {
            disparar();
            ultimoDisparo = ahora;
        }
    }

    private void disparar() {
        float xCentro = this.posicionX + (this.ancho / 2.0f);
        float yInicio = this.posicionY;
        Proyectil nuevo = new Proyectil(
                xCentro,
                yInicio,
                TipoProyectil.ALIADO,
                true,
                8f
        );
        proyectiles.add(nuevo);
    }


    public void actualizarProyectiles(List<NaveInvasora> navesVivas, List<MuroDeEnergia> muros) {
        List<Proyectil> activos = new ArrayList<>();
        for (Proyectil p : proyectiles) {
            if (!p.isEstaActivo()) continue;

            p.mover();

            if (p.getPosicionY() < 0) {
                p.destruir();
                continue;
            } 
            

            // Colisiones con las naves enemigas
            if (navesVivas != null){
                for (NaveInvasora nave : navesVivas){
                    if (p.detectaColision(nave)){
                        sumarPuntaje();
                        p.destruir();
                        nave.destruir();
                        break;
                    }
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

    public List<Proyectil> getProyectiles() {
        return proyectiles;
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
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public float getVelocidadDelJugador() { return velocidadDelJugador; }
    public int getCooldownDisparo() { return cooldownDisparo; }

    public int getVida() {
        return vida;
    }

    public void sumarPuntaje(){
        this.puntosJugador += 5;
    }
    public int getPuntos(){
        return puntosJugador;
    }
}
