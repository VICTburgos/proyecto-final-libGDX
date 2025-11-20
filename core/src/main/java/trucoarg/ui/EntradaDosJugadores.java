package trucoarg.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import trucoarg.pantallas.PantallaDosJugadores;
import trucoarg.personajesSolitario.CartaSolitario;
import java.util.List;

public class EntradaDosJugadores implements InputProcessor {

    private final List<CartaSolitario> cartasJugador1;
    private final List<CartaSolitario> cartasJugador2;
    private final PantallaDosJugadores pantalla;

    public EntradaDosJugadores(List<CartaSolitario> cartasJugador1,
                               List<CartaSolitario> cartasJugador2,
                               PantallaDosJugadores pantalla) {
        this.cartasJugador1 = cartasJugador1;
        this.cartasJugador2 = cartasJugador2;
        this.pantalla = pantalla;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Convertir coordenadas de pantalla a coordenadas de juego
        float x = screenX;
        float y = Gdx.graphics.getHeight() - screenY;

        System.out.println("Click detectado en: (" + x + ", " + y + ")");

        // Primero verificar clicks en botones
        Boton[] botones = pantalla.getBotones();
        if (botones != null) {
            for (Boton boton : botones) {
                if (boton != null && boton.fueClickeado(x, y)) {
                    System.out.println("Procesando click en botón: " + boton.getTexto());
                    pantalla.procesarClickBoton(boton);
                    return true;
                }
            }
        }

        // Luego verificar clicks en cartas del Jugador 1
        for (int i = 0; i < cartasJugador1.size(); i++) {
            CartaSolitario carta = cartasJugador1.get(i);

            if (carta.fueClickeada(x, y)) {
                if (carta.getYaJugadas()) return true;

                pantalla.jugarCarta(carta, 1);
                return true;
            }
        }

        // Finalmente verificar clicks en cartas del Jugador 2
        for (int i = 0; i < cartasJugador2.size(); i++) {
            CartaSolitario carta = cartasJugador2.get(i);

            if (carta.fueClickeada(x, y)) {
                if (carta.getYaJugadas()) return true;

                pantalla.jugarCarta(carta, 2);
                return true;
            }
        }

        return false;
    }

    // Métodos requeridos por InputProcessor
    @Override public boolean keyDown(int keycode) { return false; }
    @Override public boolean keyUp(int keycode) { return false; }
    @Override public boolean keyTyped(char character) { return false; }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }
}
