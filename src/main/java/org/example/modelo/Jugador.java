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
}
