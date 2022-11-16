package enums;

public enum ValorEspanol implements Valor {
    CERO(0),
    AS(1), DOS(2), TRES(3), CUATRO(4), CINCO(5),
    SEIS(6), SIETE(7), SOTA(8), CABALLERO(9), REY(10);

    private final Integer valor;

    ValorEspanol(Integer valor) {
        this.valor = valor;
    }

    public static ValorEspanol[] valores() {
        return new ValorEspanol[] {
                AS, DOS, TRES, CUATRO, CINCO,
                SEIS, SIETE, SOTA, CABALLERO, REY};
    }

    @Override
    public Integer getValorNumerico() {
        return this.valor;
    }

    @Override
    public TipoDeCarta getTipoDeCarta() {
        return TipoDeCarta.ESPANOLA;
    }
}
