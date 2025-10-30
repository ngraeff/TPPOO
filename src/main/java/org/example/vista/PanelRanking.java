package org.example.vista;

import org.example.controlador.ControladorJuego;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PanelRanking extends JPanel {
    private ControladorJuego controlador;
    private VentanaPrincipal ventanaPrincipal;
    private JList<String> lstRanking;
    private JButton btnVolverMenu;
    private JLabel lblTitulo;

    public PanelRanking(ControladorJuego controlador, VentanaPrincipal ventanaPrincipal) {
        this.controlador = controlador;
        this.ventanaPrincipal = ventanaPrincipal;
        inicializar();
    }

    private void inicializar() {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
        
        // Crear título
        lblTitulo = new JLabel("RANKING DE PUNTUACIONES", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 36));
        lblTitulo.setForeground(Color.GREEN);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        
        // Crear lista del ranking
        crearListaRanking();
        
        // Crear botón volver
        btnVolverMenu = new JButton("VOLVER AL MENÚ");
        configurarBoton(btnVolverMenu);
        btnVolverMenu.addActionListener(e -> ventanaPrincipal.mostrarMenuPrincipal());
        
        // Agregar componentes
        add(lblTitulo, BorderLayout.NORTH);
        add(lstRanking, BorderLayout.CENTER);
        add(btnVolverMenu, BorderLayout.SOUTH);
    }

    private void crearListaRanking() {
        // Obtener datos del controlador
        List<String> datosRanking = controlador.getRanking();
        
        // Crear JList
        lstRanking = new JList<>(datosRanking.toArray(new String[0]));
        lstRanking.setFont(new Font("Arial", Font.BOLD, 16));
        lstRanking.setForeground(Color.WHITE);
        lstRanking.setBackground(Color.DARK_GRAY);
        lstRanking.setSelectionBackground(Color.CYAN);
        lstRanking.setSelectionForeground(Color.BLACK);
        lstRanking.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // Centrar el texto
        lstRanking.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setHorizontalAlignment(SwingConstants.CENTER);
                return this;
            }
        });
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
}
