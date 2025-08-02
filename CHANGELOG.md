# 📓 Changelog

Todos los cambios importantes de este proyecto se documentarán en este archivo.

El formato sigue el estándar [Keep a Changelog](https://keepachangelog.com/es/1.0.0/).

## [Unreleased]
### Added

## [1.0.0] - 2025-05-30
### Added
- Proyecto creado con Gradle e importado a IntelliJ IDEA.
- Inicialización del proyecto con estructura base usando LibGDX.
- Archivo README.md creado con descripción e instrucciones de compilación.
- Creación del repositorio y carga inicial en GitHub.

## [1.1.0] - 2025-08-02 - Segunda pre-entrega - 02/08/2025

### 🎮 Funcionalidades implementadas
- Implementación de la lógica principal del juego de truco en modo solitario.
- Incorporación de movimiento e interacción con cartas mediante `InputProcessor`.
- Manejo de turnos básicos entre jugador y CPU.
- Mecánica para mover cartas por teclado.
- Lógica de jugadas y comparación de valores de cartas.
- Implementacion de colisiones.

### 🎨 Interfaz gráfica y entorno
- Creación de la pantalla principal (`PantallaMenu`) con música de fondo.
- Pantalla de juego (`PantallaUnJugador`) con fondo visual, HUD y cartas.
- Implementación de `OrthographicCamera` y `FitViewport` para adaptabilidad.
- Sistema de renderizado para imágenes (`Imagen`) y textos (`Texto`).

### 🔊 Sonido
- Música de fondo en el menú principal.
- Musica de fondo para el modo un jugador.

### 📜 Organización del proyecto
- Estructura de paquetes: `elementos`, `pantallas`, `personajesSolitario`, `utiles`, etc.
- Clases separadas por responsabilidades: `Carta`, `MazoSolitario`, `JugadorSolitario`, `Render`, etc.
- Uso de assets optimizado y organizado.


### 🧪 Extras
- Preparado para integrar futuros modos de juego o pantallas adicionales.
---
