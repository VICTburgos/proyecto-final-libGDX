package trucoarg.personajesSolitario;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import trucoarg.ui.EntradasSolitario;
import trucoarg.utiles.Configuracion;
import trucoarg.utiles.PalosCartas;

public class Carta extends Sprite {
    private final int NUMERO;
    private final PalosCartas PALOS_CARTAS;
    private Vector2 posicion;
    private float velocidad = 300f;

    private Rectangle qcyo;

    public Carta(int NUMERO, PalosCartas PALOS_CARTAS, String rutaImagen, float y, float x) {
        super(new Texture(rutaImagen));
        this.NUMERO = NUMERO;
        this.PALOS_CARTAS = PALOS_CARTAS;
        posicion = new Vector2(x, y);
        setPosition(posicion.x, posicion.y);

        qcyo= new Rectangle(posicion.x, posicion.y, getWidth(), getHeight());
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

    public void mover(EntradasSolitario entradas) {
        float delta = Gdx.graphics.getDeltaTime();
        boolean seMovio = false;

        if (entradas.isArriba()) {
            posicion.y += velocidad * delta;
            seMovio = true;
        }
        if (entradas.isAbajo()) {
            posicion.y -= velocidad * delta;
            seMovio = true;
        }
        if (entradas.isDerecha()) {
            posicion.x += velocidad * delta;
            seMovio = true;
        }
        if (entradas.isIzquierda()) {
            posicion.x -= velocidad * delta;
            seMovio = true;
        }

        if (seMovio) {
            posicion.x = MathUtils.clamp(posicion.x, 0, Configuracion.ANCHO - getWidth());
            posicion.y = MathUtils.clamp(posicion.y, 0, Configuracion.ALTO - getHeight());


            setPosition(posicion.x, posicion.y);
            qcyo.setPosition(posicion);

        }
    }

    public Rectangle getQcyo(){
        return qcyo;
    }


    public void setPosicion(Vector2 nuevaPosicion) {
        posicion.set(nuevaPosicion);
        setPosition(posicion.x, posicion.y);
    }


    public Vector2 getPosicion() {
        return new Vector2(posicion);
    }


    public void setVelocidad(float nuevaVelocidad) {
        this.velocidad = nuevaVelocidad;
    }


    public float getVelocidad() {
        return velocidad;
    }
}
