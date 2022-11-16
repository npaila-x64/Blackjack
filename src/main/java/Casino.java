public class Casino {

    public void iniciar() {
        JuegoDeCarta juego = Carioca.crearNuevoJuego();

        System.out.print("¿Que desea jugar?\n> ");

        Jugador dumbo = Jugador.crearJugador();
        dumbo.agregarAMonto(500);
        dumbo.setNombre("Dumbo");
        juego.agregarJugador(dumbo);

        Jugador nicolas = Jugador.crearJugador();
        nicolas.setNombre("Nicolás");
        nicolas.agregarAMonto(300);
        juego.agregarJugador(nicolas);

        juego.jugar();
    }


    private void jugar(JuegoDeCarta juego) {

        if (juego instanceof Blackjack) {

        }

    }

}

