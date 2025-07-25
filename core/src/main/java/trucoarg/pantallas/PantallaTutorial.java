package trucoarg.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import trucoarg.elementos.Imagen;
import trucoarg.io.EntradasUsuario;
import trucoarg.utiles.Configuracion;
import trucoarg.utiles.Recursos;
import trucoarg.utiles.Render;

public class PantallaTutorial implements Screen {
    private Imagen fondo;
    private SpriteBatch b;
    EntradasUsuario entradas;


    @Override
    public void show() {
        fondo= new Imagen(Recursos.IMAGEN_TUTORIAL);
        fondo.dimensionarImg(Configuracion.ANCHO, Configuracion.ALTO);
        b = Render.batch;

        entradas = new EntradasUsuario();
        Gdx.input.setInputProcessor(entradas);

    }

    @Override
    public void render(float delta) {

        if (manejarEntradas()) return;

        b.begin();
        fondo.dibujar();
        b.end();
    }


    private boolean manejarEntradas() {
        if (entradas.escape()) {
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
        fondo.dispose();
    }
}
