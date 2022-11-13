public class Controlador {

    public void iniciar() {
        Blackjack blackjack = Blackjack.crearNuevoJuego();

        Jugador jugador1 = Jugador.crearJugador();
        jugador1.agregarAMonto(500);
        jugador1.setNombre("Dumbo");
        blackjack.agregarJugador(jugador1);

        Jugador jugador2 = Jugador.crearJugador();
        jugador2.setNombre("Nicolás");
        jugador2.agregarAMonto(300);
        blackjack.agregarJugador(jugador2);

        blackjack.jugar();
    }
}

