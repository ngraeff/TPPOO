package org.example.modelo;

import org.example.enums.TipoProyectil;

public class Proyectil {

    private float posicionX;
    private float posicionY;
    private TipoProyectil proyectil;
    private boolean estaActivo;
    private float velocidad;
    private final int ancho =3;
    private final int alto = 8;

    public Proyectil(float posicionX, float posicionY, TipoProyectil proyectil, boolean estaActivo, float velocidad) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.proyectil = proyectil;
        this.estaActivo = true;
        this.velocidad = velocidad;
    }

    public float getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(float posicionX) {
        this.posicionX = posicionX;
    }

    public float getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(float posicionY) {
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

    public float getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(float velocidad) {
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
        float px = this.getPosicionX();
        float py = this.getPosicionY();
        float pw = this.getAncho();
        float ph = this.getAlto();

        float nx = nave.getPosicionX();
        float ny = nave.getPosicionY();
        float nw = nave.getAncho();
        float nh = nave.getAlto();

    if((px + pw >= nx && px <= nx + nw && py + ph >= ny && py <= ny + nh) && this.proyectil == TipoProyectil.ALIADO){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean detectaColision(Jugador jugador){

        float px = this.getPosicionX();
        float py = this.getPosicionY();
        float pw = this.getAncho();
        float ph = this.getAlto();

        float nx = jugador.getPosicionX();
        float ny = jugador.getPosicionY();
        float nw = jugador.getAncho();
        float nh = jugador.getAlto();

        if((px + pw >= nx && px <= nx + nw && py + ph >= ny && py <= ny + nh) && this.proyectil == TipoProyectil.ENEMIGO ){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean detectaColision(MuroDeEnergia muro){
        float px = this.getPosicionX();
        float py = this.getPosicionY();
        float pw = this.getAncho();
        float ph = this.getAlto();

        float nx = muro.getPosicionX();
        float ny = muro.getPosicionY();
        float nw = muro.getAncho();
        float nh = muro.getAlto();
        if (proyectil == TipoProyectil.ENEMIGO){
            if(px + pw >= nx && px <= nx + nw && py + ph -30 >= ny && py <= ny + nh){
                return true;
            }
        }else{
            if(px + pw >= nx && px <= nx + nw && py + ph  >= ny && py <= ny + nh){
                return true;
            }
        }

        return false;
    }

    public boolean isEstaActivo() {
        return estaActivo;
    }

}
