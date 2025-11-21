package trucoarg.personajesDosJugadores;

import trucoarg.personajesSolitario.CartaSolitario;
import java.util.List;

/**
 * Clase responsable de calcular el envido de una mano
 * Principio de Responsabilidad Única: solo calcula envidos
 */
public class CalculadoraEnvido {

    /**
     * Calcula el envido de una mano de 3 cartas
     * @param mano Lista de 3 cartas
     * @return El valor de envido (0-33)
     */
    public int calcularEnvido(List<CartaSolitario> mano) {
        int mejorEnvido = 0;

        // Buscar parejas del mismo palo
        for (int i = 0; i < mano.size(); i++) {
            for (int j = i + 1; j < mano.size(); j++) {
                CartaSolitario carta1 = mano.get(i);
                CartaSolitario carta2 = mano.get(j);

                if (mismoPalo(carta1, carta2)) {
                    int valor1 = getValorEnvido(carta1);
                    int valor2 = getValorEnvido(carta2);
                    int envido = 20 + valor1 + valor2;
                    mejorEnvido = Math.max(mejorEnvido, envido);
                }
            }
        }

        // Si no hay pareja, tomar la carta más alta
        if (mejorEnvido == 0) {
            for (CartaSolitario carta : mano) {
                mejorEnvido = Math.max(mejorEnvido, getValorEnvido(carta));
            }
        }

        return mejorEnvido;
    }

    /**
     * Compara dos envidos y determina el ganador
     * @return 1 si gana envido1, 2 si gana envido2, 0 si empate
     */
    public int compararEnvido(int envido1, int envido2) {
        if (envido1 > envido2) return 1;
        if (envido2 > envido1) return 2;
        return 0; // Empate (se resuelve por "mano")
    }

    /**
     * Obtiene el valor de una carta para envido
     * Figuras (10, 11, 12) valen 0
     */
    private int getValorEnvido(CartaSolitario carta) {
        int numero = carta.getNUMERO();

        // Las figuras valen 0 en envido
        if (numero >= 10) return 0;

        // Los números del 1-7 valen su valor nominal
        return numero;
    }

    /**
     * Verifica si dos cartas son del mismo palo
     */
    private boolean mismoPalo(CartaSolitario carta1, CartaSolitario carta2) {
        // Asumiendo que CartaSolitario tiene un método para obtener el palo
        // Si no existe, deberás agregarlo o usar otro método
        return carta1.getPALOS_CARTAS().equals(carta2.getPALOS_CARTAS());
    }
}
