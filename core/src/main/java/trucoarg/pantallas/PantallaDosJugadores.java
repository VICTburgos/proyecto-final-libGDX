package trucoarg.pantallas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import trucoarg.elementos.Imagen;
import trucoarg.personajesDosJugadores.JuegoTruco;
import trucoarg.personajesDosJugadores.JugadorBase;
import trucoarg.personajesSolitario.CartaSolitario;
import trucoarg.ui.Boton;
import trucoarg.ui.EntradaDosJugadores;
import trucoarg.utiles.Configuracion;
import trucoarg.utiles.Recursos;
import trucoarg.utiles.Render;

import java.util.ArrayList;
import java.util.List;

public class PantallaDosJugadores implements Screen {

    private Imagen fondo;
    private SpriteBatch batch;

    private JuegoTruco juego;
    private JugadorBase jugador1;
    private JugadorBase jugador2;

    // Visual: cartas jugadas en mesa
    private final List<CartaSolitario> jugadasJ1 = new ArrayList<>();
    private final List<CartaSolitario> jugadasJ2 = new ArrayList<>();

    private final Vector2[] posicionesJugadasJ1 = new Vector2[3];
    private final Vector2[] posicionesJugadasJ2 = new Vector2[3];

    // Botones de canto
    private Boton btnTruco;
    private Boton btnRetruco;
    private Boton btnValeCuatro;
    private Boton btnQuiero;
    private Boton btnNoQuiero;

    // Fuente para mostrar información
    private BitmapFont fuente;

    // Texto de estado
    private String mensajeEstado = "";

    @Override
    public void show() {
        fondo = new Imagen(Recursos.FONDODOSJUGADORES);
        fondo.dimensionarImg(Configuracion.ANCHO, Configuracion.ALTO);
        batch = Render.batch;

        juego = new JuegoTruco();
        jugador1 = juego.getJugador1();
        jugador2 = juego.getJugador2();

        configurarPosicionesMesa();
        crearBotones();
        posicionarCartasJugadorAbajo(jugador1.getMano());
        posicionarCartasJugadorArriba(jugador2.getMano());

        fuente = new BitmapFont();
        fuente.getData().setScale(2f);
        fuente.setColor(Color.WHITE);

        actualizarEstadoBotones();

        Gdx.input.setInputProcessor(new EntradaDosJugadores(
            jugador1.getMano(),
            jugador2.getMano(),
            this
        ));
    }

    private void crearBotones() {
        float btnAncho = 150;
        float btnAlto = 50;
        float margen = 20;
        float centroX = Configuracion.ANCHO / 2f;

        // Botones de canto (izquierda)
        btnTruco = new Boton("TRUCO", margen, Configuracion.ALTO / 2f + 50, btnAncho, btnAlto);
        btnRetruco = new Boton("RETRUCO", margen, Configuracion.ALTO / 2f - 20, btnAncho, btnAlto);
        btnValeCuatro = new Boton("VALE 4", margen, Configuracion.ALTO / 2f - 90, btnAncho, btnAlto);

        // Botones de respuesta (derecha)
        btnQuiero = new Boton("QUIERO", Configuracion.ANCHO - btnAncho - margen,
            Configuracion.ALTO / 2f + 50, btnAncho, btnAlto);
        btnNoQuiero = new Boton("NO QUIERO", Configuracion.ANCHO - btnAncho - margen,
            Configuracion.ALTO / 2f - 20, btnAncho, btnAlto);

        // Estilo argentino (celeste y blanco)
        Color azulArg = new Color(0.4f, 0.6f, 0.85f, 0.9f);
        Color blanco = Color.WHITE;
        Color borde = new Color(0.2f, 0.4f, 0.6f, 1f);

        btnTruco.setColor(azulArg, blanco, borde);
        btnRetruco.setColor(azulArg, blanco, borde);
        btnValeCuatro.setColor(azulArg, blanco, borde);

        Color verde = new Color(0.2f, 0.7f, 0.3f, 0.9f);
        Color rojo = new Color(0.8f, 0.2f, 0.2f, 0.9f);

        btnQuiero.setColor(verde, blanco, borde);
        btnNoQuiero.setColor(rojo, blanco, borde);
    }

    private void actualizarEstadoBotones() {
        // Si hay un canto pendiente, solo mostrar botones de respuesta
        if (juego.hayCantoPendiente()) {
            int jugadorResponde = juego.getJugadorQueDebeResponder();

            btnTruco.setVisible(false);
            btnRetruco.setVisible(false);
            btnValeCuatro.setVisible(false);

            btnQuiero.setVisible(true);
            btnNoQuiero.setVisible(true);

            // AMBOS botones siempre habilitados cuando hay canto pendiente
            // porque el que debe responder es el jugadorResponde
            btnQuiero.setHabilitado(true);
            btnNoQuiero.setHabilitado(true);

            mensajeEstado = "Jugador " + jugadorResponde + " debe responder el canto";

            System.out.println("DEBUG: Canto pendiente. J" + jugadorResponde + " debe responder");
        } else {
            // Modo normal: mostrar botones de canto
            btnQuiero.setVisible(false);
            btnNoQuiero.setVisible(false);

            btnTruco.setVisible(true);
            btnRetruco.setVisible(true);
            btnValeCuatro.setVisible(true);

            int turno = juego.getTurnoActual();

            // Habilitar botones según las reglas
            btnTruco.setHabilitado(!juego.isManoTerminada() &&
                juego.puedeJugar(turno));
            btnRetruco.setHabilitado(!juego.isManoTerminada() &&
                juego.puedeJugar(turno));
            btnValeCuatro.setHabilitado(!juego.isManoTerminada() &&
                juego.puedeJugar(turno));

            mensajeEstado = "Turno: Jugador " + turno + " | Ronda " + juego.getTiradaActual();
        }
    }

    public void jugarCarta(CartaSolitario carta, int jugador) {
        // Validar turno mediante motor
        if (!juego.puedeJugar(jugador)) {
            System.out.println("NO ES TURNO: J" + jugador);
            mensajeEstado = "No es tu turno!";
            return;
        }

        // Intentar jugar en el motor
        boolean ok = juego.jugarCarta(jugador, carta);
        if (!ok) return;

        // Ubicación visual
        if (jugador == 1) {
            int idx = jugadasJ1.size();
            carta.setPosicion(posicionesJugadasJ1[idx]);
            jugadasJ1.add(carta);
        } else {
            int idx = jugadasJ2.size();
            carta.setPosicion(posicionesJugadasJ2[idx]);
            jugadasJ2.add(carta);
        }

        // Si ambos ya jugaron la tirada → procesar
        if (jugadasJ1.size() == jugadasJ2.size()) {
            int resultado = juego.procesarTirada();
            System.out.println("Resultado procesarTirada(): " + resultado);

            // Si la mano terminó
            if (juego.isManoTerminada()) {
                System.out.println("La mano terminó. Reiniciando...");
                mensajeEstado = "Mano terminada! J1: " + juego.getPuntosJ1() +
                    " - J2: " + juego.getPuntosJ2();

                juego.reiniciarManoSiCorresponde();
                jugador1 = juego.getJugador1();
                jugador2 = juego.getJugador2();

                jugadasJ1.clear();
                jugadasJ2.clear();

                posicionarCartasJugadorAbajo(jugador1.getMano());
                posicionarCartasJugadorArriba(jugador2.getMano());
            }
        }

        actualizarEstadoBotones();
    }

    public void procesarClickBoton(Boton boton) {
        System.out.println("DEBUG: procesarClickBoton() - Botón: " + boton.getTexto());

        int turno = juego.getTurnoActual();

        if (boton == btnTruco) {
            if (juego.cantar(turno, "truco")) {
                mensajeEstado = "Jugador " + turno + " cantó TRUCO!";
                System.out.println("DEBUG: J" + turno + " cantó TRUCO");
                actualizarEstadoBotones();
            } else {
                System.out.println("DEBUG: No se pudo cantar TRUCO");
            }
        } else if (boton == btnRetruco) {
            if (juego.cantar(turno, "retruco")) {
                mensajeEstado = "Jugador " + turno + " cantó RETRUCO!";
                System.out.println("DEBUG: J" + turno + " cantó RETRUCO");
                actualizarEstadoBotones();
            } else {
                System.out.println("DEBUG: No se pudo cantar RETRUCO");
            }
        } else if (boton == btnValeCuatro) {
            if (juego.cantar(turno, "vale cuatro")) {
                mensajeEstado = "Jugador " + turno + " cantó VALE CUATRO!";
                System.out.println("DEBUG: J" + turno + " cantó VALE CUATRO");
                actualizarEstadoBotones();
            } else {
                System.out.println("DEBUG: No se pudo cantar VALE CUATRO");
            }
        } else if (boton == btnQuiero) {
            int jugadorResponde = juego.getJugadorQueDebeResponder();
            System.out.println("DEBUG: Intentando QUIERO - Debe responder J" + jugadorResponde);

            int resultado = juego.responderCanto(jugadorResponde, true);
            System.out.println("DEBUG: Resultado respuesta QUIERO: " + resultado);

            if (resultado > 0) {
                // Alguien ganó la mano por el canto
                mensajeEstado = "Jugador " + resultado + " ganó la mano! J1: " +
                    juego.getPuntosJ1() + " - J2: " + juego.getPuntosJ2();

                juego.reiniciarManoSiCorresponde();
                jugador1 = juego.getJugador1();
                jugador2 = juego.getJugador2();
                jugadasJ1.clear();
                jugadasJ2.clear();
                posicionarCartasJugadorAbajo(jugador1.getMano());
                posicionarCartasJugadorArriba(jugador2.getMano());
            } else if (resultado == 0) {
                // QUIERO - se continúa jugando
                mensajeEstado = "QUIERO! Se juega al valor cantado";
            }
            actualizarEstadoBotones();

        } else if (boton == btnNoQuiero) {
            int jugadorResponde = juego.getJugadorQueDebeResponder();
            System.out.println("DEBUG: Intentando NO QUIERO - Debe responder J" + jugadorResponde);

            int resultado = juego.responderCanto(jugadorResponde, false);
            System.out.println("DEBUG: Resultado respuesta NO QUIERO: " + resultado);

            if (resultado > 0) {
                mensajeEstado = "NO QUIERO! Jugador " + resultado + " gana la mano. J1: " +
                    juego.getPuntosJ1() + " - J2: " + juego.getPuntosJ2();

                juego.reiniciarManoSiCorresponde();
                jugador1 = juego.getJugador1();
                jugador2 = juego.getJugador2();
                jugadasJ1.clear();
                jugadasJ2.clear();
                posicionarCartasJugadorAbajo(jugador1.getMano());
                posicionarCartasJugadorArriba(jugador2.getMano());
            }
            actualizarEstadoBotones();
        }
    }

    public Boton[] getBotones() {
        return new Boton[]{btnTruco, btnRetruco, btnValeCuatro, btnQuiero, btnNoQuiero};
    }

    private void configurarPosicionesMesa() {
        float cx = Configuracion.ANCHO / 2f;
        float cy = Configuracion.ALTO / 2f;
        posicionesJugadasJ1[0] = new Vector2(cx - 300, cy - 120);
        posicionesJugadasJ1[1] = new Vector2(cx - 50, cy - 120);
        posicionesJugadasJ1[2] = new Vector2(cx + 200, cy - 120);

        posicionesJugadasJ2[0] = new Vector2(cx - 300, cy + 40);
        posicionesJugadasJ2[1] = new Vector2(cx - 50, cy + 40);
        posicionesJugadasJ2[2] = new Vector2(cx + 200, cy + 40);
    }

    private void posicionarCartasJugadorAbajo(List<CartaSolitario> mano) {
        float x = Configuracion.ANCHO / 2f - 300;
        float y = Configuracion.ALTO - 650;
        float dx = 250;

        for (int i = 0; i < mano.size(); i++) {
            CartaSolitario c = mano.get(i);
            c.setSize(100, 200);
            c.setPosicion(new Vector2(x + i * dx, y));
            c.setYaJugadas(false);
        }
    }

    private void posicionarCartasJugadorArriba(List<CartaSolitario> mano) {
        float x = Configuracion.ANCHO / 2f - 300;
        float y = Configuracion.ALTO - 220;
        float dx = 250;

        for (int i = 0; i < mano.size(); i++) {
            CartaSolitario c = mano.get(i);
            c.setSize(100, 200);
            c.setPosicion(new Vector2(x + i * dx, y));
            c.setYaJugadas(false);
        }
    }

    @Override
    public void render(float delta) {
        Render.limpiarPantalla(0, 0, 0);
        batch.begin();

        fondo.dibujar();

        // Dibujar manos
        for (CartaSolitario c : jugador1.getMano()) c.dibujar(batch);
        for (CartaSolitario c : jugador2.getMano()) c.dibujar(batch);

        // Dibujar mesa
        for (CartaSolitario c : jugadasJ1) c.dibujar(batch);
        for (CartaSolitario c : jugadasJ2) c.dibujar(batch);

        // Dibujar información de puntos
        fuente.draw(batch, "J1: " + juego.getPuntosJ1() + " pts", 50, Configuracion.ALTO - 50);
        fuente.draw(batch, "J2: " + juego.getPuntosJ2() + " pts", 50, Configuracion.ALTO - 100);

        // Dibujar mensaje de estado (centrado)
        if (!mensajeEstado.isEmpty()) {
            fuente.draw(batch, mensajeEstado,
                Configuracion.ANCHO / 2f - 200,
                Configuracion.ALTO - 50);
        }

        batch.end();

        // Dibujar botones (requiere su propio batch)
        batch.begin();
        if (btnTruco != null) btnTruco.dibujar(batch);
        if (btnRetruco != null) btnRetruco.dibujar(batch);
        if (btnValeCuatro != null) btnValeCuatro.dibujar(batch);
        if (btnQuiero != null) btnQuiero.dibujar(batch);
        if (btnNoQuiero != null) btnNoQuiero.dibujar(batch);
        batch.end();
    }

    @Override public void resize(int width, int height) {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
    @Override
    public void dispose() {
        fondo.dispose();
        btnTruco.dispose();
        btnRetruco.dispose();
        btnValeCuatro.dispose();
        btnQuiero.dispose();
        btnNoQuiero.dispose();
        fuente.dispose();
    }
}
