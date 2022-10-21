import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.NoSuchElementException;

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

//    @Test
//    void verificaQueDealerPideCartasCuandoJugadorSeBaja() {
//        baraja.barajar();
//        Carta carta;
//        carta = new Carta("CORAZON", "NUEVE");
//        dealer.getManoEnJuego().agregarCarta(carta);
//        carta = new Carta("PICA", "TRES");
//        dealer.getManoEnJuego().agregarCarta(carta);
//
//        juego.pedirCartasADealer();
//        juego.bajarMano();
//        assertTrue(dealer.getManoEnJuego().calcularSumaDeMano() >= 16);
//    }

    @Test
    void verificaQueDealerEsGanador() {
        Carta carta;
        carta = new Carta("PICA", "QUINA");
        jugador.getManoEnJuego().agregarCarta(carta);
        carta = new Carta("TREBOL", "JOTA");
        jugador.getManoEnJuego().agregarCarta(carta);
        carta = new Carta("PICA", "QUINA");
        dealer.getManoEnJuego().agregarCarta(carta);
        carta = new Carta("PICA", "AS");
        dealer.getManoEnJuego().agregarCarta(carta);

        var ganador = juego.evaluarManoGanadora();
        assertEquals(ganador, dealer);
    }

    @Test
    void verificaQueJugadorEsBlackjack() {
        Carta carta;
        carta = new Carta("CORAZON", "QUINA");
        jugador.getManoEnJuego().agregarCarta(carta);
        carta = new Carta("PICA", "AS");
        jugador.getManoEnJuego().agregarCarta(carta);
        carta = new Carta("TREBOL", "JOTA");
        dealer.getManoEnJuego().agregarCarta(carta);

        var ganador = juego.evaluarManoGanadora();
        assertEquals(jugador, ganador);
    }

    @Test
    void pideCartaParaUnaManoVacia() {
        baraja.pedirCarta(jugador);
        assertEquals(jugador.getManoEnJuego().totalCartas(), 1);
    }

//    @Test
//    @DisplayName("Caso excepción donde la mano de jugador es nula")
//    void verificarGanadorCasoManoJugadorNula() {
//        Carta carta;
//        carta = new Carta("CORAZON", "QUINA");
//        dealer.getMano().agregarCarta(carta);
//        var exception = assertThrows(NullPointerException.class,
//                () -> juego.bajarse(),
//                "Se ha ingresado una entrada nula");
//        logger.info("Se ha lanzado la excepción NullPointerException, dado " +
//                "que la mano del jugador estaba nulo. " + exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("Caso excepción donde la carta dada es un valor inválido")
//    void verificarObtenerValorNumericoDeCartaEsNula() {
//        String carta = "una carta de uno de corazones";
//        var exception = assertThrows(NullPointerException.class,
//                () -> app.obtenerValorNumericoDeCarta(carta),
//                "Se ha ingresado una carta inválida");
//        logger.info("Se ha lanzado la excepción NullPointerException, dado " +
//                "que la carta dada es inválida. " + exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("Caso excepción donde se pide una carta de una baraja vacía")
//    void verificaPedirCartaCasoBarajaVacía() {
//        baraja = new ArrayList<>();
//        var exception = assertThrows(IndexOutOfBoundsException.class,
//                () -> app.pedirCarta(baraja, manoJugador),
//                "Se le ha pedido una carta a una baraja vacía");
//        logger.info("Se ha lanzado la excepción IndexOutOfBoundsException, dado " +
//                "que la baraja estaba vacía cuando se le pidío una carta. " + exception.getMessage());
//    }
//
//    @Test
//    @DisplayName("Caso excepción donde la opción dada es inválida")
//    void verificaPedirValorCasoNoValorNumerico() {
//        String entrada = "a";
//        // Configura el System.in para que lea una
//        // entrada dada por variable en vez de por consola
//        var in = new ByteArrayInputStream(entrada.getBytes());
//        System.setIn(in);
//        var exception = assertThrows(NoSuchElementException.class,
//                () -> Utils.pedirOpcion(),
//                "Se ha ingresado una opción inválida");
//        logger.info("Se ha lanzado la excepción NoSuchElementException, dado " +
//                "que la opción dada es inválida. " + exception.getMessage());
//    }
//
//    @Test
//    void verificaPartirManoTest() {
//        Carta carta;
//        carta = new Carta("CORAZON", "AS");
//        jugador.getMano().agregarCarta(carta);
//        carta = new Carta("PICA", "AS");
//        jugador.getMano().agregarCarta(carta);
//        List<List<String>> manosJugador = jugador.getMano().partirMano();
//        assertTrue(manosJugador.get(0).get(0).equals("CORAZON AS") &&
//                manosJugador.get(1).get(0).equals("PICA AS"));
//    }
//
//    @Test
//    void verificaEsManoPartibleTest() {
//        Carta carta;
//        carta = new Carta("CORAZON", "AS");
//        jugador.getMano().agregarCarta(carta);
//        carta = new Carta("PICA", "AS");
//        jugador.getMano().agregarCarta(carta);
//        assertTrue(jugador.getMano().esManoPartible());
//    }
//
//    @Test
//    void verificaEsManoPartibleFallaCartasDesigualesTest() {
//        Carta carta;
//        carta = new Carta("CORAZON", "AS");
//        jugador.getMano().agregarCarta(carta);
//        carta = new Carta("PICA", "AS");
//        jugador.getMano().agregarCarta(carta);
//        assertFalse(jugador.getMano().esManoPartible());
//    }
//
//    @Test
//    void verificaEsManoPartibleFallaMasDeDosCartasTest() {
//        Carta carta;
//        carta = new Carta("CORAZON", "AS");
//        jugador.getMano().agregarCarta(carta);
//        carta = new Carta("PICA", "AS");
//        jugador.getMano().agregarCarta(carta);
//        carta = new Carta("TREBOL", "AS");
//        jugador.getMano().agregarCarta(carta);
//        assertFalse(jugador.getMano().esManoPartible());
//    }
//
//    @Test
//    @DisplayName("Caso excepción donde la mano a partir dada es nula")
//    void verificaPartirManoManoEsNula() {
//        var exception = assertThrows(NullPointerException.class,
//                () -> jugador.getMano().partirMano(null),
//                "Se ha ingresado una opción inválida");
//        logger.info("Se ha lanzado la excepción NoSuchElementException, dado " +
//                "que la mano dada es nula. " + exception.getMessage());
//    }
}