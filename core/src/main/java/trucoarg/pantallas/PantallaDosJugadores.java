package trucoarg.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import trucoarg.elementos.Imagen;
import trucoarg.personajesDosJugadores.JuegoTruco;
import trucoarg.personajesDosJugadores.JugadorBase;
import trucoarg.personajesSolitario.CartaSolitario;
import trucoarg.ui.EntradaDosJugadores;
import trucoarg.utiles.ColisionesDosJugadores;
import trucoarg.utiles.Configuracion;
import trucoarg.utiles.Recursos;
import trucoarg.utiles.Render;

import java.util.ArrayList;
import java.util.List;

public class PantallaDosJugadores implements Screen {

    private Imagen fondo;
    private SpriteBatch batch;

    private JuegoTruco juego;
    private JugadorBase jugador1;
    private JugadorBase jugador2;
    private boolean ganadorMostrado = false;


    private ColisionesDosJugadores colisiones;
    private int resultadoActual = -1;

    // NUEVO: listas de cartas jugadas
    private final List<CartaSolitario> jugadasJ1 = new ArrayList<>();
    private final List<CartaSolitario> jugadasJ2 = new ArrayList<>();

    // posiciones predeterminadas de jugadas
    private final Vector2[] posicionesJugadasJ1 = new Vector2[3];
    private final Vector2[] posicionesJugadasJ2 = new Vector2[3];

    @Override
    public void show() {
        fondo = new Imagen(Recursos.FONDODOSJUGADORES);
        fondo.dimensionarImg(Configuracion.ANCHO, Configuracion.ALTO);
        batch = Render.batch;

        juego = new JuegoTruco();
        jugador1 = juego.getJugador1();
        jugador2 = juego.getJugador2();
        colisiones = new ColisionesDosJugadores();

        // Definir posiciones de jugadas (izquierda, medio, derecha)
        float centroX = Configuracion.ANCHO / 2f;
        float centroY = Configuracion.ALTO / 2f;
        float separacionJugadas = 250f;

        posicionesJugadasJ1[0] = new Vector2(centroX - separacionJugadas, centroY - 100);
        posicionesJugadasJ1[1] = new Vector2(centroX - 50, centroY - 100);
        posicionesJugadasJ1[2] = new Vector2(centroX + separacionJugadas - 150, centroY - 100);

        posicionesJugadasJ2[0] = new Vector2(centroX - separacionJugadas, centroY + 50);
        posicionesJugadasJ2[1] = new Vector2(centroX - 50, centroY + 50);
        posicionesJugadasJ2[2] = new Vector2(centroX + separacionJugadas - 150, centroY + 50);

        // Mayor separación entre manos
        posicionarCartasJugadorAbajo(jugador1.getMano());
        posicionarCartasJugadorArriba(jugador2.getMano());

        // Input
        Gdx.input.setInputProcessor(new EntradaDosJugadores(jugador1.getMano(), jugador2.getMano(), this));
    }

    public void jugarCarta(CartaSolitario carta, int jugador) {
        List<CartaSolitario> jugadas = (jugador == 1) ? jugadasJ1 : jugadasJ2;
        Vector2[] posiciones = (jugador == 1) ? posicionesJugadasJ1 : posicionesJugadasJ2;

        if (jugadas.size() < 3) {
            carta.setPosicion(posiciones[jugadas.size()]);
            jugadas.add(carta);
        }

        if (jugadasJ1.size() == jugadasJ2.size()) {
            ganadorMostrado = false;          // permitir mostrar ganador otra vez
            colisiones.liberarColision();     // resetear solo la bandera de colisión
        }
    }


    private void posicionarCartasJugadorAbajo(List<CartaSolitario> mano) {
        float xInicial = Configuracion.ANCHO / 2f - 300;
        float y = Configuracion.ALTO - 650; // más arriba
        float separacion = 250; // más separadas

        for (int i = 0; i < mano.size(); i++) {
            CartaSolitario carta = mano.get(i);
            carta.setSize(100, 200);
            carta.setPosicion(new Vector2(xInicial + i * separacion, y));
        }
    }

    private void posicionarCartasJugadorArriba(List<CartaSolitario> mano) {
        float xInicial = Configuracion.ANCHO / 2f - 300;
        float y = Configuracion.ALTO - 200; // más arriba
        float separacion = 250;

        for (int i = 0; i < mano.size(); i++) {
            CartaSolitario carta = mano.get(i);
            carta.setSize(100, 200);
            carta.setPosicion(new Vector2(xInicial + i * separacion, y));
        }
    }

    @Override
    public void render(float delta) {
        Render.limpiarPantalla(0, 0, 0);

        batch.begin();
        fondo.dibujar();

        for (CartaSolitario carta : jugador1.getMano()) carta.dibujar(batch);
        for (CartaSolitario carta : jugador2.getMano()) carta.dibujar(batch);

        batch.end();

        resultadoActual = colisiones.verificarColisiones(jugadasJ1, jugadasJ2);

        if (resultadoActual != -1 && !ganadorMostrado) {
            switch (resultadoActual) {
                case 0:
                    System.out.println("Empate");
                    break;
                case 1:
                    System.out.println("Ganó Jugador 1");
                    break;
                case 2:
                    System.out.println("Ganó Jugador 2");
                    break;
            }

            ganadorMostrado = true;
        }

    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { fondo.dispose(); }
}
