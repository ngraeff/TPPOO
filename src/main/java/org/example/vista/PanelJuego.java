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
    private final VistaJugador vistaJugador;
    private final VistaNaveInvasora vistaNaveInvasora;
    
    public PanelJuego(ControladorJuego controlador) {
        this.controlador = controlador;
        this.vistaJugador = new VistaJugador();
        this.vistaNaveInvasora = new VistaNaveInvasora();
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

        // Delegar dibujo a las vistas

        vistaJugador.dibujar(g2d, controlador.getDatosJugadorADibujar());
        vistaNaveInvasora.dibujar(g2d, controlador.getDatosNavesInvasoras());
    }
}