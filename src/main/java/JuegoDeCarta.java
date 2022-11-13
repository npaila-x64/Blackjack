import java.util.List;

public abstract class JuegoDeCarta {

    protected Baraja baraja;
    protected List<Jugador> jugadores;
    protected Integer jugadorEnJuego = -1;
    protected Boolean jugadorEstaEnJuego;

    public abstract void jugar();
    public abstract void agregarJugador(Jugador jugador);

    protected void realizarApuesta() {
        mostrarMontoDeJugador();
        System.out.print("¿Cuanto apuesta " + obtenerJugadorEnJuego().getNombre() + "?\n> ");
        int apuesta = Utils.pedirOpcionHasta(obtenerJugadorEnJuego().getMonto());
        obtenerJugadorEnJuego().apostar(apuesta);
    }

    protected void mostrarMontoDeJugador() {
        System.out.printf("El monto total de %s es $%s\n",
                obtenerJugadorEnJuego().getNombre(), obtenerJugadorEnJuego().getMonto());
    }

    protected void enfocarJugador() {
        jugadorEstaEnJuego = true;
    }

    protected void desenfocarJugador() {
        jugadorEstaEnJuego = false;
    }

    protected Jugador obtenerJugadorEnJuego() {
        return jugadores.get(jugadorEnJuego);
    }

    protected void realizarApuestas() {
        reiniciarConteoDeJugadores();
        while (haySiguienteJugador()) {
            this.siguienteJugador();
            realizarApuesta();
        }
    }

    protected void reiniciarConteoDeJugadores() {
        jugadorEnJuego = -1;
    }

    protected void siguienteJugador() {
        jugadorEnJuego = jugadorEnJuego + 1;
        if (jugadores.get(jugadorEnJuego).esDealer()) siguienteJugador();
    }

    protected boolean haySiguienteJugador() {
        return jugadorEnJuego + 1 < jugadores.size();
    }

    protected void pedirCartaAManoDeJugador() {
        baraja.pedirCarta(obtenerJugadorEnJuego());
    }

    protected void pedirCartaAManoDeJugador(Mano mano) {
        obtenerJugadorEnJuego().setManoEnJuego(mano);
        baraja.pedirCarta(obtenerJugadorEnJuego());
    }
}
