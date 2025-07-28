package trucoarg.personajesModoSolitario;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import trucoarg.utiles.RecursoCartas;

public class MazoSolitario {

    private final List<RecursoCartas> disponibles;

    public MazoSolitario(){
        disponibles = new ArrayList<>();
        Collections.addAll(disponibles, RecursoCartas.values());
        Collections.shuffle(disponibles);
    }

    public Carta sacarCartita(){
        if(disponibles.isEmpty()){
            return null;
        }
        return disponibles.remove(0).crearCarta();
    }

    public boolean estaVacio(){
        return disponibles.isEmpty();
    }
}
