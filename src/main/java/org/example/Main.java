package org.example;

// IMPORTS
import javax.swing.*;
import org.example.vista.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {
        // Inicio interfaz grafica
        SwingUtilities.invokeLater(() -> {
            try {
                javax.swing.UIManager.setLookAndFeel(
                        javax.swing.UIManager.getSystemLookAndFeelClassName());  //Busca poner el LaF nativo
            } catch (Exception e) {
                System.err.println("No se pudo configurar el look and feel: " + e.getMessage());
            }

            // Crear y mostrar la ventana principal
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);

            System.out.println("Juego Iniciado");
        });
    }
}