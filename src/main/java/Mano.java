import java.util.ArrayList;
import java.util.List;

public class Mano {

    private final List<Carta> cartas;

    public Mano() {
        cartas = new ArrayList<>();
    }

    public Carta getPrimeraCarta() {
        return cartas.get(0);
    }

    public Carta getSegundaCarta() {
        return cartas.get(1);
    }

    public void agregarCarta(Carta carta) {
        cartas.add(carta);
    }

    public void mostrarConCartaEscondida() {
        int indice = 0;

        for (Carta carta : cartas) {
            if (indice == 0) {
                System.out.println("[***************]");
            } else {
                System.out.printf("[%s DE %s]%n",
                        carta.getValor(), carta.getPinta());
            }
            indice++;
        }
    }

    public void mostrarMano() {
        for (Carta carta : cartas) {
            System.out.printf("[%s DE %s]%n",
                    carta.getValor(), carta.getPinta());
        }
    }

    public boolean esBlackjack() {

        if (totalCartas() != 2) {
            return false;
        }

        boolean existeAs = false;
        boolean existe10 = false;

        for (int indice = 0; indice < 2; indice++) {
            if (cartas.get(indice).getValorNumerico() == 1) {
                existeAs = true;
            }
            if (cartas.get(indice).getValorNumerico() == 10) {
                existe10 = true;
            }
        }

        return (existeAs && existe10);
    }

    public boolean sePasoDe21() {
        return calcularSumaDeMano() > 21;
    }

    public int totalCartas() {
        return cartas.size();
    }

    public int calcularSumaDeMano() {
        int valorTotal = 0;

        for (Carta carta : cartas) {
            valorTotal += carta.getValorNumerico();
        }

        return valorTotal;
    }

    public boolean esManoPartible() {
        if (totalCartas() != 2) return false;
        return obtenerPrimeraCarta().getValorNumerico() ==
                obtenerSegundaCarta().getValorNumerico();
    }

    private Carta obtenerPrimeraCarta() {
        return cartas.get(0);
    }

    private Carta obtenerSegundaCarta() {
        return cartas.get(1);
    }

    @Override public String toString() {
        return String.format("[Mano: cantidadDeCartas=%s]", cartas.size());
    }
}
