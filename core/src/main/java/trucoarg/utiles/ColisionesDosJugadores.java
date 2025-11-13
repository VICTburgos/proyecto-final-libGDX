package trucoarg.utiles;

import trucoarg.personajesSolitario.CartaSolitario;
import java.util.ArrayList;
import java.util.List;

public class ColisionesDosJugadores {

    private final List<CartaSolitario> cartasJugador1EnMesa;
    private final List<CartaSolitario> cartasJugador2EnMesa;

    private int resultadoTirada = -1;
    private boolean colisionProcesada = false; // evita procesar la misma colisión varias veces

    // 🔢 Contadores de tiradas ganadas
    private int tiradasGanadasJ1 = 0;
    private int tiradasGanadasJ2 = 0;

    public ColisionesDosJugadores() {
        cartasJugador1EnMesa = new ArrayList<>();
        cartasJugador2EnMesa = new ArrayList<>();
    }

    public int verificarColisiones(List<CartaSolitario> manoJ1, List<CartaSolitario> manoJ2) {
        if (colisionProcesada) return -1;

        for (CartaSolitario carta1 : manoJ1) {
            for (CartaSolitario carta2 : manoJ2) {
                if (carta1.getQcyo().overlaps(carta2.getQcyo())) {

                    if (!cartasJugador1EnMesa.contains(carta1)) cartasJugador1EnMesa.add(carta1);
                    if (!cartasJugador2EnMesa.contains(carta2)) cartasJugador2EnMesa.add(carta2);
                }
            }
        }

        // Si hay al menos una carta jugada por cada jugador, comparar las ÚLTIMAS detectadas
        if (!cartasJugador1EnMesa.isEmpty() && !cartasJugador2EnMesa.isEmpty()) {
            CartaSolitario c1 = cartasJugador1EnMesa.get(cartasJugador1EnMesa.size() - 1);
            CartaSolitario c2 = cartasJugador2EnMesa.get(cartasJugador2EnMesa.size() - 1);

            if (c1.getNIVEL() > c2.getNIVEL()) {
                resultadoTirada = 1;
                tiradasGanadasJ1++;
            } else if (c2.getNIVEL() > c1.getNIVEL()) {
                resultadoTirada = 2;
                tiradasGanadasJ2++;
            } else {
                resultadoTirada = 0; // empate
            }

            colisionProcesada = true;

            // Limpiar para la próxima tirada
            cartasJugador1EnMesa.clear();
            cartasJugador2EnMesa.clear();

            return resultadoTirada;
        }

        return -1;
    }

    public void reset() {
        cartasJugador1EnMesa.clear();
        cartasJugador2EnMesa.clear();
        resultadoTirada = -1;
        colisionProcesada = false;
        tiradasGanadasJ1 = 0;
        tiradasGanadasJ2 = 0;
    }

    // 🔹 Métodos para obtener resultados
    public int getResultadoTirada() {
        return resultadoTirada;
    }

    public int getTiradasGanadasJ1() {
        return tiradasGanadasJ1;
    }

    public int getTiradasGanadasJ2() {
        return tiradasGanadasJ2;
    }

    // 🔹 Si querés verificar si alguien ganó la mano completa (2 de 3)
    public int verificarGanadorMano() {
        if (tiradasGanadasJ1 >= 2) return 1;
        if (tiradasGanadasJ2 >= 2) return 2;
        return -1; // aún no hay ganador de la mano
    }

    public void liberarColision() {
        colisionProcesada = false;
    }
}
