package enums;

public enum PintaInglesa implements Pinta {

    CORAZON,
    TREBOL,
    DIAMANTE,
    PICA,
    JOKER;

    @Override
    public TipoDeCarta getTipoDePinta() {
        return TipoDeCarta.INGLESA;
    }

    public static PintaInglesa[] pintas() {
        return new PintaInglesa[]{CORAZON, TREBOL, DIAMANTE, PICA};
    }
}
