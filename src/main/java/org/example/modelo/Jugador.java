package org.example.modelo;

public class Jugador {

    private String nombre;
    private int puntaje;
    private int vida;
    private int posicionX;
    private int posicionY;
    private int cooldownDisparo;

    public Jugador(String nombre, int puntaje, int vida, int posicionX, int posicionY, int cooldownDisparo) {
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.vida = vida;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.cooldownDisparo = cooldownDisparo;
    }
}
