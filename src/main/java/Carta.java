import enums.*;

import java.util.NoSuchElementException;

public class Carta {

    private Pinta pinta;
    private Integer valorNumerico;
    private TipoDeCarta tipoDeCarta;
    private Valor valor;

    public Carta() {}

    public static Carta crearJoker(TipoDeCarta tipoDeCarta) {
        Carta joker = new Carta();
        joker.setTipoDeCarta(tipoDeCarta);
        joker.setPinta(Pinta.getJoker(tipoDeCarta));
        joker.setValor(Valor.getCero(tipoDeCarta));
        return joker;
    }

    public Carta(Pinta pinta, Valor valor, TipoDeCarta tipoDeCarta) {
        this.pinta = pinta;
        this.valor = valor;
        this.valorNumerico = valor.getValorNumerico();
        this.tipoDeCarta = tipoDeCarta;
    }

    public Pinta getPinta() {
        return pinta;
    }

    public void setPinta(Pinta pinta) {
        this.pinta = pinta;
    }

    public Integer getValorNumerico() {
        return valorNumerico;
    }

    public void setTipoDeCarta(TipoDeCarta tipoDeCarta) {
        this.tipoDeCarta = tipoDeCarta;
    }

    public Valor getValor() {
        return valor;
    }

    public void setValor(Valor valor) {
        this.valor = valor;
        this.valorNumerico = valor.getValorNumerico();
    }

    public void mostrarCarta() {
        System.out.printf("[%s DE %s]\n", getValor(), getPinta());
    }

    @Override public String toString() {
        return String.format("[Carta: tipoDeCarta=%s, pinta=%s, valor=%s, valorNumerico=%s]",
                tipoDeCarta, pinta, valor, valorNumerico);
    }
}
