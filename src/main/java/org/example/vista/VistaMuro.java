package org.example.vista;
import javax.swing.*;
import java.awt.*;
import java.util.List;


public class VistaMuro {
    private Image imagenMuro;
    public VistaMuro() {

        imagenMuro = new ImageIcon(getClass().getResource("/img/muro.png")).getImage();
    }

    public void dibujar(Graphics2D g2d, List<float[]> datosMuro) {
        if (datosMuro == null) return;
        for (float[] datos : datosMuro) {
            int x = Math.round(datos[0]);
            int y = Math.round(datos[1]);
            int ancho = Math.round(datos[2]);
            int alto = Math.round(datos[3]);
            float vida = datos[4];

            //3.0 por la vida. 0.2 es el minimo.
            float alpha = Math.max(0.2f, vida / 3.0f);

            Composite originalComposite = g2d.getComposite();
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

            g2d.drawImage(imagenMuro, x, y, ancho, alto, null);

            g2d.setComposite(originalComposite);
        }
    }
}
