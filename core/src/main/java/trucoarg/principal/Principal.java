package trucoarg.principal;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import trucoarg.pantallas.PantallaMenu;
import trucoarg.utiles.Render;

public class Principal extends Game {
    public static SpriteBatch batch;

    @Override
    public void create() {
        batch = new SpriteBatch();
        Render.app = this;
        Render.inicializar();
        this.setScreen(new PantallaMenu());
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
