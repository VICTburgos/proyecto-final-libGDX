package trucoarg.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import trucoarg.elementos.Imagen;
import trucoarg.elementos.Texto;
import trucoarg.io.EntradasUsuario;
import trucoarg.utiles.Configuracion;
import trucoarg.utiles.Recursos;
import trucoarg.utiles.Render;

public class PantallaUnJugador implements Screen {

    private Imagen fondo;
    private SpriteBatch b;
    EntradasUsuario entradas;

    Texto informacionSalida;

    @Override
    public void show() {
        fondo= new Imagen(Recursos.FONDOUNJUGADOR);
        fondo.dimensionarImg(Configuracion.ANCHO, Configuracion.ALTO);
        b = Render.batch;

        entradas= new EntradasUsuario();
        Gdx.input.setInputProcessor(entradas);

        informacionSalida= new Texto(Recursos.FUENTE_MENU, 40, Color.WHITE, true);

    }

    @Override
    public void render(float delta) {
        Render.limpiarPantalla(1,1,1);
        if (manejarEntradas())return;
        b.begin();
        fondo.dibujar();
        informacionSalida.setTexto("ESC para ir al menu principal...");
        informacionSalida.setPosicion(50,650);
        informacionSalida.dibujar();
        b.end();
    }

    private boolean manejarEntradas() {
        if (entradas.escape()) {
            Recursos.MUSICA_JUEGO.stop();
            Recursos.MUSICA_JUEGO.setPosition(0);
            Recursos.MUSICA_GENERAL.play();
            Render.app.setScreen(new PantallaMenu());
            return true;
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
    }
}
