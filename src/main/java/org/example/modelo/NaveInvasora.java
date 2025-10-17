package org.example.modelo;

public class NaveInvasora {
    private int posicionX;
    private int posicionY;
    private int velocidadDeMovimiento;
    private boolean estaViva;

    public NaveInvasora(int posicionX, int posicionY, int velocidadDeMovimiento, boolean estaViva) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.velocidadDeMovimiento = velocidadDeMovimiento;
        this.estaViva = estaViva;
    }
}
