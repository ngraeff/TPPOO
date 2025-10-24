package org.example;

// IMPORTS
import javax.swing.*;

import org.example.controlador.ControladorJuego;
import org.example.enums.Dificultad;
import org.example.vista.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {

        // Levanto controlador
        ControladorJuego controladorJuego = new ControladorJuego();



         // Inicio interfaz grafica
         SwingUtilities.invokeLater(() -> {
                     try {
                         javax.swing.UIManager.setLookAndFeel(
                                 javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());  //Busca poner el LaF nativo
                     } catch (Exception e) {
                         System.err.println("No se pudo configurar el look and feel: " + e.getMessage());
                     }
                     // Crear y mostrar la ventana principal
                     VentanaPrincipal ventana = new VentanaPrincipal(controladorJuego);
                     ventana.setVisible(true);


                     System.out.println("Iniciando juego");
                 });
        // ------------------------------






        //    });
    }
}