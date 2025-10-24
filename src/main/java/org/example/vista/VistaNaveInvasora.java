package org.example.vista;

import org.example.modelo.NaveInvasora;
import java.awt.*;
import java.util.List;

public class VistaNaveInvasora {

    private Color colorNaves = Color.RED;
    private final int anchoNave = 30;
    private final int altoNave = 20;

    public void dibujar(Graphics2D g2d, List<int[]> datosNaves) {
        g2d.setColor(Color.RED);
        for (int[] datos : datosNaves) {
            int x = datos[0];
            int y = datos[1];
            g2d.fillRect(x, y, anchoNave, altoNave);
        }
    }

    // Opcional: si querés cambiar el color de las naves dinámicamente
    public void setColorNaves(Color colorNaves) {
        this.colorNaves = colorNaves;
    }
}