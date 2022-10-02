import java.util.ArrayList;
import java.util.List;

public class Juego {

    private Baraja baraja;
    private Jugador dealer;
    private Jugador jugador;

    public void setBaraja(Baraja baraja) {
        this.baraja = baraja;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public void setDealer(Jugador dealer) {
        this.dealer = dealer;
    }

    public void iniciar() {
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

        baraja = new Baraja();
        baraja.barajar();

        Mano manoDealer = new Mano();
        dealer = new Jugador(manoDealer, true);
        Mano manoJugador = new Mano();
        jugador = new Jugador(manoJugador);
        repartir(dealer);
        repartir(jugador);

        jugar();
    }

    private void jugar() {
        salirJuego:
        while (true) {
            mostrarManos();
            switch (Utilidad.pedirOpcion()) {
                case 1 -> baraja.pedirCarta(jugador);
                case 2 -> {
                    if (esManoDealerBlackjack()) break salirJuego;
                    realizarTurnoDeDealer();
                    procederABajarse();
                    break salirJuego;
                }
                case 3 -> {
                    if (jugador.getMano().esManoPartible()) {
//                        jugarADobleMano(baraja, partirMano(manoJugador), manoDealer);
                        break salirJuego;
                    }
                    mostrarManoNoEsPartible();
                }
            }
        }
    }

    public List<List<String>> partirMano() {
        List<String> primeraMano = new ArrayList<>();
//        primeraMano.add(jugador.getMano().get(0));
        List<String> segundaMano = new ArrayList<>();
//        segundaMano.add(manoJugador.get(1));
//        manoJugador.clear();
        List<List<String>> manosJugador = new ArrayList<>();
        manosJugador.add(primeraMano);
        manosJugador.add(segundaMano);
        return manosJugador;
    }

    public void jugarADobleMano() {
//        List<String> primeraMano = manosJugador.get(0);
//        List<String> segundaMano = manosJugador.get(1);
        salirJuego:
        while (true) {
            mostrarManosConDobleMano();
            switch (Utilidad.pedirOpcion()) {
                case 1 -> baraja.pedirCarta(jugador);
                case 2 -> baraja.pedirCarta(jugador);
                case 3 -> {
                    if (esManoDealerBlackjack()) break salirJuego;
                    realizarTurnoDeDealer();
//                    procederABajarse(baraja, primeraMano, manoDealer);
//                    procederABajarse(baraja, segundaMano, manoDealer);
                    break salirJuego;
                }
            }
        }
    }

    public void procederABajarse() {
        var manoGanadora = bajarse();
        mostrarGanador(manoGanadora);
    }

    public boolean esManoDealerBlackjack() {
        if (dealer.getMano().esBlackjack()) {
            mostrarGanador(dealer);
            return true;
        }
        return false;
    }

    public void mostrarManoNoEsPartible() {
        System.out.println("----->X  Tu mano no se puede partir\n");
    }

    public Jugador bajarse() throws NullPointerException {
        System.out.println("La mano del dealer es: ");
        dealer.getMano().mostrarMano();
        System.out.println("\nTu mano es: ");
        jugador.getMano().mostrarMano();

        Jugador jugadorGanador = verificarGanador();
        return jugadorGanador;
    }

    public Jugador verificarGanador() {

        if (jugador.getMano().esBlackjack()) return jugador;
        if (dealer.getMano().esBlackjack()) return dealer;
        if (dealer.getMano().sePasoDe21()) return dealer;
        if (jugador.getMano().sePasoDe21()) return jugador;

        return jugador.getMano().calcularSumaDeMano() > dealer.getMano().calcularSumaDeMano() ?
                jugador : dealer;
    }

    public void mostrarGanador(Jugador ganador) {
        if (ganador.equals(jugador)) {
            System.out.println("¡¡Ganaste esta ronda!! :)))");
        } else {
            System.out.println("¡¡Perdiste esta ronda!! :(((");
        }
        System.out.println();
    }

    public void realizarTurnoDeDealer() {
        // CPU simple basado en una estrategia simple,
        // pedir cartas mientras que el total de su mano sea menor a 16
        while (dealer.getMano().calcularSumaDeMano() < 16) {
            baraja.pedirCarta(dealer);
        }
    }

    private void mostrarManos() {
        System.out.println("La mano del dealer es: ");
        dealer.getMano().mostrarConCartaEscondida();
        System.out.println("\nTu mano es: ");
        jugador.getMano().mostrarMano();
        mostrarPedirOpcion();
    }

    private void mostrarManosConDobleMano() {
        System.out.println("La mano del dealer es: ");
        dealer.getMano().mostrarConCartaEscondida();
        System.out.println("\nTu primera mano es: ");
//        mostrarMano(manosJugador.get(0));
        System.out.println("\nTu segunda mano es: ");
//        mostrarMano(manosJugador.get(1));
        System.out.println();
        mostrarPedirOpcionDobleMano();
    }

    public void mostrarPedirOpcion() {
        System.out.print("""
            
            Escriba
            (1) para pedir carta
            (2) para bajarse
            (3) para partir tu mano
            """.concat("> "));
    }

    public void mostrarPedirOpcionDobleMano() {
        System.out.print("""
            
            Escriba
            (1) para pedir carta a tu primera mano
            (2) para pedir carta a tu segunda mano
            (3) para bajarse
            """.concat("> "));
    }

    private void repartir(Jugador jugador) {
        baraja.pedirCarta(jugador);
        baraja.pedirCarta(jugador);
    }
}
