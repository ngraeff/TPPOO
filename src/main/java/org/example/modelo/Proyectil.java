package org.example.modelo;

import org.example.enums.TipoProyectil;

public class Proyectil {
    private int posicionX;
    private int posicionY;
    private TipoProyectil origen;

    public Proyectil(int posicionX, int posicionY, TipoProyectil origen) {
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.origen = origen;
    }
}
