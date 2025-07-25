package trucoarg.elementos;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import trucoarg.utiles.Render;

public class Texto {

    BitmapFont fuente;
    private float x=0, y=0;
    private String texto= "";
    GlyphLayout glyphLayout;

    public Texto(String fuenteSoli, int tamanio, Color color, boolean sombra) {
        FreeTypeFontGenerator generador = new FreeTypeFontGenerator(Gdx.files.internal(fuenteSoli));
        FreeTypeFontGenerator.FreeTypeFontParameter parametros = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parametros.size = tamanio;
        parametros.color = color;
        if (sombra) {
            parametros.shadowColor = Color.BLACK;
            parametros.shadowOffsetX = 1;
            parametros.shadowOffsetY = 1;
        }
        fuente = generador.generateFont(parametros);
        glyphLayout= new GlyphLayout();

    }

    public void dibujar(){
        fuente.draw(Render.batch, texto, x, y);
    }

    public void setPosicion(float x, float y){
        this.x=x;
        this.y=y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
        glyphLayout.setText(fuente, texto);
    }

    public float getAncho(){
        return glyphLayout.width;
    }

    public float getAlto(){
        return glyphLayout.height;
    }

    public void setColor(Color color){
        fuente.setColor(color);
    }

    //cuidado con esto
    public Vector2 getPosicion(){
      return new Vector2(x, y);
    }
}

