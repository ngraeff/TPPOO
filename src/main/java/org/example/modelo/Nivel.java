package org.example.modelo;

import org.example.enums.Dificultad;

public class Nivel {
    private  int numero;
    private Dificultad dificultad;
    private Oleada oleada;

    public Nivel(int numero, Oleada oleada, Dificultad dificultad) {
        this.numero = numero;
        this.oleada = oleada;
        this.dificultad = dificultad;
    }
}
