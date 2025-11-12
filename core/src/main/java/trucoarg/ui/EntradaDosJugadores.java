package trucoarg.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import trucoarg.personajesSolitario.CartaSolitario;
import trucoarg.utiles.Configuracion;

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
        float y = Gdx.graphics.getHeight() - screenY;
        float x = screenX;

        float centroX = (Configuracion.ANCHO / 2f) - 50;
        float centroY = (Configuracion.ALTO / 2f) - 100;

        for (CartaSolitario carta : cartasJugador1) {
            if (carta.fueClickeada(x, y)) {
                System.out.println("Click en carta del Jugador 1: " + carta.getNUMERO() + " de " + carta.getPALOS_CARTAS());
                carta.moverAlCentro(Gdx.graphics.getDeltaTime());
                return true;
            }
        }

        for (CartaSolitario carta : cartasJugador2) {
            if (carta.fueClickeada(x, y)) {
                System.out.println("Click en carta del Jugador 2: " + carta.getNUMERO() + " de " + carta.getPALOS_CARTAS());
                carta.moverAlCentro(Gdx.graphics.getDeltaTime());
                return true;
            }
        }

        return false;
    }



    @Override public boolean keyDown(int keycode) { return false; }
    @Override public boolean keyUp(int keycode) { return false; }
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }
}
