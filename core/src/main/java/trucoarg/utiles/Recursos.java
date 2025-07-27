package trucoarg.utiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

public class Recursos {

    public static final String FONDOMENU= "imagenes/fondoMenu.png";
    public static final String FONDOUNJUGADOR= "imagenes/fondoUnJugador.png";
    public static final String IMAGEN_TUTORIAL= "imagenes/pantallaTutorial.png";

    public static final String FUENTE_MENU= "fuentes/Argentina.ttf";

    public static Music MUSICA_GENERAL;
    public static Music MUSICA_JUEGO;

    public static void cargarCanciones() {
        MUSICA_GENERAL = Gdx.audio.newMusic(Gdx.files.internal("musica/musicaGeneral.mp3"));
        MUSICA_JUEGO = Gdx.audio.newMusic(Gdx.files.internal("musica/musicaJuego.mp3"));

    }

    public static void liberar() {
        if (MUSICA_GENERAL != null) MUSICA_GENERAL.dispose();
        if (MUSICA_JUEGO != null) MUSICA_JUEGO.dispose();


    }



}
