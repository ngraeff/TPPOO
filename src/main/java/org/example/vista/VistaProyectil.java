package org.example.vista;

import java.awt.*;
import java.util.List;

public class VistaProyectil {

    public void dibujar(Graphics2D g2d, List<float[]> proyectiles) {

        g2d.setColor(Color.YELLOW);
        for (float[] p : proyectiles) {

            g2d.fillRect(Math.round(p[0]), Math.round(p[1]), Math.round(p[2]), Math.round(p[3]));
        }
    }
}