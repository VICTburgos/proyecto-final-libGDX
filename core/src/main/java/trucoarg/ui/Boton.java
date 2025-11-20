package trucoarg.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.Gdx;
import trucoarg.utiles.Recursos;

public class Boton {

    private Rectangle bounds; // Área de click del botón
    private String texto;
    private BitmapFont fuente;
    private ShapeRenderer shapeRenderer;
    private boolean visible;
    private boolean habilitado;

    private Color colorFondo;
    private Color colorTexto;
    private Color colorBorde;
    private Color colorHover;

    private GlyphLayout layout; // Para medir el texto correctamente

    public Boton(String texto, float x, float y, float ancho, float alto) {
        this.texto = texto;
        this.bounds = new Rectangle(x, y, ancho, alto);
        this.shapeRenderer = new ShapeRenderer();
        this.visible = true;
        this.habilitado = true;
        this.layout = new GlyphLayout();

        // Colores por defecto (estilo argentino con celeste y blanco)
        this.colorFondo = new Color(0.3f, 0.5f, 0.7f, 0.9f); // Azul
        this.colorTexto = Color.WHITE;
        this.colorBorde = new Color(0.8f, 0.8f, 0.8f, 1f); // Gris claro
        this.colorHover = new Color(0.4f, 0.6f, 0.8f, 1f); // Azul más claro

        cargarFuente();
    }

    private void cargarFuente() {
        try {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
                Gdx.files.internal(Recursos.FUENTE_MENU)
            );
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 22;
            parameter.color = colorTexto;
            parameter.borderWidth = 1;
            parameter.borderColor = Color.BLACK;

            fuente = generator.generateFont(parameter);
            generator.dispose();
        } catch (Exception e) {
            System.out.println("Error cargando fuente para botón: " + e.getMessage());
            fuente = new BitmapFont(); // Fuente por defecto si falla
            fuente.getData().setScale(1.5f);
        }
    }

    public void dibujar(SpriteBatch batch) {
        if (!visible) return;

        batch.end(); // Terminar el batch para dibujar shapes

        // Verificar si el mouse está sobre el botón
        float mouseX = Gdx.input.getX();
        float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();
        boolean hover = bounds.contains(mouseX, mouseY) && habilitado;

        // Dibujar fondo del botón
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        if (!habilitado) {
            shapeRenderer.setColor(0.3f, 0.3f, 0.3f, 0.5f); // Gris si está deshabilitado
        } else if (hover) {
            shapeRenderer.setColor(colorHover);
        } else {
            shapeRenderer.setColor(colorFondo);
        }

        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        shapeRenderer.end();

        // Dibujar borde
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        Gdx.gl.glLineWidth(2);
        shapeRenderer.setColor(colorBorde);
        shapeRenderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
        shapeRenderer.end();

        batch.begin(); // Reiniciar el batch para dibujar texto

        // Centrar el texto en el botón usando GlyphLayout
        layout.setText(fuente, texto);
        float textX = bounds.x + (bounds.width - layout.width) / 2f;
        float textY = bounds.y + (bounds.height + layout.height) / 2f;

        if (!habilitado) {
            fuente.setColor(0.5f, 0.5f, 0.5f, 1f);
        } else {
            fuente.setColor(colorTexto);
        }

        fuente.draw(batch, texto, textX, textY);
    }

    /**
     * Verifica si el botón fue clickeado en las coordenadas dadas
     * @param x Coordenada X (ya convertida con altura de pantalla)
     * @param y Coordenada Y (ya convertida con altura de pantalla)
     * @return true si el click está dentro del botón y está habilitado
     */
    public boolean fueClickeado(float x, float y) {
        boolean dentroDelBoton = bounds.contains(x, y);

        // Debug para ver si el click está siendo detectado
        if (dentroDelBoton && visible) {
            System.out.println("Click en botón '" + texto + "' en (" + x + ", " + y + ") - Habilitado: " + habilitado);
        }

        return visible && habilitado && dentroDelBoton;
    }

    /**
     * Obtiene el Rectangle del botón para debug
     */
    public Rectangle getBounds() {
        return bounds;
    }

    // Getters y Setters
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setPosicion(float x, float y) {
        bounds.x = x;
        bounds.y = y;
    }

    public void setColor(Color fondo, Color texto, Color borde) {
        this.colorFondo = fondo;
        this.colorTexto = texto;
        this.colorBorde = borde;
        if (fuente != null) {
            fuente.setColor(texto);
        }
    }

    public void dispose() {
        if (fuente != null) fuente.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();
    }
}
