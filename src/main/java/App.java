import java.util.*;

public class App {

    public static void main(String[] args) {
        Juego blackjack = new Juego();
        blackjack.iniciar();
    }

    public static void mostrarDatosDeClases() {
        System.out.println(new Juego().toString());
        System.out.println(new Baraja().toString());
        System.out.println(new Carta().toString());
        System.out.println(new Mano().toString());
        System.out.println(new Jugador(new Mano()).toString());
    }
}
