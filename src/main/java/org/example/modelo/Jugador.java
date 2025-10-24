package org.example.modelo;


public class Jugador {


    private int vida;
    private int posicionX;
    private int posicionY;
    private int cooldownDisparo;
    private int ancho;
    private int alto;

    public Jugador(int vida, int posicionX, int posicionY, int cooldownDisparo, int altoJugador, int anchoJugador) {
        this.vida = vida;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.cooldownDisparo = cooldownDisparo;
        this.alto = altoJugador;
        this.ancho = anchoJugador;
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
        this.posicionX -= 10;
    }
}

public void moverDerecha(int limiteDerecho) {
    if(this.posicionX < limiteDerecho - this.ancho) {
        this.posicionX += 10;
    }
}

// Getters para la vista
public int getPosicionX() { return posicionX; }
public int getPosicionY() { return posicionY; }
public int getAncho() { return ancho; }
public int getAlto() { return alto; }
}
