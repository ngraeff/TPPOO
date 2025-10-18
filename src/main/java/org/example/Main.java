package org.example;

// IMPORTS
import javax.swing.*;

import org.example.controlador.ControladorJuego;
import org.example.enums.Dificultad;
import org.example.vista.VentanaPrincipal;

public class Main {
    public static void main(String[] args) {


        // Inicio interfaz grafica
        // SwingUtilities.invokeLater(() -> {
        //           try {
        //      javax.swing.UIManager.setLookAndFeel(
        //              javax.swing.UIManager.getSystemLookAndFeelClassName());  //Busca poner el LaF nativo
        //  } catch (Exception e) {
        //      System.err.println("No se pudo configurar el look and feel: " + e.getMessage());
        //  }
        //          // Crear y mostrar la ventana principal
        //  VentanaPrincipal ventana = new VentanaPrincipal();
        //  ventana.setVisible(true);

        System.out.println("Iniciando Jugador");

        ControladorJuego controladorJuego = new ControladorJuego();
        //SE ELIGE LA DIFICULTAD EN PANTALLA INICIAL
        //CUANDO SE DA CLICK AL INICIO DE PARTIDA SE CREA LA PARTIDA
        controladorJuego.crearJugador(Dificultad.FACIL);
        controladorJuego.crearPartida(Dificultad.FACIL,0,100,"Nicolas");






        //    });
    }
}