import enums.TipoDeCarta;
import org.junit.jupiter.api.Test;

import static enums.PintaInglesa.*;
import static enums.ValorIngles.*;
import static org.junit.jupiter.api.Assertions.*;

class CariocaTest {

    @Test
    void existeTriosTest() {
        Carioca carioca = Carioca.crearNuevoJuego();
        Jugador jugador = Jugador.crearJugador();
        carioca.agregarJugador(jugador);
        carioca.setJugadorEnJuego(jugador);
        jugador.getManoEnJuego().agregarCarta(new Carta(TREBOL, QUINA, TipoDeCarta.INGLESA));
        jugador.getManoEnJuego().agregarCarta(new Carta(CORAZON, TRES, TipoDeCarta.INGLESA));
        jugador.getManoEnJuego().agregarCarta(new Carta(CORAZON, DOS, TipoDeCarta.INGLESA));
        jugador.getManoEnJuego().agregarCarta(new Carta(PICA, CUATRO, TipoDeCarta.INGLESA));
        jugador.getManoEnJuego().agregarCarta(new Carta(PICA, QUINA, TipoDeCarta.INGLESA));
        jugador.getManoEnJuego().agregarCarta(new Carta(TREBOL, QUINA, TipoDeCarta.INGLESA));
        jugador.getManoEnJuego().agregarCarta(new Carta(TREBOL, QUINA, TipoDeCarta.INGLESA));
        jugador.getManoEnJuego().agregarCarta(new Carta(TREBOL, TRES, TipoDeCarta.INGLESA));
        jugador.getManoEnJuego().agregarCarta(new Carta(CORAZON, TRES, TipoDeCarta.INGLESA));
        System.out.println(jugador);
        assertTrue(carioca.existeTrios(2));
        System.out.println(jugador);
    }
}