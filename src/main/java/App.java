import java.util.*;

public class App {

    public static void main(String[] args) {

        App blackjack = new App();
        blackjack.iniciar();
    }

    public void iniciar() {

        List<String> baraja = crearBaraja();
        barajar(baraja);

        jugar(baraja);
    }

    public void jugar(List<String> baraja) {

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
        List<String> manoJugador = crearMano();
        List<String> manoDealer = crearMano();
        repartir(baraja, manoJugador);
        repartir(baraja, manoDealer);

        salirBucle:
        while (true) {
            mostrarManos(manoJugador, manoDealer);
            switch (pedirOpcion()) {
                case 1 -> pedirCarta(baraja, manoJugador);
                case 2 -> {bajarse(baraja, manoJugador, manoDealer); break salirBucle;}
                case 3 -> {
                    if (esManoPartible(manoJugador)) {
                        //cambiarModoManoDoble(baraja, partirMano(manoJugador), manoDealer);
                        break salirBucle;
                    } else {
                        System.out.println("Tu mano no se puede partir.");
                    }
                }
            }
        }
    }

    public void cambiarModoManoDoble(String[] baraja, String[][] manosJugador, String[] manoDealer) {
        salirBucle:
        while (true) {
            switch (pedirOpcion()) {
            }
        }
    }

    private int pedirValor() throws InputMismatchException {
        return new Scanner(System.in).nextInt();
    }

    public int pedirOpcion() {
        try {
            System.out.print("\nEscriba (1) para pedir carta, (2) para bajarse, o (3) para partir tu mano.\n> ");
            return pedirValor();
        } catch (InputMismatchException e) {
            return pedirOpcion();
        }
    }

    private void mostrarManos(List<String> manoJugador, List<String> manoDealer) {
        System.out.println("La mano del dealer es: ");
        mostrarManoConCartaEscondida(manoDealer);
        System.out.println("\nTu mano es: ");
        mostrarMano(manoJugador);
    }

    private void mostrarManosConDobleMano(List<String> manosJugador, List<String> manoDealer) {
        /*
        System.out.println("La mano del dealer es: ");
        mostrarManoConCartaEscondida(manoDealer);
        System.out.println("\nTus manos son: ");
        for (var mano : manosJugador) {
            mostrarMano(mano);
            System.out.println();
        }
        */
    }

    public void turnoDeDealer(List<String> baraja, List<String> manoDealer) {
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

    public boolean esManoPartible(List<String> manoJugador) {
        if (contarCartasEnMano(manoJugador) > 2) return false;
        return obtenerValorNumericoDeCarta(manoJugador.get(0)) ==
                obtenerValorNumericoDeCarta(manoJugador.get(1));
    }

    public void bajarse(List<String> baraja, List<String> manoJugador, List<String> manoDealer) throws NullPointerException {

        // Cuando el Jugador decide bajarse, el Dealer pide sus cartas
        turnoDeDealer(baraja, manoDealer);

        System.out.println("La mano del dealer es: ");
        mostrarMano(manoDealer);
        System.out.println("\nTu mano es: ");
        mostrarMano(manoJugador);

        List<String> manoGanadora = verificarGanador(manoJugador, manoDealer);

        if (manoGanadora.equals(manoJugador)) {
            System.out.println("¡¡Ganaste!! :)))");
        } else {
            System.out.println("¡¡Perdiste!! :(((");
        }
    }

    public List<String> verificarGanador(List<String> manoJugador, List<String> manoDealer) {

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

    public void mostrarMano(List<String> mano) {
        for (String carta : mano) {
            if (carta == null) {
                break;
            }
            System.out.printf("[%s DE %s]%n",
                    obtenerValorLiteralDeCarta(carta), obtenerPintaDeCarta(carta));
        }
    }

    public void mostrarManoConCartaEscondida(List<String> mano) {

        int indice = 0;

        for (String carta : mano) {
            if (carta == null) {
                break;
            }
            if (indice == 0) {
                System.out.println("[***************]");
            } else {
                System.out.printf("[%s DE %s]%n",
                        obtenerValorLiteralDeCarta(carta), obtenerPintaDeCarta(carta));
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

    public boolean esBlackjack(List<String> mano) {

        if (contarCartasEnMano(mano) == 0 || contarCartasEnMano(mano) > 2) {
            return false;
        }

        boolean existeAs = false;
        boolean existe10 = false;

        for (int indice = 0; indice < 2; indice++) {
            if (obtenerValorNumericoDeCarta(mano.get(indice)) == 1) {
                existeAs = true;
            }
            if (obtenerValorNumericoDeCarta(mano.get(indice)) == 10) {
                existe10 = true;
            }
        }

        return (existeAs && existe10);
    }

    public int contarCartasEnMano(List<String> mano) {

        int cartas = 0;

        for (String carta : mano) {
            if (carta == null) {
                break;
            }
            cartas++;
        }

        return cartas;
    }

    public boolean sePasoDe21(List<String> mano) {
        return calcularSumaDeMano(mano) > 21;
    }

    public List<String> crearBaraja() {

        var pintas = List.of("CORAZON", "TREBOL", "DIAMANTE", "PICA");
        var numerosCartas = List.of("AS", "DOS", "TRES", "CUATRO", "CINCO", "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ", "JOTA", "QUINA", "KAISER");

        List<String> baraja = new ArrayList<>();

        int indice = 0;
        for (String p : pintas) {
            for (String n : numerosCartas) {
                baraja.add(p + " " + n);
                indice++;
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

    public int calcularSumaDeMano(List<String> mano) {

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
