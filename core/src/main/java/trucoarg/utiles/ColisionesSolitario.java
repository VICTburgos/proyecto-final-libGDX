package trucoarg.utiles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import trucoarg.elementos.Imagen;
import trucoarg.personajesSolitario.Carta;
import trucoarg.personajesSolitario.MazoSolitario;
import com.badlogic.gdx.math.Rectangle;

public class ColisionesSolitario {

    private SpriteBatch b;
    public static ShapeRenderer shapeRendere;

    private Rectangle zonaBastoRect, zonaCopaRect, zonaOroRect, zonaEspadaRect;

    private Imagen zonaBasto, zonaCopa, zonaOro, zonaEspada;

    float anchoZonaImg = 150;
    float altoZonaImg = 250;


    public ColisionesSolitario() {
        b = Render.batch;
        shapeRendere = new ShapeRenderer();

        zonaBasto = new Imagen(Recursos.ZONA_BASTO);
        zonaCopa = new Imagen(Recursos.ZONA_COPA);
        zonaOro = new Imagen(Recursos.ZONA_ORO);
        zonaEspada = new Imagen(Recursos.ZONA_ESPADA);

        float margenSuperior = 100;
        float anchoZona = 150;
        float altoZona = 250;
        float separacion = 100;

        float totalAncho = 4 * anchoZona + 3 * separacion;
        float inicioX = (Configuracion.ANCHO - totalAncho) / 2;
        float y = Configuracion.ALTO - altoZona - margenSuperior;

        zonaBastoRect = new Rectangle(inicioX, y, anchoZona, altoZona);
        zonaCopaRect = new Rectangle(inicioX + (anchoZona + separacion), y, anchoZona, altoZona);
        zonaOroRect = new Rectangle(inicioX + 2 * (anchoZona + separacion), y, anchoZona, altoZona);
        zonaEspadaRect = new Rectangle(inicioX + 3 * (anchoZona + separacion), y, anchoZona, altoZona);

    }

    public void render() {
        b.begin();
        dibujarZonas();

        b.end();
        shapeRendere.begin(ShapeRenderer.ShapeType.Line);
        shapeRendere.setColor(0, 1, 0, 1);
        shapeRendere.rect(zonaBastoRect.x, zonaBastoRect.y, zonaBastoRect.width, zonaBastoRect.height);
        shapeRendere.setColor(0, 1, 1, 1);
        shapeRendere.rect(zonaCopaRect.x, zonaCopaRect.y, zonaCopaRect.width, zonaCopaRect.height);
        shapeRendere.setColor(0, 0, 1, 1);
        shapeRendere.rect(zonaOroRect.x, zonaOroRect.y, zonaOroRect.width, zonaOroRect.height);
        shapeRendere.setColor(1, 1, 0, 1);
        shapeRendere.rect(zonaEspadaRect.x, zonaEspadaRect.y, zonaEspadaRect.width, zonaEspadaRect.height);
        shapeRendere.end();
    }

    public boolean colision(Carta cartaActual, MazoSolitario mazoSolitario) {
        boolean colisionValida = false;

        if (cartaActual.getPALOS_CARTAS() == PalosCartas.BASTO && cartaActual.getQcyo().overlaps(zonaBastoRect)) {
            colisionValida = true;
        } else if (cartaActual.getPALOS_CARTAS() == PalosCartas.COPA && cartaActual.getQcyo().overlaps(zonaCopaRect)) {
            colisionValida = true;
        } else if (cartaActual.getPALOS_CARTAS() == PalosCartas.ORO && cartaActual.getQcyo().overlaps(zonaOroRect)) {
            colisionValida = true;
        } else if (cartaActual.getPALOS_CARTAS() == PalosCartas.ESPADA && cartaActual.getQcyo().overlaps(zonaEspadaRect)) {
            colisionValida = true;
        }


        if (!colisionValida) {
            mazoSolitario.reiniciarMazo();
        }

        return colisionValida;
    }


    public void dibujarZonas() {
        zonaBasto.dimensionarImg(anchoZonaImg, altoZonaImg);
        zonaBasto.setPosicion(zonaBastoRect.x, zonaBastoRect.y);
        zonaBasto.dibujar();

        zonaCopa.dimensionarImg(anchoZonaImg, altoZonaImg);
        zonaCopa.setPosicion(zonaCopaRect.x, zonaCopaRect.y);
        zonaCopa.dibujar();

        zonaOro.dimensionarImg(anchoZonaImg, altoZonaImg);
        zonaOro.setPosicion(zonaOroRect.x, zonaOroRect.y);
        zonaOro.dibujar();

        zonaEspada.dimensionarImg(anchoZonaImg, altoZonaImg);
        zonaEspada.setPosicion(zonaEspadaRect.x, zonaEspadaRect.y);
        zonaEspada.dibujar();
    }

    public void dispose() {
        b.dispose();
        shapeRendere.dispose();
        zonaEspada.dispose();
        zonaOro.dispose();
        zonaCopa.dispose();
        zonaBasto.dispose();
    }

}
