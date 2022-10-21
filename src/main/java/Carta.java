import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class Carta {

    private final List<String> cartas = List.of("AS", "DOS", "TRES", "CUATRO", "CINCO",
            "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ", "JOTA", "QUINA", "KAISER");
    private String pinta;
    private Integer valorNumerico;
    private String valor;

    public Carta() {}

    public Carta(String pinta, String valor) {
        setPinta(pinta);
        setValor(valor);
    }

    public String getPinta() {
        return pinta;
    }

    public void setPinta(String pinta) {
        this.pinta = pinta;
    }

    public Integer getValorNumerico() {
        return valorNumerico;
    }

    private void setValorNumerico(Integer valorNumerico) {
        this.valorNumerico = valorNumerico;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        if (!cartas.contains(valor)) throw new NoSuchElementException();
        setValorNumerico(crearMapaDeValores().get(valor));
        this.valor = valor;
    }

    public HashMap<String, Integer> crearMapaDeValores() {
        HashMap<String, Integer> mapa = new HashMap<>();
        int valorCarta = 1;

        for (String carta : cartas) {
            if (valorCarta > 10) { valorCarta = 10; }
            mapa.put(carta, valorCarta);
            valorCarta++;
        }

        return mapa;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", pinta, valor, valorNumerico);
    }
}
