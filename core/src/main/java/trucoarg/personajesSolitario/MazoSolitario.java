package trucoarg.personajesSolitario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import trucoarg.utiles.CartasFinales;

public class MazoSolitario {

    private final List<CartasFinales> disponibles;

    public MazoSolitario(){
        disponibles = new ArrayList<>();
        inicializar();
    }

    private void inicializar(){
        Collections.addAll(disponibles, CartasFinales.values());
        Collections.shuffle(disponibles);
    }

    public CartaSolitario sacarCartita(){
        if(disponibles.isEmpty()){
            return null;
        }
        return disponibles.remove(0).crearCarta();
    }

    public boolean estaVacio(){
        return disponibles.isEmpty();
    }

    public void reiniciarMazo(){
        disponibles.clear();
        inicializar();
    }
}
