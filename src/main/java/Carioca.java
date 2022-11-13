import enums.TipoDeCarta;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

public class Carioca extends JuegoDeCarta {

    private Integer numeroPartida = 0;
    private Integer numeroDePartidas = 1;

    private Carioca(Baraja baraja, List<Jugador> jugadores) {
        this.baraja = baraja;
        this.jugadores = jugadores;
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
        reiniciarConteoPartida();
        while (haySiguientePartida()) {
            this.siguientePartida();
            getBaraja().barajar();
            repartirCartas();
            realizarTurnosDeJugadores();
        }
        evaluarApuesta();
    }

    private void repartirCartas() {
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
        reiniciarConteoDeJugadores();
        while (haySiguienteJugador()) {
            this.siguienteJugador();
            jugarManosDeJugador();
        }
    }

    private void reiniciarConteoPartida() {
        numeroPartida = 1;
    }

    private void siguientePartida() {
        numeroPartida = numeroPartida + 1;
    }

    private boolean haySiguientePartida() {
        return numeroPartida + 1 < numeroDePartidas;
    }

    private void evaluarApuesta() {
    }

    private void jugarManosDeJugador() {
        enfocarJugador();
        while (jugadorEstaEnJuego) {
            mostrarTurnoDeJugador();
            var opciones = generarOpciones();
            mostrarOpcionesAJugador(opciones);
            int opcionEscogida = Utils.pedirOpcionHasta(opciones.size());
            opciones.get(opcionEscogida)
                    .get(0)
                    .run();
        }
    }

    private void mostrarTurnoDeJugador() {
        System.out.println("+**+");
        System.out.printf("**** Es turno de %s\n", obtenerJugadorEnJuego().getNombre());
        System.out.printf("**** Su monto es de $%s\n", obtenerJugadorEnJuego().getMonto());
        System.out.printf("**** Su apuesta es de $%s\n", obtenerJugadorEnJuego().getApuesta());
        System.out.println("+**+");
        System.out.println();
    }

    private void mostrarResultadosDeJugador() {
        System.out.println("+**+");
        System.out.printf("**** Resultados de %s\n", obtenerJugadorEnJuego().getNombre());
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
        agregarOpcionesPedirCarta(opciones);
        agregarOpcionBajarse(opciones);
        return opciones;
    }

    private boolean esManoJugadorPartible() {
        return obtenerJugadorEnJuego().getManoEnJuego().esManoPartible();
    }

    private boolean esManoJugadorPartible(Mano mano) {
        obtenerJugadorEnJuego().setManoEnJuego(mano);
        return esManoJugadorPartible();
    }

    public Jugador obtenerJugadorEnJuego() {
        return jugadores.get(jugadorEnJuego);
    }

    private void mostrarManoDeJugador() {
        System.out.printf("\nLa %s° mano es: \n",
                jugadores.get(jugadorEnJuego).getManos().indexOf(obtenerJugadorEnJuego().getManoEnJuego()) + 1);
        obtenerJugadorEnJuego().getManoEnJuego().mostrarMano();
    }

    private void mostrarManosDeJugador() {
        if (obtenerJugadorEnJuego().getManos().size() == 1) {
            System.out.println("\nSu mano es: ");
            obtenerJugadorEnJuego().getManoEnJuego().mostrarMano();
        } else {
            IntStream.range(0, obtenerJugadorEnJuego().getManos().size())
                    .peek(indice -> System.out.printf("\nLa %s° mano es: \n", indice + 1))
                    .forEach(indice -> obtenerJugadorEnJuego().getManos().get(indice).mostrarMano());
        }
    }

    public void mostrarOpcionesAJugador(List<List<Runnable>> opciones) {
        System.out.println("\nEscriba");
        IntStream.range(1, opciones.size())
                .peek(indice -> System.out.printf("[%s] ", indice))
                .forEach(indice -> opciones.get(indice).get(1).run());
        System.out.print("> ");
    }

    private void repartir(int cantidad) {
        for (int indice = 0; indice < cantidad; indice++) {
            baraja.pedirCarta(obtenerJugadorEnJuego());
        }
    }

    private void agregarOpcionSalida(List<List<Runnable>> opciones) {
        opciones.add(List.of(
                () -> System.exit(0),
                () -> System.out.println("para salir")));
    }

    private void agregarOpcionesPedirCarta(List<List<Runnable>> opciones) {
        if (obtenerJugadorEnJuego().getManos().size() == 1) {
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
        int contador = 0;
        for (Mano mano : obtenerJugadorEnJuego().getManos()) {
            String opcion = String.format("para pedir carta a su %s° mano", contador + 1);
            obtenerJugadorEnJuego().setManoEnJuego(mano);
            opciones.add(List.of(
                    () -> pedirCartaAManoDeJugador(mano),
                    () -> System.out.println(opcion)));
            contador++;
        }
    }

    private void agregarOpcionBajarse(List<List<Runnable>> opciones) {
        opciones.add(List.of(
                this::desenfocarJugador,
                () -> System.out.println("para bajarse")));
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
