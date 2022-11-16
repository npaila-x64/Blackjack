import enums.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Baraja {

    private TipoDeCarta tipoDeCarta;
    private List<Carta> cartas;

    public Baraja(TipoDeCarta tipoDeCarta) {
        this.tipoDeCarta = tipoDeCarta;
        cartas = new ArrayList<>();
        crearMazo();
    }

    private List<Carta> crearMazo() {
        for (Pinta pinta : Pinta.fromTipoDeCarta(tipoDeCarta)) {
            for (Valor valor : Valor.fromTipoDeCarta(tipoDeCarta)) {
                cartas.add(new Carta(pinta, valor, tipoDeCarta));
            }
        }
        return cartas;
    }

    public Baraja(BarajaBuilder builder) {
        tipoDeCarta = builder.getTipoDeCarta();
        cartas = builder.getCartas();
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

    public Carta pedirCarta() {
        Carta carta = cartas.get(0);
        cartas.remove(carta);
        return carta;
    }

    public void agregarCartas(List<Carta> cartas) {
        this.cartas.addAll(cartas);
    }

    public TipoDeCarta getTipoDeCarta() {
        return tipoDeCarta;
    }

    public Integer size() {
        return cartas.size();
    }

    @Override public String toString() {
        return String.format("[Baraja: cantidadDeCartas=%s, tipoDePinta=%s]", size(), tipoDeCarta);
    }
}
