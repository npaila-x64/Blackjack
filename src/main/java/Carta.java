import enums.Pinta;
import enums.Valor;

import java.util.HashMap;
import java.util.NoSuchElementException;

public class Carta {

    private Pinta pinta;
    private Integer valorNumerico;
    private Valor valor;

    public Carta() {}

    public Carta(Pinta pinta, Valor valor) {
        setPinta(pinta);
        setValor(valor);
    }

    public Pinta getPinta() {
        return pinta;
    }

    public void setPinta(Pinta pinta) {
        if (!Pinta.getPintas().contains(pinta)) throw new NoSuchElementException();
        this.pinta = pinta;
    }

    public Integer getValorNumerico() {
        return valorNumerico;
    }

    private void setValorNumerico(Integer valorNumerico) {
        this.valorNumerico = valorNumerico;
    }

    public Valor getValor() {
        return valor;
    }

    public void setValor(Valor valor) {
        if (!Valor.getValores().contains(valor)) throw new NoSuchElementException();
        setValorNumerico(crearMapaDeValores().get(valor));
        this.valor = valor;
    }

    public HashMap<Valor, Integer> crearMapaDeValores() {
        HashMap<Valor, Integer> mapa = new HashMap<>();
        int valorCarta = 1;

        for (Valor valor : Valor.getValores()) {
            if (valorCarta > 10) { valorCarta = 10; }
            mapa.put(valor, valorCarta);
            valorCarta++;
        }

        return mapa;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", pinta, valor, valorNumerico);
    }
}
