package org.example.modelo;

public class NaveInvasora {
    private int posicionX;
    private int posicionY;
    private int velocidadDeMovimiento;
    private boolean estaViva;
    public final int ancho = 30;
    public final int alto = 20;


    public NaveInvasora(int posicionX, int posicionY, int velocidadDeMovimiento, boolean estaViva) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.velocidadDeMovimiento = velocidadDeMovimiento;
        this.estaViva = estaViva;
    }

    // METODOS MOVIMIENTO
public void mover() {
    this.posicionX += this.velocidadDeMovimiento;
}

public void cambiarDireccion() {
    this.velocidadDeMovimiento *= -1;
    this.posicionY += 20; // Bajar una fila
}

// Getters para la vista
public int getPosicionX() { return posicionX; }
public int getPosicionY() { return posicionY; }
public boolean isEstaViva() { return estaViva; }
public void setEstaViva(boolean viva) { this.estaViva = viva; }
public int getAncho() { return ancho; }
public int getAlto() { return alto; }
public void setPosicionX(int posicionX) { this.posicionX = posicionX; }
}
