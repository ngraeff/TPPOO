package org.example.enums;

public enum Dificultad {
    SIN_INFORMAR(0,0,0f,0f,0f,0f,0,0,0,0,0f),
    FACIL(5,0,5f,1f,400f,400f,300,30,30,100, 0.5f),
    MEDIO(3,0,7f,1.3f,400f,400f,300,30,30,200,0.5f),
    DIFICIL(1,0,7f,1.5f,400f,400f,300,30,30,300,0.5f);

    private final int vidaInicialJugador;
    private final int puntosInicialJugador;
    private final float velocidadDelJugador;
    private float velocidadNavesEnemigas;
    private final float posicionJugadorX;
    private final float posicionJugadorY;
    private final int cooldownJugador;
    private final int anchoJugador;
    private final int altoJugador;
    private final int creditosParaIniciar;
    private final float incrementoVelocidadNavesEnemigas;

    Dificultad(int vidaInicialJugador, int puntosInicialJugador,float velocidadDelJugador, float velocidadNavesEnemigas,float posicionJugadorX, float posicionJugadorY,int cooldownDisparo,int anchoJugador,int altoJugador,int creditosParaIniciar, float incrementoVelocidadNavesEnemigas) {
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

    public float getVelocidadDelJugador() {return velocidadDelJugador;}

    public float getVelocidadNavesEnemigas() {
        return velocidadNavesEnemigas;
    }

    public float getPosicionJugadorX() {
        return posicionJugadorX;
    }

    public float getPosicionJugadorY() {
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

    public float getIncrementoVelocidadNavesEnemigas() {
        return incrementoVelocidadNavesEnemigas;
    }
}
