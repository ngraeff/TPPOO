package org.example.vista;

import org.example.controlador.ControladorJuego;
import org.example.enums.Dificultad;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PanelDificultad extends JPanel {

    private ControladorJuego controlador;
    private VentanaPrincipal ventanaPrincipal;

    private JButton btnFacil;
    private JButton btnMedio;
    private JButton btnDificil;
    private JButton btnVolverMenu;

    public PanelDificultad(ControladorJuego controlador, VentanaPrincipal ventanaPrincipal) {
        this.controlador = controlador;
        this.ventanaPrincipal = ventanaPrincipal;
        inicializar();
    }

    private void inicializar() {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JLabel lblTitulo = new JLabel("SELECCIONAR DIFICULTAD", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(Color.GREEN);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        JPanel panelBotones = new JPanel(new GridLayout(4, 1, 20, 20));
        panelBotones.setBackground(Color.BLACK);
        panelBotones.setBorder(BorderFactory.createEmptyBorder(50, 200, 50, 200));

        btnFacil = new JButton("FÁCIL");
        btnMedio = new JButton("MEDIO");
        btnDificil = new JButton("DIFÍCIL");
        btnVolverMenu = new JButton("VOLVER");

        configurarBoton(btnFacil);
        configurarBoton(btnMedio);
        configurarBoton(btnDificil);
        configurarBoton(btnVolverMenu);

        btnFacil.addActionListener(e -> seleccionarDificultad(Dificultad.FACIL));
        btnMedio.addActionListener(e -> seleccionarDificultad(Dificultad.MEDIO));
        btnDificil.addActionListener(e -> seleccionarDificultad(Dificultad.DIFICIL));
        btnVolverMenu.addActionListener(e -> ventanaPrincipal.mostrarMenuPrincipal());

        panelBotones.add(btnFacil);
        panelBotones.add(btnMedio);
        panelBotones.add(btnDificil);
        panelBotones.add(btnVolverMenu);

        add(lblTitulo, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
    }

    private void configurarBoton(JButton boton) {
        boton.setFont(new Font("Arial", Font.BOLD, 18));
        boton.setForeground(Color.BLACK);
        boton.setBackground(Color.WHITE);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(200, 50));

        boton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                boton.setBackground(Color.CYAN);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boton.setBackground(Color.WHITE);
            }
        });
    }

    private void seleccionarDificultad(Dificultad dificultad) {
        controlador.seleccionarDificultad(dificultad);
        JOptionPane.showMessageDialog(this,
                "Dificultad seleccionada: " + dificultad,
                "Configuración",
                JOptionPane.INFORMATION_MESSAGE);
        ventanaPrincipal.mostrarMenuPrincipal();
    }
}
