import java.util.*;
import java.util.stream.IntStream;

public class Blackjack implements JuegoDeCarta {

    private Baraja baraja;
    private List<Jugador> jugadores;
    private Integer jugadorEnJuego = 0;
    private Boolean jugadorEstaEnJuego;

    private Blackjack(Baraja baraja, List<Jugador> jugadores) {
        this.baraja = baraja;
        this.jugadores = jugadores;
    }

    public static Blackjack crearNuevoJuego() {
        List<Jugador> jugadores = new LinkedList<>();
        jugadores.add(Jugador.crearDealer());
        Blackjack blackjack = new Blackjack(new Baraja(), jugadores);
        blackjack.getBaraja().barajar();
        return blackjack;
    }

    @Override
    public void jugar() {
        mostrarTitulo();
        repartirCartas();
        realizarApuestas();
        realizarTurnosDeJugadores();
        bajarJugadores();
    }

    private void realizarTurnosDeJugadores() {
        reiniciarConteoDeJugadores();
        while (haySiguienteJugador()) {
            this.siguienteJugador();
            jugarManosDeJugador();
        }
    }

    private void realizarApuestas() {
        reiniciarConteoDeJugadores();
        while (haySiguienteJugador()) {
            this.siguienteJugador();
            realizarApuesta();
        }
    }

    private void realizarApuesta() {
        mostrarMontoDeJugador();
        System.out.print("¿Cuanto apuesta " + obtenerJugadorEnJuego().getNombre() + "?\n> ");
        int apuesta = Utils.pedirOpcionHasta(obtenerJugadorEnJuego().getMonto());
        obtenerJugadorEnJuego().apostar(apuesta);
    }

    private void mostrarMontoDeJugador() {
        System.out.printf("El monto total de %s es $%s\n",
                obtenerJugadorEnJuego().getNombre(), obtenerJugadorEnJuego().getMonto());
    }

    private void bajarJugadores() {
        if (!esManoDealerBlackjack()) pedirCartasADealer();
        reiniciarConteoDeJugadores();
        while (haySiguienteJugador()) {
            this.siguienteJugador();
            evaluarManosDeJugadores();
        }
    }

    private void reiniciarConteoDeJugadores() {
        jugadorEnJuego = 0;
    }

    private void evaluarManosDeJugadores() {
        mostrarResultadosDeJugador();
        mostrarManoDeDealer();
        for (Mano mano : obtenerJugadorEnJuego().getManos()) {
            evaluarManoDeJugador(mano);
        }
        obtenerJugadorEnJuego().setApuesta(0);
        mostrarMontoDeJugador();
    }

    private void evaluarManoDeJugador(Mano mano) {
        obtenerJugadorEnJuego().setManoEnJuego(mano);
        mostrarManoDeJugador();
        mostrarGanadorDeRonda();
        evaluarApuesta();
    }

    private void evaluarApuesta() {
        int apuestaUnitaria = obtenerJugadorEnJuego().getApuesta()
                / obtenerJugadorEnJuego().getManos().size();
        if (evaluarManoGanadora().equals(obtenerJugadorEnJuego())) {
            obtenerJugadorEnJuego().agregarAMonto(apuestaUnitaria);
            obtenerJugadorEnJuego().agregarAMonto(apuestaUnitaria);
            System.out.printf("%s acaba de ganar $%s\n",
                    obtenerJugadorEnJuego().getNombre(), apuestaUnitaria);
        } else {
            System.out.printf("%s acaba de perder $%s\n",
                    obtenerJugadorEnJuego().getNombre(), apuestaUnitaria);
        }
    }

    private void jugarManosDeJugador() {
        enfocarJugador();
        while (jugadorEstaEnJuego) {
            mostrarTurnoDeJugador();
            var opciones = generarOpciones();
            mostrarManoDeDealerConCartaEscondida();
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

    private void repartirCartas() {
        for (Jugador jugador : jugadores) {
            repartir(jugador);
        }
    }

    private void mostrarTitulo() {
        System.out.println(
               " /$$$$$$$  /$$                     /$$                               /$$      \n" +
               "| $$__  $$| $$                    | $$                              | $$      \n" +
               "| $$  \\ $$| $$  /$$$$$$   /$$$$$$$| $$   /$$ /$$  /$$$$$$   /$$$$$$$| $$   /$$\n" +
               "| $$$$$$$ | $$ |____  $$ /$$_____/| $$  /$$/|__/ |____  $$ /$$_____/| $$  /$$/\n" +
               "| $$__  $$| $$  /$$$$$$$| $$      | $$$$$$/  /$$  /$$$$$$$| $$      | $$$$$$/ \n" +
               "| $$  \\ $$| $$ /$$__  $$| $$      | $$_  $$ | $$ /$$__  $$| $$      | $$_  $$ \n" +
               "| $$$$$$$/| $$|  $$$$$$$|  $$$$$$$| $$ \\  $$| $$|  $$$$$$$|  $$$$$$$| $$ \\  $$\n" +
               "|_______/ |__/ \\_______/ \\_______/|__/  \\__/| $$ \\_______/ \\_______/|__/  \\__/\n" +
               "                                       /$$  | $$                              \n" +
               "                                      |  $$$$$$/                              \n" +
               "                                       \\______/                              ");
    }

    private List<List<Runnable>> generarOpciones() {
        // Las opciones se van agregando según la situación del jugador
        // LinkedList garantiza el orden de inserción de las opciones y
        // por ende como estas se muestran al jugador
        List<List<Runnable>> opciones = new LinkedList<>();
        agregarOpcionSalida(opciones);
        agregarOpcionesPedirCarta(opciones);
        agregarOpcionesPartirCarta(opciones);
        agregarOpcionBajarse(opciones);
        return opciones;
    }

    private void siguienteJugador() {
        jugadorEnJuego = jugadorEnJuego + 1;
        if (jugadores.get(jugadorEnJuego).esDealer()) siguienteJugador();
    }

    private boolean haySiguienteJugador() {
        return jugadorEnJuego + 1 < jugadores.size();
    }

    private void pedirCartaAManoDeJugador() {
        baraja.pedirCarta(obtenerJugadorEnJuego());
    }

    private void pedirCartaAManoDeJugador(Mano mano) {
        obtenerJugadorEnJuego().setManoEnJuego(mano);
        baraja.pedirCarta(obtenerJugadorEnJuego());
    }

    private void enfocarJugador() {
        jugadorEstaEnJuego = true;
    }

    private void desenfocarJugador() {
        jugadorEstaEnJuego = false;
    }

    private boolean esManoJugadorPartible() {
        return obtenerJugadorEnJuego().getManoEnJuego().esManoPartible();
    }

    private boolean esManoJugadorPartible(Mano mano) {
        obtenerJugadorEnJuego().setManoEnJuego(mano);
        return esManoJugadorPartible();
    }

    public Jugador evaluarManoGanadora() {
        if (esManoDealerBlackjack()) return obtenerDealer();
        if (esManoJugadorBlackjack()) return obtenerJugadorEnJuego();
        if (sePasoManoDeDealerDe21()) return obtenerJugadorEnJuego();
        if (sePasoManoDeJugadorDe21()) return obtenerDealer();

        return obtenerJugadorEnJuego().getManoEnJuego().calcularSumaDeMano() >
                obtenerDealer().getManoEnJuego().calcularSumaDeMano() ?
                obtenerJugadorEnJuego() : obtenerDealer();
    }

    private Boolean esManoDealerBlackjack() {
        return esManoBlackjack(obtenerDealer().getManoEnJuego());
    }

    private Boolean esManoJugadorBlackjack() {
        return esManoBlackjack(obtenerJugadorEnJuego().getManoEnJuego());
    }

    private Boolean esManoBlackjack(Mano mano) {
        return mano.esBlackjack();
    }

    private Boolean sePasoManoDeDealerDe21() {
        return sePasoManoDe21(obtenerDealer().getManoEnJuego());
    }

    private Boolean sePasoManoDeJugadorDe21() {
        return sePasoManoDe21(obtenerJugadorEnJuego().getManoEnJuego());
    }

    private Boolean sePasoManoDe21(Mano mano) {
        return mano.sePasoDe21();
    }

    private void mostrarGanadorDeRonda() {
        if (evaluarManoGanadora().equals(obtenerJugadorEnJuego())) {
            System.out.printf("¡¡%s ganó esta mano!! :)))\n\n", obtenerJugadorEnJuego().getNombre());
        } else {
            System.out.printf("¡¡%s perdió esta mano!! :(((\n\n", obtenerJugadorEnJuego().getNombre());
        }
    }

    private void pedirCartasADealer() {
        // CPU simple basado en una estrategia simple,
        // pedir cartas mientras que el total de su mano sea menor a 16
        while (obtenerDealer().getManoEnJuego().calcularSumaDeMano() < 16) {
            baraja.pedirCarta(obtenerDealer());
        }
    }

    private void mostrarManoDeDealerConCartaEscondida() {
        System.out.println("La mano del dealer es: ");
        obtenerDealer().getManoEnJuego().mostrarConCartaEscondida();
        mostrarManosDeJugador();
    }

    public Jugador obtenerDealer() {
        return jugadores.stream()
                .filter(Jugador::esDealer)
                .findFirst()
                .orElseThrow();
    }

    public Jugador obtenerJugadorEnJuego() {
        return jugadores.get(jugadorEnJuego);
    }

    private void mostrarManoDeDealer() {
        System.out.println("La mano del dealer es: ");
        obtenerDealer().getManoEnJuego().mostrarMano();
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

    private void partirManoDeJugador() {
        partirManoDeJugador(obtenerJugadorEnJuego().getManos().get(0));
    }

    private void partirManoDeJugador(Mano mano) {
        obtenerJugadorEnJuego().setManoEnJuego(mano);
        int apuestaUnitaria = obtenerJugadorEnJuego().getApuesta()
                / obtenerJugadorEnJuego().getManos().size();
        obtenerJugadorEnJuego().apostar(apuestaUnitaria);
        obtenerJugadorEnJuego().partirMano();
    }

    public void mostrarOpcionesAJugador(List<List<Runnable>> opciones) {
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
        if (obtenerJugadorEnJuego().getManos().size() == 1) {
            agregarOpcionPartirCartaSingular(opciones);
        } else {
            agregarOpcionPartirCartaMultiple(opciones);
        }
    }

    private void agregarOpcionPartirCartaSingular(List<List<Runnable>> opciones) {
        if (esManoJugadorPartible(obtenerJugadorEnJuego().getManoEnJuego())
                && obtenerJugadorEnJuego().getMonto() / obtenerJugadorEnJuego().getApuesta() >= 1) {
            opciones.add(List.of(
                    this::partirManoDeJugador,
                    () -> System.out.println("para partir su mano")));
        }
    }

    private void agregarOpcionPartirCartaMultiple(List<List<Runnable>> opciones) {
        int contador = 0;
        for (Mano mano : obtenerJugadorEnJuego().getManos()) {
            if (esManoJugadorPartible(mano)) {
                String opcion = String.format("para partir su %s° mano", contador + 1);
                opciones.add(List.of(
                        () -> partirManoDeJugador(mano),
                        () -> System.out.println(opcion)));
            }
            contador++;
        }
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
