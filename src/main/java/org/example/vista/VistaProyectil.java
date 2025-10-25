package org.example.vista;

import org.example.modelo.Proyectil;
import java.awt.*;
import java.util.List;

public class VistaProyectil {
    private Color colorAliado = Color.YELLOW;

    public void dibujar(Graphics2D g2d, List<Proyectil> proyectiles) {
        g2d.setColor(colorAliado);
        for (Proyectil p : proyectiles) {
            if (p.isEstaActivo()) {
                g2d.fillRect(1, 1, 3, 8);
            }
        }
    }
}