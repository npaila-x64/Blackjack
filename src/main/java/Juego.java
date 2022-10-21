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
        // Las opciones se van generando según la situación del jugador
        // LinkedList garantiza el orden de inserción de las opciones
        List<List<Runnable>> opciones = new LinkedList<>();

        opciones.add(List.of(
                () -> System.exit(0),
                () -> System.out.println("para salir")));

        if (jugador.getManos().size() == 1) {
            opciones.add(List.of(
                    this::pedirCartaAManoDeJugador,
                    () -> System.out.println("para pedir carta")));
        } else {
            for (int indice = 0; indice < jugador.getManos().size(); indice++) {
                String opcion = String.format("para pedir carta a tu %s° mano", indice + 1);
                int finalIndice = indice;
                opciones.add(List.of(
                        () -> pedirCartaAManoDeJugador(finalIndice),
                        () -> System.out.println(opcion)));
            }
        }

        if (jugador.getManos().size() == 1) {
            if (esManoJugadorPartible(jugador)) {
                opciones.add(List.of(
                        this::partirManoDeJugador,
                        () -> System.out.println("para partir tu mano")));
            }
        } else {
            for (int indice = 0; indice < jugador.getManos().size(); indice++) {
                if (esManoJugadorPartible(jugador, indice)) {
                    String opcion = String.format("para partir tu %s° mano", indice + 1);
                    int finalIndice = indice;
                    opciones.add(List.of(
                            () -> partirManoDeJugador(finalIndice),
                            () -> System.out.println(opcion)));
                }
            }
        }

        opciones.add(List.of(
                this::bajarManosDeJugador,
                () -> System.out.println("para bajarse")));

        return opciones;
    }

    private void jugar() {
        while (existeManoPorJugar()) {
            var opciones = generarOpciones();
            mostrarManosConDealerEscondido();
            mostrarOpciones(opciones);
            opciones.get(Utils.pedirOpcionHasta(opciones.size()))
                    .get(0) // obtiene el método correspodiente a la acción que se desea realizar
                    .run();
        }
    }

    private void pedirCartaAManoDeJugador() {
        pedirCartaAManoDeJugador(0);
    }

    private void pedirCartaAManoDeJugador(Integer indiceMano) {
        jugador.setManoEnJuego(jugador.getManos().get(indiceMano));
        baraja.pedirCarta(jugador);
    }

    private void bajarManosDeJugador() {
        if (!esManoDealerBlackjack()) pedirCartasADealer();
        int cantidadDeManos = jugador.getManos().size();
        for (int indiceMano = 0; indiceMano < cantidadDeManos; indiceMano++) {
            jugador.setManoEnJuego(jugador.getManos().get(indiceMano));
            mostrarMano(indiceMano);
            mostrarGanadorDeRonda(evaluarManoGanadora());
        }
        jugador.eliminarManos();
    }

    public boolean esManoJugadorPartible(Jugador jugador) {
        return jugador.getManoEnJuego().esManoPartible();
    }

    public boolean esManoJugadorPartible(Jugador jugador, Integer indiceMano) {
        jugador.setManoEnJuego(jugador.getManos().get(indiceMano));
        return jugador.getManoEnJuego().esManoPartible();
    }

    private boolean existeManoPorJugar() {
        return !jugador.getManos().isEmpty();
    }

    public boolean esManoDealerBlackjack() {
        return dealer.getManoEnJuego().esBlackjack();
    }

    public Jugador evaluarManoGanadora() {

        if (jugador.getManoEnJuego().esBlackjack()) return jugador;
        if (dealer.getManoEnJuego().esBlackjack()) return dealer;
        if (dealer.getManoEnJuego().sePasoDe21()) return dealer;
        if (jugador.getManoEnJuego().sePasoDe21()) return jugador;

        return jugador.getManoEnJuego().calcularSumaDeMano() > dealer.getManoEnJuego().calcularSumaDeMano() ?
                jugador : dealer;
    }

    public void mostrarGanadorDeRonda(Jugador ganador) {
        if (ganador.equals(jugador)) {
            System.out.println("¡¡Ganaste esta ronda!! :)))\n");
        } else {
            System.out.println("¡¡Perdiste esta ronda!! :(((\n");
        }
    }

    public void pedirCartasADealer() {
        // CPU simple basado en una estrategia simple,
        // pedir cartas mientras que el total de su mano sea menor a 16
        while (dealer.getManoEnJuego().calcularSumaDeMano() < 16) {
            baraja.pedirCarta(dealer);
        }
    }

    private void mostrarManosConDealerEscondido() {
        System.out.println("La mano del dealer es: ");
        dealer.getManoEnJuego().mostrarConCartaEscondida();
        mostrarManosDeJugador();
    }

    private void mostrarMano(Integer indiceMano) {
        System.out.println("La mano del dealer es: ");
        dealer.getManoEnJuego().mostrarMano();
        System.out.printf("\nTu %s° mano es: \n", indiceMano + 1);
        jugador.getManos().get(indiceMano).mostrarMano();
    }

    private void mostrarManosDeJugador() {
        if (jugador.getManos().size() == 1) {
            System.out.println("\nTu mano es: ");
            jugador.getManoEnJuego().mostrarMano();
        } else {
            for (int indice = 0; indice < jugador.getManos().size(); indice++) {
                System.out.printf("\nTu %s° mano es: \n", indice + 1);
                jugador.getManos().get(indice).mostrarMano();
            }
        }
    }

    public void partirManoDeJugador() {
        partirManoDeJugador(0);
    }

    public void partirManoDeJugador(Integer numero) {
        jugador.setManoEnJuego(jugador.getManos().get(numero));
        jugador.partirMano();
    }

    public void mostrarOpciones(List<List<Runnable>> opciones) {
        System.out.println("\nEscriba");
        for (int indice = 1; indice < opciones.size(); indice++) {
            System.out.printf("(%s) ", opciones.indexOf(opciones.get(indice)));
            opciones.get(indice).get(1)
                    .run();
        }
        System.out.print("> ");
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
