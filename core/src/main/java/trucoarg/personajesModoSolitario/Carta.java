package trucoarg.personajesModoSolitario;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import trucoarg.utiles.PalosCartas;

public class Carta extends Sprite {
    private final int NUMERO;
    private final PalosCartas PALOS_CARTAS;


    public Carta(int NUMERO, PalosCartas PALOS_CARTAS, String rutaImagen) {
        super(new Texture(rutaImagen));
        this.NUMERO= NUMERO;
        this.PALOS_CARTAS= PALOS_CARTAS;
    }

    public PalosCartas getPALOS_CARTAS() {
        return PALOS_CARTAS;
    }

    public int getNUMERO() {
        return NUMERO;
    }


    public void dibujar(SpriteBatch b) {
        super.draw(b);
    }
}
