package trucoarg.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import trucoarg.elementos.Imagen;
import trucoarg.personajesDosJugadores.JuegoTruco;
import trucoarg.personajesDosJugadores.JugadorBase;
import trucoarg.personajesSolitario.CartaSolitario;
import trucoarg.utiles.Configuracion;
import trucoarg.utiles.Recursos;
import trucoarg.utiles.Render;

import java.util.List;

public class PantallaDosJugadores implements Screen {

    private Imagen fondo;
    private SpriteBatch batch;

    private JuegoTruco juego;
    private JugadorBase jugador1;
    private JugadorBase jugador2;

    @Override
    public void show() {
        fondo = new Imagen(Recursos.FONDODOSJUGADORES);
        fondo.dimensionarImg(Configuracion.ANCHO, Configuracion.ALTO);

        batch = Render.batch;

        juego = new JuegoTruco();
        jugador1 = juego.getJugador1();
        jugador2 = juego.getJugador2();

        posicionarCartasJugadorAbajo(jugador1.getMano());
        posicionarCartasJugadorArriba(jugador2.getMano());
    }

    private void posicionarCartasJugadorAbajo(List<CartaSolitario> mano) {
        float xInicial = Configuracion.ANCHO / 2f - 300;
        float y = 50;
        float separacion = 220;

        for (int i = 0; i < mano.size(); i++) {
            CartaSolitario carta = mano.get(i);
            carta.setSize(100, 200);
            carta.setPosicion(new com.badlogic.gdx.math.Vector2(xInicial + i * separacion, y));
        }
    }

    private void posicionarCartasJugadorArriba(List<CartaSolitario> mano) {
        float xInicial = Configuracion.ANCHO / 2f - 300;
        float y = Configuracion.ALTO - 350;
        float separacion = 220;

        for (int i = 0; i < mano.size(); i++) {
            CartaSolitario carta = mano.get(i);
            carta.setSize(100, 200);
            carta.setPosicion(new com.badlogic.gdx.math.Vector2(xInicial + i * separacion, y));
        }
    }

    @Override
    public void render(float delta) {
        Render.limpiarPantalla(0, 0, 0);

        batch.begin();

        fondo.dibujar();

        for (CartaSolitario carta : jugador1.getMano()) {
            carta.dibujar(batch);
        }

        for (CartaSolitario carta : jugador2.getMano()) {
            carta.dibujar(batch);
        }

        batch.end();

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            Render.app.setScreen(new PantallaMenu());
        }
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { fondo.dispose(); }
}
