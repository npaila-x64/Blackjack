import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.NoSuchElementException;
import static enums.Pinta.*;
import static enums.Valor.*;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private Blackjack blackjack;
    private Baraja baraja;
    private Logger logger;

    @BeforeEach
    void init() {
        blackjack = Blackjack.crearNuevoJuego();
        baraja = blackjack.getBaraja();
        logger = Logger.getLogger("AppTest.class");
    }

    @BeforeAll
    static void limpiarLog() {
        try {
            new File("src/test/resources/testinglogs.log").delete();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    void verificaQueDealerPideCartasCuandoJugadorSeBaja() {
//        baraja.barajar();
//        Carta carta;
//        carta = new Carta(CORAZON, NUEVE);
//        juego.obtenerDealer().getManoEnJuego().agregarCarta(carta);
//        carta = new Carta(PICA, TRES);
//        juego.obtenerDealer().getManoEnJuego().agregarCarta(carta);
//
//        juego.pedirCartasADealer();
//        assertTrue(juego.obtenerDealer().getManoEnJuego().calcularSumaDeMano() >= 16);
//    }

    @Test
    void verificaQueDealerEsGanador() {
        Carta carta;
        carta = new Carta(PICA, QUINA);
        blackjack.obtenerJugadorEnJuego().getManoEnJuego().agregarCarta(carta);
        carta = new Carta(TREBOL, JOTA);
        blackjack.obtenerJugadorEnJuego().getManoEnJuego().agregarCarta(carta);
        carta = new Carta(PICA, QUINA);
        blackjack.obtenerDealer().getManoEnJuego().agregarCarta(carta);
        carta = new Carta(PICA, AS);
        blackjack.obtenerDealer().getManoEnJuego().agregarCarta(carta);

        var ganador = blackjack.evaluarManoGanadora();
        assertEquals(ganador, blackjack.obtenerDealer());
    }

    @Test
    void verificaQueManoJugadorEsBlackjack() {
        blackjack.obtenerJugadorEnJuego().getManoEnJuego().agregarCarta(new Carta(CORAZON, QUINA));
        blackjack.obtenerJugadorEnJuego().getManoEnJuego().agregarCarta(new Carta(PICA, AS));
        blackjack.obtenerDealer().getManoEnJuego().agregarCarta(new Carta(TREBOL, JOTA));

        var ganador = blackjack.evaluarManoGanadora();
        assertEquals(blackjack.obtenerJugadorEnJuego(), ganador);
    }

    @Test
    void pideCartaParaUnaManoVacia() {
        baraja.pedirCarta(blackjack.obtenerJugadorEnJuego());
        assertEquals(blackjack.obtenerJugadorEnJuego().getManoEnJuego().totalCartas(), 1);
    }

//    @Test
//    @DisplayName("Caso excepción donde la mano de jugador es nula")
//    void verificarGanadorCasoManoJugadorNula() {
//        Carta carta;
//        carta = new Carta(CORAZON, QUINA);
//        juego.obtenerDealer().getManoEnJuego().agregarCarta(carta);
//        Mano jugadorNulo = null;
//        juego.obtenerJugador().setManoEnJuego(jugadorNulo);
//        var exception = assertThrows(NullPointerException.class,
//                () -> juego.evaluarManoGanadora(),
//                "Se ha ingresado una entrada nula");
//        logger.info("Se ha lanzado la excepción NullPointerException, dado " +
//                "que la mano del jugador estaba nulo. " + exception.getMessage());
//    }

    @Test
    @DisplayName("Caso excepción donde la carta dada contiene un valor nulo")
    void verificarObtenerValorNumericoDeCartaEsNula() {
        var exception = assertThrows(NullPointerException.class,
                () -> new Carta(CORAZON, null),
                "Se ha ingresado una carta inválida");
        logger.info("Se ha lanzado la excepción NullPointerException, dado " +
                "que el valor carta dada es nulo. " + exception.getMessage());
    }

    @Test
    @DisplayName("Caso excepción donde se pide una carta de una baraja vacía")
    void verificaPedirCartaCasoBarajaVacia() {
        baraja = new Baraja();
        var tamanioBaraja = baraja.size();
        for (int indice = 0; indice < tamanioBaraja; indice++) {
            baraja.pedirCarta(blackjack.obtenerJugadorEnJuego());
        }
        var exception = assertThrows(IndexOutOfBoundsException.class,
                () -> baraja.pedirCarta(blackjack.obtenerJugadorEnJuego()),
                "Se le ha pedido una carta a una baraja vacía");
        logger.info("Se ha lanzado la excepción IndexOutOfBoundsException, dado " +
                "que la baraja estaba vacía cuando se le pidío una carta. " + exception.getMessage());
    }

    @Test
    @DisplayName("Caso excepción donde la opción dada es inválida")
    void verificaPedirValorCasoNoValorNumerico() {
        String entrada = "a";
        // Configura el System.in para que lea una
        // entrada dada por variable en vez de por consola
        var in = new ByteArrayInputStream(entrada.getBytes());
        System.setIn(in);
        var exception = assertThrows(NoSuchElementException.class,
                () -> Utils.pedirOpcionHasta(2),
                "Se ha ingresado una opción inválida");
        logger.info("Se ha lanzado la excepción NoSuchElementException, dado " +
                "que la opción dada es inválida. " + exception.getMessage());
    }

    @Test
    void verificaPartirManoTest() {
        Carta primeraCarta = new Carta(CORAZON, AS);
        blackjack.obtenerJugadorEnJuego().getManoEnJuego().agregarCarta(primeraCarta);
        Carta segundaCarta = new Carta(PICA, AS);
        blackjack.obtenerJugadorEnJuego().getManoEnJuego().agregarCarta(segundaCarta);
        blackjack.obtenerJugadorEnJuego().partirMano();
        var manosJugador = blackjack.obtenerJugadorEnJuego().getManos();
        assertTrue(manosJugador.get(0).getPrimeraCarta().equals(primeraCarta) &&
                manosJugador.get(1).getPrimeraCarta().equals(segundaCarta));
    }

    @Test
    void verificaEsManoPartibleTest() {
        Carta carta;
        carta = new Carta(CORAZON, AS);
        blackjack.obtenerJugadorEnJuego().getManoEnJuego().agregarCarta(carta);
        carta = new Carta(PICA, AS);
        blackjack.obtenerJugadorEnJuego().getManoEnJuego().agregarCarta(carta);
        assertTrue(blackjack.obtenerJugadorEnJuego().getManoEnJuego().esManoPartible());
    }

    @Test
    void verificaEsManoPartibleFallaCartasDesigualesTest() {
        Carta carta;
        carta = new Carta(CORAZON, AS);
        blackjack.obtenerJugadorEnJuego().getManoEnJuego().agregarCarta(carta);
        carta = new Carta(PICA, DOS);
        blackjack.obtenerJugadorEnJuego().getManoEnJuego().agregarCarta(carta);
        assertFalse(blackjack.obtenerJugadorEnJuego().getManoEnJuego().esManoPartible());
    }

    @Test
    void verificaEsManoPartibleFallaMasDeDosCartasTest() {
        Carta carta;
        carta = new Carta(CORAZON, AS);
        blackjack.obtenerJugadorEnJuego().getManoEnJuego().agregarCarta(carta);
        carta = new Carta(PICA, AS);
        blackjack.obtenerJugadorEnJuego().getManoEnJuego().agregarCarta(carta);
        carta = new Carta(TREBOL, AS);
        blackjack.obtenerJugadorEnJuego().getManoEnJuego().agregarCarta(carta);
        assertFalse(blackjack.obtenerJugadorEnJuego().getManoEnJuego().esManoPartible());
    }

    @Test
    @DisplayName("Caso excepción donde la mano a partir no existe")
    void verificaPartirManoNoExistente() {
        var exception = assertThrows(IndexOutOfBoundsException.class,
                () -> blackjack.obtenerJugadorEnJuego().partirMano(),
                "Se ha ingresado una opción inválida");
        logger.info("Se ha lanzado la excepción NoSuchElementException, dado " +
                "que la mano dada es nula. " + exception.getMessage());
    }
}