package enums;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface Valor {

    Integer getValorNumerico();
    TipoDeCarta getTipoDeCarta();

    static Valor getCero(TipoDeCarta tipoDeCarta) {
        switch (tipoDeCarta) {
            case ESPANOLA -> {return ValorEspanol.CERO;}
            case INGLESA -> {return ValorIngles.CERO;}
            default -> throw new NoSuchElementException();
        }
    }

    static Set<Valor> fromTipoDeCarta(TipoDeCarta tipoDeCarta) {
        return Stream.of(List.of(ValorEspanol.valores()), List.of(ValorIngles.valores()))
                .flatMap(List::stream)
                .filter(valor -> valor.getTipoDeCarta().equals(tipoDeCarta))
                .collect(Collectors.toSet());
    }
}
