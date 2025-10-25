package org.example.vista;

import org.example.modelo.Proyectil;
import java.awt.*;
import java.util.List;

public class VistaProyectil {
    private Color colorAliado = Color.YELLOW;

    public void dibujar(Graphics2D g2d, List<int[]> proyectiles) {
        g2d.setColor(Color.YELLOW);
        for (int[] p : proyectiles) {
            g2d.fillRect(p[0], p[1], p[2], p[3]);
        }
    }
}