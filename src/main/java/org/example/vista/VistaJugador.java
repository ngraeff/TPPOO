package org.example.vista;
import org.example.modelo.Jugador;
import java.awt.*;
import java.util.List;

public class VistaJugador {

    private Color colorJugador = Color.GREEN;

    public void dibujar(Graphics2D g2d, List<Integer> datosJugador) {
        if (datosJugador == null) return;
        int posicionX = datosJugador.get(0);
        int posicionY = datosJugador.get(1);
        int ancho = datosJugador.get(2);
        int alto = datosJugador.get(3);
        g2d.setColor(colorJugador);
        g2d.fillRect(
                posicionX,
                posicionY,
                ancho,
                alto
        );
    }

    // Opcional: permitir cambiar color según estado
    public void setColorJugador(Color colorJugador) {
        this.colorJugador = colorJugador;
    }
}
