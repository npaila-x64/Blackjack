public class Controlador {

    public void iniciar() {
        Blackjack blackjack = Blackjack.crearNuevoJuego();
        Jugador jugador1 = Jugador.crearJugador();
        jugador1.agregarAMonto(500);
        jugador1.setNombre("Dumbo");
        Jugador jugador2 = Jugador.crearJugador();
        jugador2.setNombre("Nicolás");
        jugador2.agregarAMonto(300);
//        Jugador jugador3 = Jugador.crearJugador();
//        jugador3.setNombre("Jackson");
        blackjack.agregarJugador(jugador1);
        blackjack.agregarJugador(jugador2);
//        juego.agregarJugador(jugador3);
        blackjack.jugar();
    }
}

