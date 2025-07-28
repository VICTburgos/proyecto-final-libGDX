package trucoarg.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import trucoarg.elementos.Imagen;
import trucoarg.elementos.Texto;
import trucoarg.ui.EntradasMenu;
import trucoarg.utiles.Configuracion;
import trucoarg.utiles.Recursos;
import trucoarg.utiles.Render;

public class PantallaTutorial implements Screen {
    private Imagen fondo;
    private SpriteBatch b;
    EntradasMenu entradas;

    Texto informacionSalida;

    @Override
    public void show() {
        fondo= new Imagen(Recursos.IMAGEN_TUTORIAL);
        fondo.dimensionarImg(Configuracion.ANCHO, Configuracion.ALTO);
        b = Render.batch;

        entradas = new EntradasMenu();
        Gdx.input.setInputProcessor(entradas);

        informacionSalida= new Texto(Recursos.FUENTE_MENU, 40, Color.WHITE, true);

    }

    @Override
    public void render(float delta) {
        Render.limpiarPantalla(1,1,1);

        if (salir()) return;

        b.begin();
        fondo.dibujar();
        informacionSalida.setTexto("ESCAPE para salir");
        informacionSalida.setPosicion(50,650);
        informacionSalida.dibujar();
        b.end();
    }


    private boolean salir() {
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
