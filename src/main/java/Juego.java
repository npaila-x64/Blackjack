import java.util.*;

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
        imprimirTitulo();

        baraja = new Baraja();
        baraja.barajar();

        dealer = new Jugador(new Mano(), true);
        jugador = new Jugador(new Mano());
        repartirCartas();

        jugar();
    }

    private void repartirCartas() {
        repartir(dealer);
        repartir(jugador);
    }

    private void imprimirTitulo() {
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
    }

    private List<List<Runnable>> generarOpciones() {
        // Las opciones se van generando según el contexto

        List<List<Runnable>> opciones = new LinkedList<>(); //LinkedList garantiza el orden de inserción

        opciones.add(List.of(
                () -> System.exit(0),
                () -> System.out.println("para salir")));

        opciones.add(List.of(
                this::pedirCartaAJugador,
                () -> System.out.println("para pedir carta")));

        opciones.add(List.of(
                this::bajarJugador,
                () -> System.out.println("para bajarse")));

        if (esManoJugadorPartible(jugador)) {
            opciones.add(List.of(
                    this::partirManoJugador,
                    () -> System.out.println("para partir tu mano")));
        }
        return opciones;
    }

    private void jugar() {
        while (true) {
            var opciones = generarOpciones();
            mostrarManos();
            mostrarOpciones(opciones);
            opciones.get(Utilidad.pedirOpcionHasta(opciones.size()))
                    .get(0) // obtiene el método correspodiente a la acción que se desea realizar
                    .run();
        }
    }

    private void pedirCartaAJugador() {
        baraja.pedirCarta(jugador);
    }

    private void bajarJugador() {
        if (esManoDealerBlackjack()) {
            mostrarGanador(dealer);
            return;
        }
        realizarTurnoDeDealer();
        procederABajarse();
    }

    public boolean esManoJugadorPartible(Jugador jugador) {
        return jugador.getManoEnJuego().esManoPartible();
    }

    public void partirManoJugador() {
        jugador.partirMano();
        salirJuego:
        while (true) {
            mostrarManosConDobleMano();
            switch (Utilidad.pedirOpcionHasta(3)) {
                case 1 -> {
                    jugador.setManoEnJuego(jugador.getManos().get(0));
                    baraja.pedirCarta(jugador);}
                case 2 -> {
                    jugador.setManoEnJuego(jugador.getManos().get(1));
                    baraja.pedirCarta(jugador);}
                case 3 -> {
                    if (esManoDealerBlackjack()) break salirJuego;
                    realizarTurnoDeDealer();
                    procederABajarse();
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
        return dealer.getManoEnJuego().esBlackjack();
    }

    public Jugador bajarse() throws NullPointerException {
        System.out.println("La mano del dealer es: ");
        dealer.getManoEnJuego().mostrarMano();
        System.out.println("\nTu mano es: ");
        jugador.getManoEnJuego().mostrarMano();

        Jugador jugadorGanador = verificarGanador();
        return jugadorGanador;
    }

    public Jugador verificarGanador() {

        if (jugador.getManoEnJuego().esBlackjack()) return jugador;
        if (dealer.getManoEnJuego().esBlackjack()) return dealer;
        if (dealer.getManoEnJuego().sePasoDe21()) return dealer;
        if (jugador.getManoEnJuego().sePasoDe21()) return jugador;

        return jugador.getManoEnJuego().calcularSumaDeMano() > dealer.getManoEnJuego().calcularSumaDeMano() ?
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
        while (dealer.getManoEnJuego().calcularSumaDeMano() < 16) {
            baraja.pedirCarta(dealer);
        }
    }

    private void mostrarManos() {
        System.out.println("La mano del dealer es: ");
        dealer.getManoEnJuego().mostrarConCartaEscondida();
        System.out.println("\nTu mano es: ");
        jugador.getManoEnJuego().mostrarMano();
    }

    private void mostrarManosConDobleMano() {
        System.out.println("La mano del dealer es: ");
        dealer.getManoEnJuego().mostrarConCartaEscondida();
        System.out.println("\nTu primera mano es: ");
        jugador.setManoEnJuego(jugador.getManos().get(0));
        jugador.getManoEnJuego().mostrarMano();
        System.out.println("\nTu segunda mano es: ");
        jugador.setManoEnJuego(jugador.getManos().get(1));
        jugador.getManoEnJuego().mostrarMano();
        System.out.println();
        mostrarPedirOpcionDobleMano();
    }

    public void mostrarOpciones(List<List<Runnable>> opciones) {
        System.out.println("\nEscriba");
        for (List<Runnable> opcion : opciones) {
            System.out.printf("(%s) ", opciones.indexOf(opcion));
            opcion.get(1)
                    .run();
        }
        System.out.print("> ");
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

    @Override
    public String toString() {
        return super.toString();
    }
}
