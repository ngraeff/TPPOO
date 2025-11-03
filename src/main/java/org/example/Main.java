package org.example;

// IMPORTS
import javax.swing.*;

import org.example.controlador.ControladorJuego;
import org.example.vista.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {

        // Levanto controlador
        ControladorJuego controladorJuego = new ControladorJuego();
        
        // Agregar partidas de prueba al ranking
        controladorJuego.agregarPartidaAlRanking("Ana", 2500);
        controladorJuego.agregarPartidaAlRanking("Carlos", 1800);
        controladorJuego.agregarPartidaAlRanking("María", 3200);
        controladorJuego.agregarPartidaAlRanking("Luis", 1200);
        controladorJuego.agregarPartidaAlRanking("Sofia", 2100);



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
                     controladorJuego.setVista(ventana);
                     ventana.setVisible(true);


                     System.out.println("Iniciando juego");
                 });
        // ------------------------------

        //    });
    }
}