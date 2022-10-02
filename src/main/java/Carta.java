import java.util.HashMap;
import java.util.List;

public class Carta {

    private String pinta;
    private Integer valorNumerico;
    private String valor;

    public Carta() {}

    public Carta(String pinta, String valor) {
        this.pinta = pinta;
        this.valor = valor;
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
        setValorNumerico(crearMapaDeValores().get(valor));
        this.valor = valor;
    }

    public HashMap<String, Integer> crearMapaDeValores() {

        List<String> cartas = List.of("AS", "DOS", "TRES", "CUATRO", "CINCO",
                "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ", "JOTA", "QUINA", "KAISER");

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
