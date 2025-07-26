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

public class PantallaMenu implements Screen {

    private Imagen fondo;
    private SpriteBatch b;
    private Texto[] opciones = new Texto[5];
    private String[] textos = {"Un jugador", "Dos jugadores", "Opciones", "Tutorial", "Salir..."};
    private EntradasUsuario entradas;
    private int opc = 1;
    private float tiempo = 0;

    @Override
    public void show() {
        fondo = new Imagen(Recursos.FONDOMENU);
        fondo.dimensionarImg(Configuracion.ANCHO, Configuracion.ALTO);
        b = Render.batch;
        cargarOpcionesCentradas();
        entradas = new EntradasUsuario();
        Gdx.input.setInputProcessor(entradas);
    }

    @Override
    public void render(float delta) {
        tiempo += delta;

        manejarEntradas();
        actualizarColores();

        b.begin();
        fondo.dibujar();
        for (Texto t : opciones) {
            t.dibujar();
        }
        b.end();
    }

    private void manejarEntradas() {
        if (tiempo > 0.15f) {
            if (entradas.abajo()) {
                opc++;
                if (opc > opciones.length) opc = 1;
                tiempo = 0;
            }

            if (entradas.arriba()) {
                opc--;
                if (opc < 1) opc = opciones.length;
                tiempo = 0;
            }

            if (entradas.enter()) {
                ejecutarOpcion();
                tiempo = 0;
            }
        }
    }

    private void actualizarColores() {
        for (int i = 0; i < opciones.length; i++) {
            if (i == opc - 1) {
                opciones[i].setColor(Color.BLUE);
            } else {
                opciones[i].setColor(Color.WHITE);
            }
        }
    }
    private void cargarOpcionesCentradas() {
        int avance = 60;
        int yInicial = 500;

        for (int i = 0; i < opciones.length; i++) {
            opciones[i] = new Texto(Recursos.FUENTE_MENU, 60, Color.WHITE, true);
            opciones[i].setTexto(textos[i]);


            float xCentrado = (Configuracion.ANCHO - opciones[i].getAncho()) / 2;

            opciones[i].setPosicion(xCentrado, yInicial - (i * avance));
        }
    }

    private void ejecutarOpcion() {
        switch (opc) {
            case 1:
            Render.app.setScreen(new PantallaUnJugador());
                break;
            case 2:
                //Dos jugadores
                break;
            case 3:
                //opciones
                break;
            case 4:
                Render.app.setScreen(new PantallaTutorial());
                break;
            case 5:
                Gdx.app.exit();
                break;
        }
    }

    @Override public void resize(int width, int height) {

    }
    @Override public void pause() {

    }
    @Override public void resume() {

    }
    @Override public void hide() {

    }
    @Override public void dispose() {
        b.dispose();
        fondo.dispose();
    }
}
