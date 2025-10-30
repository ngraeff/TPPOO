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


public class VentanaPrincipal extends JFrame {
    // Componentes de clase
    private ControladorJuego controlador;
    private JPanel panelPrincipal;
    private JPanel panelMenu;
    private PanelJuego panelJuego;
    private JPanel panelCreditos;
    private PanelRanking panelRanking;
    private PanelDificultad panelDificultad;

    // Componentes del menú principal
    private JButton btnJugar;
    private JButton btnRanking;
    private JButton btnSalir;
    private JButton btnDificultad;
    private JLabel lblTitulo;

    // Componentes de créditos
   // private JTextField txtCreditos;
    private JButton btnCargarCreditos;
    private JLabel lblCreditosActuales;


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

    /***
     * Inicializa los componentes del menu inicial.
     */
    private void inicializarComponentes() {
        panelPrincipal = new JPanel(new CardLayout());
        crearPanelMenu();
        //crearPanelCreditos();
        crearPanelRanking();
        panelPrincipal.add(panelMenu, "MENU");
        //panelPrincipal.add(panelCreditos, "CREDITOS");
        panelPrincipal.add(panelRanking, "RANKING");

    }

    /***
     * Crea el panel del menu inicial
     */
    private void crearPanelMenu() {  // CREACION DEL MENU PRINCIPAL
        panelMenu = new JPanel(new BorderLayout());
        panelMenu.setBackground(Color.BLACK);

        // Título
        lblTitulo = new JLabel("SPACE INVADERS", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 48));
        lblTitulo.setForeground(Color.GREEN);
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));

        // Opcion creditos
        JPanel panelCreditos = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelCreditos.setBackground(Color.BLACK);

        lblCreditosActuales = new JLabel("Créditos: " + controlador.getCreditosJugador());
        lblCreditosActuales.setFont(new Font("Arial", Font.PLAIN, 20));
        lblCreditosActuales.setForeground(Color.GREEN);

        btnCargarCreditos = new JButton("Cargar");
        configurarBoton(btnCargarCreditos);
        btnCargarCreditos.setPreferredSize(new Dimension(100, 30));

        panelCreditos.add(lblCreditosActuales);
        panelCreditos.add(btnCargarCreditos);

        // Panel de botones
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 10, 10));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        panelBotones.setBackground(Color.BLACK);

        btnJugar = new JButton("JUGAR");
        btnRanking = new JButton("RANKING");
        btnDificultad = new JButton("DIFICULTAD");
        btnSalir = new JButton("SALIR");

        // Configuracion de botones
        configurarBoton(btnJugar);
        configurarBoton(btnRanking);
        configurarBoton(btnDificultad);
        configurarBoton(btnSalir);

        // Agrego funciones a los botones
        btnJugar.addActionListener(e -> mostrarPanelJuego());
        btnDificultad.addActionListener(e -> mostrarPanelDificultad());
        btnRanking.addActionListener(e -> mostrarRanking());
        btnCargarCreditos.addActionListener(e -> accionBotonCreditos());
        btnSalir.addActionListener(e -> System.exit(0));

        // Agrego botones al panel.
        panelBotones.add(btnJugar);
        panelBotones.add(btnRanking);
        panelBotones.add(btnDificultad);
        panelBotones.add(btnSalir);

        panelMenu.add(lblTitulo, BorderLayout.NORTH);
        panelMenu.add(panelBotones, BorderLayout.CENTER);
        panelMenu.add(panelCreditos, BorderLayout.SOUTH);
    }

    /***
     * Crea el panel del juego.
     */
    private void crearPanelJuego() {
        panelJuego = new PanelJuego(controlador);
    }

    /***
     * Muestra el panel donde se realiza el juego.
     */
    private void mostrarPanelJuego() {
        boolean juegoIniciado = controlador.iniciarJuego();

        if (!juegoIniciado) return;

        crearPanelJuego();
        panelPrincipal.add(panelJuego, "JUEGO");

        CardLayout layout = (CardLayout) panelPrincipal.getLayout();
        layout.show(panelPrincipal, "JUEGO");

        ((PanelJuego) panelJuego).enfocarCanvas();
    }

    /***
     * Muestra el menu principal de nuevo (para volver).
     */
    public void mostrarMenuPrincipal() {
        ((CardLayout) panelPrincipal.getLayout()).show(panelPrincipal, "MENU");
    }

    /***
     * Muestra el panel para seleccionar la dificultad.
     */
    private void mostrarPanelDificultad() {
        //Crea el panel de dificultad
        panelDificultad = new PanelDificultad(controlador, this);
        panelPrincipal.add(panelDificultad, "DIFICULTAD");
        ((CardLayout) panelPrincipal.getLayout()).show(panelPrincipal, "DIFICULTAD");
    }

    /***
     *
     */
    public void accionBotonCreditos(){

        String input = JOptionPane.showInputDialog(this, "Ingrese cantidad de créditos a cargar:", "Cargar Créditos", JOptionPane.PLAIN_MESSAGE);
        if (input != null && !input.isEmpty()) {
            try {
                int cantidad = Integer.parseInt(input);
                if (cantidad > 0) {
                    controlador.cargarCreditos(cantidad);
                    lblCreditosActuales.setText("Créditos: " + controlador.getCreditosJugador());
                } else {
                    mostrarMensaje("Ingrese un número mayor a 0", "Error");
                }
            } catch (NumberFormatException ex) {
                mostrarMensaje("Ingrese un número válido", "Error");
            }
        }

    }
    // CONFIGURACION DE LOS BOTONES

    /***
     * Configura los botones
     * @param boton Boton a configurar.
     */
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
    /***
     * Configura la ventana del menu principal
     */
    private void configurarVentana() {
        setTitle("Space Invaders");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        add(panelPrincipal);
    }

    /***
     * Muestra mensaje de WARNING
     * @param mensaje Mensaje a mostrar.
     * @param titulo Titulo a mostrar.
     */
    public void mostrarMensaje(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(
                this,
                mensaje,
                titulo,
                JOptionPane.WARNING_MESSAGE
        );
    }

    /***
     * Crea el panel de ranking.
     */
    private void crearPanelRanking() {
        panelRanking = new PanelRanking(controlador, this);
    }

    /***
     * Muestra el panel de ranking.
     */
    private void mostrarRanking() {
        ((CardLayout) panelPrincipal.getLayout()).show(panelPrincipal, "RANKING");
    }



}