import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


class AppTest {

    private App app;

    @BeforeEach
    void init() {
        app = new App();
    }

    @Test
    void verificaQueDealerPideCartasCuandoJugadorSeBaja() {
        var baraja = app.crearBaraja();
        app.barajar(baraja);
        var manoJugador = app.crearMano();
        var manoDealer = app.crearMano();
        manoDealer[0] = "CORAZON NUEVE";
        manoDealer[1] = "PICA TRES";

        app.bajarse(baraja, manoJugador, manoDealer);
        assertTrue(app.calcularSumaDeMano(manoDealer) >= 16);
    }

    @Test
    void verificaQueDealerEsGanador() {
        var manoJugador = app.crearMano();
        var manoDealer = app.crearMano();
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
        var manoJugador = app.crearMano();
        var manoDealer = app.crearMano();
        manoJugador[0] = "CORAZON QUINA";
        manoJugador[1] = "PICA AS";
        manoDealer[0] = "TREBOL JOTA";

        var manoGanadora = app.verificarGanador(manoJugador, manoDealer);
        assertArrayEquals(manoJugador, manoGanadora);
    }

    @Test
    void pideCartaParaUnaManoVacia() {
        var baraja = app.crearBaraja();
        var mano = app.crearMano();

        mano = app.pedirCarta(baraja, mano);
        assertNotNull(mano[0]);
    }
}