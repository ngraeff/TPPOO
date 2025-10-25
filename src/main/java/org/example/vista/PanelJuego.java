package org.example.vista;

import org.example.controlador.ControladorJuego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PanelJuego extends JPanel {
    private final ControladorJuego controlador;
    private final VistaJugador vistaJugador;
    private final VistaNaveInvasora vistaNaveInvasora;
    private final VistaProyectil vistaProyectil;
    private final Timer timer;

    // Subcomponentes
    private final JPanel panelInfo;
    private final JPanel panelControles;
    private final JLabel lblInfoJuego;
    private final JButton btnPausar;
    private final JButton btnTerminar;

    // Canvas de dibujo (subpanel interno)
    private final JPanel canvasJuego;

    public PanelJuego(ControladorJuego controlador) {
        this.controlador = controlador;
        this.vistaJugador = new VistaJugador();
        this.vistaNaveInvasora = new VistaNaveInvasora();
        this.vistaProyectil = new VistaProyectil();

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // ====== Panel superior ======
        panelInfo = new JPanel();
        panelInfo.setBackground(Color.DARK_GRAY);
        panelInfo.setPreferredSize(new Dimension(800, 50));
        lblInfoJuego = new JLabel("Puntuación: 0 | Vidas: " + this.controlador.getVidaInicial());
        lblInfoJuego.setForeground(Color.WHITE);
        lblInfoJuego.setFont(new Font("Arial", Font.BOLD, 16));
        panelInfo.add(lblInfoJuego);

        // ====== Canvas central ======
        canvasJuego = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                vistaJugador.dibujar(g2d, controlador.getDatosJugadorADibujar());
                vistaNaveInvasora.dibujar(g2d, controlador.getDatosNavesInvasoras());
                vistaProyectil.dibujar(g2d, controlador.getDatosProyectiles());
            }
        };
        canvasJuego.setBackground(Color.BLACK);
        canvasJuego.setPreferredSize(new Dimension(800, 600));

        canvasJuego.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                controlador.teclaPresionada(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                controlador.teclaSoltada(e.getKeyCode());
            }
        });

        // ====== Panel inferior ======
        panelControles = new JPanel();
        panelControles.setBackground(Color.DARK_GRAY);
        panelControles.setPreferredSize(new Dimension(800, 50));
        btnPausar = new JButton("PAUSAR");
        btnTerminar = new JButton("TERMINAR");
        panelControles.add(btnPausar);
        panelControles.add(btnTerminar);

        // ====== Ensamble ======
        add(panelInfo, BorderLayout.NORTH);
        add(canvasJuego, BorderLayout.CENTER);
        add(panelControles, BorderLayout.SOUTH);

        // ===== Foco del Teclado =====
        setFocusable(true);
        requestFocusInWindow();
        addHierarchyListener(e -> {
            if (isShowing()) requestFocusInWindow();
        });

        // ====== Timer del juego ======
        timer = new Timer(16, e -> {
            controlador.actualizarJuego(canvasJuego.getWidth());
            canvasJuego.repaint();
        });
        timer.start();


    }

    /** Para que el foco vaya directo al canvas al iniciar */
    public void enfocarCanvas() {
        canvasJuego.requestFocusInWindow();
    }
}
