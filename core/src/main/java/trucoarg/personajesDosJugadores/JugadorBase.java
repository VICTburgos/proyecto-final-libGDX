package trucoarg.personajesDosJugadores;

import com.badlogic.gdx.graphics.g2d.Sprite;
import trucoarg.personajesSolitario.MazoSolitario;
import trucoarg.personajesSolitario.CartaSolitario;

import java.util.ArrayList;
import java.util.List;

public class JugadorBase extends Sprite {
    private CartaSolitario carta;
    private String nombre;
    private List<CartaSolitario> mano;
    private boolean esMano; // <<--- acá se define si es mano o pie

    public JugadorBase(String nombre, MazoSolitario mazo, boolean esMano) {
        this.nombre = nombre;
        this.esMano = esMano;
        this.mano = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            carta = mazo.sacarCartita();
            if(carta != null){
                mano.add(carta);
            }
        }
    }

    public boolean esMano() {
        return esMano;
    }

    public void setEsMano(boolean esMano) {
        this.esMano = esMano;
    }

    public List<CartaSolitario> getMano() {
        return mano;
    }
}

