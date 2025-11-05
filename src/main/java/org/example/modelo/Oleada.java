package org.example.modelo;

import org.example.enums.Dificultad;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Random;


public class Oleada {
    private List<NaveInvasora> naveInvasoras;
    private int velocidad;
    private long ultimoDisparo = 0;
    private final int coolDown = 700;

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
    public static Oleada crearOleadaBasica(Dificultad dificultad, int nivel) {
        List<NaveInvasora> naves = new ArrayList<>();
        int velocidadNavesInvasoras = dificultad.getVelocidadNavesEnemigas() + dificultad.getIncrementoVelocidadNavesEnemigas()*nivel;

        // Crear 5 filas x 8 columnas de invasores
        for (int fila = 0; fila < 5; fila++) {
            for (int col = 0; col < 8; col++) {
                int x = 50 + col * 60;
                int y = 50 + fila * 40;
                NaveInvasora nave = new NaveInvasora(x, y, velocidadNavesInvasoras, true);
                naves.add(nave);
            }
        }

        return new Oleada(naves, velocidadNavesInvasoras + dificultad.getIncrementoVelocidadNavesEnemigas()*nivel);
    }

    /**
     * Detiene el movimiento de todas las naves invasoras.
     * Se llama cuando se alcanza el límite crítico.
     * Como el método detener() fue eliminado, esto simplemente establece la velocidad a 0.
     */
    public void detenerMovimiento() {
        for (NaveInvasora nave : naveInvasoras) {
            if (nave.isEstaViva()) {
                // Establecer velocidad a 0 para detener el movimiento
                nave.setVelocidadDeMovimiento(0);
            }
        }
    }

    // METODOS MOVIMIENTO
    public void actualizar(int anchoPanel) {
        if (naveInvasoras == null || naveInvasoras.isEmpty()) {
            return;
        }

        dispararProyectilEnemigo();
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

    public boolean hayNavesVivas() {
        return !getNavesVivas().isEmpty();
    }

    public void dispararProyectilEnemigo() {
        long ahora = System.currentTimeMillis();
        if (ahora - ultimoDisparo < coolDown) {
            return;
        }

        List<NaveInvasora> navesVivas = getNavesVivas();
        if (navesVivas.isEmpty()) return;


        Random random = new Random();
        NaveInvasora nave = navesVivas.get(random.nextInt(navesVivas.size()));
        nave.disparar();


        ultimoDisparo = ahora;
    }
}
