package org.example.modelo;

import java.util.ArrayList;
import java.util.List;

public class Jugador {


    private int vida;
    private int posicionX;
    private int posicionY;
    private int cooldownDisparo;
    private int ancho;
    private int alto;
    private int velocidadDelJugador;
    private List<Proyectil> proyectiles;
    private long ultimoDisparo;

    public Jugador(int vida, int posicionX, int posicionY, int cooldownDisparo, int altoJugador, int anchoJugador, int velocidadDelJugador) {
        this.vida = vida;
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
            this.posicionX -= this.velocidadDelJugador ;
        }
    }

    public void moverDerecha(int limiteDerecho) {
        if(this.posicionX < limiteDerecho - this.ancho*2) {
            this.posicionX += this.velocidadDelJugador;
        }
        else {
            this.posicionX = (limiteDerecho - this.ancho-20);
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
        int xCentro = this.posicionX + (this.ancho / 2);
        int yInicio = this.posicionY;

        Proyectil nuevo = new Proyectil(
                xCentro,
                yInicio,
                Proyectil.TipoProyectil.ALIADO,
                true,
                8
        );

        proyectiles.add(nuevo);
    }

    public void actualizarProyectiles(List<NaveInvasora> navesVivas) {
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
                        p.destruir();
                        nave.destruir();
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
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public int getVelocidadDelJugador() { return velocidadDelJugador; }
    public int getCooldownDisparo() { return cooldownDisparo; }
}
