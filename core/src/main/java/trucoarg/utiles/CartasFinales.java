package trucoarg.utiles;

import trucoarg.personajesSolitario.Carta;

public enum CartasFinales {
    // BASTO
    C1B(1, PalosCartas.BASTO, Recursos.ANCHO_BASTO),
    C2B(2, PalosCartas.BASTO, Recursos.DOS_BASTO),
    C3B(3, PalosCartas.BASTO, Recursos.TRES_BASTO),
    C4B(4, PalosCartas.BASTO, Recursos.CUATRO_BASTO),
    C5B(5, PalosCartas.BASTO, Recursos.CINCO_BASTO),
    C6B(6, PalosCartas.BASTO, Recursos.SEIS_BASTO),
    C7B(7, PalosCartas.BASTO, Recursos.SIETE_BASTO),
    C10B(10, PalosCartas.BASTO, Recursos.DIEZ_BASTO),
    C11B(11, PalosCartas.BASTO, Recursos.ONCE_BASTO),
    C12B(12, PalosCartas.BASTO, Recursos.DOCE_BASTO),

    // COPA
    C1C(1, PalosCartas.COPA, Recursos.ANCHO_COPA),
    C2C(2, PalosCartas.COPA, Recursos.DOS_COPA),
    C3C(3, PalosCartas.COPA, Recursos.TRES_COPA),
    C4C(4, PalosCartas.COPA, Recursos.CUATRO_COPA),
    C5C(5, PalosCartas.COPA, Recursos.CINCO_COPA),
    C6C(6, PalosCartas.COPA, Recursos.SEIS_COPA),
    C7C(7, PalosCartas.COPA, Recursos.SIETE_COPA),
    C10C(10, PalosCartas.COPA, Recursos.DIEZ_COPA),
    C11C(11, PalosCartas.COPA, Recursos.ONCE_COPA),
    C12C(12, PalosCartas.COPA, Recursos.DOCE_COPA),

    // ESPADA
    C1E(1, PalosCartas.ESPADA, Recursos.ANCHO_ESPADA),
    C2E(2, PalosCartas.ESPADA, Recursos.DOS_ESPADA),
    C3E(3, PalosCartas.ESPADA, Recursos.TRES_ESPADA),
    C4E(4, PalosCartas.ESPADA, Recursos.CUATRO_ESPADA),
    C5E(5, PalosCartas.ESPADA, Recursos.CINCO_ESPADA),
    C6E(6, PalosCartas.ESPADA, Recursos.SEIS_ESPADA),
    C7E(7, PalosCartas.ESPADA, Recursos.SIETE_ESPADA),
    C10E(10, PalosCartas.ESPADA, Recursos.DIEZ_ESPADA),
    C11E(11, PalosCartas.ESPADA, Recursos.ONCE_ESPADA),
    C12E(12, PalosCartas.ESPADA, Recursos.DOCE_ESPADA),

    // ORO
    C1O(1, PalosCartas.ORO, Recursos.ANCHO_ORO),
    C2O(2, PalosCartas.ORO, Recursos.DOS_ORO),
    C3O(3, PalosCartas.ORO, Recursos.TRES_ORO),
    C4O(4, PalosCartas.ORO, Recursos.CUATRO_ORO),
    C5O(5, PalosCartas.ORO, Recursos.CINCO_ORO),
    C6O(6, PalosCartas.ORO, Recursos.SEIS_ORO),
    C7O(7, PalosCartas.ORO, Recursos.SIETE_ORO),
    C10O(10, PalosCartas.ORO, Recursos.DIEZ_ORO),
    C11O(11, PalosCartas.ORO, Recursos.ONCE_ORO),
    C12O(12, PalosCartas.ORO, Recursos.DOCE_ORO);

    private final int numero;
    private final PalosCartas PALOS_CARTAS;
    private final String RUTA;

    CartasFinales(int numero, PalosCartas PALOS_CARTAS, String RUTA) {
        this.numero = numero;
        this.PALOS_CARTAS = PALOS_CARTAS;
        this.RUTA = RUTA;
    }

    public Carta crearCarta(){
        return new Carta(numero, PALOS_CARTAS, RUTA,390, 60);
    }
}

