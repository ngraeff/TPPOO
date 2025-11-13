package org.example.vista;

import org.example.controlador.ControladorJuego;
import org.example.enums.EstadoDeJuego;

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
    private final VistaMuro vistaMuro;

    private final JLabel lblInfoJuego;
    private final JPanel canvasJuego;

    // Botones
    private final JButton btnPausar;
    private final JButton btnReanudar;
    private final JButton btnTerminar;

    private final int ancho = 800;
    private Timer timer;

    public PanelJuego(ControladorJuego controlador, VentanaPrincipal ventanaPrincipal) {
        this.controlador = controlador;
        this.vistaJugador = new VistaJugador();
        this.vistaNaveInvasora = new VistaNaveInvasora();
        this.vistaProyectil = new VistaProyectil();
        this.ventanaPrincipal = ventanaPrincipal;
        this.vistaMuro = new VistaMuro();
        this.timer = null;

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // ===== Panel superior =====
        JPanel panelInfo = new JPanel();
        panelInfo.setBackground(Color.DARK_GRAY);
        panelInfo.setPreferredSize(new Dimension(800, 50));

        lblInfoJuego = new JLabel("Puntuación: " + controlador.getPuntosPartida() +
                " | Vidas: " + controlador.getVidaInicial() + " | Nivel: " + controlador.getNivelPartida());
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
                vistaMuro.dibujar(g2d,controlador.getDatosMuro());
                vistaProyectil.dibujar(g2d, controlador.getDatosProyectiles());
                vistaProyectil.dibujar(g2d, controlador.getDatosProyectilesEnemigos());
            }
        };
        canvasJuego.setBackground(Color.BLACK);
        canvasJuego.setPreferredSize(new Dimension(800, 600));
        iniciarTimer();

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

    private void pausarJuegoVista() {
        btnPausar.setVisible(false);
        btnReanudar.setVisible(true);
    }

    public void terminarJuego(){
        if (timer != null) timer.stop();
        switch (controlador.getEstadoDeJuego()) {
            case GAME_OVER_VIDA -> ventanaPrincipal.mostrarMensaje(
                    "¡Te has quedado sin vidas! \nEl juego ha terminado\nPuntaje: " + controlador.getPuntosJugador(),
                    "GAME OVER"
            );
            case GAME_OVER_LIMITE -> ventanaPrincipal.mostrarMensaje(
                    "¡Las naves invasoras han alcanzado tu posición!\n\n" +
                            "Puntuación final: " + controlador.getPuntosJugador() + "\n" +
                            "Vidas restantes: " + controlador.getVidasJugador(),
                    "GAME OVER"
            );
            default -> ventanaPrincipal.mostrarMensaje(
                    "Finalizó la partida\n\n" +
                            "Puntuación final: " + controlador.getPuntosJugador(),
                    "Partida Terminada"
            );
        }

        String  nombre = JOptionPane.showInputDialog(
                null,
                "Ingrese su nombre para el ranking:",
                "Guardar Puntaje",
                JOptionPane.PLAIN_MESSAGE
        );
        int contador = 1;
        while ((nombre == null || nombre.trim().isEmpty()) && contador < 3){
            contador++;
            nombre = JOptionPane.showInputDialog(
                    null,
                    "Por favor, ingrese su nombre para el ranking:",
                    "Guardar Puntaje",
                    JOptionPane.PLAIN_MESSAGE
            );}

        while (nombre == null || nombre.trim().isEmpty()){
            contador++;
            nombre = JOptionPane.showInputDialog(
                    null,
                    "Dale loko, ayudame. Ingresa tu nombre para el ranking :) :",
                    "Guardar Puntaje",
                    JOptionPane.PLAIN_MESSAGE
            );}

        ventanaPrincipal.mostrarMensaje("Puntaje guardado exitosamente.", "Ranking");

        ventanaPrincipal.mostrarMensaje("Se devolvieron " + controlador.getCreditosJugador() +" creditos.","Devolucion de Creditos");
        controlador.terminarJuego(nombre);
        ventanaPrincipal.mostrarMenuPrincipal();
    }

    private void reanudarJuegoVista() {

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

    public void actualizarInfo(int puntos, int vidas, int nivel) {
        lblInfoJuego.setText("Puntuación: " + puntos + " | Vidas: " + vidas + " | Nivel: " + nivel);
    }

    private void iniciarTimer() {
        if (timer != null && timer.isRunning()) timer.stop();

        timer = new Timer(16, e -> {
            controlador.actualizarJuego(ancho);
            actualizarSiTermino();
            if (controlador.tieneJugador()){
                actualizarInfo(controlador.getPuntosJugador(),controlador.getVidasJugador(),controlador.getNivelPartida());
            }else{
                actualizarInfo(0,0,0);
            }
        repaintCanvas();

        });
        timer.start();
    }


    public void actualizarSiTermino() {
        //Verifica si partida esta en GAMEOVER
        if (controlador.getEstadoDeJuego() == EstadoDeJuego.GAME_OVER_LIMITE || controlador.getEstadoDeJuego() == EstadoDeJuego.GAME_OVER_VIDA) {
            terminarJuego();
        }
    }
    public void pausarJuego() {
        if (timer != null) timer.stop();
        pausarJuegoVista();
    }

    public void reanudarJuego() {
        if (timer != null) timer.start();
        reanudarJuegoVista();
    }



}
