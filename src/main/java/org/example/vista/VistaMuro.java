package org.example.vista;
import javax.swing.*;
import java.awt.*;
import java.util.List;


public class VistaMuro {
    private Image imagenMuro;
    public VistaMuro() {

        imagenMuro = new ImageIcon(getClass().getResource("/img/muro.png")).getImage();
    }

    public void dibujar(Graphics2D g2d, List<int[]> datosMuro) {
        if (datosMuro == null) return;
        for (int[] datos : datosMuro) {
            int x = datos[0];
            int y = datos[1];
            int ancho = datos[2];
            int alto = datos[3];
            g2d.drawImage(imagenMuro,x, y, ancho, alto,null);
        }
    }
}
