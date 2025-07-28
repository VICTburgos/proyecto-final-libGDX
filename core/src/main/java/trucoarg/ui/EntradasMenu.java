package trucoarg.ui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class EntradasMenu implements InputProcessor {

    private boolean abajo = false, arriba = false, enter = false, escape=false;

    public boolean abajo() {
        return abajo;
    }

    public boolean arriba() {
        return arriba;
    }

    public boolean enter() {
        boolean fuePresionado = enter;
        enter = false;
        return fuePresionado;
    }

    public boolean escape() {
        boolean fuePresionado = escape;
        escape = false;
        return fuePresionado;
    }


    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.DOWN) {
            abajo = true;
        }

        if (keycode == Input.Keys.UP) {
            arriba = true;
        }

        if (keycode == Input.Keys.ENTER) {
            enter = true;
        }

        if(keycode== Input.Keys.ESCAPE){
            escape= true;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.DOWN) {
            abajo = false;
        }

        if (keycode == Input.Keys.UP) {
            arriba = false;
        }

        if (keycode == Input.Keys.ENTER) {
            enter = false; // por si las duvidas
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
