package trucoarg.utiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import trucoarg.principal.Principal;

public class Render {
    public static SpriteBatch batch;
    public static Principal app;

    public static void inicializar() {
        batch = new SpriteBatch();
    }

    public static void limpiarPantalla(float r, float g, float b){
        Gdx.gl.glClearColor(r, g, b, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
