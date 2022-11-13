package enums;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Pinta {

    TipoDePinta getTipoDePinta();

    static Set<Pinta> fromTipoDeCarta(TipoDePinta tipoDeCarta) {
        return Stream.of(List.of(PintaEspanola.values()), List.of(PintaInglesa.values()))
                .flatMap(List::stream)
                .filter(card -> card.getTipoDePinta().equals(tipoDeCarta))
                .collect(Collectors.toSet());
    }
}
