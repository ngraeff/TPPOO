package org.example.vista;

import javax.swing.*;
import java.awt.*;
public class VistaJugador {

    private Image imagenJugador;

    public VistaJugador() {
        imagenJugador = new ImageIcon(getClass().getResource("/img/jugador.png")).getImage();
    }

    public void dibujar(Graphics2D g2d, float[] datosJugador) {
        if (datosJugador == null) return;
        int x = Math.round(datosJugador[0]);
        int y = Math.round(datosJugador[1]);
        int ancho = Math.round(datosJugador[2]);
        int alto = Math.round(datosJugador[3]);
        g2d.drawImage(imagenJugador, x, y, ancho, alto, null);
    }
}
