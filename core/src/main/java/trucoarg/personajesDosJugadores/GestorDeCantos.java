package trucoarg.personajesDosJugadores;

public class GestorDeCantos {

    private String cantoActual = null; // "truco", "retruco", "vale cuatro"
    private int jugadorQueCanto = -1;
    private boolean esperandoRespuesta = false;
    private boolean cantoAceptado = false;

    // Puntos que vale cada nivel
    private static final int PUNTOS_TRUCO = 2;
    private static final int PUNTOS_RETRUCO = 3;
    private static final int PUNTOS_VALE_CUATRO = 4;

    public void reset() {
        cantoActual = null;
        jugadorQueCanto = -1;
        esperandoRespuesta = false;
        cantoAceptado = false;
    }

    /**
     * Intenta cantar truco/retruco/vale cuatro
     * @param jugador El jugador que canta (1 o 2)
     * @param canto El tipo de canto ("truco", "retruco", "vale cuatro")
     * @return true si el canto fue exitoso
     */
    public boolean cantar(int jugador, String canto) {
        String cantoLower = canto.toLowerCase().trim();

        System.out.println("DEBUG GestorCantos.cantar() - J" + jugador + " intenta cantar: " + cantoLower);
        System.out.println("  Estado actual: cantoActual=" + cantoActual + ", esperandoRespuesta=" + esperandoRespuesta);

        // No se puede cantar si hay un canto pendiente de respuesta
        if (esperandoRespuesta) {
            System.out.println("  RECHAZADO: Hay canto pendiente");
            return false;
        }

        // Validar secuencia de cantos
        if (cantoLower.equals("truco")) {
            // Truco solo si no hay ningún canto previo
            if (cantoActual != null) {
                System.out.println("  RECHAZADO: Ya hay canto activo");
                return false;
            }
            cantoActual = "truco";

        } else if (cantoLower.equals("retruco")) {
            // Retruco solo si hay truco aceptado y lo canta el otro jugador
            if (cantoActual == null || !cantoActual.equals("truco") || !cantoAceptado) {
                System.out.println("  RECHAZADO: No hay truco aceptado para retruco");
                return false;
            }
            if (jugador == jugadorQueCanto) {
                System.out.println("  RECHAZADO: El mismo jugador no puede retrucarse a sí mismo");
                return false;
            }
            cantoActual = "retruco";

        } else if (cantoLower.equals("vale cuatro") || cantoLower.equals("vale 4")) {
            // Vale cuatro solo si hay retruco aceptado y lo canta el otro jugador
            if (cantoActual == null || !cantoActual.equals("retruco") || !cantoAceptado) {
                System.out.println("  RECHAZADO: No hay retruco aceptado para vale cuatro");
                return false;
            }
            if (jugador == jugadorQueCanto) {
                System.out.println("  RECHAZADO: El mismo jugador no puede hacer vale cuatro después de su retruco");
                return false;
            }
            cantoActual = "vale cuatro";

        } else {
            System.out.println("  RECHAZADO: Canto desconocido");
            return false;
        }

        jugadorQueCanto = jugador;
        esperandoRespuesta = true;
        cantoAceptado = false;

        System.out.println("  ÉXITO: Canto registrado. Esperando respuesta del J" + getJugadorQueDebeResponder());
        return true;
    }

    /**
     * Responde al canto pendiente
     * @param jugador El jugador que responde
     * @param quiero true si acepta, false si rechaza
     * @return 0 si continúa jugando, >0 si alguien ganó la mano (número del ganador)
     */
    public int responder(int jugador, boolean quiero) {
        System.out.println("DEBUG GestorCantos.responder() - J" + jugador + " responde: " + (quiero ? "QUIERO" : "NO QUIERO"));
        System.out.println("  Estado: cantoActual=" + cantoActual + ", jugadorQueCanto=" + jugadorQueCanto);

        if (!esperandoRespuesta) {
            System.out.println("  ERROR: No hay canto pendiente");
            return -1;
        }

        if (jugador == jugadorQueCanto) {
            System.out.println("  ERROR: El jugador que cantó no puede responder");
            return -1;
        }

        esperandoRespuesta = false;

        if (quiero) {
            // Acepta el canto, se sigue jugando
            cantoAceptado = true;
            System.out.println("  Canto aceptado. Se juega por " + puntosDeLaMano() + " puntos");
            return 0;

        } else {
            // Rechaza el canto, gana el que cantó
            System.out.println("  Canto rechazado. Gana J" + jugadorQueCanto);
            return jugadorQueCanto;
        }
    }

    /**
     * @return Los puntos que vale la mano actual (sin canto = 1, con cantos según nivel)
     */
    public int puntosDeLaMano() {
        if (cantoActual == null || !cantoAceptado) {
            return 1; // Mano sin canto vale 1 punto
        }

        switch (cantoActual) {
            case "truco":
                return PUNTOS_TRUCO;
            case "retruco":
                return PUNTOS_RETRUCO;
            case "vale cuatro":
                return PUNTOS_VALE_CUATRO;
            default:
                return 1;
        }
    }

    public boolean estaEsperandoRespuesta() {
        return esperandoRespuesta;
    }

    public int getJugadorQueDebeResponder() {
        if (!esperandoRespuesta) return -1;
        return jugadorQueCanto == 1 ? 2 : 1;
    }

    public String getCantoActual() {
        return cantoActual;
    }

    public int getJugadorQueCanto() {
        return jugadorQueCanto;
    }

    public boolean isCantoAceptado() {
        return cantoAceptado;
    }
}
