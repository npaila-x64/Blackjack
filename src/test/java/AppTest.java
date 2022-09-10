import org.apache.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;


class AppTest {

    private App app;
    private String[] manoJugador;
    private String[] manoDealer;
    private String[] baraja;
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
        manoDealer[0] = "CORAZON NUEVE";
        manoDealer[1] = "PICA TRES";

        app.bajarse(baraja, manoJugador, manoDealer);
        assertTrue(app.calcularSumaDeMano(manoDealer) >= 16);
    }

    @Test
    void verificaQueDealerEsGanador() {
        manoJugador[0] = "TREBOL JOTA";
        manoJugador[1] = "PICA QUINA";
        manoDealer[0] = "TREBOL JOTA";
        manoDealer[1] = "PICA QUINA";
        manoDealer[2] = "PICA AS";

        var manoGanadora = app.verificarGanador(manoJugador, manoDealer);
        assertArrayEquals(manoDealer, manoGanadora);
    }

    @Test
    void verificaQueJugadorEsBlackjack() {
        manoJugador[0] = "CORAZON QUINA";
        manoJugador[1] = "PICA AS";
        manoDealer[0] = "TREBOL JOTA";

        var manoGanadora = app.verificarGanador(manoJugador, manoDealer);
        assertArrayEquals(manoJugador, manoGanadora);
    }

    @Test
    void pideCartaParaUnaManoVacia() {
        manoJugador = app.pedirCarta(baraja, manoJugador);
        assertNotNull(manoJugador[0]);
    }

    @Test
    @DisplayName("Caso excepción donde la mano de jugador es nula")
    void verificarGanadorCasoManoJugadorNula() {
        manoDealer[0] = "CORAZON QUINA";
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
        baraja = new String[]{};
        var exception = assertThrows(IndexOutOfBoundsException.class,
                () -> app.pedirCarta(baraja, manoJugador),
                "Se le ha pedido una carta a una baraja vacía");
        logger.info("Se ha lanzado la excepción IndexOutOfBoundsException, dado " +
                "que la baraja estaba vacía cuando se le pidío una carta. " + exception.getMessage());
    }

    @Test
    @DisplayName("Caso excepción donde la opcion dada es inválida")
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
}