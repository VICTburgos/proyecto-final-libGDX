package trucoarg.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import trucoarg.elementos.Imagen;
import trucoarg.elementos.Texto;
import trucoarg.personajesSolitario.Carta;
import trucoarg.personajesSolitario.MazoSolitario;
import trucoarg.ui.EntradasSolitario;
import trucoarg.utiles.Configuracion;
import trucoarg.utiles.PalosCartas;
import trucoarg.utiles.Recursos;
import trucoarg.utiles.Render;

import static com.badlogic.gdx.Gdx.input;

public class PantallaUnJugador implements Screen {

    private Imagen fondo;
    private SpriteBatch b;
    EntradasSolitario entradasJuegoSoli;

    Texto informacionSalida;
    Texto ganador;

    private MazoSolitario mazoSolitario;
    private Carta cartaActual;

    private int contAciertos = 0;

    private static final Vector2 POSICION_INICIAL = new Vector2();

    @Override
    public void show() {
        fondo = new Imagen(Recursos.FONDOUNJUGADOR);
        fondo.dimensionarImg(Configuracion.ANCHO, Configuracion.ALTO);
        b = Render.batch;

        entradasJuegoSoli = new EntradasSolitario();
        input.setInputProcessor(entradasJuegoSoli);

        informacionSalida = new Texto(Recursos.FUENTE_MENU, 40, Color.WHITE, true);
        ganador = new Texto(Recursos.FUENTE_MENU, 60, Color.BLUE, true);

        mazoSolitario = new MazoSolitario();
        cartaActual = mazoSolitario.sacarCartita();


        float centroX = (Configuracion.ANCHO - 200) / 2f;
        float centroY = (Configuracion.ALTO - 500) / 2f;
        POSICION_INICIAL.set(centroX, centroY);

        if (cartaActual != null) {
            cartaActual.setPosicion(POSICION_INICIAL);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.input.setInputProcessor(entradasJuegoSoli);
        Render.limpiarPantalla(1, 1, 1);
        b.begin();
        fondo.dibujar();

        informacionSalida.setTexto("ESC para ir al menu principal...");
        informacionSalida.setPosicion(50, 650);
        informacionSalida.dibujar();

        if (cartaActual != null) {
            cartaActual.setSize(200, 300);
            cartaActual.dibujar(b);

        }

        if (contAciertos == 40) {
            ganar();
        }

        b.end();

        if (manejarEntradas()) return;
    }

    private boolean manejarEntradas() {
        if (entradasJuegoSoli.escape()) {
            Recursos.MUSICA_JUEGO.stop();
            Recursos.MUSICA_JUEGO.setPosition(0);
            Recursos.MUSICA_GENERAL.play();
            Render.app.setScreen(new PantallaMenu());
            return true;
        }

        if (entradasJuegoSoli.basto()) {
            if (cartaActual.getPALOS_CARTAS() == PalosCartas.BASTO) {
                System.out.println("Muuy bieeen, segui asi");
                contAciertos++;
                sacarNuevaCarta();
            } else {
                System.out.println("A curtirse, vas devuelta");
                reiniciarJuego();
            }
        }

        if (entradasJuegoSoli.copa()) {
            if (cartaActual.getPALOS_CARTAS() == PalosCartas.COPA) {
                System.out.println("Esas copas las quiero yo!");
                contAciertos++;
                sacarNuevaCarta();
            } else {
                System.out.println("Pero mira lo que venir a errar");
                reiniciarJuego();
            }
        }

        if (entradasJuegoSoli.oro()) {
            if (cartaActual.getPALOS_CARTAS() == PalosCartas.ORO) {
                System.out.println("oro...");
                contAciertos++;
                sacarNuevaCarta();
            } else {
                System.out.println("Bueno mas para mi...");
                reiniciarJuego();
            }
        }

        if (entradasJuegoSoli.espada()) {
            if (cartaActual.getPALOS_CARTAS() == PalosCartas.ESPADA) {
                System.out.println("Ay me pinché!");
                contAciertos++;
                sacarNuevaCarta();
            } else {
                System.out.println("...");
                reiniciarJuego();
            }
        }

        if (cartaActual != null) {
            cartaActual.mover(entradasJuegoSoli);
        }

        return false;
    }

        private void sacarNuevaCarta() {
        cartaActual = mazoSolitario.sacarCartita();
        if (cartaActual != null) {
            cartaActual.setPosicion(POSICION_INICIAL);
        }
    }

    private void ganar() {
        ganador.setTexto("GANASTE, MUY BIEN COMPAÑERO");
        ganador.setPosicion(200, 120);
        ganador.dibujar();
    }

    private void reiniciarJuego() {
        contAciertos = 0;
        mazoSolitario.reiniciarMazo();
        cartaActual = mazoSolitario.sacarCartita();

            if (cartaActual != null) {
                cartaActual.setPosicion(POSICION_INICIAL);
            }
     }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        Recursos.liberar();
        fondo.dispose();
        b.dispose();
    }
}
