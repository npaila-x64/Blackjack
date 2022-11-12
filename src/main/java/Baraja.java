import enums.Pinta;
import enums.Valor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baraja {

    private final List<Carta> cartas;

    public Baraja() {
        cartas = new ArrayList<>();

        for (Pinta p : Pinta.getPintas()) {
            for (Valor n : Valor.getValores()) {
                Carta carta = new Carta();
                carta.setPinta(p);
                carta.setValor(n);
                cartas.add(carta);
            }
        }
    }

    public void barajar() {
        Collections.shuffle(cartas);
    }

    public void pedirCarta(Jugador jugador) {
        Mano mano = jugador.getManoEnJuego();
        Carta carta = cartas.get(0);
        mano.agregarCarta(carta);
        cartas.remove(carta);
    }

    public Integer size() {
        return cartas.size();
    }

    @Override public String toString() {
        return String.format("[Baraja: cantidadDeCartas=%s]", size());
    }
}
