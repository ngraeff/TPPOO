package org.example.modelo;

import org.example.enums.TipoProyectil;

public class Proyectil {
    public boolean isEstaActivo() {
        return estaActivo;
    }

    public enum TipoProyectil {
        ENEMIGO,
        ALIADO;
    }
    private int posicionX;
    private int posicionY;
    private TipoProyectil proyectil;
    private boolean estaActivo;
    private int velocidad;
    private final int ancho =3;
    private final int alto = 8;

    public Proyectil(int posicionX, int posicionY, TipoProyectil proyectil, boolean estaActivo, int velocidad) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.proyectil = proyectil;
        this.estaActivo = true;
        this.velocidad = velocidad;
    }

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public int getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(int posicionY) {
        this.posicionY = posicionY;
    }

    public TipoProyectil getProyectil() {
        return proyectil;
    }

    public void setProyectil(TipoProyectil proyectil) {
        this.proyectil = proyectil;
    }

    public void setEstaActivo(boolean estaActivo) {
        this.estaActivo = estaActivo;
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }

    public int getAlto() {
        return alto;
    }

    public int getAncho() {
        return ancho;
    }

    public void mover (){
        if(estaActivo){
            if(proyectil == TipoProyectil.ALIADO){
                posicionY -= velocidad;
            } else {
                posicionY += velocidad;
            }
        }
    }

    public void destruir(){
        if(estaActivo){
            this.estaActivo = false;
        }

    }

}
