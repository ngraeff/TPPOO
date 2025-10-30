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
    public void actualizar(int anchoPanel) {
        if (naveInvasoras == null || naveInvasoras.isEmpty()) {
            return;
        }

        moverTodas();

        boolean tocoBorde = false;
        for (NaveInvasora nave : getNavesVivas()) {
            int x = nave.getPosicionX();
            if (x <= 0 || x + nave.getAncho() >= anchoPanel) {
                tocoBorde = true;
                break;
            }
        }

        if (tocoBorde) {
            cambiarDireccionTodas();
        }

        // Clamp opcional para no salir del panel
        for (NaveInvasora nave : getNavesVivas()) {
            if (nave.getPosicionX() < 0) {
                nave.setPosicionX(0);
            } else if (nave.getPosicionX() + nave.getAncho() > anchoPanel) {
                nave.setPosicionX(anchoPanel - nave.getAncho());
            }
        }
    }

    public void moverTodas() {
        for (NaveInvasora nave : naveInvasoras) {
            if (nave.isEstaViva()) {
                nave.mover();
            }
        }
    }

    public void cambiarDireccionTodas() {
        for (NaveInvasora nave : naveInvasoras) {
            if (nave.isEstaViva()) {
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
