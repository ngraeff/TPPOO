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
    private final VentanaPrincipal ventanaPrincipal;

    private final JLabel lblInfoJuego;
    private final JPanel canvasJuego;

    // Botones
    private final JButton btnPausar;
    private final JButton btnReanudar;
    private final JButton btnTerminar;

    public PanelJuego(ControladorJuego controlador, VentanaPrincipal ventanaPrincipal) {
        this.controlador = controlador;
        this.vistaJugador = new VistaJugador();
        this.vistaNaveInvasora = new VistaNaveInvasora();
        this.vistaProyectil = new VistaProyectil();
        this.ventanaPrincipal = ventanaPrincipal;

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // ===== Panel superior =====
        JPanel panelInfo = new JPanel();
        panelInfo.setBackground(Color.DARK_GRAY);
        panelInfo.setPreferredSize(new Dimension(800, 50));

        lblInfoJuego = new JLabel("Puntuación: " + controlador.getPuntosPartida() +
                " | Vidas: " + controlador.getVidaInicial());
        lblInfoJuego.setForeground(Color.WHITE);
        lblInfoJuego.setFont(new Font("Arial", Font.BOLD, 16));
        panelInfo.add(lblInfoJuego);

        // ===== Canvas del juego =====
        canvasJuego = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                vistaJugador.dibujar(g2d, controlador.getDatosJugadorADibujar());
                vistaNaveInvasora.dibujar(g2d, controlador.getDatosNavesInvasoras());
                vistaProyectil.dibujar(g2d, controlador.getDatosProyectiles());
                vistaProyectil.dibujar(g2d, controlador.getDatosProyectilesEnemigos());
            }
        };
        canvasJuego.setBackground(Color.BLACK);
        canvasJuego.setPreferredSize(new Dimension(800, 600));

        // Eventos de teclado
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

        // ===== Panel inferior =====
        JPanel panelControles = new JPanel();
        panelControles.setBackground(Color.DARK_GRAY);
        panelControles.setPreferredSize(new Dimension(800, 50));

        btnPausar = new JButton("PAUSAR");
        btnReanudar = new JButton("REANUDAR");
        btnReanudar.setVisible(false); // no visible al inicio
        btnTerminar = new JButton("TERMINAR");

        panelControles.add(btnPausar);
        panelControles.add(btnReanudar);
        panelControles.add(btnTerminar);

        // Eventos de botones
        btnPausar.addActionListener(e -> pausarJuego());
        btnReanudar.addActionListener(e -> reanudarJuego());
        btnTerminar.addActionListener(e -> terminarJuego());

        // ===== Ensamble =====
        add(panelInfo, BorderLayout.NORTH);
        add(canvasJuego, BorderLayout.CENTER);
        add(panelControles, BorderLayout.SOUTH);

        // ===== Foco para teclado =====
        setFocusable(true);
        addHierarchyListener(e -> {
            if (isShowing()) enfocarCanvas();
        });
        btnPausar.setFocusable(false);
        btnReanudar.setFocusable(false);
        btnTerminar.setFocusable(false);
    }

    private void pausarJuego() {
        controlador.pausarJuego();
        btnPausar.setVisible(false);
        btnReanudar.setVisible(true);
    }

    public void terminarJuego(){
        controlador.terminarJuego();
        ventanaPrincipal.mostrarMenuPrincipal();
    }

    private void reanudarJuego() {
        controlador.reanudarJuego();
        btnReanudar.setVisible(false);
        btnPausar.setVisible(true);
        enfocarCanvas(); // para que las teclas vuelvan a funcionar
    }

    public void enfocarCanvas() {
        canvasJuego.requestFocusInWindow();
    }

    public void repaintCanvas() {
        canvasJuego.repaint();
    }

    public void actualizarInfo(int puntos, int vidas) {
        lblInfoJuego.setText("Puntuación: " + puntos + " | Vidas: " + vidas);
    }
}
