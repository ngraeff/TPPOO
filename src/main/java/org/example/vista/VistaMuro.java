package org.example.vista;
import javax.swing.*;
import java.awt.*;
import java.util.List;


public class VistaMuro {
    private Image imagenMuro;
    public VistaMuro() {
        imagenMuro = new ImageIcon(getClass().getResource("/img/muro.png")).getImage();
    }

    public void dibujar(Graphics2D g2d, List<Integer> datosMuro) {
        if (datosMuro == null) return;
        int x = datosMuro.get(0);
        int y = datosMuro.get(1);
        int ancho = datosMuro.get(2);
        int alto = datosMuro.get(3);
        g2d.drawImage(imagenMuro, x, y, ancho, alto, null);
    }
}
