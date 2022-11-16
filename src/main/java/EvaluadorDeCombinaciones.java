import enums.Pinta;
import enums.TipoDeCarta;

import java.util.*;
import java.util.stream.Collectors;

public class EvaluadorDeCombinaciones {

    private static final String POR_PINTA = "POR_PINTA";
    private static final String POR_VALOR = "POR_VALOR";
    private List<Carta> cartas = new ArrayList<>();
    private Integer jokers;

    private EvaluadorDeCombinaciones() {}

    public EvaluadorDeCombinaciones(List<Carta> cartas) {
        this.cartas = new ArrayList<>(cartas);
        this.jokers = 0;
        extraerJokers();
    }

    private void extraerJokers() {
        for (int indice = 0; indice < cartas.size(); indice++) {
            Carta carta = cartas.get(indice);
            TipoDeCarta tipoDeCarta = carta.getPinta().getTipoDePinta();
            if (carta.getPinta().equals(Pinta.getJoker(tipoDeCarta))) {
                cartas.remove(carta);
                jokers++;
            }
        }
    }

    public boolean evaluarTrios(Integer cantidadDeTrios) {
        var duplicados = calcularDuplicados();

        int triosContados = 0;
        for (Integer duplicado : duplicados) {
            if (duplicado == 3) {
                triosContados++;
            }
            if (duplicado == 2 && jokers > 0) {
                triosContados++;
                jokers--;
            }
        }

        return triosContados == cantidadDeTrios;
    }

    private List<Integer> calcularDuplicados() {
        var distintos =
                cartas
                .stream()
                .collect(Collectors.toMap(Carta::getValorNumerico, c -> c, (c, q) -> c))
                .values();
        var duplicados = new ArrayList<Integer>();

        for (Carta distinto : distintos) {
            duplicados.add(0);
            for (Carta carta : cartas) {
                if (distinto.getValor().equals(carta.getValor())) {
                    duplicados.set(duplicados.size() - 1, duplicados.get(duplicados.size() - 1) + 1);
                }
            }
        }

        return duplicados;
    }

//    private List<List<Carta>> sortearPorPinta() {
//
//
//
//    }

    public boolean evaluarEscalas(Integer cantidadDeEscalas) {





        return false;
    }

}
