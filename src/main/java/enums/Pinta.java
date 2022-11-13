package enums;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Pinta {

    TipoDeCarta getTipoDePinta();

    static Pinta getJoker(TipoDeCarta tipoDeCarta) {
        switch (tipoDeCarta) {
            case ESPANOLA -> {return PintaEspanola.JOKER;}
            case INGLESA -> {return PintaInglesa.JOKER;}
            default -> throw new NoSuchElementException();
        }
    }

    static Set<Pinta> fromTipoDeCarta(TipoDeCarta tipoDeCarta) {
        return Stream.of(List.of(PintaEspanola.pintas()), List.of(PintaInglesa.pintas()))
                .flatMap(List::stream)
                .filter(pinta -> pinta.getTipoDePinta().equals(tipoDeCarta))
                .collect(Collectors.toSet());
    }
}
