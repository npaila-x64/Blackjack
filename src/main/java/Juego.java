import java.util.*;
import java.util.stream.IntStream;

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
        agregarOpcionSalida(opciones);
        agregarOpcionesPedirCarta(opciones);
        agregarOpcionesPartirCarta(opciones);
        agregarOpcionBajarse(opciones);
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
        jugador.setManoEnJuego(indiceMano);
        baraja.pedirCarta(jugador);
    }

    private void bajarManosDeJugador() {
        if (!esManoDealerBlackjack()) pedirCartasADealer();
        int cantidadDeManos = jugador.getManos().size();
        for (int indiceMano = 0; indiceMano < cantidadDeManos; indiceMano++) {
            jugador.setManoEnJuego(indiceMano);
            mostrarMano(indiceMano);
            mostrarGanadorDeRonda(evaluarManoGanadora());
        }
        jugador.eliminarManos();
    }

    public boolean esManoJugadorPartible(Jugador jugador) {
        return jugador.getManoEnJuego().esManoPartible();
    }

    public boolean esManoJugadorPartible(Jugador jugador, Integer indiceMano) {
        jugador.setManoEnJuego(indiceMano);
        return esManoJugadorPartible(jugador);
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
            IntStream.range(0, jugador.getManos().size())
                    .peek(indice -> System.out.printf("\nTu %s° mano es: \n", indice + 1))
                    .forEach(indice -> jugador.getManos().get(indice).mostrarMano());
        }
    }

    public void partirManoDeJugador() {
        partirManoDeJugador(0);
    }

    public void partirManoDeJugador(Integer indiceMano) {
        jugador.setManoEnJuego(indiceMano);
        jugador.partirMano();
    }

    public void mostrarOpciones(List<List<Runnable>> opciones) {
        System.out.println("\nEscriba");
        IntStream.range(1, opciones.size())
                .peek(indice -> System.out.printf("[%s] ", indice))
                .forEach(indice -> opciones.get(indice).get(1).run());
        System.out.print("> ");
    }

    private void repartir(Jugador jugador) {
        baraja.pedirCarta(jugador);
        baraja.pedirCarta(jugador);
    }

    private void agregarOpcionSalida(List<List<Runnable>> opciones) {
        opciones.add(List.of(
                () -> System.exit(0),
                () -> System.out.println("para salir")));
    }

    private void agregarOpcionesPartirCarta(List<List<Runnable>> opciones) {
        if (jugador.getManos().size() == 1) {
            agregarOpcionPartirCartaSingular(opciones);
        } else {
            agregarOpcionPartirCartaMultiple(opciones);
        }
    }

    private void agregarOpcionPartirCartaSingular(List<List<Runnable>> opciones) {
        if (esManoJugadorPartible(jugador)) {
            opciones.add(List.of(
                    this::partirManoDeJugador,
                    () -> System.out.println("para partir tu mano")));
        }
    }

    private void agregarOpcionPartirCartaMultiple(List<List<Runnable>> opciones) {
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

    private void agregarOpcionesPedirCarta(List<List<Runnable>> opciones) {
        if (jugador.getManos().size() == 1) {
            agregarOpcionPedirCartaSingular(opciones);
        } else {
            agregarOpcionPedirCartaMultiple(opciones);
        }
    }

    private void agregarOpcionPedirCartaSingular(List<List<Runnable>> opciones) {
        opciones.add(List.of(
                this::pedirCartaAManoDeJugador,
                () -> System.out.println("para pedir carta")));
    }

    private void agregarOpcionPedirCartaMultiple(List<List<Runnable>> opciones) {
        for (int indice = 0; indice < jugador.getManos().size(); indice++) {
            String opcion = String.format("para pedir carta a tu %s° mano", indice + 1);
            int finalIndice = indice;
            opciones.add(List.of(
                    () -> pedirCartaAManoDeJugador(finalIndice),
                    () -> System.out.println(opcion)));
        }
    }

    private void agregarOpcionBajarse(List<List<Runnable>> opciones) {
        opciones.add(List.of(
                this::bajarManosDeJugador,
                () -> System.out.println("para bajarse")));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
