package trucoarg.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import trucoarg.elementos.Imagen;
import trucoarg.elementos.Texto;
import trucoarg.personajesModoSolitario.Carta;
import trucoarg.personajesModoSolitario.MazoSolitario;
import trucoarg.ui.EntradasJugadorSolitario;
import trucoarg.ui.EntradasMenu;
import trucoarg.utiles.Configuracion;
import trucoarg.utiles.Recursos;
import trucoarg.utiles.Render;

public class PantallaUnJugador implements Screen {

    private Imagen fondo;
    private SpriteBatch b;
    EntradasJugadorSolitario entradasJuegoSoli;

    Texto informacionSalida;

    private  MazoSolitario mazoSolitario;
    private Carta cartaActual;

    @Override
    public void show() {
        fondo= new Imagen(Recursos.FONDOUNJUGADOR);
        fondo.dimensionarImg(Configuracion.ANCHO, Configuracion.ALTO);
        b = Render.batch;

        entradasJuegoSoli= new EntradasJugadorSolitario();
        Gdx.input.setInputProcessor(entradasJuegoSoli);

        informacionSalida= new Texto(Recursos.FUENTE_MENU, 40, Color.WHITE, true);

        mazoSolitario= new MazoSolitario();
        cartaActual= mazoSolitario.sacarCartita();

    }

    @Override
    public void render(float delta) {
        Render.limpiarPantalla(1,1,1);
        b.begin();
        fondo.dibujar();
        informacionSalida.setTexto("ESC para ir al menu principal...");
        informacionSalida.setPosicion(50,650);
        informacionSalida.dibujar();

            if (cartaActual != null) {
                cartaActual.setPosition(390, 60);
                cartaActual.setSize(200, 300);
                cartaActual.dibujar(b);
            }
        b.end();
        if (manejarEntradas())return;

    }

    private boolean manejarEntradas() {
        if (entradasJuegoSoli.escape()) {
            Recursos.MUSICA_JUEGO.stop();
            Recursos.MUSICA_JUEGO.setPosition(0);
            Recursos.MUSICA_GENERAL.play();
            Render.app.setScreen(new PantallaMenu());
            return true;
        }

        if(entradasJuegoSoli.basto()){
            cartaActual= mazoSolitario.sacarCartita();
        }
        return false;
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
