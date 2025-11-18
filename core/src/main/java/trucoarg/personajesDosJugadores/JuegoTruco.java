package trucoarg.personajesDosJugadores;

import trucoarg.personajesSolitario.CartaSolitario;
import trucoarg.personajesSolitario.MazoSolitario;
import trucoarg.utiles.ColisionesDosJugadores;

public class JuegoTruco {

    private JugadorBase jugador1;
    private JugadorBase jugador2;

    private final MazoSolitario mazo;
    private final ColisionesDosJugadores colisiones;

    private boolean jugador1EsMano = false;

    private int manoOriginal;    // 1 o 2 → quién es mano en ESTA mano
    private int turnoActual = 1; // 1 o 2
    private int tiradaActual = 1; // 1..3

    private int rondasGanadasJ1 = 0;
    private int rondasGanadasJ2 = 0;

    private boolean manoTerminada = false;

    private CartaSolitario cartaJugadaJ1 = null;
    private CartaSolitario cartaJugadaJ2 = null;

    private int puntosJ1 = 0;
    private int puntosJ2 = 0;

    public JuegoTruco() {
        mazo = new MazoSolitario();
        colisiones = new ColisionesDosJugadores();
        iniciarNuevaMano();
    }

    public void iniciarNuevaMano() {
        System.out.println("\n===== iniciarNuevaMano() llamado =====");

        // Reinicio de mazo SOLO aquí (cuando verdaderamente empezamos una nueva mano)
        mazo.reiniciarMazo();
        colisiones.reset();

        jugador1EsMano = !jugador1EsMano;
        manoOriginal = jugador1EsMano ? 1 : 2;

        jugador1 = new JugadorBase("Jugador 1", mazo, jugador1EsMano);
        jugador2 = new JugadorBase("Jugador 2", mazo, !jugador1EsMano);

        manoTerminada = false;
        tiradaActual = 1;
        rondasGanadasJ1 = 0;
        rondasGanadasJ2 = 0;

        turnoActual = manoOriginal;

        cartaJugadaJ1 = null;
        cartaJugadaJ2 = null;

        System.out.println("Mano original: J" + manoOriginal);
    }

    // jugarCarta: solo guarda carta y cambia turno (no reinicia mazo)
    public boolean jugarCarta(int jugador, CartaSolitario carta) {
        if (manoTerminada) return false;
        if (jugador != turnoActual) return false;
        if (carta.getYaJugadas()) return false;

        carta.setYaJugadas(true);

        if (jugador == 1 && cartaJugadaJ1 == null) {
            cartaJugadaJ1 = carta;
            turnoActual = 2;
            System.out.println("J1 jugó (nivel=" + carta.getNIVEL() + ")");
            return true;
        }

        if (jugador == 2 && cartaJugadaJ2 == null) {
            cartaJugadaJ2 = carta;
            turnoActual = 1;
            System.out.println("J2 jugó (nivel=" + carta.getNIVEL() + ")");
            return true;
        }
        return false;
    }

    // procesar tirada: compara las 2 cartas y actualiza estado
    public int procesarTirada() {
        if (manoTerminada) return -1;
        if (cartaJugadaJ1 == null || cartaJugadaJ2 == null) return -1;

        System.out.println("\n--- procesarTirada() Ronda " + tiradaActual + " ---");

        // uso comparación directa (colisiones hace similar pero así queda consistente)
        int resultado;
        if (cartaJugadaJ1.getNIVEL() > cartaJugadaJ2.getNIVEL()) resultado = 1;
        else if (cartaJugadaJ2.getNIVEL() > cartaJugadaJ1.getNIVEL()) resultado = 2;
        else resultado = 0;

        if (resultado == 1) {
            System.out.println("Gana tirada J1");
            rondasGanadasJ1++;
            turnoActual = 1;
        } else if (resultado == 2) {
            System.out.println("Gana tirada J2");
            rondasGanadasJ2++;
            turnoActual = 2;
        } else {
            System.out.println("Parda en la tirada");
            turnoActual = manoOriginal; // parda -> empieza el mano
        }

        // Verificar fin de la mano según reglas:
        int ganadorMano = verificarFinDeMano();
        if (ganadorMano != -1) {
            manoTerminada = true;
            if (ganadorMano == 1) puntosJ1++;
            else puntosJ2++;

            System.out.println("\n***** FIN DE MANO *****");
            System.out.println("Ganador de la mano: J" + ganadorMano);
            System.out.println("Puntos -> J1: " + puntosJ1 + " | J2: " + puntosJ2);
            return ganadorMano;
        }

        // preparar siguiente ronda
        tiradaActual++;
        cartaJugadaJ1 = null;
        cartaJugadaJ2 = null;

        System.out.println("Siguiente tirada: " + tiradaActual + ". Turno: J" + turnoActual);
        return resultado;
    }

    private int verificarFinDeMano() {
        // si alguien ya ganó 2 rondas
        if (rondasGanadasJ1 == 2) return 1;
        if (rondasGanadasJ2 == 2) return 2;

        // si se jugaron 3 tiradas, decidir por quien ganó más o mano en triple empate
        if (tiradaActual == 3) {
            if (rondasGanadasJ1 > rondasGanadasJ2) return 1;
            if (rondasGanadasJ2 > rondasGanadasJ1) return 2;
            // empate 0-0 o 1-1 -> gana manoOriginal
            return manoOriginal;
        }

        return -1;
    }

    // getters para que la pantalla controle reinicio correctamente
    public boolean isManoTerminada() { return manoTerminada; }
    public int getTurnoActual() { return turnoActual; }
    public int getTiradaActual() { return tiradaActual; }
    public int getPuntosJ1() { return puntosJ1; }
    public int getPuntosJ2() { return puntosJ2; }
    public JugadorBase getJugador1() { return jugador1; }
    public JugadorBase getJugador2() { return jugador2; }

    // método para forzar reinicio visual/estado desde pantalla (si pantalla quiere)
    public void reiniciarManoSiCorresponde() {
        if (manoTerminada) {
            iniciarNuevaMano();
        }
    }
    public boolean puedeJugar(int jugador) {
        if (manoTerminada) return false;
        return turnoActual == jugador;
    }

}
