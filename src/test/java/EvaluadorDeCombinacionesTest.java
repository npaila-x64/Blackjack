import enums.TipoDeCarta;
import org.junit.jupiter.api.Test;

import static enums.PintaInglesa.*;
import static enums.ValorIngles.*;
import static enums.ValorIngles.TRES;
import static org.junit.jupiter.api.Assertions.*;

class EvaluadorDeCombinacionesTest {

    @Test
    void existeTriosTest() {
        Carioca carioca = Carioca.crearNuevoJuego();
        Jugador jugador = Jugador.crearJugador();
        carioca.agregarJugador(jugador);
        carioca.setJugadorEnJuego(jugador);
//        jugador.getManoEnJuego().agregarCarta(new Carta(TREBOL, QUINA, TipoDeCarta.INGLESA));
        jugador.getManoEnJuego().agregarCarta(new Carta(CORAZON, TRES, TipoDeCarta.INGLESA));
        jugador.getManoEnJuego().agregarCarta(new Carta(CORAZON, QUINA, TipoDeCarta.INGLESA));
//        jugador.getManoEnJuego().agregarCarta(new Carta(PICA, CUATRO, TipoDeCarta.INGLESA));
        jugador.getManoEnJuego().agregarCarta(new Carta(PICA, QUINA, TipoDeCarta.INGLESA));
        jugador.getManoEnJuego().agregarCarta(new Carta(TREBOL, QUINA, TipoDeCarta.INGLESA));
        jugador.getManoEnJuego().agregarCarta(new Carta(TREBOL, TRES, TipoDeCarta.INGLESA));
        jugador.getManoEnJuego().agregarCarta(new Carta(CORAZON, TRES, TipoDeCarta.INGLESA));
        EvaluadorDeCombinaciones ev = new EvaluadorDeCombinaciones(jugador.getManoEnJuego().getCartas());
        assertTrue(ev.evaluarTrios(2));
    }
}