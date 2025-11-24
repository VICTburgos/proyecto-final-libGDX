package trucoarg.personajesDosJugadores;

import trucoarg.personajesSolitario.CartaSolitario;
import java.util.List;

/**
 * Maneja los cantos de Envido: Envido, Real Envido y Falta Envido
 * CORREGIDO: Permite subir la apuesta cuando hay canto pendiente
 */
public class Envido extends Canto {

    private static final int PUNTOS_ENVIDO = 2;
    private static final int PUNTOS_REAL_ENVIDO = 3;
    private static final int PUNTOS_ENVIDO_ENVIDO = 4;

    private int cantosEnvidoAcumulados = 0;

    @Override
    public void reset() {
        super.reset();
        cantosEnvidoAcumulados = 0;
    }

    @Override
    public boolean cantar(int jugador, String tipoCanto) {
        String cantoLower = tipoCanto.toLowerCase().trim();

        System.out.println("DEBUG Envido.cantar() - J" + jugador + " intenta cantar: " + cantoLower);
        System.out.println("  Estado actual: cantoActual=" + cantoActual + ", esperandoRespuesta=" + esperandoRespuesta);

        // CLAVE: Si hay canto pendiente, solo el jugador que debe responder puede "subir"
        if (esperandoRespuesta) {
            int jugadorQueDebeResponder = getJugadorQueDebeResponder();

            if (jugador != jugadorQueDebeResponder) {
                System.out.println("  RECHAZADO: No es el jugador que debe responder");
                return false;
            }

            // El jugador que debe responder está "subiendo" la apuesta
            System.out.println("  J" + jugador + " está subiendo la apuesta de " + cantoActual + " a " + cantoLower);
        }

        if (!validarCanto(cantoLower, jugador)) {
            return false;
        }

        // Registrar el canto ANTES de incrementar
        if (cantoLower.equals("envido")) {
            cantosEnvidoAcumulados++;
        }

        cantoActual = cantoLower;
        jugadorQueCanto = jugador;
        esperandoRespuesta = true;
        cantoAceptado = false;

        System.out.println("  ÉXITO: Canto registrado. Esperando respuesta del J" + getJugadorQueDebeResponder());
        return true;
    }

    @Override
    protected boolean validarCanto(String tipoCanto, int jugador) {
        switch (tipoCanto) {
            case "envido":
                // Si no hay canto activo, se puede cantar libremente
                if (cantoActual == null) {
                    if (cantosEnvidoAcumulados >= 2) {
                        System.out.println("  RECHAZADO: Ya se cantó envido 2 veces");
                        return false;
                    }
                    return true;
                }

                // Si hay canto activo, solo se puede "subir" si el canto actual es envido
                if (!cantoActual.equals("envido")) {
                    System.out.println("  RECHAZADO: No se puede cantar envido después de " + cantoActual);
                    return false;
                }

                // Máximo 2 envidos acumulados
                if (cantosEnvidoAcumulados >= 2) {
                    System.out.println("  RECHAZADO: Ya se cantó envido 2 veces");
                    return false;
                }
                return true;

            case "real envido":
                // Real envido se puede cantar:
                // 1. Al inicio (sin canto previo)
                // 2. Como respuesta a envido (subiendo)
                if (cantoActual == null) {
                    return true;
                }

                if (cantoActual.equals("envido")) {
                    return true; // Subiendo desde envido
                }

                System.out.println("  RECHAZADO: Real envido solo al inicio o después de envido");
                return false;

            case "falta envido":
                // Falta envido se puede cantar:
                // 1. Al inicio (sin canto previo)
                // 2. Como respuesta a envido o real envido (subiendo)
                if (cantoActual == null) {
                    return true;
                }

                if (cantoActual.equals("envido") || cantoActual.equals("real envido")) {
                    return true; // Subiendo
                }

                System.out.println("  RECHAZADO: Falta envido solo al inicio o en secuencia de envido");
                return false;

            default:
                System.out.println("  RECHAZADO: Canto desconocido");
                return false;
        }
    }

    @Override
    public int responder(int jugador, boolean quiero) {
        System.out.println("DEBUG Envido.responder() - J" + jugador + " responde: " + (quiero ? "QUIERO" : "NO QUIERO"));
        System.out.println("  Estado: cantoActual=" + cantoActual + ", cantosEnvidoAcumulados=" + cantosEnvidoAcumulados);

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
            cantoAceptado = true;
            int pts = getPuntos();
            System.out.println("  Canto aceptado. Se juega por " + pts + " puntos");
            return 0;
        } else {
            System.out.println("  Canto rechazado. Gana J" + jugadorQueCanto);
            return jugadorQueCanto;
        }
    }

    @Override
    public int getPuntos() {
        if (cantoActual == null) {
            return 1; // Valor por defecto si se rechaza antes de cantar
        }

        switch (cantoActual) {
            case "envido":
                return PUNTOS_ENVIDO * cantosEnvidoAcumulados;
            case "real envido":
                return PUNTOS_REAL_ENVIDO + (PUNTOS_ENVIDO * cantosEnvidoAcumulados);
            case "falta envido":
                // TODO: Implementar cálculo según puntos de cada jugador
                return 15;
            default:
                return 1;
        }
    }

    /**
     * Verifica qué cantos de envido están disponibles como respuesta
     * al canto actual (para "subir" la apuesta)
     */
    public boolean puedeSubirConEnvido() {
        if (!esperandoRespuesta) return false;
        if (cantoActual == null) return false;

        // Se puede subir con envido solo si el canto actual es envido y no se llegó al máximo
        return cantoActual.equals("envido") && cantosEnvidoAcumulados < 2;
    }

    public boolean puedeSubirConRealEnvido() {
        if (!esperandoRespuesta) return false;
        if (cantoActual == null) return false;

        // Se puede subir con real envido desde envido
        return cantoActual.equals("envido");
    }

    public boolean puedeSubirConFaltaEnvido() {
        if (!esperandoRespuesta) return false;
        if (cantoActual == null) return false;

        // Se puede subir con falta envido desde envido o real envido
        return cantoActual.equals("envido") || cantoActual.equals("real envido");
    }

    public int calcularEnvido(List<CartaSolitario> mano) {
        if (mano == null || mano.size() != 3) return 0;

        int[] valores = new int[3];
        String[] palos = new String[3];

        for (int i = 0; i < 3; i++) {
            CartaSolitario carta = mano.get(i);
            valores[i] = obtenerValorEnvido(carta);
            palos[i] = String.valueOf(carta.getPALOS_CARTAS());
        }

        int maxEnvido = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = i + 1; j < 3; j++) {
                if (palos[i].equals(palos[j])) {
                    int envido = 20 + valores[i] + valores[j];
                    maxEnvido = Math.max(maxEnvido, envido);
                }
            }
        }

        if (maxEnvido == 0) {
            maxEnvido = Math.max(valores[0], Math.max(valores[1], valores[2]));
        }

        return maxEnvido;
    }

    private int obtenerValorEnvido(CartaSolitario carta) {
        int numero = carta.getNUMERO();

        if (numero >= 1 && numero <= 7) {
            return numero;
        }
        if (numero == 10 || numero == 11 || numero == 12) {
            return 0;
        }
        return 0;
    }
}
