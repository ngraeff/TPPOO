package org.example.vista;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VistaNaveInvasora {

    private Image imagenNave;
    private final int anchoNave = 30;
    private final int altoNave = 20;

    public VistaNaveInvasora() {
        imagenNave = new ImageIcon(getClass().getResource("/img/naveInvasora.png")).getImage();
    }

    public void dibujar(Graphics2D g2d, List<int[]> datosNaves) {


        for (int[] datos : datosNaves) {
            int x = datos[0];
            int y = datos[1];
            g2d.drawImage(imagenNave,x, y, anchoNave, altoNave,null);
        }
    }

}