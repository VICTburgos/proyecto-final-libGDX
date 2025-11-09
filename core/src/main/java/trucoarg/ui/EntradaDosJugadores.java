package trucoarg.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import trucoarg.personajesSolitario.CartaSolitario;
import java.util.List;

public class EntradaDosJugadores implements InputProcessor {

    private final List<CartaSolitario> cartasJugador1;
    private final List<CartaSolitario> cartasJugador2;


    public EntradaDosJugadores(List<CartaSolitario> cartasJugador1, List<CartaSolitario> cartasJugador2) {
        this.cartasJugador1 = cartasJugador1;
        this.cartasJugador2 = cartasJugador2;
    }


    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        float y = Gdx.graphics.getHeight() - screenY; // CORRECCIÓN del eje Y
        float x = screenX;

        for (CartaSolitario carta : cartasJugador1) {
            if (carta.fueClickeada(x, y)) {
                System.out.println("Click en carta del Jugador 1: " + carta.getNUMERO() + " de " + carta.getPALOS_CARTAS());
                carta.setPosicion(new Vector2(carta.getPosicion().x, carta.getPosicion().y + 20));
                return true;
            }
        }

        for (CartaSolitario carta : cartasJugador2) {
            if (carta.fueClickeada(x, y)) {
                System.out.println("Click en carta del Jugador 2: " + carta.getNUMERO() + " de " + carta.getPALOS_CARTAS());
                carta.setPosicion(new Vector2(carta.getPosicion().x, carta.getPosicion().y + 20));
                return true;
            }
        }

        return false;
    }


    // Métodos no usados
    @Override public boolean keyDown(int keycode) { return false; }
    @Override public boolean keyUp(int keycode) { return false; }
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }
}
