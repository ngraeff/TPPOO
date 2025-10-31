package org.example.modelo;

import org.example.enums.Dificultad;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Oleada {
    private List<NaveInvasora> naveInvasoras;
    private int velocidad;

    public Oleada(List<NaveInvasora> naveInvasoras, int velocidad) {
        this.naveInvasoras = naveInvasoras;
        this.velocidad = velocidad;
    }

    /**
     * Crea una oleada básica de naves invasoras.
     * GRASP: Creator - Oleada sabe cómo crear el conjunto de naves que contiene.
     * @param dificultad Dificultad del juego que determina la velocidad de las naves.
     * @return Una oleada con 5 filas x 8 columnas de invasores.
     */
    public static Oleada crearOleadaBasica(Dificultad dificultad) {
        List<NaveInvasora> naves = new ArrayList<>();
        int velocidadNavesInvasoras = dificultad.getVelocidadNavesEnemigas();

        // Crear 5 filas x 8 columnas de invasores
        for (int fila = 0; fila < 5; fila++) {
            for (int col = 0; col < 8; col++) {
                int x = 50 + col * 60;
                int y = 50 + fila * 40;
                NaveInvasora nave = new NaveInvasora(x, y, velocidadNavesInvasoras, true);
                naves.add(nave);
            }
        }

        return new Oleada(naves, velocidadNavesInvasoras);
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
