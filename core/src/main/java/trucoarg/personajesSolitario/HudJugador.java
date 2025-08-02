package trucoarg.personajesSolitario;

import trucoarg.pantallas.PantallaUnJugador;

public class HudJugador {
    PantallaUnJugador pantallaUnJugador;
    private int aciertos;
    private float tiempa;
    private boolean repeticiones;

    public HudJugador(int aciertos, float tiempa, boolean repetir) {
        this.aciertos = aciertos;
        this.tiempa = tiempa;
        this.repeticiones = repetir;
    }
}
