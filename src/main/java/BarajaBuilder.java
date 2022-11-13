import enums.Pinta;
import enums.TipoDeCarta;
import enums.Valor;

import java.util.ArrayList;
import java.util.List;

public class BarajaBuilder {

    private TipoDeCarta tipoDeCarta;
    private List<Carta> cartas;

    public BarajaBuilder(TipoDeCarta tipoDeCarta) {
        this.tipoDeCarta = tipoDeCarta;
        this.cartas = new ArrayList<>();
    }

    public void agregarMazo() {
        for (Pinta pinta : Pinta.fromTipoDeCarta(tipoDeCarta)) {
            for (Valor valor : Valor.fromTipoDeCarta(tipoDeCarta)) {
                cartas.add(new Carta(pinta, valor, tipoDeCarta));
            }
        }
    }

    public void agregarDosJokers() {
        cartas.add(Carta.crearJoker(tipoDeCarta));
        cartas.add(Carta.crearJoker(tipoDeCarta));
    }

    public Baraja construir() {
        Baraja baraja = new Baraja(this);
        return baraja;
    }

    public TipoDeCarta getTipoDeCarta() {
        return tipoDeCarta;
    }

    public List<Carta> getCartas() {
        return cartas;
    }
}
