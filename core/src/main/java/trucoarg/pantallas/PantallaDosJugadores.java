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

    private int puntosParaGanar = 15;

    private final Vector2[] posicionesJugadasJ1 = new Vector2[3];
    private final Vector2[] posicionesJugadasJ2 = new Vector2[3];

    // Botones de canto de TRUCO (izquierda)
    private Boton btnTruco;
    private Boton btnRetruco;
    private Boton btnValeCuatro;

    // Botones de canto de ENVIDO (izquierda, debajo del truco)
    private Boton btnEnvido;
    private Boton btnRealEnvido;
    private Boton btnFaltaEnvido;

    // Botones de respuesta (derecha)
    private Boton btnQuiero;
    private Boton btnNoQuiero;

    // Fuente para mostrar información
    private BitmapFont fuente;
    private BitmapFont fuenteVictoria; // 🆕 Fuente grande para mostrar victoria

    // Texto de estado
    private String mensajeEstado = "";

    // Tipo de canto pendiente ("truco" o "envido")
    private String tipoCantoPendiente = null;

    // 🆕 CONTROL DE VICTORIA
    private boolean juegoTerminado = false;
    private float tiempoVictoria = 0f;
    private static final float TIEMPO_MOSTRAR_VICTORIA = 3f; // 3 segundos
    private Object gameInstance; // Referencia al Game principal

    // 🆕 CONSTRUCTORES CON GAME
    public PantallaDosJugadores(int puntosParaGanar, Object game) {
        this.puntosParaGanar = puntosParaGanar;
        this.gameInstance = game;
    }

    public PantallaDosJugadores(Object game) {
        this(15, game);
    }

    // Constructores legacy (sin game)
    public PantallaDosJugadores(int puntosParaGanar) {
        this(puntosParaGanar, null);
    }

    public PantallaDosJugadores() {
        this(15, null);
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

        // 🆕 Fuente grande para victoria
        fuenteVictoria = new BitmapFont();
        fuenteVictoria.getData().setScale(4f);
        fuenteVictoria.setColor(Color.YELLOW);

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
    }

    private void actualizarEstadoBotones() {
        // 🆕 Si el juego terminó, ocultar todos los botones
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
                mensajeEstado = "J" + jugadorResponde + " debe responder " + cantoActual.toUpperCase();

                btnTruco.setVisible(false);
                btnRetruco.setVisible(false);
                btnValeCuatro.setVisible(false);
                btnEnvido.setVisible(false);
                btnRealEnvido.setVisible(false);
                btnFaltaEnvido.setVisible(false);

                btnQuiero.setVisible(true);
                btnNoQuiero.setVisible(true);
                btnQuiero.setHabilitado(true);
                btnNoQuiero.setHabilitado(true);

            } else {
                tipoCantoPendiente = "envido";
                String cantoActual = juego.getGestorEnvido().getCantoActual();
                mensajeEstado = "J" + jugadorResponde + " puede QUIERO/NO QUIERO o SUBIR desde " + cantoActual.toUpperCase();

                btnTruco.setVisible(false);
                btnRetruco.setVisible(false);
                btnValeCuatro.setVisible(false);

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

            btnTruco.setHabilitado(!manoTerminada);
            btnRetruco.setHabilitado(!manoTerminada && juego.getGestorTruco().isCantoAceptado());
            btnValeCuatro.setHabilitado(!manoTerminada && juego.getGestorTruco().isCantoAceptado());

            boolean puedeEnvido = !manoTerminada && tiradaActual == 1 && !juego.isEnvidoYaResuelto();

            btnEnvido.setVisible(puedeEnvido);
            btnRealEnvido.setVisible(puedeEnvido);
            btnFaltaEnvido.setVisible(puedeEnvido);

            btnEnvido.setHabilitado(puedeEnvido);
            btnRealEnvido.setHabilitado(puedeEnvido);
            btnFaltaEnvido.setHabilitado(puedeEnvido);

            mensajeEstado = "Turno: J" + juego.getTurnoActual() + " | Ronda " + juego.getTiradaActual();
        }
    }

    // 🆕 Método para ocultar todos los botones
    private void ocultarTodosLosBotones() {
        btnTruco.setVisible(false);
        btnRetruco.setVisible(false);
        btnValeCuatro.setVisible(false);
        btnEnvido.setVisible(false);
        btnRealEnvido.setVisible(false);
        btnFaltaEnvido.setVisible(false);
        btnQuiero.setVisible(false);
        btnNoQuiero.setVisible(false);
    }

    // 🆕 Método para verificar victoria y congelar el juego
    private void verificarVictoria() {
        if (juego.hayGanador() && !juegoTerminado) {
            juegoTerminado = true;
            tiempoVictoria = 0f;
            int ganador = juego.getGanadorFinal();
            mensajeEstado = "¡¡¡GANÓ JUGADOR " + ganador + "!!!";
            System.out.println("🏆 FIN DEL JUEGO - Ganador: J" + ganador);
            actualizarEstadoBotones();
        }
    }

    public void jugarCarta(CartaSolitario carta, int jugador) {
        if (juegoTerminado) return; // 🆕 No permitir jugar si terminó

        if (!juego.puedeJugar(jugador)) {
            System.out.println("NO ES TURNO: J" + jugador);
            mensajeEstado = "No es tu turno!";
            return;
        }

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
            int resultado = juego.procesarTirada();
            System.out.println("Resultado procesarTirada(): " + resultado);

            if (juego.isManoTerminada()) {
                System.out.println("La mano terminó. Reiniciando...");

                verificarVictoria(); // 🆕
                if (juegoTerminado) return;

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
        if (juegoTerminado) return; // 🆕 No procesar clicks si terminó

        System.out.println("DEBUG: procesarClickBoton() - Botón: " + boton.getTexto());

        if (boton == btnQuiero || boton == btnNoQuiero) {
            if (!juego.hayCantoPendiente()) {
                System.out.println("DEBUG: No hay canto pendiente para responder");
                return;
            }

            int jugadorResponde = juego.getJugadorQueDebeResponder();
            boolean quiero = (boton == btnQuiero);

            System.out.println("DEBUG: J" + jugadorResponde + " responde: " + (quiero ? "QUIERO" : "NO QUIERO"));

            int resultado = -1;

            if (tipoCantoPendiente != null && tipoCantoPendiente.equals("truco")) {
                resultado = juego.responderCanto(jugadorResponde, quiero);

                if (resultado > 0) {
                    verificarVictoria(); // 🆕
                    if (juegoTerminado) return;

                    mensajeEstado = "J" + resultado + " ganó la mano! J1: " +
                        juego.getPuntosJ1() + " - J2: " + juego.getPuntosJ2();

                    reiniciarManoVisual();
                } else if (resultado == 0) {
                    mensajeEstado = "QUIERO! Se juega al valor cantado";
                }

            } else if (tipoCantoPendiente != null && tipoCantoPendiente.equals("envido")) {
                resultado = juego.responderEnvido(jugadorResponde, quiero);

                verificarVictoria(); // 🆕
                if (juegoTerminado) return;

                if (resultado > 0) {
                    mensajeEstado = "J" + resultado + " ganó 1 punto (NO QUIERO)! J1: " +
                        juego.getPuntosJ1() + " - J2: " + juego.getPuntosJ2();
                } else if (resultado == 0) {
                    mensajeEstado = "QUIERO ENVIDO! Mostrando cartas... J1: " +
                        juego.getPuntosJ1() + " - J2: " + juego.getPuntosJ2();
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
            System.out.println("DEBUG: J" + turno + " intenta cantar " + tipoCanto.toUpperCase());

            if (juego.cantar(turno, tipoCanto)) {
                mensajeEstado = "J" + turno + " cantó " + tipoCanto.toUpperCase() + "!";
                System.out.println("DEBUG: Canto exitoso");
                actualizarEstadoBotones();
            } else {
                System.out.println("DEBUG: No se pudo cantar " + tipoCanto);
                mensajeEstado = "No puedes cantar " + tipoCanto.toUpperCase() + " ahora";
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
                System.out.println("DEBUG: J" + jugadorQueCanta + " está SUBIENDO con " + tipoEnvido.toUpperCase());
            } else {
                jugadorQueCanta = juego.getTurnoActual();
                System.out.println("DEBUG: J" + jugadorQueCanta + " intenta cantar " + tipoEnvido.toUpperCase());
            }

            if (juego.cantarEnvido(jugadorQueCanta, tipoEnvido)) {
                mensajeEstado = "J" + jugadorQueCanta + " cantó " + tipoEnvido.toUpperCase() + "!";
                System.out.println("DEBUG: Canto de envido exitoso");
                actualizarEstadoBotones();
            } else {
                System.out.println("DEBUG: No se pudo cantar " + tipoEnvido);
                mensajeEstado = "No puedes cantar " + tipoEnvido.toUpperCase() + " ahora";
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
            btnQuiero, btnNoQuiero
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
        // 🆕 CONTROL DE TIEMPO DE VICTORIA
        if (juegoTerminado) {
            tiempoVictoria += delta;
            if (tiempoVictoria >= TIEMPO_MOSTRAR_VICTORIA) {
                volverAlMenu();
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

        // 🆕 MENSAJE DE VICTORIA GRANDE
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
        } else if (!mensajeEstado.isEmpty()) {
            fuente.draw(batch, mensajeEstado,
                Configuracion.ANCHO / 2f - 200,
                Configuracion.ALTO - 50);
        }

        if (btnTruco != null) btnTruco.dibujar(batch);
        if (btnRetruco != null) btnRetruco.dibujar(batch);
        if (btnValeCuatro != null) btnValeCuatro.dibujar(batch);
        if (btnEnvido != null) btnEnvido.dibujar(batch);
        if (btnRealEnvido != null) btnRealEnvido.dibujar(batch);
        if (btnFaltaEnvido != null) btnFaltaEnvido.dibujar(batch);
        if (btnQuiero != null) btnQuiero.dibujar(batch);
        if (btnNoQuiero != null) btnNoQuiero.dibujar(batch);
        batch.end();
    }

    // 🆕 VOLVER AL MENÚ
    private void volverAlMenu() {
        if (gameInstance instanceof com.badlogic.gdx.Game) {
            System.out.println("Volviendo al menú de selección...");
            ((com.badlogic.gdx.Game) gameInstance).setScreen(
                new PantallaSeleccionPuntos(gameInstance)
            );
        } else {
            System.out.println("No se pudo volver al menú (gameInstance no válido)");
        }
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
        fuente.dispose();
        if (fuenteVictoria != null) fuenteVictoria.dispose();
    }
}
