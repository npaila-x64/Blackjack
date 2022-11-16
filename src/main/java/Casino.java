public class Casino {

    public void iniciar() {

        JuegoDeCarta juego = pedirJuego();

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

    private JuegoDeCarta pedirJuego() {
        mostrarJuegos();
        int opcion = Utils.pedirOpcionHasta(3);
        switch (opcion) {
            case 0 -> System.exit(0);
            case 1 -> {return Blackjack.crearNuevoJuego();}
            case 2 -> {return Carioca.crearNuevoJuego();}
            case 3 -> {return Escoba.crearNuevoJuego();}
        }
        throw new IllegalStateException();
    }

    private void mostrarJuegos() {
        System.out.println("¿Que desea jugar?");
        System.out.println("[1] Blackjack");
        System.out.println("[2] Carioca");
        System.out.println("[3] Escoba");
        System.out.println("Para salir escriba [0]");
        System.out.print("> ");
    }
}

