package org.example.modelo;

import java.util.List;

public class Oleada {
    private List<NaveInvasora> naveInvasoras;

    public Oleada(List<NaveInvasora> naveInvasoras, int velocidad) {
        this.naveInvasoras = naveInvasoras;
        this.velocidad = velocidad;
    }

    private int velocidad;

}
