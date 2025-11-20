package trucoarg.personajesDosJugadores;

public class GestorDeCantos {

    private int nivelCanto = 1;
    private boolean cantoAceptado = false;

    private int jugadorQueCanto = 0;
    private boolean esperandoRespuesta = false;
    private int jugadorQueDebeResponder = 0;

    public boolean puedeCantar(int jugador, String canto) {
        // No se puede cantar si hay un canto pendiente de respuesta
        if (esperandoRespuesta) return false;

        switch (canto.toLowerCase()) {
            case "truco":
                return nivelCanto == 1;
            case "retruco":
                return nivelCanto == 2 && cantoAceptado;
            case "vale cuatro":
                return nivelCanto == 3 && cantoAceptado;
        }

        return false;
    }

    public boolean cantar(int jugador, String canto) {
        System.out.println("DEBUG GestorCantos: J" + jugador + " intenta cantar '" + canto + "'");

        if (!puedeCantar(jugador, canto)) {
            System.out.println("DEBUG GestorCantos: No se puede cantar ahora");
            return false;
        }

        switch (canto.toLowerCase()) {
            case "truco":
                nivelCanto = 2;
                System.out.println("DEBUG GestorCantos: Nivel subió a 2 (Truco)");
                break;
            case "retruco":
                nivelCanto = 3;
                System.out.println("DEBUG GestorCantos: Nivel subió a 3 (Retruco)");
                break;
            case "vale cuatro":
                nivelCanto = 4;
                System.out.println("DEBUG GestorCantos: Nivel subió a 4 (Vale Cuatro)");
                break;
        }

        jugadorQueCanto = jugador;
        esperandoRespuesta = true;
        jugadorQueDebeResponder = (jugador == 1) ? 2 : 1;

        System.out.println("DEBUG GestorCantos: J" + jugadorQueDebeResponder + " debe responder");
        return true;
    }

    /**
     * @param jugador El jugador que está respondiendo
     * @param quiero true = QUIERO, false = NO QUIERO
     * @return
     * 0 -> QUIERO (se sigue jugando)
     * 1 -> gana J1
     * 2 -> gana J2
     * -1 -> inválido
     */
    public int responder(int jugador, boolean quiero) {
        System.out.println("DEBUG GestorCantos: responder() - J" + jugador +
            " dice " + (quiero ? "QUIERO" : "NO QUIERO"));

        if (!esperandoRespuesta) {
            System.out.println("DEBUG GestorCantos: No hay canto pendiente");
            return -1;
        }

        // Verificar que sea el jugador correcto quien responde
        if (jugador != jugadorQueDebeResponder) {
            System.out.println("DEBUG GestorCantos: No es tu turno de responder. Debe responder J" + jugadorQueDebeResponder);
            return -1;
        }

        esperandoRespuesta = false;

        if (!quiero) {
            // NO QUIERO → gana el que cantó
            cantoAceptado = false;
            System.out.println("DEBUG GestorCantos: NO QUIERO - Gana J" + jugadorQueCanto);
            return jugadorQueCanto;
        }

        // QUIERO → seguimos la mano con el valor cantado
        cantoAceptado = true;
        jugadorQueDebeResponder = 0;
        System.out.println("DEBUG GestorCantos: QUIERO - Se sigue jugando");
        return 0;
    }

    /** Puntos reales según reglas del truco */
    public int puntosDeLaMano() {
        if (!cantoAceptado) {
            // NO QUIERO → vale el nivel anterior
            int puntos = Math.max(1, nivelCanto - 1);
            System.out.println("DEBUG GestorCantos: Puntos por NO QUIERO = " + puntos);
            return puntos;
        }

        // QUIERO → vale el nivel actual
        System.out.println("DEBUG GestorCantos: Puntos por QUIERO = " + nivelCanto);
        return nivelCanto;
    }

    public void reset() {
        System.out.println("DEBUG GestorCantos: reset()");
        nivelCanto = 1;
        cantoAceptado = false;
        jugadorQueCanto = 0;
        esperandoRespuesta = false;
        jugadorQueDebeResponder = 0;
    }

    public int getNivelCanto() { return nivelCanto; }
    public boolean estaEsperandoRespuesta() { return esperandoRespuesta; }
    public int getJugadorQueDebeResponder() { return jugadorQueDebeResponder; }
    public boolean isCantoAceptado() { return cantoAceptado; }
}
