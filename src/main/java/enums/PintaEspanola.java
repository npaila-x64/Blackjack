package enums;

public enum PintaEspanola implements Pinta {
    ORO,
    COPA,
    ESPADA,
    BASTO,
    JOKER;

    @Override
    public TipoDeCarta getTipoDePinta() {
        return TipoDeCarta.ESPANOLA;
    }

    public static PintaEspanola[] pintas() {
        return new PintaEspanola[]{ORO, COPA, ESPADA, BASTO};
    }
}
