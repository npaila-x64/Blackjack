package enums;

public enum PintaEspanola implements Pinta {
    ORO,
    COPA,
    ESPADA,
    BASTO;

    @Override
    public TipoDePinta getTipoDePinta() {
        return TipoDePinta.ESPANOLA;
    }
}
