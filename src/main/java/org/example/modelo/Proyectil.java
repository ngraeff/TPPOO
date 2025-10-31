package org.example.modelo;

import org.example.enums.TipoProyectil;

public class Proyectil {

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

    public boolean detectaColision(NaveInvasora nave){
        int px = this.getPosicionX();
        int py = this.getPosicionY();
        int pw = this.getAncho();
        int ph = this.getAlto();

        int nx = nave.getPosicionX();
        int ny = nave.getPosicionY();
        int nw = nave.getAncho();
        int nh = nave.getAlto();

    if((px + pw >= nx && px <= nx + nw && py + ph >= ny && py <= ny + nh) && this.proyectil == TipoProyectil.ALIADO){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean detectaColision(Jugador jugador){

        int px = this.getPosicionX();
        int py = this.getPosicionY();
        int pw = this.getAncho();
        int ph = this.getAlto();

        int nx = jugador.getPosicionX();
        int ny = jugador.getPosicionY();
        int nw = jugador.getAncho();
        int nh = jugador.getAlto();

        if((px + pw >= nx && px <= nx + nw && py + ph >= ny && py <= ny + nh) && this.proyectil == TipoProyectil.ENEMIGO ){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isEstaActivo() {
        return estaActivo;
    }

}
