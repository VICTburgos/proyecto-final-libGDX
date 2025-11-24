package trucoarg.personajesDosJugadores;

/**
 * Clase base abstracta para todos los cantos del Truco Argentino
 * Proporciona funcionalidad común para Truco y Envido
 */
public abstract class Canto {

    protected String cantoActual = null;
    protected int jugadorQueCanto = -1;
    protected boolean esperandoRespuesta = false;
    protected boolean cantoAceptado = false;

    /**
     * Reinicia el estado del canto
     */
    public void reset() {
        cantoActual = null;
        jugadorQueCanto = -1;
        esperandoRespuesta = false;
        cantoAceptado = false;
    }

    /**
     * Intenta realizar un canto
     * @param jugador El jugador que canta (1 o 2)
     * @param tipoCanto El tipo específico de canto
     * @return true si el canto fue exitoso
     */
    public abstract boolean cantar(int jugador, String tipoCanto);

    /**
     * Responde al canto pendiente
     * @param jugador El jugador que responde
     * @param quiero true si acepta, false si rechaza
     * @return 0 si continúa jugando, >0 si alguien ganó (número del ganador), -1 si error
     */
    public int responder(int jugador, boolean quiero) {
        System.out.println("DEBUG " + this.getClass().getSimpleName() + ".responder() - J" + jugador +
            " responde: " + (quiero ? "QUIERO" : "NO QUIERO"));
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
            System.out.println("  Canto aceptado. Se juega por " + getPuntos() + " puntos");
            return 0;
        } else {
            // Rechaza el canto, gana el que cantó
            System.out.println("  Canto rechazado. Gana J" + jugadorQueCanto);
            return jugadorQueCanto;
        }
    }

    /**
     * Calcula los puntos que vale el canto actual
     * @return Los puntos según el tipo de canto
     */
    public abstract int getPuntos();

    /**
     * Verifica si se puede realizar un canto específico según el estado actual
     * @param tipoCanto El tipo de canto a verificar
     * @param jugador El jugador que intenta cantar
     * @return true si el canto es válido
     */
    protected abstract boolean validarCanto(String tipoCanto, int jugador);

    // Getters comunes
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
