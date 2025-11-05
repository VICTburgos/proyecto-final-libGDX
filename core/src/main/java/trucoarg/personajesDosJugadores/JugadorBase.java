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

    public JugadorBase(String nombre, MazoSolitario mazo) {
        this.nombre = nombre;
        this.mano = new ArrayList<>();

        for(int i = 0; i < 3; i++){
            carta = mazo.sacarCartita();
            if(carta != null){
                mano.add(carta);
            }
        }
    }

    public List<CartaSolitario> getMano() {
        return mano;
    }
}
