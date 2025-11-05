package trucoarg.personajesDosJugadores;

import trucoarg.personajesSolitario.MazoSolitario;

public class JuegoTruco{

    private JugadorBase jugador1;
    private JugadorBase jugador2;
    private boolean jugador1EsMano;
    private MazoSolitario mazo;

    public JuegoTruco() {
        mazo = new MazoSolitario();

        jugador1EsMano = Math.random() < 0.5;

        jugador1 = new JugadorBase("Jugador 1", mazo, jugador1EsMano);
        jugador2 = new JugadorBase("Jugador 2", mazo, !jugador1EsMano);
    }

    public void cambiarMano() {
        jugador1EsMano = !jugador1EsMano;
        jugador1.setEsMano(jugador1EsMano);
        jugador2.setEsMano(!jugador1EsMano);
    }

    public JugadorBase getJugador1() { return jugador1; }
    public JugadorBase getJugador2() { return jugador2; }
}

