package org.example.modelo;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Ranking {
    private List<String> partidas;

    public Ranking() {
        this.partidas = new ArrayList<>();
    }

    public void agregarPartida(String nombreJugador, int puntosJugador) {
        partidas.add(nombreJugador + " - " + puntosJugador);
        ordenarPartidas();
        // Mantener solo el top 10 sin reasignar a una subList
        while (partidas.size() > 10) {
            partidas.remove(partidas.size() - 1);
        }
    }

    public void ordenarPartidas() {
        this.partidas.sort((a, b) -> Integer.compare(
                Integer.parseInt(b.split(" - ")[1]),
                Integer.parseInt(a.split(" - ")[1])
        ));
    }

    public List<String> getPartidas() {
        int toIndex = Math.min(partidas.size(), 10);
        return Collections.unmodifiableList(new ArrayList<>(partidas.subList(0, toIndex)));
    }
    
    public void limpiarRanking(){
         partidas.clear();
    }
    
}
