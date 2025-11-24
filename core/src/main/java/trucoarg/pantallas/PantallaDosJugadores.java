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

    // Texto de estado
    private String mensajeEstado = "";

    // Tipo de canto pendiente ("truco" o "envido")
    private String tipoCantoPendiente = null;

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
        float separacion = 10;

        // Colores comunes
        Color azulArg = new Color(0.4f, 0.6f, 0.85f, 0.9f);
        Color violeta = new Color(0.6f, 0.3f, 0.8f, 0.9f);
        Color blanco = Color.WHITE;
        Color borde = new Color(0.2f, 0.4f, 0.6f, 1f);
        Color verde = new Color(0.2f, 0.7f, 0.3f, 0.9f);
        Color rojo = new Color(0.8f, 0.2f, 0.2f, 0.9f);

        // ===== BOTONES DE TRUCO (izquierda arriba) =====
        float trucoPosY = Configuracion.ALTO / 2f + 100;

        btnTruco = new Boton("TRUCO", margen, trucoPosY, btnAncho, btnAlto);
        btnRetruco = new Boton("RETRUCO", margen, trucoPosY - btnAlto - separacion, btnAncho, btnAlto);
        btnValeCuatro = new Boton("VALE 4", margen, trucoPosY - (btnAlto + separacion) * 2, btnAncho, btnAlto);

        btnTruco.setColor(azulArg, blanco, borde);
        btnRetruco.setColor(azulArg, blanco, borde);
        btnValeCuatro.setColor(azulArg, blanco, borde);

        // ===== BOTONES DE ENVIDO (izquierda abajo) =====
        float envidoPosY = Configuracion.ALTO / 2f - 50;

        btnEnvido = new Boton("ENVIDO", margen, envidoPosY, btnAncho, btnAlto);
        btnRealEnvido = new Boton("REAL ENVIDO", margen, envidoPosY - btnAlto - separacion, btnAncho, btnAlto);
        btnFaltaEnvido = new Boton("FALTA ENVIDO", margen, envidoPosY - (btnAlto + separacion) * 2, btnAncho, btnAlto);

        btnEnvido.setColor(violeta, blanco, borde);
        btnRealEnvido.setColor(violeta, blanco, borde);
        btnFaltaEnvido.setColor(violeta, blanco, borde);

        // ===== BOTONES DE RESPUESTA (derecha) =====
        float respuestaPosY = Configuracion.ALTO / 2f + 50;

        btnQuiero = new Boton("QUIERO", Configuracion.ANCHO - btnAncho - margen,
            respuestaPosY, btnAncho, btnAlto);
        btnNoQuiero = new Boton("NO QUIERO", Configuracion.ANCHO - btnAncho - margen,
            respuestaPosY - btnAlto - separacion, btnAncho, btnAlto);

        btnQuiero.setColor(verde, blanco, borde);
        btnNoQuiero.setColor(rojo, blanco, borde);
    }

    private void actualizarEstadoBotones() {
        // Verificar si hay canto pendiente
        boolean hayTrucoPendiente = juego.getGestorTruco().estaEsperandoRespuesta();
        boolean hayEnvidoPendiente = juego.getGestorEnvido().estaEsperandoRespuesta();

        if (hayTrucoPendiente || hayEnvidoPendiente) {
            // HAY CANTO PENDIENTE
            int jugadorResponde = juego.getJugadorQueDebeResponder();

            if (hayTrucoPendiente) {
                tipoCantoPendiente = "truco";
                String cantoActual = juego.getGestorTruco().getCantoActual();
                mensajeEstado = "J" + jugadorResponde + " debe responder " + cantoActual.toUpperCase();

                // Para truco: solo mostrar Quiero/No Quiero
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
                // ENVIDO PENDIENTE - Mostrar botones de respuesta Y opciones para subir
                tipoCantoPendiente = "envido";
                String cantoActual = juego.getGestorEnvido().getCantoActual();
                mensajeEstado = "J" + jugadorResponde + " puede QUIERO/NO QUIERO o SUBIR " + cantoActual.toUpperCase();

                // Ocultar botones de truco
                btnTruco.setVisible(false);
                btnRetruco.setVisible(false);
                btnValeCuatro.setVisible(false);

                // Mostrar botones de respuesta
                btnQuiero.setVisible(true);
                btnNoQuiero.setVisible(true);
                btnQuiero.setHabilitado(true);
                btnNoQuiero.setHabilitado(true);

                // Mostrar botones de envido según lo que se puede "subir"
                boolean puedeEnvido = juego.getGestorEnvido().puedeSubirConEnvido();
                boolean puedeReal = juego.getGestorEnvido().puedeSubirConRealEnvido();
                boolean puedeFalta = juego.getGestorEnvido().puedeSubirConFaltaEnvido();

                btnEnvido.setVisible(puedeEnvido);
                btnEnvido.setHabilitado(puedeEnvido);

                btnRealEnvido.setVisible(puedeReal);
                btnRealEnvido.setHabilitado(puedeReal);

                btnFaltaEnvido.setVisible(puedeFalta);
                btnFaltaEnvido.setHabilitado(puedeFalta);
            }

        } else {
            // NO HAY CANTO PENDIENTE - Modo normal
            tipoCantoPendiente = null;

            btnQuiero.setVisible(false);
            btnNoQuiero.setVisible(false);

            boolean manoTerminada = juego.isManoTerminada();
            int tiradaActual = juego.getTiradaActual();

            // BOTONES DE TRUCO: siempre visibles si la mano no terminó
            btnTruco.setVisible(!manoTerminada);
            btnRetruco.setVisible(!manoTerminada);
            btnValeCuatro.setVisible(!manoTerminada);

            // Habilitar según estado
            btnTruco.setHabilitado(!manoTerminada);
            btnRetruco.setHabilitado(!manoTerminada && juego.getGestorTruco().isCantoAceptado());
            btnValeCuatro.setHabilitado(!manoTerminada && juego.getGestorTruco().isCantoAceptado());

            // BOTONES DE ENVIDO: solo en primera tirada
            boolean puedeEnvido = !manoTerminada && tiradaActual == 1;

            btnEnvido.setVisible(puedeEnvido);
            btnRealEnvido.setVisible(puedeEnvido);
            btnFaltaEnvido.setVisible(puedeEnvido);

            btnEnvido.setHabilitado(puedeEnvido);
            btnRealEnvido.setHabilitado(puedeEnvido);
            btnFaltaEnvido.setHabilitado(puedeEnvido);

            mensajeEstado = "Turno: J" + juego.getTurnoActual() + " | Ronda " + juego.getTiradaActual();
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

        // ===== PROCESAR RESPUESTAS (QUIERO / NO QUIERO) =====
        if (boton == btnQuiero || boton == btnNoQuiero) {
            if (!juego.hayCantoPendiente()) {
                System.out.println("DEBUG: No hay canto pendiente para responder");
                return;
            }

            int jugadorResponde = juego.getJugadorQueDebeResponder();
            boolean quiero = (boton == btnQuiero);

            System.out.println("DEBUG: J" + jugadorResponde + " responde: " + (quiero ? "QUIERO" : "NO QUIERO"));

            int resultado = -1;

            // Responder según el tipo de canto pendiente
            if (tipoCantoPendiente != null && tipoCantoPendiente.equals("truco")) {
                resultado = juego.responderCanto(jugadorResponde, quiero);

                if (resultado > 0) {
                    // Alguien ganó la mano por rechazar el truco
                    mensajeEstado = "J" + resultado + " ganó la mano! J1: " +
                        juego.getPuntosJ1() + " - J2: " + juego.getPuntosJ2();

                    reiniciarManoVisual();
                } else if (resultado == 0) {
                    mensajeEstado = "QUIERO! Se juega al valor cantado";
                }

            } else if (tipoCantoPendiente != null && tipoCantoPendiente.equals("envido")) {
                resultado = juego.responderEnvido(jugadorResponde, quiero);

                if (resultado > 0) {
                    // Alguien ganó el envido
                    mensajeEstado = "J" + resultado + " ganó el ENVIDO! J1: " +
                        juego.getPuntosJ1() + " - J2: " + juego.getPuntosJ2();
                } else if (resultado == 0) {
                    mensajeEstado = "QUIERO ENVIDO! Mostrando cartas...";
                    // Aquí podrías agregar lógica para mostrar las cartas y calcular envidos
                }
            }

            actualizarEstadoBotones();
            return;
        }

        // ===== PROCESAR CANTOS DE TRUCO =====
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

        // ===== PROCESAR CANTOS DE ENVIDO =====
        // IMPORTANTE: Puede ser canto inicial O subida de apuesta
        String tipoEnvido = null;

        if (boton == btnEnvido) {
            tipoEnvido = "envido";
        } else if (boton == btnRealEnvido) {
            tipoEnvido = "real envido";
        } else if (boton == btnFaltaEnvido) {
            tipoEnvido = "falta envido";
        }

        if (tipoEnvido != null) {
            // Determinar quién canta: puede ser el turno actual O el que debe responder (si está subiendo)
            int jugadorQueCanta;

            if (juego.getGestorEnvido().estaEsperandoRespuesta()) {
                // Hay canto pendiente - el jugador está subiendo la apuesta
                jugadorQueCanta = juego.getJugadorQueDebeResponder();
                System.out.println("DEBUG: J" + jugadorQueCanta + " está SUBIENDO con " + tipoEnvido.toUpperCase());
            } else {
                // No hay canto pendiente - canto inicial
                jugadorQueCanta = turno;
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
        if (btnEnvido != null) btnEnvido.dibujar(batch);
        if (btnRealEnvido != null) btnRealEnvido.dibujar(batch);
        if (btnFaltaEnvido != null) btnFaltaEnvido.dibujar(batch);
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
        btnEnvido.dispose();
        btnRealEnvido.dispose();
        btnFaltaEnvido.dispose();
        btnQuiero.dispose();
        btnNoQuiero.dispose();
        fuente.dispose();
    }
}
