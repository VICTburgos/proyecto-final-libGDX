package trucoarg.elementos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import trucoarg.utiles.Render;

public class Imagen {

    private Texture t;
    private Sprite s;

    public Imagen(String ruta){
        t= new Texture(ruta);
        s= new Sprite(t);
    }
    public void dibujar(){s.draw(Render.batch);}

    public void dimensionarImg(float ancho, float alto){s.setSize(ancho, alto);}

    public void setPosicion(float x, float y){
        s.setPosition(x, y);
    }

    public void dispose() {
        if (t != null) {
            t.dispose();
            t = null;
        }
    }
}
