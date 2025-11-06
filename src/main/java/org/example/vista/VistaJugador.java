package org.example.vista;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VistaJugador {

    private Image imagenJugador;

    public VistaJugador() {
        imagenJugador = new ImageIcon(getClass().getResource("/img/jugador.png")).getImage();
    }

    public void dibujar(Graphics2D g2d, List<Integer> datosJugador) {
        if (datosJugador == null) return;
        int x = datosJugador.get(0);
        int y = datosJugador.get(1);
        int ancho = datosJugador.get(2);
        int alto = datosJugador.get(3);
        g2d.drawImage(imagenJugador, x, y, ancho, alto, null);
    }
}
