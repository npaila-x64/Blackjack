import org.apache.log4j.Logger;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


class AppTest {

    private App app;
    private List<String> manoJugador;
    private List<String> manoDealer;
    private List<String> baraja;
    private Logger logger;

    @BeforeEach
    void init() {
        app = new App();
        manoJugador = app.crearMano();
        manoDealer = app.crearMano();
        baraja = app.crearBaraja();
        logger = Logger.getLogger("AppTest.class");
    }

    @Test
    void verificaQueDealerPideCartasCuandoJugadorSeBaja() {
        app.barajar(baraja);
        manoDealer.add("CORAZON NUEVE");
        manoDealer.add("PICA TRES");

        app.realizarTurnoDeDealer(baraja, manoDealer);
        app.procederABajarse(baraja, manoJugador, manoDealer);
        assertTrue(app.calcularSumaDeMano(manoDealer) >= 16);
    }

    @Test
    void verificaQueDealerEsGanador() {
        manoJugador.add("TREBOL JOTA");
        manoJugador.add("PICA QUINA");
        manoDealer.add("TREBOL JOTA");
        manoDealer.add("PICA QUINA");
        manoDealer.add("PICA AS");

        var manoGanadora = app.verificarGanador(manoJugador, manoDealer);
        assertEquals(manoDealer, manoGanadora);
    }

    @Test
    void verificaQueJugadorEsBlackjack() {
        manoJugador.add("CORAZON QUINA");
        manoJugador.add("PICA AS");
        manoDealer.add("TREBOL JOTA");

        var manoGanadora = app.verificarGanador(manoJugador, manoDealer);
        assertEquals(manoJugador, manoGanadora);
    }

    @Test
    void pideCartaParaUnaManoVacia() {
        app.pedirCarta(baraja, manoJugador);
        assertNotNull(manoJugador.get(0));
    }

    @Test
    @DisplayName("Caso excepción donde la mano de jugador es nula")
    void verificarGanadorCasoManoJugadorNula() {
        manoDealer.add("CORAZON QUINA");
        var exception = assertThrows(NullPointerException.class,
                () -> app.bajarse(baraja, null, manoDealer),
                "Se ha ingresado una entrada nula");
        logger.info("Se ha lanzado la excepción NullPointerException, dado " +
                "que la mano del jugador estaba nulo. " + exception.getMessage());
    }

    @Test
    @DisplayName("Caso excepción donde la carta dada es un valor inválido")
    void verificarObtenerValorNumericoDeCartaEsNula() {
        String carta = "una carta de uno de corazones";
        var exception = assertThrows(NullPointerException.class,
                () -> app.obtenerValorNumericoDeCarta(carta),
                "Se ha ingresado una carta inválida");
        logger.info("Se ha lanzado la excepción NullPointerException, dado " +
                "que la carta dada es inválida. " + exception.getMessage());
    }

    @Test
    @DisplayName("Caso excepción donde se pide una carta de una baraja vacía")
    void verificaPedirCartaCasoBarajaVacía() {
        baraja = new ArrayList<>();
        var exception = assertThrows(IndexOutOfBoundsException.class,
                () -> app.pedirCarta(baraja, manoJugador),
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
                () -> app.pedirOpcion(),
                "Se ha ingresado una opción inválida");
        logger.info("Se ha lanzado la excepción NoSuchElementException, dado " +
                "que la opción dada es inválida. " + exception.getMessage());
    }

    @Test
    void verificaPartirManoTest() {
        manoJugador.add("CORAZON AS");
        manoJugador.add("PICA AS");
        List<List<String>> manosJugador = app.partirMano(manoJugador);
        assertTrue(manosJugador.get(0).get(0).equals("CORAZON AS") &&
                manosJugador.get(1).get(0).equals("PICA AS"));
    }

    @Test
    void verificaEsManoPartibleTest() {
        manoJugador.add("CORAZON AS");
        manoJugador.add("PICA AS");
        assertTrue(app.esManoPartible(manoJugador));
    }

    @Test
    void verificaEsManoPartibleFallaCartasDesigualesTest() {
        manoJugador.add("CORAZON AS");
        manoJugador.add("PICA TRES");
        assertFalse(app.esManoPartible(manoJugador));
    }

    @Test
    void verificaEsManoPartibleFallaMasDeDosCartasTest() {
        manoJugador.add("CORAZON AS");
        manoJugador.add("PICA AS");
        manoJugador.add("TREBOL AS");
        assertFalse(app.esManoPartible(manoJugador));
    }

}