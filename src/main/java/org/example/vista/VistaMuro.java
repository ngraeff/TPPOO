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
            int vida = datos[4];

            //3.0 por la vida. 0.2 es el minimo.
            float alpha = Math.max(0.2f, vida / 3.0f);

            Composite originalComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

            g2d.drawImage(imagenMuro, x, y, ancho, alto, null);

            g2d.setComposite(originalComposite);
        }
    }
}
