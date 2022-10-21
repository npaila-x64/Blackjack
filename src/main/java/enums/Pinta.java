package enums;

import java.util.List;

public enum Pinta {
    CORAZON, TREBOL, DIAMANTE, PICA;

    public static List<Pinta> getPintas() {
        return List.of(CORAZON, TREBOL, DIAMANTE, PICA);
    }
}
