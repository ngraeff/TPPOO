package org.example.modelo;

public class MuroDeEnergia {
    private int vida;
    private int posicionX;
    private int posicionY;
    private int ancho;
    private int alto;
    private boolean estaVivo;

    public MuroDeEnergia(int vida, int posicionX, int posicionY, int ancho, int alto) {
        this.vida = vida;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.ancho = ancho;
        this.alto = alto;
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
