package org.example.modelo;

import java.util.List;
import java.util.stream.Collectors;

public class Oleada {
    private List<NaveInvasora> naveInvasoras;
    private int velocidad;


    public Oleada(List<NaveInvasora> naveInvasoras, int velocidad) {
        this.naveInvasoras = naveInvasoras;
        this.velocidad = velocidad;
    }

    // METODOS MOVIMIENTO
   
public void moverTodas() {
    for(NaveInvasora nave : naveInvasoras) {
        if(nave.isEstaViva()) {
            nave.mover();
        }
    }
}

public void cambiarDireccionTodas() {
    for(NaveInvasora nave : naveInvasoras) {
        if(nave.isEstaViva()) {
            nave.cambiarDireccion();
        }
    }
}

public List<NaveInvasora> getNavesVivas() {
    return naveInvasoras.stream()
        .filter(NaveInvasora::isEstaViva)
        .collect(Collectors.toList());
}



}
