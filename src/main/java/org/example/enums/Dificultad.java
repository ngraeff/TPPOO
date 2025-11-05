package org.example.enums;

public enum Dificultad {
    SIN_INFORMAR(0,0,0,0,0,0,0,0,0,0,0),
    FACIL(5,0,5,1,400,400,300,30,30,100, 1),
    MEDIO(3,0,7,8,400,400,300,30,30,200,2),
    DIFICIL(1,0,7,15,400,400,300,30,30,300,3);

    private final int vidaInicialJugador;
    private final int puntosInicialJugador;
    private int velocidadDelJugador;
    private int velocidadNavesEnemigas;
    private final int posicionJugadorX;
    private final int posicionJugadorY;
    private final int cooldownJugador;
    private final int anchoJugador;
    private final int altoJugador;
    private final int creditosParaIniciar;
    private final int incrementoVelocidadNavesEnemigas;

    Dificultad(int vidaInicialJugador, int puntosInicialJugador,int velocidadDelJugador, int velocidadNavesEnemigas,int posicionJugadorX, int posicionJugadorY,int cooldownDisparo,int anchoJugador,int altoJugador,int creditosParaIniciar, int incrementoVelocidadNavesEnemigas) {
        this.vidaInicialJugador = vidaInicialJugador;
        this.puntosInicialJugador = puntosInicialJugador;
        this.velocidadDelJugador = velocidadDelJugador;
        this.velocidadNavesEnemigas = velocidadNavesEnemigas;
        this.posicionJugadorX = posicionJugadorX;
        this.posicionJugadorY = posicionJugadorY;
        this.cooldownJugador = cooldownDisparo;
        this.anchoJugador = anchoJugador;
        this.altoJugador = altoJugador;
        this.creditosParaIniciar = creditosParaIniciar;
        this.incrementoVelocidadNavesEnemigas = incrementoVelocidadNavesEnemigas;
    }

    public int getVidaInicialJugador() {
        return vidaInicialJugador;
    }

    public int getPuntosInicialJugador() {
        return puntosInicialJugador;
    }

    public int getVelocidadDelJugador() {return velocidadDelJugador;}

    public int getVelocidadNavesEnemigas() {
        return velocidadNavesEnemigas;
    }

    public int getPosicionJugadorX() {
        return posicionJugadorX;
    }

    public int getPosicionJugadorY() {
        return posicionJugadorY;
    }

    public int getCooldownJugador() {
        return cooldownJugador;
    }

    public int getAnchoJugador() {
        return anchoJugador;
    }

    public int getAltoJugador() {
        return altoJugador;
    }

    public int getCreditosParaIniciar() {
        return creditosParaIniciar;
    }

    public int getIncrementoVelocidadNavesEnemigas() {
        return incrementoVelocidadNavesEnemigas;
    }
}
