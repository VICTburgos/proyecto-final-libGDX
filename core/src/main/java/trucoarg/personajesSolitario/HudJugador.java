package trucoarg.personajesSolitario;

import box2dLight.BlendFunc;
import com.badlogic.gdx.graphics.Color;
import trucoarg.elementos.Texto;
import trucoarg.utiles.Configuracion;
import trucoarg.utiles.Recursos;

public class HudJugador {

    float centroY = (Configuracion.ALTO - 500) / 2f;
    private int aciertos;
    private int repeticiones;
    private final Texto textoAciertos;
    private final Texto textoRepeticiones;

    public HudJugador(int aciertos, int repeticiones) {
        this.aciertos = aciertos;
        this.repeticiones = repeticiones;

        textoAciertos = new Texto(Recursos.FUENTE_MENU, 40, Color.WHITE, true);
        textoRepeticiones = new Texto(Recursos.FUENTE_MENU, 40, Color.WHITE, true);

    }

    public void render() {
        textoAciertos.setTexto("Aciertos: " + aciertos);
        textoRepeticiones.setTexto("Repeticiones: " + repeticiones);

        textoAciertos.setPosicion(200 , centroY);
        textoAciertos.dibujar();

        textoRepeticiones.setPosicion(600 , centroY);
        textoRepeticiones.dibujar();
    }

    public int getAciertos() {
        return aciertos;
    }

    public void setAciertos(int aciertos) {
        this.aciertos = aciertos;
    }

    public int getRepeticiones() {
        return repeticiones;
    }

    public void setRepeticiones(int repeticiones) {
        this.repeticiones = repeticiones;
    }

    public void dispose() {
    }
}
