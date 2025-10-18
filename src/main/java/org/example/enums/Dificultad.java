package org.example.enums;

public enum Dificultad {
    FACIL(5,0,1),
    MEDIO(3,0,2),
    DIFICIL(1,0,3);

    private final int vidaInicialJugador;
    private final int puntosInicialJugador;
    private final int velocidadNavesEnemigas;

    Dificultad(int vidaInicialJugador, int puntosInicialJugador, int velocidadNavesEnemigas) {
        this.vidaInicialJugador = vidaInicialJugador;
        this.puntosInicialJugador = puntosInicialJugador;
        this.velocidadNavesEnemigas = velocidadNavesEnemigas;
    }

    public int getVidaInicialJugador() {
        return vidaInicialJugador;
    }
    public int getPuntosInicialJugador() {
        return puntosInicialJugador;
    }
    public int getVelocidadNavesEnemigas() {
        return velocidadNavesEnemigas;
    }
}
