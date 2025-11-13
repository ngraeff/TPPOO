package org.example.vista;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VistaMuro {

    public void dibujar(Graphics2D g2d, List<float[]> datosMuro) {
        if (datosMuro == null) return;

        for (float[] datos : datosMuro) {
            int x = Math.round(datos[0]);
            int y = Math.round(datos[1]);
            int ancho = Math.round(datos[2]);
            int alto = Math.round(datos[3]);
            float vida = datos[4];

            // Transparencia según la vida (mínimo 0.2)
            float alpha = Math.max(0.2f, vida / 3.0f);

            // Guardar el composite original
            Composite originalComposite = g2d.getComposite();

            // Aplicar transparencia
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

            // Dibujar muro verde
            g2d.setColor(Color.GREEN);
            g2d.fillRect(x, y, ancho, alto);

            // Restaurar el composite original
            g2d.setComposite(originalComposite);
        }
    }
}

