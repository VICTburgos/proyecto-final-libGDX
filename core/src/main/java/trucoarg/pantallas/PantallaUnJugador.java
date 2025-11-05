package trucoarg.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import trucoarg.elementos.Imagen;
import trucoarg.elementos.Texto;
import trucoarg.personajesSolitario.CartaSolitario;
import trucoarg.personajesSolitario.HudJugadorSolitario;
import trucoarg.personajesSolitario.MazoSolitario;
import trucoarg.ui.EntradasSolitario;
import trucoarg.utiles.*;

import static com.badlogic.gdx.Gdx.input;

public class PantallaUnJugador implements Screen {

    private Imagen fondo;
    private SpriteBatch b;
    EntradasSolitario entradasJuegoSoli;
    HudJugadorSolitario estadisticas;

    Texto informacionSalida;
    Texto ganador;

    private MazoSolitario mazoSolitario;
    private CartaSolitario cartaActual;
    private ColisionesSolitario colisionesSolitario;

    private int contAciertos = 0;
    private int contRepeticiones=0;

    private static final Vector2 POSICION_INICIAL = new Vector2();
    float centroX = (Configuracion.ANCHO - 200) / 2f;
    float centroY = (Configuracion.ALTO - 500) / 2f;

    @Override
    public void show() {
        fondo = new Imagen(Recursos.FONDOUNJUGADOR);
        fondo.dimensionarImg(Configuracion.ANCHO, Configuracion.ALTO);
        b = Render.batch;

        entradasJuegoSoli = new EntradasSolitario();
        estadisticas= new HudJugadorSolitario(contAciertos, contRepeticiones);
        input.setInputProcessor(entradasJuegoSoli);

        informacionSalida = new Texto(Recursos.FUENTE_MENU, 40, Color.WHITE, true);
        ganador = new Texto(Recursos.FUENTE_MENU, 60, Color.BLUE, true);

        mazoSolitario = new MazoSolitario();
        cartaActual = mazoSolitario.sacarCartita();
        colisionesSolitario= new ColisionesSolitario();



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

        if(estadisticas!= null){
            estadisticas.render();
        }

        if (cartaActual != null) {
            cartaActual.setSize(200, 300);
            cartaActual.dibujar(b);
            colisionesSolitario.dibujarZonas();

        }


        if (contAciertos == 40) {
            ganar();
        }

        b.end();
        colisionesSolitario.render();

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
                sacarNuevaCarta();
            } else {
                System.out.println("A curtirse, vas devuelta");
                reiniciarJuego();
            }
        }

        if (entradasJuegoSoli.copa()) {
            if (cartaActual.getPALOS_CARTAS() == PalosCartas.COPA) {
                System.out.println("Esas copas las quiero yo!");
                sacarNuevaCarta();
            } else {
                System.out.println("Pero mira lo que venir a errar");
                reiniciarJuego();
            }
        }

        if (entradasJuegoSoli.oro()) {
            if (cartaActual.getPALOS_CARTAS() == PalosCartas.ORO) {
                System.out.println("oro...");
                sacarNuevaCarta();
            } else {
                System.out.println("Bueno mas para mi...");
                reiniciarJuego();
            }
        }

        if (entradasJuegoSoli.espada()) {
            if (cartaActual.getPALOS_CARTAS() == PalosCartas.ESPADA) {
                System.out.println("Ay me pinché!");
                sacarNuevaCarta();
            } else {
                System.out.println("...");
                reiniciarJuego();
            }
        }

        if (cartaActual != null) {
            cartaActual.mover(entradasJuegoSoli);
        }
        if (entradasJuegoSoli.enter()) {
            boolean acierto = colisionesSolitario.colision(cartaActual, mazoSolitario);
            if (acierto) {
                  sacarNuevaCarta();
            }
            else{
                reiniciarJuego();
            }
        }



        return false;
    }

        private void sacarNuevaCarta() {
        contAciertos++;
        estadisticas.setAciertos(contAciertos);
            System.out.println("tus aciertos son: "+ estadisticas.getAciertos());
        cartaActual = mazoSolitario.sacarCartita();
        if (cartaActual != null) {
            cartaActual.setPosicion(POSICION_INICIAL);
        }
    }

    private void ganar() {
        ganador.setTexto("GANASTE, MUY BIEN COMPAÑERO");
        ganador.setPosicion(centroX-200, centroY+100);
        ganador.dibujar();
    }

    private void reiniciarJuego() {
        contAciertos = 0;
        contRepeticiones++;
        estadisticas.setRepeticiones(contRepeticiones);
        estadisticas.setAciertos(contAciertos);
        System.out.println("Tus repes son"+ estadisticas.getRepeticiones());
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
