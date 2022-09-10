import java.util.*;

public class App {

    public static void main(String[] args) {

        App blackjack = new App();
        blackjack.iniciar();
    }

    public void iniciar() {

        String[] baraja = crearBaraja();
        barajar(baraja);

        jugar(baraja);
    }

    public void jugar(String[] baraja) {

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
        String[] manoJugador = crearMano();
        String[] manoDealer = crearMano();
        repartir(baraja, manoJugador);
        repartir(baraja, manoDealer);

        while (true) {
            mostrarManos(manoJugador, manoDealer);
            int opcion = pedirOpcion();
            if (opcion == 1) {
                pedirCarta(baraja, manoJugador);
            }
            if (opcion == 2) {
                bajarse(baraja, manoJugador, manoDealer);
                break;
            }
        }
    }

    private int pedirValor() throws InputMismatchException {
        return new Scanner(System.in).nextInt();
    }

    public int pedirOpcion() {
        try {
            System.out.print("\nEscriba (1) para pedir carta o (2) para bajarse.\n> ");
            return pedirValor();
        } catch (InputMismatchException e) {
            return pedirOpcion();
        }
    }

    private void mostrarManos(String[] manoJugador, String[] manoDealer) {
        System.out.println("La mano del dealer es: ");
        mostrarManoConCartaEscondida(manoDealer);
        System.out.println("\nTu mano es: ");
        mostrarMano(manoJugador);
    }

    public void turnoDeDealer(String[] baraja, String[] manoDealer) {
        // Verifica primero que la mano del dealer no sea blackjack,
        // pues esta se verifica después
        if (!esBlackjack(manoDealer)) {
            // CPU simple basado en una estrategia simple,
            // pedir cartas mientras que el total de su mano sea menor a 16
            while (calcularSumaDeMano(manoDealer) < 16) {
                pedirCarta(baraja, manoDealer);
            }
        }
    }

    public void bajarse(String[] baraja, String[] manoJugador, String[] manoDealer) throws NullPointerException {

        // Cuando el Jugador decide bajarse, el Dealer pide sus cartas
        turnoDeDealer(baraja, manoDealer);

        System.out.println("La mano del dealer es: ");
        mostrarMano(manoDealer);
        System.out.println("\nTu mano es: ");
        mostrarMano(manoJugador);

        String[] manoGanadora = verificarGanador(manoJugador, manoDealer);

        if (manoGanadora == manoJugador) {
            System.out.println("¡¡Ganaste!! :)))");
        } else {
            System.out.println("¡¡Perdiste!! :(((");
        }
    }

    public String[] verificarGanador(String[] manoJugador, String[] manoDealer) {

        if (esBlackjack(manoJugador)) {
            return manoJugador;
        }

        if (esBlackjack(manoDealer)) {
            return manoDealer;
        }

        if (sePasoDe21(manoJugador)) {
            return manoDealer;
        }

        if (sePasoDe21(manoDealer)) {
            return manoJugador;
        }

        return calcularSumaDeMano(manoJugador) > calcularSumaDeMano(manoDealer) ?
                manoJugador : manoDealer;
    }

    public void mostrarMano(String[] mano) {
        for (String carta : mano) {
            if (carta == null) {
                break;
            }
            System.out.println("[" + obtenerValorLiteralDeCarta(carta) + " DE "
                    + obtenerPintaDeCarta(carta) + "]");
        }
    }

    public void mostrarManoConCartaEscondida(String[] mano) {

        int indice = 0;

        for (String carta : mano) {
            if (carta == null) {
                break;
            }
            if (indice == 0) {
                System.out.println("[***************]");
            } else {
                System.out.println("[" + obtenerValorLiteralDeCarta(carta) + " DE "
                        + obtenerPintaDeCarta(carta) + "]");
            }
            indice++;
        }
    }

    public void repartir(String[] baraja, String[] mano) {
        pedirCarta(baraja, mano);
        pedirCarta(baraja, mano);
    }

    public String[] pedirCarta(String[] baraja, String[] mano) {

        String carta;
        carta = baraja[0];
        eliminarPrimeraCartaDeBaraja(baraja);

        moverCartasDeMano(mano);
        mano[0] = carta;

        return mano;
    }

    public void eliminarPrimeraCartaDeBaraja(String[] baraja) {
        for (int i = 0; i < baraja.length - 1; i++) {
            baraja[i] = baraja[i + 1];
        }
        baraja[baraja.length - 1] = null;
    }

    public void moverCartasDeMano(String[] mano) {
        for (int i = mano.length - 1; i > 0; i--) {
            mano[i] = mano[i - 1];
        }
    }

    public boolean esBlackjack(String[] mano) {

        if (contarCartasEnMano(mano) == 0 || contarCartasEnMano(mano) > 2) {
            return false;
        }

        boolean existeAs = false;
        boolean existe10 = false;

        for (int i = 0; i < 2; i++) {
            if (obtenerValorNumericoDeCarta(mano[i]) == 1) {
                existeAs = true;
            }
            if (obtenerValorNumericoDeCarta(mano[i]) == 10) {
                existe10 = true;
            }
        }

        return (existeAs && existe10);
    }

    public int contarCartasEnMano(String[] mano) {

        int cartas = 0;

        for (String carta : mano) {
            if (carta == null) {
                break;
            }
            cartas++;
        }

        return cartas;
    }

    public boolean sePasoDe21(String[] mano) {
        return calcularSumaDeMano(mano) > 21;
    }

    public String[] crearBaraja() {

        var pintas = new String[] {"CORAZON", "TREBOL", "DIAMANTE", "PICA"};
        var numerosCartas = new String[] {"AS", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ", "JOTA", "QUINA", "KAISER"};

        String[] baraja = new String[52];

        int indice = 0;
        for (String p : pintas) {
            for (String n : numerosCartas) {
                baraja[indice] = p + " " + n;
                indice++;
            }
        }

        return baraja;
    }

    public void barajar(String[] baraja) {

        List<String> barajaList = Arrays.asList(baraja);
        Collections.shuffle(barajaList);

        for (int i = 0; i < baraja.length; i++) {
            baraja[i] = barajaList.get(i);
        }
    }

    public String[] crearMano() {
        String[] mano = new String[11];
        return mano;
    }

    public int calcularSumaDeMano(String[] mano) {

        int valorTotal = 0;

        for (String carta : mano) {
            if (carta == null) {
                break;
            }
            valorTotal += obtenerValorNumericoDeCarta(carta);
        }

        return valorTotal;
    }

    public int obtenerValorNumericoDeCarta(String carta) {
        String valorDeCarta = carta.split(" ")[1];
        return crearMapaDeValores().get(valorDeCarta);
    }

    public String obtenerValorLiteralDeCarta(String carta) {
        String valorDeCarta = carta.split(" ")[1];
        return valorDeCarta;
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
            if (valorCarta > 10) { valorCarta = 10; }
            mapa.put(carta, valorCarta);
            valorCarta++;
        }

        return mapa;
    }
}
