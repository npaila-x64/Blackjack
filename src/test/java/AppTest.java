import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.NoSuchElementException;
import static enums.Pinta.*;
import static enums.Valor.*;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    private Juego juego;
    private Jugador jugador;
    private Jugador dealer;
    private Baraja baraja;
    private Logger logger;

    @BeforeEach
    void init() {
        baraja = new Baraja();
        jugador = new Jugador(new Mano());
        dealer = new Jugador(new Mano(), true);
        juego = new Juego();
        juego.setBaraja(baraja);
        juego.setJugador(jugador);
        juego.setDealer(dealer);
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

    @Test
    void verificaQueDealerPideCartasCuandoJugadorSeBaja() {
        baraja.barajar();
        Carta carta;
        carta = new Carta(CORAZON, NUEVE);
        dealer.getManoEnJuego().agregarCarta(carta);
        carta = new Carta(PICA, TRES);
        dealer.getManoEnJuego().agregarCarta(carta);

        juego.pedirCartasADealer();
        assertTrue(dealer.getManoEnJuego().calcularSumaDeMano() >= 16);
    }

    @Test
    void verificaQueDealerEsGanador() {
        Carta carta;
        carta = new Carta(PICA, QUINA);
        jugador.getManoEnJuego().agregarCarta(carta);
        carta = new Carta(TREBOL, JOTA);
        jugador.getManoEnJuego().agregarCarta(carta);
        carta = new Carta(PICA, QUINA);
        dealer.getManoEnJuego().agregarCarta(carta);
        carta = new Carta(PICA, AS);
        dealer.getManoEnJuego().agregarCarta(carta);

        var ganador = juego.evaluarManoGanadora();
        assertEquals(ganador, dealer);
    }

    @Test
    void verificaQueManoJugadorEsBlackjack() {
        jugador.getManoEnJuego().agregarCarta(new Carta(CORAZON, QUINA));
        jugador.getManoEnJuego().agregarCarta(new Carta(PICA, AS));
        dealer.getManoEnJuego().agregarCarta(new Carta(TREBOL, JOTA));

        var ganador = juego.evaluarManoGanadora();
        assertEquals(jugador, ganador);
    }

    @Test
    void pideCartaParaUnaManoVacia() {
        baraja.pedirCarta(jugador);
        assertEquals(jugador.getManoEnJuego().totalCartas(), 1);
    }

    @Test
    @DisplayName("Caso excepci??n donde la mano de jugador es nula")
    void verificarGanadorCasoManoJugadorNula() {
        Carta carta;
        carta = new Carta(CORAZON, QUINA);
        dealer.getManoEnJuego().agregarCarta(carta);
        Mano jugadorNulo = null;
        jugador.setManoEnJuego(jugadorNulo);
        var exception = assertThrows(NullPointerException.class,
                () -> juego.evaluarManoGanadora(),
                "Se ha ingresado una entrada nula");
        logger.info("Se ha lanzado la excepci??n NullPointerException, dado " +
                "que la mano del jugador estaba nulo. " + exception.getMessage());
    }

    @Test
    @DisplayName("Caso excepci??n donde la carta dada contiene un valor nulo")
    void verificarObtenerValorNumericoDeCartaEsNula() {
        var exception = assertThrows(NullPointerException.class,
                () -> new Carta(CORAZON, null),
                "Se ha ingresado una carta inv??lida");
        logger.info("Se ha lanzado la excepci??n NullPointerException, dado " +
                "que el valor carta dada es nulo. " + exception.getMessage());
    }

    @Test
    @DisplayName("Caso excepci??n donde se pide una carta de una baraja vac??a")
    void verificaPedirCartaCasoBarajaVacia() {
        baraja = new Baraja();
        var tamanioBaraja = baraja.size();
        for (int indice = 0; indice < tamanioBaraja; indice++) {
            baraja.pedirCarta(jugador);
        }
        var exception = assertThrows(IndexOutOfBoundsException.class,
                () -> baraja.pedirCarta(jugador),
                "Se le ha pedido una carta a una baraja vac??a");
        logger.info("Se ha lanzado la excepci??n IndexOutOfBoundsException, dado " +
                "que la baraja estaba vac??a cuando se le pid??o una carta. " + exception.getMessage());
    }

    @Test
    @DisplayName("Caso excepci??n donde la opci??n dada es inv??lida")
    void verificaPedirValorCasoNoValorNumerico() {
        String entrada = "a";
        // Configura el System.in para que lea una
        // entrada dada por variable en vez de por consola
        var in = new ByteArrayInputStream(entrada.getBytes());
        System.setIn(in);
        var exception = assertThrows(NoSuchElementException.class,
                () -> Utils.pedirOpcionHasta(2),
                "Se ha ingresado una opci??n inv??lida");
        logger.info("Se ha lanzado la excepci??n NoSuchElementException, dado " +
                "que la opci??n dada es inv??lida. " + exception.getMessage());
    }

    @Test
    void verificaPartirManoTest() {
        Carta primeraCarta = new Carta(CORAZON, AS);
        jugador.getManoEnJuego().agregarCarta(primeraCarta);
        Carta segundaCarta = new Carta(PICA, AS);
        jugador.getManoEnJuego().agregarCarta(segundaCarta);
        jugador.partirMano();
        var manosJugador = jugador.getManos();
        assertTrue(manosJugador.get(0).getPrimeraCarta().equals(primeraCarta) &&
                manosJugador.get(1).getPrimeraCarta().equals(segundaCarta));
    }

    @Test
    void verificaEsManoPartibleTest() {
        Carta carta;
        carta = new Carta(CORAZON, AS);
        jugador.getManoEnJuego().agregarCarta(carta);
        carta = new Carta(PICA, AS);
        jugador.getManoEnJuego().agregarCarta(carta);
        assertTrue(jugador.getManoEnJuego().esManoPartible());
    }

    @Test
    void verificaEsManoPartibleFallaCartasDesigualesTest() {
        Carta carta;
        carta = new Carta(CORAZON, AS);
        jugador.getManoEnJuego().agregarCarta(carta);
        carta = new Carta(PICA, DOS);
        jugador.getManoEnJuego().agregarCarta(carta);
        assertFalse(jugador.getManoEnJuego().esManoPartible());
    }

    @Test
    void verificaEsManoPartibleFallaMasDeDosCartasTest() {
        Carta carta;
        carta = new Carta(CORAZON, AS);
        jugador.getManoEnJuego().agregarCarta(carta);
        carta = new Carta(PICA, AS);
        jugador.getManoEnJuego().agregarCarta(carta);
        carta = new Carta(TREBOL, AS);
        jugador.getManoEnJuego().agregarCarta(carta);
        assertFalse(jugador.getManoEnJuego().esManoPartible());
    }

    @Test
    @DisplayName("Caso excepci??n donde la mano a partir no existe")
    void verificaPartirManoNoExistente() {
        var exception = assertThrows(IndexOutOfBoundsException.class,
                () -> jugador.partirMano(),
                "Se ha ingresado una opci??n inv??lida");
        logger.info("Se ha lanzado la excepci??n NoSuchElementException, dado " +
                "que la mano dada es nula. " + exception.getMessage());
    }
}