package trucoarg.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import trucoarg.elementos.Imagen;
import trucoarg.ui.Boton;
import trucoarg.utiles.Configuracion;
import trucoarg.utiles.Recursos;
import trucoarg.utiles.Render;

public class PantallaSeleccionPuntos implements Screen {

    private Imagen fondo;
    private SpriteBatch batch;
    private BitmapFont tituloFuente;
    private BitmapFont subtituloFuente;

    private Boton btn15Puntos;
    private Boton btn30Puntos;

    private final Object gameInstance;

    public PantallaSeleccionPuntos(Object game) {
        this.gameInstance = game;
    }

    @Override
    public void show() {
        fondo = new Imagen(Recursos.FONDODOSJUGADORES);
        fondo.dimensionarImg(Configuracion.ANCHO, Configuracion.ALTO);
        batch = Render.batch;

        cargarFuentes();
        crearBotones();

        Gdx.input.setInputProcessor(new com.badlogic.gdx.InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                float y = Configuracion.ALTO - screenY;

                if (btn15Puntos.fueClickeado(screenX, y)) {
                    iniciarJuego(15);
                    return true;
                }

                if (btn30Puntos.fueClickeado(screenX, y)) {
                    iniciarJuego(30);
                    return true;
                }

                return false;
            }
        });
    }

    private void cargarFuentes() {
        try {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal(Recursos.FUENTE_MENU)
            );

            FreeTypeFontGenerator.FreeTypeFontParameter paramTitulo =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
            paramTitulo.size = 60;
            paramTitulo.color = Color.WHITE;
            paramTitulo.borderWidth = 3;
            paramTitulo.borderColor = new Color(0.2f, 0.4f, 0.6f, 1f);
            tituloFuente = generator.generateFont(paramTitulo);

            FreeTypeFontGenerator.FreeTypeFontParameter paramSubtitulo =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
            paramSubtitulo.size = 30;
            paramSubtitulo.color = new Color(0.9f, 0.9f, 0.9f, 1f);
            subtituloFuente = generator.generateFont(paramSubtitulo);

            generator.dispose();
        } catch (Exception e) {
            System.out.println("Error cargando fuentes: ");
            tituloFuente = new BitmapFont();
            tituloFuente.getData().setScale(4f);
            subtituloFuente = new BitmapFont();
            subtituloFuente.getData().setScale(2f);
        }
    }

    private void crearBotones() {
        float btnAncho = 300;
        float btnAlto = 100;
        float separacion = 30; // Espacio entre botones
        float centroY = Configuracion.ALTO / 2f - 50; // Más abajo del título

        // Calcular posiciones para centrar ambos botones
        float totalAncho = (btnAncho * 2) + separacion;
        float inicioX = (Configuracion.ANCHO / 2f) - (totalAncho / 2f);

        Color azulArg = new Color(0.4f, 0.6f, 0.85f, 0.9f);
        Color amarillo = new Color(1f, 0.8f, 0.2f, 0.9f);
        Color blanco = Color.WHITE;
        Color borde = new Color(0.2f, 0.4f, 0.6f, 1f);

        // Botón izquierdo (15 puntos)
        btn15Puntos = new Boton("15 PUNTOS",
            inicioX,
            centroY,
            btnAncho,
            btnAlto);
        btn15Puntos.setColor(azulArg, blanco, borde);

        // Botón derecho (30 puntos)
        btn30Puntos = new Boton("30 PUNTOS",
            inicioX + btnAncho + separacion,
            centroY,
            btnAncho,
            btnAlto);
        btn30Puntos.setColor(amarillo, new Color(0.2f, 0.2f, 0.2f, 1f), borde);
    }

    private void iniciarJuego(int puntosParaGanar) {
        System.out.println("Iniciando juego a " + puntosParaGanar + " puntos");

        if (gameInstance instanceof com.badlogic.gdx.Game) {
            PantallaDosJugadores pantallaJuego = new PantallaDosJugadores(puntosParaGanar, gameInstance);
            ((com.badlogic.gdx.Game) gameInstance).setScreen(pantallaJuego);
        } else {
            System.out.println("ERROR: No se pudo iniciar el juego (gameInstance no válido)");
        }
    }

    @Override
    public void render(float delta) {
        Render.limpiarPantalla(0.1f, 0.1f, 0.15f);

        batch.begin();

        fondo.dibujar();

        String titulo = "TRUCO ARGENTINO";
        float tituloX = Configuracion.ANCHO / 2f - 250;
        float tituloY = Configuracion.ALTO - 150;
        tituloFuente.draw(batch, titulo, tituloX, tituloY);

        String subtitulo = "Elegí los puntos para ganar";
        float subtituloX = Configuracion.ANCHO / 2f - 200;
        float subtituloY = Configuracion.ALTO - 250;
        subtituloFuente.draw(batch, subtitulo, subtituloX, subtituloY);

        btn15Puntos.dibujar(batch);
        btn30Puntos.dibujar(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        if (fondo != null) fondo.dispose();
        if (tituloFuente != null) tituloFuente.dispose();
        if (subtituloFuente != null) subtituloFuente.dispose();
        if (btn15Puntos != null) btn15Puntos.dispose();
        if (btn30Puntos != null) btn30Puntos.dispose();
    }
}
