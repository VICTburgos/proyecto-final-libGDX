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

    private final List<CartaSolitario> jugadasJ1 = new ArrayList<>();
    private final List<CartaSolitario> jugadasJ2 = new ArrayList<>();

    private int puntosParaGanar = 15;

    private final Vector2[] posicionesJugadasJ1 = new Vector2[3];
    private final Vector2[] posicionesJugadasJ2 = new Vector2[3];

    private Boton btnTruco;
    private Boton btnRetruco;
    private Boton btnValeCuatro;

    private Boton btnEnvido;
    private Boton btnRealEnvido;
    private Boton btnFaltaEnvido;

    private Boton btnQuiero;
    private Boton btnNoQuiero;

    // 🆕 BOTÓN IR AL MAZO
    private Boton btnIrAlMazo;

    private BitmapFont fuente;
    private BitmapFont fuenteVictoria;
    private BitmapFont fuenteCanto;

    private String tipoCantoPendiente = null;

    private String mensajeTemporal = "";
    private float tiempoMensajeTemporal = 0f;
    private static final float DURACION_MENSAJE_TEMPORAL = 4f;

    private boolean juegoTerminado = false;
    private float tiempoVictoria = 0f;
    private static final float TIEMPO_MOSTRAR_VICTORIA = 3f;

    public PantallaDosJugadores(int puntosParaGanar) {
        this.puntosParaGanar = puntosParaGanar;
    }

    public PantallaDosJugadores() {
        this(15);
    }

    @Override
    public void show() {
        fondo = new Imagen(Recursos.FONDODOSJUGADORES);
        fondo.dimensionarImg(Configuracion.ANCHO, Configuracion.ALTO);
        batch = Render.batch;

        juego = new JuegoTruco(puntosParaGanar);
        jugador1 = juego.getJugador1();
        jugador2 = juego.getJugador2();

        configurarPosicionesMesa();
        crearBotones();
        posicionarCartasJugadorAbajo(jugador1.getMano());
        posicionarCartasJugadorArriba(jugador2.getMano());

        fuente = new BitmapFont();
        fuente.getData().setScale(2f);
        fuente.setColor(Color.WHITE);

        fuenteVictoria = new BitmapFont();
        fuenteVictoria.getData().setScale(4f);
        fuenteVictoria.setColor(Color.YELLOW);

        fuenteCanto = new BitmapFont();
        fuenteCanto.getData().setScale(5f);
        fuenteCanto.setColor(new Color(1f, 0.8f, 0.2f, 1f));

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
        float separacion = 10;

        Color azulArg = new Color(0.4f, 0.6f, 0.85f, 0.9f);
        Color violeta = new Color(0.6f, 0.3f, 0.8f, 0.9f);
        Color blanco = Color.WHITE;
        Color borde = new Color(0.2f, 0.4f, 0.6f, 1f);
        Color verde = new Color(0.2f, 0.7f, 0.3f, 0.9f);
        Color rojo = new Color(0.8f, 0.2f, 0.2f, 0.9f);
        Color naranja = new Color(0.9f, 0.5f, 0.1f, 0.9f); // Para "Ir al Mazo"

        float trucoPosY = Configuracion.ALTO / 2f + 100;
        btnTruco = new Boton("TRUCO", margen, trucoPosY, btnAncho, btnAlto);
        btnRetruco = new Boton("RETRUCO", margen, trucoPosY - btnAlto - separacion, btnAncho, btnAlto);
        btnValeCuatro = new Boton("VALE 4", margen, trucoPosY - (btnAlto + separacion) * 2, btnAncho, btnAlto);

        btnTruco.setColor(azulArg, blanco, borde);
        btnRetruco.setColor(azulArg, blanco, borde);
        btnValeCuatro.setColor(azulArg, blanco, borde);

        float envidoPosY = Configuracion.ALTO / 2f - 50;
        btnEnvido = new Boton("ENVIDO", margen, envidoPosY, btnAncho, btnAlto);
        btnRealEnvido = new Boton("REAL ENVIDO", margen, envidoPosY - btnAlto - separacion, btnAncho, btnAlto);
        btnFaltaEnvido = new Boton("FALTA ENVIDO", margen, envidoPosY - (btnAlto + separacion) * 2, btnAncho, btnAlto);

        btnEnvido.setColor(violeta, blanco, borde);
        btnRealEnvido.setColor(violeta, blanco, borde);
        btnFaltaEnvido.setColor(violeta, blanco, borde);

        float respuestaPosY = Configuracion.ALTO / 2f + 50;
        btnQuiero = new Boton("QUIERO", Configuracion.ANCHO - btnAncho - margen, respuestaPosY, btnAncho, btnAlto);
        btnNoQuiero = new Boton("NO QUIERO", Configuracion.ANCHO - btnAncho - margen, respuestaPosY - btnAlto - separacion, btnAncho, btnAlto);

        btnQuiero.setColor(verde, blanco, borde);
        btnNoQuiero.setColor(rojo, blanco, borde);

        // 🆕 BOTÓN IR AL MAZO - Abajo a la izquierda
        float mazoPosY = 100;
        btnIrAlMazo = new Boton("IR AL MAZO", margen, mazoPosY, btnAncho, btnAlto);
        btnIrAlMazo.setColor(naranja, blanco, borde);
    }

    private void mostrarMensajeTemporal(String mensaje) {
        mensajeTemporal = mensaje;
        tiempoMensajeTemporal = DURACION_MENSAJE_TEMPORAL;
    }

    private void actualizarEstadoBotones() {
        if (juegoTerminado) {
            ocultarTodosLosBotones();
            return;
        }

        boolean hayTrucoPendiente = juego.getGestorTruco().estaEsperandoRespuesta();
        boolean hayEnvidoPendiente = juego.getGestorEnvido().estaEsperandoRespuesta();

        if (hayTrucoPendiente || hayEnvidoPendiente) {
            int jugadorResponde = juego.getJugadorQueDebeResponder();

            if (hayTrucoPendiente) {
                tipoCantoPendiente = "truco";
                String cantoActual = juego.getGestorTruco().getCantoActual();

                btnTruco.setVisible(false);
                btnRetruco.setVisible(false);
                btnValeCuatro.setVisible(false);
                btnEnvido.setVisible(false);
                btnRealEnvido.setVisible(false);
                btnFaltaEnvido.setVisible(false);
                btnIrAlMazo.setVisible(false); // 🆕

                btnQuiero.setVisible(true);
                btnNoQuiero.setVisible(true);
                btnQuiero.setHabilitado(true);
                btnNoQuiero.setHabilitado(true);

            } else {
                tipoCantoPendiente = "envido";
                String cantoActual = juego.getGestorEnvido().getCantoActual();

                btnTruco.setVisible(false);
                btnRetruco.setVisible(false);
                btnValeCuatro.setVisible(false);
                btnIrAlMazo.setVisible(false); // 🆕

                btnQuiero.setVisible(true);
                btnNoQuiero.setVisible(true);
                btnQuiero.setHabilitado(true);
                btnNoQuiero.setHabilitado(true);

                boolean puedeSubirEnvido = juego.getGestorEnvido().puedeSubirConEnvido();
                boolean puedeSubirReal = juego.getGestorEnvido().puedeSubirConRealEnvido();
                boolean puedeSubirFalta = juego.getGestorEnvido().puedeSubirConFaltaEnvido();

                btnEnvido.setVisible(puedeSubirEnvido);
                btnEnvido.setHabilitado(puedeSubirEnvido);

                btnRealEnvido.setVisible(puedeSubirReal);
                btnRealEnvido.setHabilitado(puedeSubirReal);

                btnFaltaEnvido.setVisible(puedeSubirFalta);
                btnFaltaEnvido.setHabilitado(puedeSubirFalta);
            }

        } else {
            tipoCantoPendiente = null;

            btnQuiero.setVisible(false);
            btnNoQuiero.setVisible(false);

            boolean manoTerminada = juego.isManoTerminada();
            int tiradaActual = juego.getTiradaActual();

            btnTruco.setVisible(!manoTerminada);
            btnRetruco.setVisible(!manoTerminada);
            btnValeCuatro.setVisible(!manoTerminada);
            btnIrAlMazo.setVisible(!manoTerminada); // 🆕 Mostrar cuando no hay canto pendiente

            btnTruco.setHabilitado(!manoTerminada);
            btnRetruco.setHabilitado(!manoTerminada && juego.getGestorTruco().isCantoAceptado());
            btnValeCuatro.setHabilitado(!manoTerminada && juego.getGestorTruco().isCantoAceptado());
            btnIrAlMazo.setHabilitado(!manoTerminada); // 🆕 Siempre habilitado

            boolean puedeEnvido = !manoTerminada && tiradaActual == 1 && !juego.isEnvidoYaResuelto();

            btnEnvido.setVisible(puedeEnvido);
            btnRealEnvido.setVisible(puedeEnvido);
            btnFaltaEnvido.setVisible(puedeEnvido);

            btnEnvido.setHabilitado(puedeEnvido);
            btnRealEnvido.setHabilitado(puedeEnvido);
            btnFaltaEnvido.setHabilitado(puedeEnvido);

        }
    }

    private void ocultarTodosLosBotones() {
        btnTruco.setVisible(false);
        btnRetruco.setVisible(false);
        btnValeCuatro.setVisible(false);
        btnEnvido.setVisible(false);
        btnRealEnvido.setVisible(false);
        btnFaltaEnvido.setVisible(false);
        btnQuiero.setVisible(false);
        btnNoQuiero.setVisible(false);
        btnIrAlMazo.setVisible(false); // 🆕
    }

    private void verificarVictoria() {
        if (juego.hayGanador() && !juegoTerminado) {
            juegoTerminado = true;
            tiempoVictoria = 0f;
            int ganador = juego.getGanadorFinal();
            actualizarEstadoBotones();
        }
    }

    public void jugarCarta(CartaSolitario carta, int jugador) {
        if (juegoTerminado) return;

        boolean ok = juego.jugarCarta(jugador, carta);
        if (!ok) return;

        if (jugador == 1) {
            int idx = jugadasJ1.size();
            carta.setPosicion(posicionesJugadasJ1[idx]);
            jugadasJ1.add(carta);
        } else {
            int idx = jugadasJ2.size();
            carta.setPosicion(posicionesJugadasJ2[idx]);
            jugadasJ2.add(carta);
        }

        if (jugadasJ1.size() == jugadasJ2.size()) {
            juego.procesarTirada();

            if (juego.isManoTerminada()) {
                verificarVictoria();
                if (juegoTerminado) return;

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
        if (juegoTerminado) return;

        // 🆕 PROCESAR BOTÓN IR AL MAZO
        if (boton == btnIrAlMazo) {
            int turnoActual = juego.getTurnoActual();
            mostrarMensajeTemporal("¡Jugador " + turnoActual + " se va al mazo!");

            // Marcar la mano como terminada
            juego.terminarManoAlMazo();

            // El otro jugador gana la mano
            int ganador = (turnoActual == 1) ? 2 : 1;
            int puntosTruco = juego.getGestorTruco().getPuntos();

            if (ganador == 1) {
                juego.agregarPuntosJ1(puntosTruco);
            } else {
                juego.agregarPuntosJ2(puntosTruco);
            }

            verificarVictoria();
            if (juegoTerminado) return;

            // Reiniciar la mano completamente
            juego.reiniciarManoSiCorresponde();
            jugador1 = juego.getJugador1();
            jugador2 = juego.getJugador2();

            jugadasJ1.clear();
            jugadasJ2.clear();

            posicionarCartasJugadorAbajo(jugador1.getMano());
            posicionarCartasJugadorArriba(jugador2.getMano());

            actualizarEstadoBotones();
            return;
        }

        if (boton == btnQuiero || boton == btnNoQuiero) {
            if (!juego.hayCantoPendiente()) {
                return;
            }

            int jugadorResponde = juego.getJugadorQueDebeResponder();
            boolean quiero = (boton == btnQuiero);

            int resultado = -1;

            if (tipoCantoPendiente != null && tipoCantoPendiente.equals("truco")) {
                resultado = juego.responderCanto(jugadorResponde, quiero);

                if (resultado > 0) {
                    verificarVictoria();
                    if (juegoTerminado) return;
                    reiniciarManoVisual();
                } else if (resultado == 0) {
                    mostrarMensajeTemporal("¡QUIERO!");
                }

            } else if (tipoCantoPendiente != null && tipoCantoPendiente.equals("envido")) {
                resultado = juego.responderEnvido(jugadorResponde, quiero);

                verificarVictoria();
                if (juegoTerminado) return;

                if (resultado > 0) {
                } else if (resultado == 0) {
                    mostrarMensajeTemporal("¡QUIERO ENVIDO!");
                }
            }

            actualizarEstadoBotones();
            return;
        }

        int turno = juego.getTurnoActual();
        String tipoCanto = null;

        if (boton == btnTruco) {
            tipoCanto = "truco";
        } else if (boton == btnRetruco) {
            tipoCanto = "retruco";
        } else if (boton == btnValeCuatro) {
            tipoCanto = "vale cuatro";
        }

        if (tipoCanto != null) {
            if (juego.cantar(turno, tipoCanto)) {
                mostrarMensajeTemporal("¡" + tipoCanto.toUpperCase() + "!");
                actualizarEstadoBotones();
            } else {
                mensajeTemporal = "No puedes cantar " + tipoCanto.toUpperCase() + " ahora";
            }
            return;
        }

        String tipoEnvido = null;

        if (boton == btnEnvido) {
            tipoEnvido = "envido";
        } else if (boton == btnRealEnvido) {
            tipoEnvido = "real envido";
        } else if (boton == btnFaltaEnvido) {
            tipoEnvido = "falta envido";
        }

        if (tipoEnvido != null) {
            int jugadorQueCanta;

            if (juego.getGestorEnvido().estaEsperandoRespuesta()) {
                jugadorQueCanta = juego.getJugadorQueDebeResponder();
            } else {
                jugadorQueCanta = juego.getTurnoActual();
            }

            if (juego.cantarEnvido(jugadorQueCanta, tipoEnvido)) {
                mostrarMensajeTemporal("¡" + tipoEnvido.toUpperCase() + "!");
                actualizarEstadoBotones();
            }
            return;
        }
    }

    private void reiniciarManoVisual() {
        juego.reiniciarManoSiCorresponde();
        jugador1 = juego.getJugador1();
        jugador2 = juego.getJugador2();
        jugadasJ1.clear();
        jugadasJ2.clear();
        posicionarCartasJugadorAbajo(jugador1.getMano());
        posicionarCartasJugadorArriba(jugador2.getMano());
    }

    public Boton[] getBotones() {
        return new Boton[]{
            btnTruco, btnRetruco, btnValeCuatro,
            btnEnvido, btnRealEnvido, btnFaltaEnvido,
            btnQuiero, btnNoQuiero, btnIrAlMazo // 🆕
        };
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
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            volverAlMenuConMusica();
            return;
        }

        if (tiempoMensajeTemporal > 0) {
            tiempoMensajeTemporal -= delta;
            if (tiempoMensajeTemporal <= 0) {
                mensajeTemporal = "";
            }
        }

        if (juegoTerminado) {
            tiempoVictoria += delta;
            if (tiempoVictoria >= TIEMPO_MOSTRAR_VICTORIA) {
                volverAlMenuConMusica();
                return;
            }
        }

        Render.limpiarPantalla(0, 0, 0);
        batch.begin();

        fondo.dibujar();

        for (CartaSolitario c : jugador1.getMano()) c.dibujar(batch);
        for (CartaSolitario c : jugador2.getMano()) c.dibujar(batch);

        for (CartaSolitario c : jugadasJ1) c.dibujar(batch);
        for (CartaSolitario c : jugadasJ2) c.dibujar(batch);

        fuente.draw(batch, "J1: " + juego.getPuntosJ1() + " pts", 50, Configuracion.ALTO - 50);
        fuente.draw(batch, "J2: " + juego.getPuntosJ2() + " pts", 50, Configuracion.ALTO - 100);

        fuente.draw(batch, "ESC para salir", 50, 650);

        if (!mensajeTemporal.isEmpty()) {
            com.badlogic.gdx.graphics.g2d.GlyphLayout layout =
                new com.badlogic.gdx.graphics.g2d.GlyphLayout(fuenteCanto, mensajeTemporal);
            float anchoTexto = layout.width;
            float altoTexto = layout.height;

            fuenteCanto.draw(batch, mensajeTemporal,
                Configuracion.ANCHO / 2f - anchoTexto / 2f,
                Configuracion.ALTO / 2f + altoTexto / 2f);
        }

        if (juegoTerminado) {
            int ganador = juego.getGanadorFinal();
            String msgVictoria = "¡GANÓ JUGADOR " + ganador + "!";
            String msgPuntos = juego.getPuntosJ1() + " - " + juego.getPuntosJ2();

            fuenteVictoria.draw(batch, msgVictoria,
                Configuracion.ANCHO / 2f - 300,
                Configuracion.ALTO / 2f + 50);

            fuente.draw(batch, msgPuntos,
                Configuracion.ANCHO / 2f - 100,
                Configuracion.ALTO / 2f - 20);

            fuente.draw(batch, "Volviendo al menú...",
                Configuracion.ANCHO / 2f - 150,
                Configuracion.ALTO / 2f - 80);
        }

        if (btnTruco != null) btnTruco.dibujar(batch);
        if (btnRetruco != null) btnRetruco.dibujar(batch);
        if (btnValeCuatro != null) btnValeCuatro.dibujar(batch);
        if (btnEnvido != null) btnEnvido.dibujar(batch);
        if (btnRealEnvido != null) btnRealEnvido.dibujar(batch);
        if (btnFaltaEnvido != null) btnFaltaEnvido.dibujar(batch);
        if (btnQuiero != null) btnQuiero.dibujar(batch);
        if (btnNoQuiero != null) btnNoQuiero.dibujar(batch);
        if (btnIrAlMazo != null) btnIrAlMazo.dibujar(batch); // 🆕
        batch.end();
    }

    private void volverAlMenuConMusica() {
        if (Recursos.MUSICA_JUEGO != null) {
            Recursos.MUSICA_JUEGO.stop();
            Recursos.MUSICA_JUEGO.setPosition(0);
        }

        if (Recursos.MUSICA_GENERAL != null) {
            Recursos.MUSICA_GENERAL.play();
        }

        dispose();
        Render.app.setScreen(new PantallaMenu());
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
        btnEnvido.dispose();
        btnRealEnvido.dispose();
        btnFaltaEnvido.dispose();
        btnQuiero.dispose();
        btnNoQuiero.dispose();
        btnIrAlMazo.dispose(); // 🆕
        fuente.dispose();
        if (fuenteVictoria != null) fuenteVictoria.dispose();
        if (fuenteCanto != null) fuenteCanto.dispose();
    }
}
