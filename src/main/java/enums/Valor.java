package enums;

import java.util.List;

public enum Valor {
    AS, DOS, TRES, CUATRO, CINCO,
    SEIS, SIETE, OCHO, NUEVE, DIEZ,
    JOTA, QUINA, KAISER;

    public static List<Valor> getValores() {
        return List.of(AS, DOS, TRES, CUATRO, CINCO,
                SEIS, SIETE, OCHO, NUEVE, DIEZ,
                JOTA, QUINA, KAISER);
    }
}
