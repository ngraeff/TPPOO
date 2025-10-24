package org.example.vista;

import org.example.controlador.ControladorJuego;
import org.example.modelo.Jugador;
import org.example.modelo.NaveInvasora;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class PanelJuego extends JPanel {
    private ControladorJuego controlador;
    
    public PanelJuego(ControladorJuego controlador) {
        this.controlador = controlador;
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        
        // Solo manejar eventos de teclado
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // Delegar al controlador
                controlador.manejarTecla(e.getKeyCode());
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // El panel se encarga de dibujar, pero pide datos al controlador
        dibujarJugador(g2d);
        dibujarInvasores(g2d);
    }
    
    private void dibujarJugador(Graphics2D g2d) {
        Jugador jugador = controlador.getJugador();
        if(jugador != null) {
            g2d.setColor(Color.GREEN);
            g2d.fillRect(jugador.getPosicionX(), jugador.getPosicionY(), 
                          jugador.getAncho(), jugador.getAlto());
        }
    }
    
    private void dibujarInvasores(Graphics2D g2d) {
        List<NaveInvasora> naves = controlador.getNavesVivas();
        if(naves != null) {
            g2d.setColor(Color.RED);
            for(NaveInvasora nave : naves) {
                g2d.fillRect(nave.getPosicionX(), nave.getPosicionY(), 30, 20);
            }
        }
    }
}