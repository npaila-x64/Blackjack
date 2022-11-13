package enums;

public enum PintaInglesa implements Pinta {
    CORAZON,
    TREBOL,
    DIAMANTE,
    PICA;

    @Override
    public TipoDePinta getTipoDePinta() {
        return TipoDePinta.INGLESA;
    }
}
