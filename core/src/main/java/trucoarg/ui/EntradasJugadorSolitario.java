package trucoarg.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class EntradasJugadorSolitario implements InputProcessor {
    private boolean basto = false, copa = false, oro = false, espada = false, escape = false;
    private boolean arriba = false, abajo = false, derecha = false, izquierda = false;

    public boolean escape() {
        boolean fuePresionado = escape;
        escape = false;
        return fuePresionado;
    }

    public boolean basto() {
        boolean fuePresionado = basto;
        basto = false;
        return fuePresionado;
    }

    public boolean copa() {
        boolean fuePresionado = copa;
        copa = false;
        return fuePresionado;
    }

    public boolean oro() {
        boolean fuePresionado = oro;
        oro = false;
        return fuePresionado;
    }

    public boolean espada() {
        boolean fuePresionado = espada;
        espada = false;
        return fuePresionado;
    }

    public boolean isArriba() {
        return arriba;
    }

    public boolean isAbajo() {
        return abajo;
    }

    public boolean isDerecha() {
        return derecha;
    }

    public boolean isIzquierda() {
        return izquierda;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.B) {
            basto = true;
        }
        if (keycode == Input.Keys.C) {
            copa = true;
        }
        if (keycode == Input.Keys.O) {
            oro = true;
        }
        if (keycode == Input.Keys.E) {
            espada = true;
        }
        if (keycode == Input.Keys.ESCAPE) {
            escape = true;
        }


        if (keycode == Input.Keys.W|| keycode == Input.Keys.UP) {
                arriba = true;
        }
        if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            abajo = true;
        }
        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
            izquierda = true;
        }
        if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
            derecha = true;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.B) {
            basto = false;
        }
        if (keycode == Input.Keys.C) {
            copa = false;
        }
        if (keycode == Input.Keys.O) {
            oro = false;
        }
        if (keycode == Input.Keys.E) {
            espada = false;
        }
        if (keycode == Input.Keys.ESCAPE) {
            escape = false;
        }

        if (keycode == Input.Keys.W || keycode == Input.Keys.UP) {
            arriba = false;
        }
        if (keycode == Input.Keys.S || keycode == Input.Keys.DOWN) {
            abajo = false;
        }
        if (keycode == Input.Keys.A || keycode == Input.Keys.LEFT) {
            izquierda = false;
        }
        if (keycode == Input.Keys.D || keycode == Input.Keys.RIGHT) {
            derecha = false;
        }

        return true;
    }

    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }
}
