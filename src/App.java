import java.util.*;

public class App {

    public static void main(String[] args) {

        App bj = new App();
        bj.iniciar();
    }

    public void iniciar() {

        List<String> baraja = crearBaraja();

        System.out.println(baraja);
        barajar(baraja);
        System.out.println(baraja);

        List<String> manoJugador = crearMano();
        List<String> manoDealer = crearMano();

        repartir(baraja, manoJugador);
        repartir(baraja, manoDealer);

        System.out.println();
        System.out.println(baraja);
        System.out.println(manoJugador);
        System.out.println(manoDealer);

        jugar(baraja, manoJugador, manoDealer);
    }

    public void jugar(List<String> baraja, List<String> manoJugador, List<String> manoDealer) {

        System.out.println("""
                 /$$$$$$$  /$$                     /$$                               /$$     \s
                | $$__  $$| $$                    | $$                              | $$     \s
                | $$  \\ $$| $$  /$$$$$$   /$$$$$$$| $$   /$$ /$$  /$$$$$$   /$$$$$$$| $$   /$$
                | $$$$$$$ | $$ |____  $$ /$$_____/| $$  /$$/|__/ |____  $$ /$$_____/| $$  /$$/
                | $$__  $$| $$  /$$$$$$$| $$      | $$$$$$/  /$$  /$$$$$$$| $$      | $$$$$$/\s
                | $$  \\ $$| $$ /$$__  $$| $$      | $$_  $$ | $$ /$$__  $$| $$      | $$_  $$\s
                | $$$$$$$/| $$|  $$$$$$$|  $$$$$$$| $$ \\  $$| $$|  $$$$$$$|  $$$$$$$| $$ \\  $$
                |_______/ |__/ \\_______/ \\_______/|__/  \\__/| $$ \\_______/ \\_______/|__/  \\__/
                                                       /$$  | $$                             \s
                                                      |  $$$$$$/                             \s
                                                       \\______/                             \s""");

        System.out.println("La mano del dealer es: ");
        mostrarManoConCartaEscondida(manoDealer);

        int puntuacionDealer = obtenerValorDeMano(manoDealer);
        int puntuacionJugador = obtenerValorDeMano(manoJugador);

        while (true) {

            System.out.println("\nTu mano es: ");
            mostrarMano(manoJugador);

            if (esBlackjack(manoJugador)) {
                System.out.println("¡¡Ganaste!! :)))");
                System.exit(0);
            }

            if (sePasoDe21(manoJugador)) {
                System.out.println("¡¡Perdiste!! :(((");
                System.exit(0);
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("\nEscriba (P) para pedir carta o (B) para bajarse.\n> ");
            String seleccion = scanner.next();

            if (seleccion.equals("P")) {
                pedirCarta(baraja, manoJugador);
            }
            if (seleccion.equals("B")) {
                bajarse(manoJugador, manoDealer);
                break;
            }
        }
    }

    public void bajarse(List<String> manoJugador, List<String> manoDealer) {

        System.out.println("\nTu mano es:");
        mostrarMano(manoJugador);
        System.out.println("\nLa mano del dealer:");
        mostrarMano(manoDealer);


    }

    public List<String> verificarGanador(List<String> manoJugador, List<String> manoDealer) {
        return new ArrayList<>();
    }

    public void mostrarMano(List<String> mano) {
        for (String carta : mano) {
            String[] cartaArray = carta.split(" ");
            System.out.println("[" + cartaArray[1] + " DE "
                    + obtenerPintaDeCarta(carta) + "]");
        }
    }

    public void mostrarManoConCartaEscondida(List<String> mano) {

        int indice = 0;

        for (String carta : mano) {
            if (indice == 0) {
                System.out.println("[***************]");
            } else {
                String[] cartaArray = carta.split(" ");
                System.out.println("[" + cartaArray[1] + " DE "
                        + obtenerPintaDeCarta(carta) + "]");
            }
            indice++;
        }
    }

    public void repartir(List<String> baraja, List<String> mano) {
        pedirCarta(baraja, mano);
        pedirCarta(baraja, mano);
    }

    public void pedirCarta(List<String> baraja, List<String> mano) {

        String carta;

        carta = baraja.get(0);
        mano.add(carta);
        baraja.remove(carta);
    }

    public boolean esBlackjack(List<String> mano) {

        if (mano.size() > 2) {
            return false;
        }

        boolean existeAs = false;
        boolean existe10 = false;

        for (String carta : mano) {
            if (obtenerPintaDeCarta(carta).equals("AS")) {
                existeAs = true;
            }
            if (obtenerValorDeCarta(carta) == 10) {
                existe10 = true;
            }
        }

        return (existeAs && existe10);
    }

    public boolean sePasoDe21(List<String> mano) {
        return obtenerValorDeMano(mano) > 21;
    }

    public List<String> crearBaraja() {

        var pintas = new String[] {"CORAZON", "TREBOL", "DIAMANTE", "PICA"};
        var numerosCartas = new String[] {"AS", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ", "JOTA", "QUINA", "KAISER"};

        List<String> baraja = new ArrayList<>();

        for (String p : pintas) {
            for (String n : numerosCartas) {
                baraja.add(p + " " + n);
            }
        }

        return baraja;
    }

    public void barajar(List<String> baraja) {
        Collections.shuffle(baraja);
    }

    public List<String> crearMano() {
        List<String> mano = new ArrayList<>();
        return mano;
    }

    public int obtenerValorDeMano(List<String> mano) {

        int valorTotal = 0;

        for (String carta : mano) {
            valorTotal += obtenerValorDeCarta(carta);
        }

        return valorTotal;
    }

    public int obtenerValorDeCarta(String carta) {
        String valorDeCarta = carta.split(" ")[1];
        return crearMapaDeValores().get(valorDeCarta);
    }

    public String obtenerPintaDeCarta(String carta) {
        String pintaDeCarta = carta.split(" ")[0];
        return pintaDeCarta;
    }

    public HashMap<String, Integer> crearMapaDeValores() {

        List<String> cartas = List.of("AS", "DOS", "TRES", "CUATRO", "CINCO",
                "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ", "JOTA", "QUINA", "KAISER");

        HashMap<String, Integer> mapa = new HashMap<>();
        int valorCarta = 1;

        for (String carta : cartas) {
            if (valorCarta > 10) {
                valorCarta = 10;
            }
            mapa.put(carta, valorCarta);
            valorCarta++;
        }

        return mapa;
    }
}
