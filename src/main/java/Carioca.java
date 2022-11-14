import enums.TipoDeCarta;

import java.util.*;
import java.util.stream.IntStream;

public class Carioca extends JuegoDeCarta {

    private Integer numeroPartida = 0;
    private Integer numeroDePartidas = 1;
    private List<Jugador> ganadores;
    private Deque<Carta> pilaDeDescartes;
    private Boolean cartaEstaTomada;
    private Boolean hayGanadorDePartida;

    private Carioca(Baraja baraja, List<Jugador> jugadores) {
        this.baraja = baraja;
        this.jugadores = jugadores;
        this.ganadores = new LinkedList<>();
        this.pilaDeDescartes = new ArrayDeque<>();
    }

    public static Carioca crearNuevoJuego() {
        List<Jugador> jugadores = new LinkedList<>();
        BarajaBuilder barajaBuilder = new BarajaBuilder(TipoDeCarta.INGLESA);
        barajaBuilder.agregarMazo();
        barajaBuilder.agregarMazo();
        barajaBuilder.agregarDosJokers();
        barajaBuilder.agregarDosJokers();
        Carioca carioca = new Carioca(barajaBuilder.construir(), jugadores);
        return carioca;
    }

    @Override
    public void jugar() {
        mostrarTitulo();
        realizarApuestas();
        realizarPartidas();
        evaluarApuestas();
    }

    private void realizarPartidas() {
        reiniciarConteoPartida();
        while (haySiguientePartida()) {
            this.siguientePartida();
            getBaraja().barajar();
            repartirCartas();
            pilaDeDescartes.add(getBaraja().pedirCarta());
            realizarTurnosDeJugadores();
        }
    }

    private void repartirCartas() {
        reiniciarConteoDeJugadores();
        while (haySiguienteJugador()) {
            this.siguienteJugador();
            switch (numeroPartida) {
                case 1 -> repartir(6);
                case 2 -> repartir(7);
                case 3 -> repartir(8);
                case 4 -> repartir(9);
                case 5 -> repartir(10);
                case 6 -> repartir(11);
                case 7 -> repartir(12);
            }
        }
    }

    private void realizarTurnosDeJugadores() {
        hayGanadorDePartida = false;
        while (!hayGanadorDePartida) {
            reiniciarConteoDeJugadores();
            while (haySiguienteJugador()) {
                this.siguienteJugador();
                turnoDeJugador();
            }
        }
    }

    private void reiniciarConteoPartida() {
        numeroPartida = 0;
    }

    private void siguientePartida() {
        numeroPartida = numeroPartida + 1;
    }

    private boolean haySiguientePartida() {
        return numeroPartida + 1 <= numeroDePartidas;
    }

    private void evaluarApuestas() {
    }

    private void turnoDeJugador() {
        enfocarJugador();
        cartaEstaTomada = false;
        while (jugadorEstaEnJuego) {
            mostrarTurnoDeJugador();
            if (!cartaEstaTomada) {
                mostrarCartaVisible();
                mostrarManoDeJugador();
            } else {
                mostrarManoEnumeradaDeJugador();
            }
            evaluarOpciones();
        }
    }

    private void evaluarOpciones() {
        var opciones = generarOpciones();
        mostrarOpcionesAJugador(opciones);
        int opcionEscogida = Utils.pedirOpcionHasta(opciones.size() - 1);
        opciones.get(opcionEscogida)
                .get(0)
                .run();
    }

    private void mostrarCartaVisible() {
        System.out.println("La carta visible es:");
        pilaDeDescartes.peekFirst().mostrarCarta();
    }

    private void pedirCartaDeMazo() {
        pedirCartaDeBaraja();
        asignarEstadoCartaTomada();
    }

    private void pedirCartaDePilaDeDescartes() {
        obtenerJugadorEnJuego().getManoEnJuego().agregarCarta(pilaDeDescartes.removeFirst());
        asignarEstadoCartaTomada();
    }

    private void asignarEstadoCartaTomada() {
        cartaEstaTomada = true;
    }

    private void devolverCartaAPilaDeDescartes(Carta carta) {
        pilaDeDescartes.addFirst(obtenerJugadorEnJuego().getManoEnJuego().removerCarta(carta));
        jugadorEstaEnJuego = false;
    }

    private void bajarse() {
        desenfocarJugador();
        hayGanadorDePartida = true;
    }

    private void mostrarTurnoDeJugador() {
        System.out.println("+**+");
        System.out.printf("**** %s° partida\n", numeroPartida);
        System.out.printf("**** Es turno de %s\n", obtenerJugadorEnJuego().getNombre());
        System.out.printf("**** Su monto es de $%s\n", obtenerJugadorEnJuego().getMonto());
        System.out.printf("**** Su apuesta es de $%s\n", obtenerJugadorEnJuego().getApuesta());
        System.out.println("+**+");
        System.out.println();
    }

    private void mostrarTitulo() {
        System.out.println(
                " ____                                                    \n" +
                "/\\  _`\\                    __                            \n" +
                "\\ \\ \\/\\_\\     __     _ __ /\\_\\    ___     ___     __     \n" +
                " \\ \\ \\/_/_  /'__`\\  /\\`'__\\/\\ \\  / __`\\  /'___\\ /'__`\\   \n" +
                "  \\ \\ \\L\\ \\/\\ \\L\\.\\_\\ \\ \\/ \\ \\ \\/\\ \\L\\ \\/\\ \\__//\\ \\L\\.\\_ \n" +
                "   \\ \\____/\\ \\__/.\\_\\\\ \\_\\  \\ \\_\\ \\____/\\ \\____\\ \\__/.\\_\\\n" +
                "    \\/___/  \\/__/\\/_/ \\/_/   \\/_/\\/___/  \\/____/\\/__/\\/_/\n" +
                "                                                         \n" +
                "                                                         \n");
    }

    private List<List<Runnable>> generarOpciones() {
        // Las opciones se van agregando según la situación del jugador
        // LinkedList garantiza el orden de inserción de las opciones y
        // por ende como estas se muestran al jugador
        List<List<Runnable>> opciones = new LinkedList<>();
        agregarOpcionSalida(opciones);
        agregarOpcionPedirCartaDeMazo(opciones);
        agregarOpcionPedirCartaDePila(opciones);
        agregarOpcionDevolverCarta(opciones);
        agregarOpcionBajarse(opciones);
        return opciones;
    }

    private void mostrarManoDeJugador() {
        System.out.println("\nSu mano es:");
        obtenerJugadorEnJuego().getManoEnJuego().mostrarMano();
    }

    private void mostrarManoEnumeradaDeJugador() {
        System.out.println("\nSu mano es:");
        obtenerJugadorEnJuego().getManoEnJuego().mostrarManoEnumerada();
    }

    public void mostrarOpcionesAJugador(List<List<Runnable>> opciones) {
        System.out.println("\nEscriba");
        IntStream.range(1, opciones.size())
                .peek(indice -> System.out.printf("[%s] ", indice))
                .forEach(indice -> opciones.get(indice).get(1).run());
        System.out.print("> ");
    }

    private void repartir(int cantidadDeCartas) {
        for (int contador = 0; contador < cantidadDeCartas; contador++) {
            pedirCartaDeBaraja();
        }
    }

    private void agregarOpcionSalida(List<List<Runnable>> opciones) {
        opciones.add(List.of(
                () -> System.exit(0),
                () -> System.out.println("para salir")));
    }

    private void agregarOpcionPedirCartaDeMazo(List<List<Runnable>> opciones) {
        if (!cartaEstaTomada) {
            opciones.add(List.of(
                    this::pedirCartaDeMazo,
                    () -> System.out.println("para tomar una carta del mazo")));
        }
    }

    private void agregarOpcionPedirCartaDePila(List<List<Runnable>> opciones) {
        if (!cartaEstaTomada) {
            opciones.add(List.of(
                    this::pedirCartaDePilaDeDescartes,
                    () -> System.out.println("para tomar la carta de la pila")));
        }
    }

    private void agregarOpcionDevolverCarta(List<List<Runnable>> opciones) {
        if (cartaEstaTomada) {
            int contador = 0;
            for (Carta carta : obtenerJugadorEnJuego().getManoEnJuego().getCartas()) {
                String opcion = String.format("para devolver la %s° carta a la pila", contador + 1);
                opciones.add(List.of(
                        () -> devolverCartaAPilaDeDescartes(carta),
                        () -> System.out.println(opcion)));
                contador++;
            }
        }
    }

    private void agregarOpcionBajarse(List<List<Runnable>> opciones) {
        // TODO Si existen trios o lo que sea
        if (false) {
            opciones.add(List.of(
                    this::bajarse,
                    () -> System.out.println("¡BAJARSE!")));
        }
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public Baraja getBaraja() {
        return baraja;
    }

    @Override public String toString() {
        return String.format("[Juego: jugadorEnJuego=%s, jugadores=%s, baraja=%s]"
                , jugadorEnJuego, jugadores, baraja);
    }
}
