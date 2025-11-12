package org.example.modelo;

public class MuroDeEnergia {
    private int vida;
    private int posicionX;
    private int posicionY;
    private final int ancho = 60;
    private final int alto = 60;
    private boolean estaVivo;

    public MuroDeEnergia(int vida, int posicionX, int posicionY) {
        this.vida = vida;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.estaVivo = true;
    }
    public int getVida() {
        return vida;
    }
    public int getPosicionX() {
        return posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void reducirVida() {
        if (vida == 0) {
            estaVivo = false;

        }else{
            vida--;
        }
    }

    public boolean getEstaVivo() {
        return estaVivo;
    }
    public void sumarVida() {
        vida++;
    }

}
