public class Casino {

    public void iniciar() {
        JuegoDeCarta juego = Carioca.crearNuevoJuego();

        Jugador jugador1 = Jugador.crearJugador();
        jugador1.agregarAMonto(500);
        jugador1.setNombre("Dumbo");
        juego.agregarJugador(jugador1);

        Jugador jugador2 = Jugador.crearJugador();
        jugador2.setNombre("Nicolás");
        jugador2.agregarAMonto(300);
        juego.agregarJugador(jugador2);

        juego.jugar();
    }
}

