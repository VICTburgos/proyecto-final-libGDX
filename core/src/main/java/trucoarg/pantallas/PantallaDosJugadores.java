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

    // Visual: cartas jugadas en mesa
    private final List<CartaSolitario> jugadasJ1 = new ArrayList<>();
    private final List<CartaSolitario> jugadasJ2 = new ArrayList<>();

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

        configurarPosicionesMesa();
        posicionarCartasJugadorAbajo(jugador1.getMano());
        posicionarCartasJugadorArriba(jugador2.getMano());

        Gdx.input.setInputProcessor(new EntradaDosJugadores(
            jugador1.getMano(),
            jugador2.getMano(),
            this
        ));
    }

    public void jugarCarta(CartaSolitario carta, int jugador) {

        // validamos turno mediante motor
        if (!juego.puedeJugar(jugador)) {
            System.out.println("NO ES TURNO: J" + jugador);
            return;
        }

        // intentamos jugar en el motor: si retorna true, la carta quedó jugada
        boolean ok = juego.jugarCarta(jugador, carta);
        if (!ok) return;

        // Ubicación visual
        if (jugador == 1) {
            int idx = jugadasJ1.size();
            carta.setPosicion(posicionesJugadasJ1[idx]);
            jugadasJ1.add(carta);
        } else {
            int idx = jugadasJ2.size();
            carta.setPosicion(posicionesJugadasJ2[idx]);
            jugadasJ2.add(carta);
        }

        // Si ambos ya jugaron la tirada → procesar tirada en motor
        if (jugadasJ1.size() == jugadasJ2.size()) {

            int resultado = juego.procesarTirada(); // esto NO reinicia mazo
            System.out.println("Resultado procesarTirada(): " + resultado);

            // Si la mano terminó, pedir reinicio AL MOTOR (solo si terminó)
            if (juego.isManoTerminada()) {
                System.out.println("La mano terminó. Se reiniciará la mano en el motor y la vista se actualizará.");
                // Actualizamos la vista: pedimos al motor reiniciar la mano completa (reparte nuevas cartas)
                juego.reiniciarManoSiCorresponde();

                // actualizamos referencias y visual
                jugador1 = juego.getJugador1();
                jugador2 = juego.getJugador2();

                // limpiar la mesa visualmente y reposicionar
                jugadasJ1.clear();
                jugadasJ2.clear();

                posicionarCartasJugadorAbajo(jugador1.getMano());
                posicionarCartasJugadorArriba(jugador2.getMano());

                System.out.println("--- Nueva mano visualizada ---");
            }
        }
    }

    private void configurarPosicionesMesa() {
        float cx = Configuracion.ANCHO / 2f;
        float cy = Configuracion.ALTO / 2f;
        posicionesJugadasJ1[0] = new Vector2(cx - 300, cy - 120);
        posicionesJugadasJ1[1] = new Vector2(cx - 50, cy - 120);
        posicionesJugadasJ1[2] = new Vector2(cx + 200, cy - 120);

        posicionesJugadasJ2[0] = new Vector2(cx - 300, cy + 40);
        posicionesJugadasJ2[1] = new Vector2(cx - 50, cy + 40);
        posicionesJugadasJ2[2] = new Vector2(cx + 200, cy + 40);
    }

    private void posicionarCartasJugadorAbajo(List<CartaSolitario> mano) {
        float x = Configuracion.ANCHO / 2f - 300;
        float y = Configuracion.ALTO - 650;
        float dx = 250;

        for (int i = 0; i < mano.size(); i++) {
            CartaSolitario c = mano.get(i);
            c.setSize(100, 200);
            c.setPosicion(new Vector2(x + i * dx, y));
            c.setYaJugadas(false); // asegurar flag visual
        }
    }

    private void posicionarCartasJugadorArriba(List<CartaSolitario> mano) {
        float x = Configuracion.ANCHO / 2f - 300;
        float y = Configuracion.ALTO - 220;
        float dx = 250;

        for (int i = 0; i < mano.size(); i++) {
            CartaSolitario c = mano.get(i);
            c.setSize(100, 200);
            c.setPosicion(new Vector2(x + i * dx, y));
            c.setYaJugadas(false);
        }
    }

    @Override
    public void render(float delta) {
        Render.limpiarPantalla(0, 0, 0);
        batch.begin();

        fondo.dibujar();

        // dibujar manos
        for (CartaSolitario c : jugador1.getMano()) c.dibujar(batch);
        for (CartaSolitario c : jugador2.getMano()) c.dibujar(batch);

        // dibujar mesa
        for (CartaSolitario c : jugadasJ1) c.dibujar(batch);
        for (CartaSolitario c : jugadasJ2) c.dibujar(batch);

        batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override public void dispose() { fondo.dispose(); }
}
