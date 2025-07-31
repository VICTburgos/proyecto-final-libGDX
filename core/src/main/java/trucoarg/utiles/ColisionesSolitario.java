package trucoarg.utiles;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import trucoarg.elementos.Imagen;

import java.awt.*;

public class ColisionesSolitario extends ApplicationAdapter {

    private SpriteBatch b;
    private ShapeRenderer shapeRendere;

    private Rectangle zonaBastoRect, zonaCopaRect, zonaOroRect, zonaEspadaRect;

    private Imagen zonaBasto, zonaCopa, zonaOro, zonaEspada;

    @Override
    public void create(){
        b= Render.batch;
        shapeRendere= Render.shapeRenderer;

        zonaBasto= new Imagen(Recursos.ZONA_BASTO);
        zonaCopa= new Imagen(Recursos.ZONA_COPA);
        zonaOro= new Imagen(Recursos.ZONA_ORO);
        zonaEspada= new Imagen(Recursos.ZONA_ESPADA);

        zonaBastoRect = new Rectangle(100, Gdx.graphics.getHeight()-150,100,120);
        zonaCopaRect = new Rectangle(100, Gdx.graphics.getHeight()-150,100,120);
        zonaOroRect = new Rectangle(100, Gdx.graphics.getHeight()-150,100,120);
        zonaEspadaRect = new Rectangle(100, Gdx.graphics.getHeight()-150,100,120);

    }

    @Override
    public void render(){
        Render.limpiarPantalla(1,1,1);
        b.begin();
        dibujarZonas();
        b.end();
        shapeRendere.begin(ShapeRenderer.ShapeType.Line);
        shapeRendere.setColor(0, 1, 0, 1);
        shapeRendere.rect();

        shapeRendere.end();
    }

    @Override
    public void  dispose(){
        b.dispose();
        shapeRendere.dispose();
        zonaEspada.dispose();
        zonaOro.dispose();
        zonaCopa.dispose();
        zonaBasto.dispose();
    }

    public void dibujarZonas(){
        zonaBasto.dibujar();
        zonaCopa.dibujar();
        zonaOro.dibujar();
        zonaEspada.dibujar();
    }


}
