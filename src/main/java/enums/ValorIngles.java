package enums;

public enum ValorIngles implements Valor {
    CERO(0),
    AS(1), DOS(2), TRES(3), CUATRO(4), CINCO(5),
    SEIS(6), SIETE(7), OCHO(8), NUEVE(9), DIEZ(10),
    JOTA(10), QUINA(10), KAISER(10);

    private final Integer valor;

    ValorIngles(Integer valor) {
        this.valor = valor;
    }

    public static ValorIngles[] valores() {
        return new ValorIngles[] {
                AS, DOS, TRES, CUATRO, CINCO,
                SEIS, SIETE, OCHO, NUEVE,
                JOTA, QUINA, KAISER};
    }

    @Override
    public Integer getValorNumerico() {
        return this.valor;
    }

    @Override
    public TipoDeCarta getTipoDeCarta() {
        return TipoDeCarta.INGLESA;
    }
}
