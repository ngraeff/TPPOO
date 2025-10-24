package org.example.vista;

// IMPORTS
import org.example.controlador.ControladorJuego;
import org.example.modelo.Jugador;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.example.enums.Dificultad;

public class VentanaPrincipal extends JFrame {
    // Componentes de clase
    private ControladorJuego controlador;
    private JPanel panelPrincipal;
    private JPanel panelMenu;
    private JPanel panelJuego;
    private JPanel panelCreditos;
    private JPanel panelRanking;

    // Componentes del menú principal
    private JButton btnJugar;
    private JButton btnRanking;
    private JButton btnSalir;
    private JLabel lblTitulo;

    // Componentes de créditos
    private JTextField txtCreditos;
    private JButton btnCargarCreditos;
    private JLabel lblCreditosActuales;

    // Componentes del juego
    private PanelJuego panelJuegoCanvas;
    private JLabel lblInfoJuego;
    private JButton btnPausar;
    private JButton btnTerminar;

    // Componentes del ranking
    private JTextArea txtRanking;
    private JButton btnVolverMenu;

    // CONSTRUCTOR
    public VentanaPrincipal(ControladorJuego controlador) {
        this.controlador = controlador;
        inicializarComponentes(); // Creacion de los componentes (parte interna)
        configurarVentana();  // Configuracion de la ventana (parte externa)
        //controlador.configurarPanelJuego();
        //mostrarMenuPrincipal();
    }

    private void inicializarComponentes() {
        panelPrincipal = new JPanel(new CardLayout());
        crearPanelMenu();
        crearPanelJuego();
        //crearPanelCreditos();
        //crearPanelRanking();

        panelPrincipal.add(panelMenu, "MENU");
        panelPrincipal.add(panelJuego, "JUEGO");
        //panelPrincipal.add(panelCreditos, "CREDITOS");
        //panelPrincipal.add(panelRanking, "RANKING");
    }

    private void crearPanelMenu() {  // CREACION DEL MENU PRINCIPAL
        panelMenu = new JPanel(new BorderLayout());
        panelMenu.setBackground(Color.BLACK);

        // Título
        lblTitulo = new JLabel("SPACE INVADERS", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 48));
        lblTitulo.setForeground(Color.GREEN);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        panelBotones.setBackground(Color.BLACK);

        btnJugar = new JButton("JUGAR");
        btnRanking = new JButton("RANKING");
        btnSalir = new JButton("SALIR");

        // LLAMO A LA CONFIGURACION DE LOS BOTONES
        configurarBoton(btnJugar);
        configurarBoton(btnRanking);
        configurarBoton(btnSalir);

        btnJugar.addActionListener(e -> mostrarPanelJuego());
        //btnRanking.addActionListener(e -> mostrarRanking());
        btnSalir.addActionListener(e -> System.exit(0));

        panelBotones.add(btnJugar);
        panelBotones.add(btnRanking);
        panelBotones.add(btnSalir);

        panelMenu.add(lblTitulo, BorderLayout.NORTH);
        panelMenu.add(panelBotones, BorderLayout.CENTER);
    }

    private void crearPanelJuego() {
        panelJuego = new JPanel(new BorderLayout());
        panelJuego.setBackground(Color.BLACK);
        
        // Canvas del juego
        panelJuegoCanvas = new PanelJuego(controlador);
        
        // Panel de información
        JPanel panelInfo = new JPanel();
        panelInfo.setBackground(Color.DARK_GRAY);
        panelInfo.setPreferredSize(new Dimension(800, 50));
        
        lblInfoJuego = new JLabel("Puntuación: 0 | Vidas: 3");
        lblInfoJuego.setForeground(Color.WHITE);
        lblInfoJuego.setFont(new Font("Arial", Font.BOLD, 16));
        panelInfo.add(lblInfoJuego);
        
        // Botones de control
        JPanel panelControles = new JPanel();
        panelControles.setBackground(Color.DARK_GRAY);
        panelControles.setPreferredSize(new Dimension(800, 50));
        
        btnPausar = new JButton("PAUSAR");
        btnTerminar = new JButton("TERMINAR");
        configurarBoton(btnPausar);
        configurarBoton(btnTerminar);
        
        panelControles.add(btnPausar);
        panelControles.add(btnTerminar);
        
        panelJuego.add(panelInfo, BorderLayout.NORTH);
        panelJuego.add(panelJuegoCanvas, BorderLayout.CENTER);
        panelJuego.add(panelControles, BorderLayout.SOUTH);
    }

    private void mostrarPanelJuego() {
        // Crear oleada de invasores
        controlador.crearOleadaInvasores();
        controlador.crearJugador(Dificultad.FACIL);
        controlador.iniciarJuego();
        
        // Cambiar al panel de juego
        CardLayout layout = (CardLayout) panelPrincipal.getLayout();
        layout.show(panelPrincipal, "JUEGO");
        panelJuegoCanvas.requestFocus(); // Para que funcione el KeyListener
    }

    // CONFIGURACION DE LOS BOTONES
    private void configurarBoton(JButton boton) {
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setForeground(Color.BLACK); // Color del texto del boton
        boton.setBackground(Color.WHITE); // Color de borde del boton
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        boton.setFocusPainted(false);
        boton.setPreferredSize(new Dimension(200, 50));

            // Agregar efecto hover
    boton.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseEntered(MouseEvent e) {
            boton.setBackground(Color.CYAN); // Color cuando el mouse está encima
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
            boton.setBackground(Color.WHITE); // Color original
        }
    });
    }

    // =======================================================

    private void configurarVentana() {
        setTitle("Space Invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        add(panelPrincipal);
    }

}
