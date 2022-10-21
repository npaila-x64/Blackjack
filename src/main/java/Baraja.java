import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baraja {

    private final List<Carta> cartas;

    public Baraja() {
        var pintas = List.of("CORAZON", "TREBOL", "DIAMANTE", "PICA");
        var numerosCartas = List.of("AS", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ", "JOTA", "QUINA", "KAISER");
        cartas = new ArrayList<>();

        for (String p : pintas) {
            for (String n : numerosCartas) {
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

    @Override
    public String toString() {
        return cartas.toString();
    }

    public Integer size() {
        return cartas.size();
    }
}
